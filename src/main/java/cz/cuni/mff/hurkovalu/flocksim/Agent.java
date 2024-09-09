/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

/**
 * Interface representing an agent in agent-based flocking simulation.
 * @author Lucie Hurkova
 */
public interface Agent {
    /**
     * Executes one step of the agent in a simulation.
     * @return information about the new state of the agent
     */
    public AgentInfo doStep();
    /**
     * Returns current information about the agent.
     * @return current information about the agent
     */
    public AgentInfo getInfo();
}
