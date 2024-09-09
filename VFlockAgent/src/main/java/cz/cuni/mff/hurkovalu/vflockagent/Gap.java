/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.vflockagent;

/**
 * Class representing a gap in space between two agents.
 * @param start start of the gap
 * @param end end of the gap
 * @author Lucie Hurkova
 */
public record Gap(double start, double end) {
    
    /**
     * Gets the size of the gap.
     * @return size of the gap
     */
    public double getSize() {
        return end - start;
    }
    
}
