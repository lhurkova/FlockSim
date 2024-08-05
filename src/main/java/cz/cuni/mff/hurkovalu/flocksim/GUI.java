/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class GUI {

    private JFrame frame;
    private FlockGraphics fGraphics;
    private int sizeX;
    private int sizeY;
    private String name;
    private final int x;

    public GUI(int sizeX, int sizeY, int x, String name) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.x = x;
        this.name = name;
    }

    public void createGUI() {
        frame = new JFrame(name);
        frame.setLocation(x, 0);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fGraphics = new FlockGraphics();
        fGraphics.setPreferredSize(new Dimension(sizeX, sizeY));
        frame.getContentPane().add(fGraphics, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public void paintAgents(List<AgentInfo> agents) {
        fGraphics.setAgents(agents);
        frame.repaint();
    }

}
