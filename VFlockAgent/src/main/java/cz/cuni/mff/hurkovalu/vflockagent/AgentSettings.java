/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package cz.cuni.mff.hurkovalu.vflockagent;

/**
 * Class storing the parameters for {@link VFlockAgent}.
 * @param view view of the agent
 * @param minDistance minimum distance between two birds
 * @param viewAngle field of view in degrees
 * @param agentSize wingspan of the agent
 * @param cohesion cohesion force
 * @param separation separation force
 * @param clearView clear view force
 * @author Lucie Hurkova
 */
public record AgentSettings(int view, int minDistance, int viewAngle, 
        int agentSize, int cohesion, int separation, int clearView) {
}
