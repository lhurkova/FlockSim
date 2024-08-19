/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
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
    
    public Flock(int winHeight, int winWidth, int flockMembersCount, AgentProvider agentProvider) {
        this.winHeight = winHeight;
        this.winWidth = winWidth;
        
        areaHeight = winHeight / SIZE_OF_AREA;
        areaWidth = winWidth / SIZE_OF_AREA;
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
            
            Agent agent = agentProvider.createAgent(position, vector, this);
            flockMembers.add(agent);
            
            Point2D areaPosition = getAreaCoord(position);
            oldPositionsAreas.putAgentInArea(areaPosition, agent.getInfo());
        }
    }
    
    private Point2D normalizeAreaCoord(Point2D position) {
        int x = ((position.x() % areaWidth) + areaWidth) % areaWidth;
        int y = ((position.y() % areaHeight) + areaHeight) % areaHeight;
        return new Point2D(x, y);
    }
    
    public List<AgentInfo> getNeighbours(Point position, int view) {
        List<AgentInfo> neighbours = new ArrayList<>();
        int areaRange = Math.ceilDiv(view, SIZE_OF_AREA);
        int minX = 0;
        int minY = 0;
        int maxX = areaWidth - 1;
        int maxY = areaHeight - 1;
        Point2D areaPosition = getAreaCoord(position);
        if (2*view < winWidth) {
            minX = areaPosition.x() - areaRange;
            maxX = areaPosition.x() + areaRange;
        }
        if (2*view < winHeight) {
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
                    double distance = position.getDistanceVector(correctedPosition).getSize();
                    if (distance <= view) {
                        neighbours.add(new AgentInfo(correctedPosition, agent.getVelocityVector()));
                    }
                }
                
            }
        }
        return neighbours;
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
    
//    public List<AgentInfo> getNeighbours(Point position, int distance) {
//        List<AgentInfo> neighbours = new ArrayList<>();
//        for (Agent agent: flockMembers) {
//            Point agentPosition = agent.getInfo().getPosition();
//            if (position.getDistanceVector(agentPosition).getSize() <= distance) {
//                neighbours.add(agent.getInfo());
//            }
//        }
//        return neighbours;
//    }
    
    public List<AgentInfo> doStep() {
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
    
    public Point normalizePosition(Point position) {
        double newX = (position.getX() + winWidth)%winWidth;
        double newY = (position.getY() + winHeight)%winHeight;
        return new Point(newX, newY, 0);
    }
    
    private static Point2D getAreaCoord(Point position) {
        return new Point2D((int)position.getX() / SIZE_OF_AREA,
        (int)position.getY() / SIZE_OF_AREA);    
    }
    
}
