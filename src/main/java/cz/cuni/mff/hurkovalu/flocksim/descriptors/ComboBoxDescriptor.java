/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.descriptors;

/**
 * Class for a description of a simulation parameter that in GUI will be implemented with
 * a JComboBox.
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class ComboBoxDescriptor extends Descriptor {
    
    private String[] options;
    private int defaultValue;
    
    /**
     * Creates a new {@link ComboBoxDescriptor} with a short description of
     * the parameter, options for the JComboBox and default value as an index in options.
     * @param description short description of the parameters
     * @param options possible values of the parameter
     * @param defaultValue default value of the parameter as an index in options
     * @throws IllegalArgumentException if defaultValue is not inside the bounds for the
     * length of the options array
     */
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
    
    /**
     * Gets possible values of the parameter.
     * @return possible values of the parameter
     */
    public String[] getOptions() {
        return options;
    }
    
    /**
     * Gets a default value of the parameter.
     * @return default value of the parameter
     */
    public int getDefaultValue() {
        return defaultValue;
    }
    
}
