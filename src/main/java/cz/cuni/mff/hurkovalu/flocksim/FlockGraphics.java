/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 * Class for painting the agents in flocking simulation.
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class FlockGraphics extends JComponent {
    
    private List<AgentInfo> agents = new ArrayList<>();
    private int currSize;
    private Color currColor;
    
    /**
     * Creates a new {@link FlockGraphics}. 
     */
    public FlockGraphics() {
        currColor = AgentColor.BLUE.getColor();
        currSize = AgentSize.MEDIUM.getSize();
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
        currColor = color.getColor();
    }
    
    /**
     * Sets a size of the agents.
     * @param size size of the agents
     */
    public void setSize(AgentSize size) {
        currSize = size.getSize();
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
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON); 
        for (AgentInfo agent: agents) {
            paintAgent(agent, g2D);
        }
    }
        
    /**
     * Enum containing possible colors of the agents.
     */
    public enum AgentColor {
        BLUE("blue", new Color(0,0,128)),
        RED("red", new Color(128,0,0)),
        GREEN("green", new Color(0,128,0));
        
        private final String displayName;
        private final Color color;

        private AgentColor(String displayName, Color color) {
            this.displayName = displayName;
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
        
        public static AgentColor valueForName(String name) {
            for (AgentColor a: values()) {
                if (a.displayName.equals(name)) return a;
            }
            throw new IllegalArgumentException(name);
        }
        
        public static String[] displayNames() {
            List<String> names = new ArrayList<>();
            for (AgentColor a: values()) {
                names.add(a.displayName);
            }
            return names.toArray(String[]::new);
        }
        
    }
    
    /**
     * Enum containing possible sizes of the agents.
     */
    public enum AgentSize {
        SMALL("small", 6),
        MEDIUM("medium", 8),
        BIG("big", 10);
        
        private String displayName;
        private int size;

        private AgentSize(String displayName, int size) {
            this.displayName = displayName;
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public static AgentSize valueForName(String name) {
            for (AgentSize a: values()) {
                if (a.displayName.equals(name)) return a;
            }
            throw new IllegalArgumentException(name);
        }
        
        public static String[] displayNames() {
            List<String> names = new ArrayList<>();
            for (AgentSize a: values()) {
                names.add(a.displayName);
            }
            return names.toArray(String[]::new);
        }
    }
    
}
