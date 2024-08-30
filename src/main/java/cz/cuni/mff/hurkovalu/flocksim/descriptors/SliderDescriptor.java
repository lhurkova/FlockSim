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
    
    private float min;
    private float max;
    private float defaultValue;
    
    public SliderDescriptor(String description, float min, float max, float defaultValue) {
        super(description, Type.SLIDER);
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
    }
    
    public float getMin() {
        return min;
    }
    
    public float getMax() {
        return max;
    }
    
    public float getDefaultValue() {
        return defaultValue;
    }
    
}
