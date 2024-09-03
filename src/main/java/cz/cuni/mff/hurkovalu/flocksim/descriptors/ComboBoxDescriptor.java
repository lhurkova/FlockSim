/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.descriptors;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class ComboBoxDescriptor extends Descriptor {
    
    private String[] options;
    private int defaultValue;
    
    public ComboBoxDescriptor(String description, String[] options,
            int defaultValue) throws IllegalArgumentException {
        super(description, Type.COMBO_BOX);
        if (defaultValue < 0 || defaultValue >= options.length) {
            throw new IllegalArgumentException("Default value "+defaultValue
                    +" is out of bounds: [0,"+(options.length-1)+"]");
        }
        this.options = options;
        this.defaultValue = defaultValue;
    }
    
    public String[] getOptions() {
        return options;
    }
    
    public int getDefaultValue() {
        return defaultValue;
    }
    
}
