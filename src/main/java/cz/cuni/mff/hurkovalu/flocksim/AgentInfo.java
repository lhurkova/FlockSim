/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

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
    
    
}
