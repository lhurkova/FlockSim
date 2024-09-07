/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import cz.cuni.mff.hurkovalu.flocksim.descriptors.Descriptor;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Class for storing parameter values for the flocking simulation. Values can be
 * stored and accessed with corresponding {@link Descriptor}.
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class Parameters {
    
    private Map<Descriptor, Object> parameters = new IdentityHashMap<>();   
    
    /**
     * Associates the specified value with the specified descriptor. If the
     * {@link Parameters} previously contained a value for the descriptor, the old
     * value is replaced by the specified value.
     * @param descriptor descriptor of the parameter
     * @param value parameter value
     */
    public void put(Descriptor descriptor, Object value) {
        parameters.put(descriptor, value);
    }
    
    /**
     * Gets a value associated with the specified descriptor as an Integer.
     * @param descriptor descriptor of the parameter
     * @return value as an Integer or null if the there is no value associated
     * with the descriptor or the value is not an Integer
     */
    public Integer getInteger(Descriptor descriptor) {
        Object value = parameters.get(descriptor);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return null;
    }
    
    /**
     * Gets a value associated with the specified descriptor as a String.
     * @param descriptor descriptor of the parameter
     * @return value as a String or null if the there is no value associated
     * with the descriptor or the value is not a String
     */
    public String getString(Descriptor descriptor) {
        Object value = parameters.get(descriptor);
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }
    
    /**
     * Gets a value associated with the specified descriptor as a Boolean.
     * @param descriptor descriptor of the parameter
     * @return value as a Boolean or null if the there is no value associated
     * with the descriptor or the value is not a Boolean
     */
    public Boolean getBoolean(Descriptor descriptor) {
        Object value = parameters.get(descriptor);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return null;
    }
    
}
