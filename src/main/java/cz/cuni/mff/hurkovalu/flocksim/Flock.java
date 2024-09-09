/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import cz.cuni.mff.hurkovalu.flocksim.spi.Agent;
import cz.cuni.mff.hurkovalu.flocksim.spi.AgentInfo;
import cz.cuni.mff.hurkovalu.flocksim.spi.Parameters;
import cz.cuni.mff.hurkovalu.flocksim.spi.FlockModel;
import cz.cuni.mff.hurkovalu.flocksim.spi.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing a flock in agent-based flocking simulation. Flock exists in
 * an area of two dimensional space of specified size and contains a specified number
 * of flock members - {@link Agent}s.
 * @author Lucie Hurkova
 */
public class Flock {
    
    private List<Agent> flockMembers = new ArrayList<>();
    private int winHeight;
    private int winWidth;
    private int areaHeight;
    private int areaWidth;
    private static final Random RND = new Random();
    private static final int SIZE_OF_AREA = 10;
    private PositionsAreas positionsAreas;
    private PositionsAreas oldPositionsAreas;
    private final Parameters params;
    private final FlockModel flockModel;
    
    /**
     * Creates a new {@link Flock} of specified size, containing specified number of 
     * flock members. The type of agent is determined by given {@link FlockModel}.
     * @param winHeight height of the simulation window
     * @param winWidth width of the simulation window
     * @param flockMembersCount number of flock members
     * @param flockModel flock model that determines the implementation of the agents
     * @param params simulation parameters
     */
    Flock(int winHeight, int winWidth, int flockMembersCount, FlockModel flockModel, Parameters params) {
        this.winHeight = winHeight;
        this.winWidth = winWidth;
        this.params = params;
        this.flockModel = flockModel;
        
        areaHeight = Math.ceilDiv(winHeight, SIZE_OF_AREA);
        areaWidth = Math.ceilDiv(winWidth, SIZE_OF_AREA);
        positionsAreas = new PositionsAreas(areaWidth, areaHeight);
        oldPositionsAreas = new PositionsAreas(areaWidth, areaHeight);
                
        for (int i = 0; i < flockMembersCount; i++) {
            int x = RND.nextInt(winWidth);
            int y = RND.nextInt(winHeight);
            Point position = new Point(x, y, 0);
            
            int vX = RND.nextInt(10, 20);
            int vY = RND.nextInt(10, 20);
            if (RND.nextBoolean()) {
                vX = -vX;
            }
            if (RND.nextBoolean()) {
                vY = -vY;
            }
            Point vector = new Point(vX, vY, 0);
            
            Agent agent = flockModel.createAgent(position, vector, this);
            flockMembers.add(agent);
            
            Point2D areaPosition = getAreaCoord(position);
            oldPositionsAreas.putAgentInArea(areaPosition, agent.getInfo());
        }
    }
    
    /**
     * Gets flock model that this {@link Flock} is using.
     * @return used flock model
     */
    FlockModel getFlockModel() {
        return flockModel;
    }
    
    private Point2D normalizeAreaCoord(Point2D position) {
        int x = ((position.x() % areaWidth) + areaWidth) % areaWidth;
        int y = ((position.y() % areaHeight) + areaHeight) % areaHeight;
        return new Point2D(x, y);
    }
    
    /**
     * Gets all flock members that are located in the area specified by center
     * and radius.
     * @param center center of the area
     * @param radius radius of the area
     * @return AgentInfos of the flock members in the area
     */
    public List<AgentInfo> getNeighbours(Point center, int radius) {
        List<AgentInfo> neighbours = new ArrayList<>();
        int areaRange = Math.ceilDiv(radius, SIZE_OF_AREA);
        int minX = 0;
        int minY = 0;
        int maxX = areaWidth - 1;
        int maxY = areaHeight - 1;
        Point2D areaPosition = getAreaCoord(center);
        if (2*radius < winWidth) {
            minX = areaPosition.x() - areaRange;
            maxX = areaPosition.x() + areaRange;
        }
        if (2*radius < winHeight) {
            minY = areaPosition.y() - areaRange;
            maxY = areaPosition.y() + areaRange;
        }
        
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Point2D currArea = new Point2D(x, y);
                List<AgentInfo> potentialNeighnbours = 
                    oldPositionsAreas.getAgentsInArea(normalizeAreaCoord(currArea));
                for (AgentInfo agent: potentialNeighnbours) {
                    Point correctedPosition = correctNeighbourPosition(currArea, agent.getPosition());
                    double distance = center.getDistanceVector(correctedPosition).getSize();
                    if (distance <= radius) {
                        neighbours.add(new AgentInfo(correctedPosition, agent.getVelocityVector()));
                    }
                }
                
            }
        }
        return neighbours;
    }

    /**
     * Gets the simulation parameters.
     * @return simulation parameters
     */
    public Parameters getParams() {
        return params;
    }
    
    private Point correctNeighbourPosition(Point2D area, Point position) {
        if (area.x() < 0) {
            position = position.add(new Point(-winWidth, 0, 0));
        } else if (area.x() >= areaWidth) {
            position = position.add(new Point(winWidth, 0, 0));
        }
        if (area.y() < 0) {
            position = position.add(new Point(0, -winHeight, 0));
        } else if (area.y() >= areaHeight) {
            position = position.add(new Point(0, winHeight, 0));
        }
        return position;
    }
    
    /**
     * Executes one step of the flocking simulation.
     * @return new information about all flock members
     */
    List<AgentInfo> doStep() {
        List<AgentInfo> infos = new ArrayList<>();
        for (Agent agent: flockMembers) {
            AgentInfo info = agent.doStep();
            infos.add(info);
            Point2D areaPosition = getAreaCoord(info.getPosition());
            positionsAreas.putAgentInArea(areaPosition, info);
        }
        oldPositionsAreas = positionsAreas;
        positionsAreas = new PositionsAreas(areaWidth, areaHeight);
        return infos;
    }
    
    /**
     * Returns valid position inside the simulation window
     * @param position position to be normalized
     * @return normalized position
     */
    public Point normalizePosition(Point position) {
        double newX = (position.getX() + winWidth)%winWidth;
        double newY = (position.getY() + winHeight)%winHeight;
        return new Point(newX, newY, 0);
    }
    
    /**
     * Gets current information about all flock members.
     * @return current information about all flock members
     */
    List<AgentInfo> getAgentInfos() {
        List<AgentInfo> infos = new ArrayList<>();
        for (Agent a: flockMembers) {
            infos.add(a.getInfo());
        }
        return infos;
    }
    
    private static Point2D getAreaCoord(Point position) {
        return new Point2D((int)position.getX() / SIZE_OF_AREA,
        (int)position.getY() / SIZE_OF_AREA);    
    }
    
}
