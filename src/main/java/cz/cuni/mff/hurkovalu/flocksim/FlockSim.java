/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cz.cuni.mff.hurkovalu.flocksim;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class FlockSim {

    public static void main(String[] args) {
//        int winHeight = 700;
//        int winWidth = 1000;
//        
//        try {
//        URL jarPath = Path.of(args[0]).toUri().toURL();
//        
//        URLClassLoader clsLoader = new URLClassLoader(new URL[]{jarPath});
//        
//        ServiceLoader<AgentProvider> sv = ServiceLoader.load(AgentProvider.class, clsLoader);
//        AgentProvider agentProvider = sv.findFirst().get();
//        
//        Flock f = new Flock(winHeight, winWidth, 20, agentProvider);
//        GUI g = new GUI(winWidth, winHeight, 20, "FlockSim");
//        try {
//            SwingUtilities.invokeAndWait(() -> g.createGUI());
//            for (int i = 0; i < 300; i++) {
//                System.out.println("Step: "+i);
//                List<AgentInfo> agents = f.doStep();
////                for (AgentInfo agent: agents) {
////                    System.out.println(agent);
////                }
//                System.out.println();
//                g.paintAgents(agents);
//                Thread.sleep(300);
//            }
//        } catch (InterruptedException | InvocationTargetException ex) {
//            Logger.getLogger(FlockSim.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(FlockSim.class.getName()).log(Level.SEVERE, null, ex);
//        }

        GUI g = new GUI(1000, 700, 20, "FlockSim");
        SwingUtilities.invokeLater(() -> g.createGUI());
    }
}
