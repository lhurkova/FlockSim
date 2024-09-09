/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package cz.cuni.mff.hurkovalu.vflockagent;

/**
 * Class storing the parameters for {@link VFlockAgent}.
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public record AgentSettings(int view, int minDistance, int viewAngle, 
        int agentSize, int cohesion, int separation, int clearView) {
}
