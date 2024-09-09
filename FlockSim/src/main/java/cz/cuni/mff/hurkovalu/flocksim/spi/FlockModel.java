/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.spi;

import cz.cuni.mff.hurkovalu.flocksim.Flock;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.Descriptor;
import java.util.List;

/**
 * Interface representing a flock model that can be used for creating instances of 
 * specif {@link Agent} implementation and getting information about the implementation.
 * @author Lucie Hurkova
 */
public interface FlockModel {
    /**
     * Creates a new instance of an {@link Agent} implementation with specified
     * position, velocity vector a and flock in which the agent belongs.
     * @param position initial position of the agent
     * @param velocityVector initial velocity vector of the agent
     * @param flock flock in which the agent belongs
     * @return instance of an {@link Agent} implementation
     */
    public Agent createAgent(Point position, Point velocityVector, Flock flock);
    
    /**
     * Gets a name of the flock model.
     * @return name of the flock model
     */
    public String getName();
    
    /**
     * Gets descriptors that specify flock model parameters or empty list if there
     * are no parameters.
     * @return descriptors that specify flock model parameters
     */
    public List<Descriptor> getDescriptors();
}
