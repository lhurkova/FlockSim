/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import java.util.Objects;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class AgentInfo {
    
    private Point position;
    private Point velocityVector;
    
    public AgentInfo(Point position, Point velocityVector) {
        this.position = position;
        this.velocityVector = velocityVector;
    }
    
    public Point getPosition() {
        return position;
    }
    
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
