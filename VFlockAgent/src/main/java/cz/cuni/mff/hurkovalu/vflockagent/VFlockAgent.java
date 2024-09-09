/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cz.cuni.mff.hurkovalu.vflockagent;

import cz.cuni.mff.hurkovalu.flocksim.spi.Agent;
import cz.cuni.mff.hurkovalu.flocksim.spi.AgentInfo;
import cz.cuni.mff.hurkovalu.flocksim.Flock;
import cz.cuni.mff.hurkovalu.flocksim.spi.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an agent in agent-based flocking simulation. BoidsAgent implements
 * {@link Agent} interface and its flocking ability is inspired by V-shaped flocks
 * that are often created by geese. In each step {@link VFlockAgent} is trying to get
 * closer to its closest neighbour in the flock and at the same time it is searching for
 * a position with a clear view in front of it.
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class VFlockAgent implements Agent {
    
    private Point position;
    private Point velocityVector;
    private Flock flock;
    private int view = 100;
    private int minDistance = 20;
    private double viewAngle = Math.PI;
    private double agentSize = 18;
    private int cohesionDivider = 10;
    private int maxSeparationSize = 10;
    private int clearViewDivider = 10;
    private int speed = 20;
    private double overlap = 1;
    
    /**
     * Creates a new {@link VFlockAgent} with given position, velocity vector,
     * agent settings and flock in which it belongs.
     * @param position position of the agent
     * @param velocityVector velocity vector of the agent
     * @param flock flock in which the agent belongs
     * @param settings agent settings containing parameters for the agent 
     */
    public VFlockAgent(Point position, Point velocityVector, Flock flock, AgentSettings settings) {
        this.position = position;
        this.velocityVector = velocityVector;
        this.flock = flock;
        view = settings.view();
        minDistance = settings.minDistance();
        viewAngle = settings.viewAngle()/(double)180 * Math.PI;
        agentSize = settings.agentSize();
        cohesionDivider = settings.cohesion();
        maxSeparationSize = settings.separation();
        clearViewDivider = settings.clearView();
    }

    /**
     * Computes one step of the agent based on its surroundings in the flock.
     * @return new info about the agent
     */
    @Override
    public AgentInfo doStep() {
        List<AgentInfo> potentialNeighbours = flock.getNeighbours(position, view);
        if (potentialNeighbours.size() > 1) {
            List<AgentInfo> neighbours = new ArrayList<>();
            double bestDistance = view + 1;
            AgentInfo mainAgent = null;
            for (AgentInfo a: potentialNeighbours) {
                Point distanceVector = position.getDistanceVector(a.getPosition());
                double angle = velocityVector.getOrientedAngle(distanceVector);
                if (Math.abs(angle) <= viewAngle/2) {
                    double distance = distanceVector.getSize();
                    if (distance > 1e-5) {
                        neighbours.add(a);
                        if (distance < bestDistance) {
                            mainAgent = a;
                            bestDistance = distance;
                        }
                    }
                }
            }
            if (mainAgent != null) {
                Point cohesionVector = position.getDistanceVector(mainAgent.getPosition());
                Point alignmentVector = mainAgent.getVelocityVector();
                List<Point> positions = neighbours.stream().map(a -> a.getPosition()).toList();
                Point separationVector = separationVector(positions);
                Point clearViewVector;
                if (cohesionVector.getSize() > 2*view/3) {
                    cohesionVector = cohesionVector.divide(cohesionDivider);
                    clearViewVector = new Point(0, 0, 0);
                } else {
                    cohesionVector = new Point(0, 0, 0);
                    clearViewVector = clearViewVector(positions);
                    clearViewVector = clearViewVector.divide(clearViewDivider);
                }
                velocityVector = velocityVector.add(cohesionVector)
                                                .add(alignmentVector)
                                                .add(separationVector)
                                                .add(clearViewVector);
                velocityVector = velocityVector.changeSizeTo(speed);
            }
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
     * Computes an vector used for separation force.
     * @param neighbours positions of the neighbours of this agent
     * @return separation vector
     */
    Point separationVector(List<Point> neighbours) {
        List<Point> separationVectors = new ArrayList<>();
        for (Point neighbour: neighbours) {
            Point distanceVector = position.getDistanceVector(neighbour);
            double distance = distanceVector.getSize();
            if (distance > 1e-5 && distance < minDistance) {
                double size = (1 - distance/minDistance) * maxSeparationSize;
                Point separationVector 
                    = distanceVector.getOppositeVector().changeSizeTo(size);
                separationVectors.add(separationVector);
            }
        }
        return averageVector(separationVectors);
    } 
    
    private Point clearViewVector(List<Point> positions) {
        Point clearViewVector = new Point(0, 0, 0);
        Point referenceVector = new Point(0, 1, 0);
        double turningAngle = velocityVector.getOrientedAngle(referenceVector);
        List<Point> sortedCorrectedPositions = positions.stream()
                .map(p -> p.rotatePoint(position, turningAngle))
                .sorted((p, q) -> Double.compare(p.getX(), q.getX())).toList();
        
        List<Gap> gaps = new ArrayList<>();
        Gap bestGap = null;
        double bestDistance = view + 10;
        double prevX = position.getX() - view;
        for (Point pos: sortedCorrectedPositions) {
            double currX = pos.getX();
            if (currX-agentSize/2 > prevX) {
                Gap currGap = new Gap(prevX, currX-agentSize/2);
                gaps.add(currGap);
                if (currGap.getSize() > agentSize + 1) {
                    double dis = Math.min(Math.abs(currX - position.getX()),
                            Math.abs(prevX - position.getX()));
                    if (dis < bestDistance) {
                        bestGap = currGap;
                        bestDistance = dis;
                    }
                }
            }
            prevX = currX + agentSize/2;
        }
        
        double lastX = sortedCorrectedPositions.getLast().getX() + agentSize/2;
        double viewEnd = position.getX() + view;
        if (lastX < viewEnd) {
            Gap lastGap = new Gap(lastX, viewEnd);
            gaps.add(lastGap);
            if (lastGap.getSize() > agentSize + 1) {
                double dis = Math.min(Math.abs(lastX - position.getX()),
                        Math.abs(viewEnd - position.getX()));
                if (dis < bestDistance) {
                    bestGap = lastGap;
                    bestDistance = dis;
                }
            }
        }
        
        if (bestGap != null) {
            double xCoordinate;
            if (Math.abs(bestGap.start() - position.getX())
                    < Math.abs(bestGap.end() - position.getX())) {
                xCoordinate = bestGap.start() + 1;
            } else {
                xCoordinate = bestGap.end() - 1;
            }
            Point viewVector = position
                .getDistanceVector(new Point(xCoordinate, position.getY(), 0));
            clearViewVector = viewVector.getVectorTurnedBy(-turningAngle);
        }
        Point minUpwashVector = minUpwashVector(sortedCorrectedPositions);
        minUpwashVector = minUpwashVector.getVectorTurnedBy(-turningAngle);
        clearViewVector = clearViewVector.add(minUpwashVector);
        return clearViewVector;
    }
    
    private Point minUpwashVector(List<Point> positions) {
        List<Point> agentsInView = new ArrayList<>();
        double start = position.getX() - agentSize - 2;
        double end = position.getX() + agentSize + 2;
        for (Point p: positions) {
            if (p.getX() > start && p.getX() < end) {
                agentsInView.add(p);
            }
        }
        if (agentsInView.size() == 1) {
            double xCoordinate = agentsInView.getFirst().getX();
            Point distanceVector = position
                .getDistanceVector(new Point(xCoordinate, position.getY(), 0));
            double distance = distanceVector.getSize();
            if (distance > agentSize - overlap) {
                distanceVector = distanceVector.changeSizeTo(distance - agentSize + overlap);
            } else {
                distanceVector = distanceVector.changeSizeTo(agentSize - overlap - distance);
                distanceVector = distanceVector.getOppositeVector();
            }
            return distanceVector;
        }
        return new Point(0, 0, 0);
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
