/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.spi.descriptors;

/**
 * Class for a description of a simulation parameter that in GUI will be implemented with
 * a JSlider.
 * @author Lucie Hurkova
 */
public class SliderDescriptor extends Descriptor {
    
    private int min;
    private int max;
    private int defaultValue;
    
    /**
     * Creates a new {@link SliderDescriptor} with a short description, minimum,
     * maximum and default value.
     * @param description short description of the parameter
     * @param min minimum value of the parameter
     * @param max maximum value of the parameter
     * @param defaultValue default value of the parameter
     * @throws IllegalArgumentException if default value is out of bound
     * for range [min, max]
     */
    public SliderDescriptor(String description, int min, int max,
            int defaultValue) throws IllegalArgumentException{
        super(description, Type.SLIDER);
        int testMin = Math.min(min, max);
        int testMax = Math.max(min, max);
        if (defaultValue < testMin || defaultValue > testMax) {
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
