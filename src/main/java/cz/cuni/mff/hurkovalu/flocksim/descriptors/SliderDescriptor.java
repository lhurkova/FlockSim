/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.descriptors;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class SliderDescriptor extends Descriptor {
    
    private int min;
    private int max;
    private int defaultValue;
    
    public SliderDescriptor(String description, int min, int max, int defaultValue) {
        super(description, Type.SLIDER);
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
