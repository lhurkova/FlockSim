/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.descriptors;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class CheckBoxDescriptor extends Descriptor {
    
    private boolean defaultValue;
    
    public CheckBoxDescriptor(String description, boolean defaultValue) {
        super(description, Type.CHECK_BOX);
        this.defaultValue = defaultValue;
    }
    
    public boolean getDefaultValue() {
        return defaultValue;
    }
}
