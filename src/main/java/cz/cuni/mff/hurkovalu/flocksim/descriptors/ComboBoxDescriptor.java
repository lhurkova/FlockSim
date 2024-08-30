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
    
    public ComboBoxDescriptor(String description, String[] options, int defaultValue) {
        super(description, Type.COMBO_BOX);
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
