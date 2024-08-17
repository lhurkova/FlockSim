/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class BoidsAgent implements Agent {
    
    private Point position;
    private Point velocityVector;
    private int view = 90;
    private int minDistance = 50;
    private int minVelocity = 20;
    private double maxAngle = Math.PI / 4;
    private Flock flock;
    
    public BoidsAgent(Point position, Point velocityVector, Flock flock) {
        this.position = position;
        this.velocityVector = velocityVector;
        this.flock = flock;
    }
    
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
            cohesion = cohesion.divide(15);
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
                ((10 - 1) * velocitySize + alignmentSize) / 10));
            velocityVector = newVelocityVector;
        }
        position = position.add(velocityVector);
        position = flock.normalizePosition(position);
        return new AgentInfo(position, velocityVector);
    }
    
    private Point averageVector(List<Point> vectors) {
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
    
    private Point alignmentVector(List<Point> neighbourVectors) {
        return averageVector(neighbourVectors);
    }
    
    private Point separationVector(List<Point> neighbours) {
        List<Point> separationVectors = new ArrayList<>();
        for (Point neighbour: neighbours) {
            Point distanceVector = position.getDistanceVector(neighbour);
            double distance = distanceVector.getSize();
            if (distance > 0 && distance < minDistance) {
                Point separationVector = distanceVector.getOppositeVector().changeSizeTo(15);
                separationVectors.add(separationVector);
            }
        }
        return averageVector(separationVectors);
    }
    
    private Point cohesionVector(List<Point> neighbours) {
        Point pointSum = new Point(0, 0, 0);
        for (Point neighbour: neighbours) {
            pointSum = pointSum.add(neighbour);
        }
        Point centroid = pointSum.divide(neighbours.size());
        return position.getDistanceVector(centroid);
    }

    @Override
    public AgentInfo getInfo() {
        return new AgentInfo(position, velocityVector);
    }
}
