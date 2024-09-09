/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cz.cuni.mff.hurkovalu.flocksim;

import javax.swing.SwingUtilities;

/**
 * Main class of the FlockSim. Creates the graphical user interface.
 * @author Lucie Hurkova
 */
public class FlockSim {

    /**
     * Executes the FlockSim.
     * @param args program arguments
     */
    public static void main(String[] args) {
        GUI g = new GUI(1000, 700, "FlockSim");
        SwingUtilities.invokeLater(() -> g.createGUI());
    }
}
