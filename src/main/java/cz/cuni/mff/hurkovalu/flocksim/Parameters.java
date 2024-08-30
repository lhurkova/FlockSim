/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import cz.cuni.mff.hurkovalu.flocksim.descriptors.Descriptor;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class Parameters {
    
    private Map<Descriptor, Object> parameters = new IdentityHashMap<>();
    
    public Parameters(Descriptor[] descriptors) {
        for (Descriptor d: descriptors) {
            parameters.put(d, null);
        }
    }
    
    public void add(Descriptor descriptor, Object value) {
        parameters.put(descriptor, value);
    }
    
    public void set(Descriptor descriptor, Object value) {
        if (parameters.containsKey(descriptor)) {
            parameters.put(descriptor, value);
        } else {
            throw new IllegalArgumentException();
        }
        
    }
    
    public Integer getInteger(Descriptor descriptor) {
        Object value = parameters.get(descriptor);
        if (value != null) {
            try {
                int intValue = (int) value;
                return intValue;
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }
    
    public String getString(Descriptor descriptor) {
        Object value = parameters.get(descriptor);
        if (value != null) {
            try {
                String stringValue = (String) value;
                return stringValue;
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }
    
    public Boolean getBoolean(Descriptor descriptor) {
        Object value = parameters.get(descriptor);
        if (value != null) {
            try {
                boolean booleanValue = (boolean) value;
                return booleanValue;
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }
    
}
