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
    
    public void put(Descriptor descriptor, Object value) {
        parameters.put(descriptor, value);
    }
    
    public Integer getInteger(Descriptor descriptor) {
        Object value = parameters.get(descriptor);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return null;
    }
    
    public String getString(Descriptor descriptor) {
        Object value = parameters.get(descriptor);
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }
    
    public Boolean getBoolean(Descriptor descriptor) {
        Object value = parameters.get(descriptor);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return null;
    }
    
}
