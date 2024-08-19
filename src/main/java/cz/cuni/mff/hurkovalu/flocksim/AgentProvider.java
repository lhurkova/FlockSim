/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public interface AgentProvider {
    public Agent createAgent(Point position, Point velocityVector, Flock flock);
}
