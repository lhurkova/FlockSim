/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.boidsagent;

/**
 * Class containing parameters for {@link BoidsAgent}.
 * @param view view of the agent
 * @param minDistance minimum distance between two birds
 * @param maxAngle maximum turning angle of the agent
 * @param cohesion cohesion force
 * @param separation separation force
 * @param alignment alignment force
 * @author Lucie Hurkova
 */
public record AgentSettings(int view, int minDistance,
        int maxAngle, int cohesion, int separation, int alignment){
    
}