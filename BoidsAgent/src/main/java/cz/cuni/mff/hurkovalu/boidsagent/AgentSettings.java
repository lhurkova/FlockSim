/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.boidsagent;

/**
 * Class containing parameters for {@link BoidsAgent}.
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public record AgentSettings(int view, int minDistance,
        int maxAngle, int cohesion, int separation, int alignment){
    
}