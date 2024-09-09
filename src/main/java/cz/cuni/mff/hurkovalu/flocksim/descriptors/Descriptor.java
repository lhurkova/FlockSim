/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.descriptors;

/**
 * Abstract class for a description of a simulation parameter.
 * @author Lucie Hurkova
 */
public abstract class Descriptor {
    
    private String description;
    private Type type;
    
    /**
     * Creates a new {@link Descriptor} with a short description and specified type.
     * @param descrition short description of the parameter
     * @param type type of the parameter
     */
    protected Descriptor(String descrition, Type type) {
        this.description = descrition;
        this.type = type;
    }
    
    /**
     * Gets a short description of the parameter.
     * @return description of the parameter
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Gets a type of the descriptor.
     * @return type of the descriptor
     */
    public Type getType() {
        return type;
    }
    
    /**
     * Enum containing types of descriptor used in FlockSim.
     */
    public enum Type {
        /**
         * Item representing a {@link IntFieldDescriptor}.
         */
        INT_FIELD,
        /**
         * Item representing a {@link ComboBoxDescriptor}.
         */
        COMBO_BOX,
        /**
         * Item representing a {@link SliderDescriptor}
         */
        SLIDER
    }
}
