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
    private static final Random R = new Random();
    
    public Flock(int winHeight, int winWidth, int flockMembersCount) {
        this.winHeight = winHeight;
        this.winWidth = winWidth;
        for (int i = 0; i < flockMembersCount; i++) {
            int x = R.nextInt(winWidth);
            int y = R.nextInt(winHeight);
            Point position = new Point(x, y, 0);
            
            int vX = R.nextInt(10, 20);
            int vY = R.nextInt(10, 20);
            if (R.nextBoolean()) {
                vX = -vX;
            }
            if (R.nextBoolean()) {
                vY = -vY;
            }
            
            Point vector = new Point(vX, vY, 0);
            flockMembers.add(new BoidsAgent(position, vector, this));
        }
    }
    
    
    public List<AgentInfo> getNeighbours(Point position, int distance) {
        List<AgentInfo> neighbours = new ArrayList<>();
        for (Agent agent: flockMembers) {
            Point agentPosition = agent.getInfo().getPosition();
            if (position.getDistanceVector(agentPosition).getSize() <= distance) {
                neighbours.add(agent.getInfo());
            }
        }
        return neighbours;
    }
    
    public List<AgentInfo> doStep() {
        List<AgentInfo> infos = new ArrayList<>();
        for (Agent agent: flockMembers) {
            infos.add(agent.doStep());
        }
        return infos;
    }
    
    public Point normalizePosition(Point position) {
        double newX = (position.getX() + winWidth)%winWidth;
        double newY = (position.getY() + winHeight)%winHeight;
        return new Point(newX, newY, 0);
    }
    
}
