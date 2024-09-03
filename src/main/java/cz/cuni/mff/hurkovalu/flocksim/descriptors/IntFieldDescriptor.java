/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.descriptors;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class IntFieldDescriptor extends Descriptor {
    
    private int min;
    private int max;
    private int defaultValue;
    
    public IntFieldDescriptor(String description, int min, int max,
            int defaultValue) throws IllegalArgumentException {
        super(description, Type.INT_FIELD);
        if (defaultValue < min || defaultValue > max) {
            throw new IllegalArgumentException("Default value "+defaultValue
                    +" is out of bounds: ["+min+","+max+"]");
        }
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
    }
    
    public int getMin() {
        return min;
    }
    
    public int getMax() {
        return max;
    }
    
    public int getDefaultValue() {
        return defaultValue;
    }

}
