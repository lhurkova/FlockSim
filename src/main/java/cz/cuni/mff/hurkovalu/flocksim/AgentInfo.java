/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import java.util.Objects;

/**
 * Class representing a state of the agent in agent-based flocking simulation.
 * Class contains a specified position and velocity vector an agent.
 * @author Lucie Hurkova
 */
public class AgentInfo {
    
    private Point position;
    private Point velocityVector;
    
    /**
     * Creates a new {@link AgentInfo} with specified position and velocity vector.
     * @param position position of an agent
     * @param velocityVector velocity vector of an agent
     */
    public AgentInfo(Point position, Point velocityVector) {
        this.position = position;
        this.velocityVector = velocityVector;
    }
    
    /**
     * Returns a position of an agent.
     * @return position of an agent
     */
    public Point getPosition() {
        return position;
    }
    
    /**
     * Returns a velocity vector of an agent.
     * @return velocity vector of an agent
     */
    public Point getVelocityVector() {
        return velocityVector;
    }

    @Override
    public String toString() {
        return "position:" + position.toString() + "\nvelocity: " + velocityVector.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AgentInfo a) {
            return position.equals(a.position) &&
            velocityVector.equals(a.velocityVector);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.position);
        hash = 97 * hash + Objects.hashCode(this.velocityVector);
        return hash;
    }
    
    
    
    
}
