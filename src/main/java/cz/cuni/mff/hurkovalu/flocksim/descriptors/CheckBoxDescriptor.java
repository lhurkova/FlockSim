/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.descriptors;

/**
 * Class for a description of a simulation parameter that in GUI will be implemented with
 * a JCheckBox.
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class CheckBoxDescriptor extends Descriptor {
    
    private boolean defaultValue;
    
    /**
     * Creates a new {@link CheckBoxDescriptor} with a short description of
     * a parameter and default value of the parameter.
     * @param description short description of the parameter
     * @param defaultValue default value of the parameter
     */
    public CheckBoxDescriptor(String description, boolean defaultValue) {
        super(description, Type.CHECK_BOX);
        this.defaultValue = defaultValue;
    }
    
    /**
     * Gets default value of the parameter.
     * @return default value of the parameter
     */
    public boolean getDefaultValue() {
        return defaultValue;
    }
}
