/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cz.cuni.mff.hurkovalu.flocksim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class FlockSim {

    public static void main(String[] args) {
        int winHeight = 700;
        int winWidth = 1000;
        Flock f = new Flock(winHeight, winWidth, 20);
        GUI g = new GUI(winWidth, winHeight, 20, "FlockSim");
        try {
            SwingUtilities.invokeAndWait(() -> g.createGUI());
            for (int i = 0; i < 300; i++) {
                System.out.println("Step: "+i);
                List<AgentInfo> agents = f.doStep();
//                for (AgentInfo agent: agents) {
//                    System.out.println(agent);
//                }
                System.out.println();
                g.paintAgents(agents);
                Thread.sleep(300);
            }
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(FlockSim.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
