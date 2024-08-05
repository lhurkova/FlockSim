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
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class FlockGraphics extends JComponent {
    
    private List<AgentInfo> agents = new ArrayList<>();
    private static final int SIZE = 10;
    
    public void setAgents(List<AgentInfo> agents) {
        this.agents = agents;
    }
    
    private void paintAgent(AgentInfo agent, Graphics g) {
        Point position = agent.getPosition();
        Point velocityVector = agent.getVelocityVector().get2DPoint();
        Point orthogonalVector = velocityVector.getOrtogonalVector().changeSizeTo(SIZE);
        
        Point a = position.add(orthogonalVector);
        Point b = position.subtract(orthogonalVector);
        Point c = position.add(velocityVector.changeSizeTo(SIZE+15));
        int[] xs = new int[] {(int)Math.round(a.getX()),
            (int)Math.round(b.getX()), (int)Math.round(c.getX())};
        int[] ys = new int[] {(int)Math.round(a.getY()),
            (int)Math.round(b.getY()), (int)Math.round(c.getY())};
        
        g.fillPolygon(xs, ys, 3);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (AgentInfo agent: agents) {
            g.setColor(Color.red);
            paintAgent(agent, g);
//            Point position = agent.getPosition();
//            g.fillOval((int) Math.round(position.getX()), (int) Math.round(position.getY()), 20, 20);
        }
    }
    
    
}
