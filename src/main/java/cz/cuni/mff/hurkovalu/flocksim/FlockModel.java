/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import cz.cuni.mff.hurkovalu.flocksim.descriptors.Descriptor;
import java.util.List;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public interface FlockModel {
    public Agent createAgent(Point position, Point velocityVector, Flock flock);
    public String getName();
    public List<Descriptor> getDescriptors();
}
