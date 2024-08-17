/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class PositionsAreas {
    private AgentList[][] agents;
    
    public PositionsAreas(int x, int y) {
        agents = new AgentList[x][y];
    }
    
    public List<AgentInfo> getAgentsInArea(Point2D pos) {
        AgentList result = agents[pos.x()][pos.y()];
        if (result != null) {
            return result.getAgents();
        }
        return new ArrayList<>();
    }
    
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
