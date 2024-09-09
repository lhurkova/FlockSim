/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.boidsagent;

import cz.cuni.mff.hurkovalu.flocksim.spi.Agent;
import cz.cuni.mff.hurkovalu.flocksim.spi.AgentInfo;
import cz.cuni.mff.hurkovalu.flocksim.Flock;
import cz.cuni.mff.hurkovalu.flocksim.spi.Point;
import java.util.ArrayList;
import java.util.List;


/**
 * Class representing an agent in agent-based flocking simulation. BoidsAgent implements
 * {@link Agent} interface and is inspired by BOIDS model. Its flocking ability
 * is based on three main rules: agent is trying to get closer to the center of the flock;
 * agent is aligning its direction with surrounding agents; if an agent is to close
 * to another agent, it is trying to put more distance between them.
 * @author Lucie Hurkova
 */
public class BoidsAgent implements Agent {
    
    private Point position;
    private Point velocityVector;
    private int view = 90;
    private int minDistance = 50;
    private int minVelocity = 20;
    private int separationSize = 15;
    private int cohesionDivider = 15;
    private int alignmentDivider = 10;
    private double maxAngle = Math.PI / 4;
    private final Flock flock;
    
    /**
     * Creates a new BoidsAgent with specified position, velocity vector, agent settings
     * and flock in which the agent belongs.
     * @param position position of the agent
     * @param velocityVector velocity vector of the agent
     * @param flock flock in which the agent belongs
     * @param settings agent settings containing agent parameters
     */
    public BoidsAgent(Point position, Point velocityVector, Flock flock, AgentSettings settings) {
        this.position = position;
        this.velocityVector = velocityVector;
        this.flock = flock;
        view = settings.view();
        minDistance = settings.minDistance();
        maxAngle = (double) settings.maxAngle() / 180 * Math.PI;
        cohesionDivider = settings.cohesion();
        separationSize = settings.separation();
        alignmentDivider = settings.alignment();
    }
    
    /**
     * Computes one step of the agent based on its surroundings in the flock.
     * @return new info about the agent
     */
    @Override
    public AgentInfo doStep() {
        List<AgentInfo> neighbours = flock.getNeighbours(position, view);
        if (neighbours.size() > 1) {
            List<Point> positions = new ArrayList<>();
            List<Point> velocityVectors = new ArrayList<>();
            for (AgentInfo neighbour: neighbours) {
                positions.add(neighbour.getPosition());
                velocityVectors.add(neighbour.getVelocityVector());
            }
            Point cohesion = cohesionVector(positions);
            cohesion = cohesion.divide(cohesionDivider);
            Point alignment = alignmentVector(velocityVectors);
            Point separation = separationVector(positions);
            
            double velocitySize = velocityVector.getSize();
            double alignmentSize = alignment.getSize();
            
            Point newVelocityVector = velocityVector.add(cohesion).add(alignment).add(separation);
            double orientedAngle = velocityVector.getOrientedAngle(newVelocityVector);
            if (Math.abs(orientedAngle) > maxAngle) {
                if (orientedAngle > 0) {
                    newVelocityVector = velocityVector.getVectorTurnedBy(maxAngle);
                } else {
                    newVelocityVector = velocityVector.getVectorTurnedBy(-maxAngle);
                }
            }
            newVelocityVector = newVelocityVector.changeSizeTo(Math.max(minVelocity,
                ((alignmentDivider - 1) * velocitySize + alignmentSize) / alignmentDivider));
            velocityVector = newVelocityVector;
        }
        position = position.add(velocityVector);
        position = flock.normalizePosition(position);
        return new AgentInfo(position, velocityVector);
    }
    
    /**
     * Computes an average vector from given vectors. 
     * @param vectors vectors
     * @return average vector from given vectors
     */
    Point averageVector(List<Point> vectors) {
        Point vectorSum = new Point(0, 0, 0);
        int count = 0;
        for (Point vector: vectors) {
            if (vector.getSquaredSize() > 0) {
                vectorSum = vectorSum.add(vector);
                count++;
            }
        }
        if (count != 0) return vectorSum.divide(count);
        return vectorSum;
    }
    
    /**
     * Computes an vector used for alignment force.
     * @param neighbourVectors velocity vectors of the neighbours of this agent
     * @return alignment vector
     */
    Point alignmentVector(List<Point> neighbourVectors) {
        return averageVector(neighbourVectors);
    }
    
    /**
     * Computes an vector used for separation force.
     * @param neighbours positions of the neighbours of this agent
     * @return separation vector
     */
    Point separationVector(List<Point> neighbours) {
        List<Point> separationVectors = new ArrayList<>();
        for (Point neighbour: neighbours) {
            Point distanceVector = position.getDistanceVector(neighbour);
            double distance = distanceVector.getSize();
            if (distance > 0 && distance < minDistance) {
                Point separationVector 
                    = distanceVector.getOppositeVector().changeSizeTo(separationSize);
                separationVectors.add(separationVector);
            }
        }
        return averageVector(separationVectors);
    }
    
    /**
     * Computes an vector used for cohesion force.
     * @param neighbours positions of the neighbours of this agent
     * @return cohesion vector
     */
    Point cohesionVector(List<Point> neighbours) {
        Point pointSum = new Point(0, 0, 0);
        for (Point neighbour: neighbours) {
            pointSum = pointSum.add(neighbour);
        }
        Point centroid = pointSum.divide(neighbours.size());
        return position.getDistanceVector(centroid);
    }

    /**
     * Gets current information about the agent.
     * @return current information about the agent
     */
    @Override
    public AgentInfo getInfo() {
        return new AgentInfo(position, velocityVector);
    }
}
