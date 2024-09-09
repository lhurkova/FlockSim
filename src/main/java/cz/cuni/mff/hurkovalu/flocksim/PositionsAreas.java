/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import cz.cuni.mff.hurkovalu.flocksim.spi.AgentInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing agents in areas for quick searching.
 * @author Lucie Hurkova
 */
public class PositionsAreas {
    private AgentList[][] agents;
    
    /**
     * Creates a new {@link PositionsAreas} with specified areas.
     * @param sizeX number of areas horizontally
     * @param sizeY number of areas vertically
     */
    public PositionsAreas(int sizeX, int sizeY) {
        agents = new AgentList[sizeX][sizeY];
    }
    
    /**
     * Gets all agents in specified area
     * @param pos coordinates of the area
     * @return all agents in area
     */
    public List<AgentInfo> getAgentsInArea(Point2D pos) {
        AgentList result = agents[pos.x()][pos.y()];
        if (result != null) {
            return result.getAgents();
        }
        return new ArrayList<>();
    }
   
    /**
     * Puts the agent in specified area.
     * @param pos coordinates of the area
     * @param agent agent to be putted in the area
     */
    public void putAgentInArea(Point2D pos, AgentInfo agent) {
        if (agents[pos.x()][pos.y()] == null) {
            agents[pos.x()][pos.y()] = new AgentList();
        }
        agents[pos.x()][pos.y()].addAgent(agent);
    }
    
    private static class AgentList {
        private List<AgentInfo> agents = new ArrayList<>();
        
        private List<AgentInfo> getAgents() {
            return agents;
        }
        
        private void addAgent(AgentInfo agent) {
            agents.add(agent);
        }
    }
    
}
