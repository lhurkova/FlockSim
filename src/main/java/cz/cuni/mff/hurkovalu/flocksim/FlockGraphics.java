/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 * Class for painting the agents in flocking simulation.
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class FlockGraphics extends JComponent {
    
    private List<AgentInfo> agents = new ArrayList<>();
    private static final int[] SIZES = new int[] {6, 10, 14};
    private int currSize;
    private Color[] colors;
    private Color currColor;
    
    /**
     * Creates a new {@link FlockGraphics}. 
     */
    public FlockGraphics() {
        colors = new Color[] {new Color(0,0,128), new Color(128, 0, 0), new Color(0, 128, 0)};
        currColor = colors[0];
        currSize = SIZES[1];
    }
    
    /**
     * Sets agents that are going to be painted during the next repaint.
     * @param agents agents to be painted
     */
    public void setAgents(List<AgentInfo> agents) {
        this.agents = agents;
    }
    
    /**
     * Sets a color of the agents.
     * @param color color of the agents
     */
    public void setColor(AgentColor color) {
        currColor = colors[color.ordinal()];
    }
    
    /**
     * Sets a size of the agents.
     * @param size size of the agents
     */
    public void setSize(AgentSize size) {
        currSize = SIZES[size.ordinal()];
    }
    
    private void paintAgent(AgentInfo agent, Graphics g) {
        Point position = agent.getPosition();
        Point velocityVector = agent.getVelocityVector().get2DPoint();
        Point orthogonalVector = velocityVector.getOrtogonalVector().changeSizeTo(currSize);
        
        Point a = position.add(orthogonalVector);
        Point b = position.subtract(orthogonalVector);
        Point c = position.add(velocityVector.changeSizeTo(currSize + 1.5 * currSize));
        int[] xs = new int[] {(int)Math.round(a.getX()),
            (int)Math.round(b.getX()), (int)Math.round(c.getX())};
        int[] ys = new int[] {(int)Math.round(a.getY()),
            (int)Math.round(b.getY()), (int)Math.round(c.getY())};
        
        g.fillPolygon(xs, ys, 3);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(currColor);
        for (AgentInfo agent: agents) {
            paintAgent(agent, g);
        }
    }
    
    /**
     * Enum containing possible colors of the agents.
     */
    public enum AgentColor {
        BLUE,
        RED,
        GREEN
    }
    
    /**
     * Enum containing possible sizes of the agents.
     */
    public enum AgentSize {
        SMALL,
        MEDIUM,
        BIG
    }
    
    
}
