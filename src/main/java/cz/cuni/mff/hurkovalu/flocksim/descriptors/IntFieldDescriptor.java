/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.descriptors;

/**
 * Class for a description of a simulation parameter that in GUI will be implemented with
 * a JTextFiel. The JTextField will only allow integer values in a specified range.
 * @author Lucie Hurkova
 */
public class IntFieldDescriptor extends Descriptor {
    
    private int min;
    private int max;
    private int defaultValue;
    
    /**
     * Creates a new {@link IntFieldDescriptor} with a short description, minimum,
     * maximum and default value.
     * @param description short description of the parameter
     * @param min minimum value of the parameter
     * @param max maximum value of the parameter
     * @param defaultValue default value of the parameter
     * @throws IllegalArgumentException if min is greater than max or default value
     * is out of bound for range [min, max]
     */
    public IntFieldDescriptor(String description, int min, int max,
            int defaultValue) throws IllegalArgumentException {
        super(description, Type.INT_FIELD);
        if (min > max) {
            throw new IllegalArgumentException("Minimum value "+min
                    +" is greater than maximum value "+max);
        }
        if (defaultValue < min || defaultValue > max) {
            throw new IllegalArgumentException("Default value "+defaultValue
                    +" is out of bounds: ["+min+","+max+"]");
        }
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
    }
    
    /**
     * Gets a minimum value of the parameter.
     * @return minimum value of the parameter
     */
    public int getMin() {
        return min;
    }
    
    /**
     * Gets a maximum value of the parameter.
     * @return maximum value of the parameter
     */
    public int getMax() {
        return max;
    }
    
    /**
     * Gets a default value of the parameter.
     * @return default value of the parameter
     */
    public int getDefaultValue() {
        return defaultValue;
    }

}
