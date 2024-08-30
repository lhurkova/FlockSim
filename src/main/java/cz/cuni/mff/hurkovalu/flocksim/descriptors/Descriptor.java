/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.descriptors;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public abstract class Descriptor {
    
    private String description;
    private Type type;
    
    public Descriptor(String descrition, Type type) {
        this.description = descrition;
        this.type = type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Type getType() {
        return type;
    }
        
    public enum Type {
        INT_FIELD,
        COMBO_BOX,
        CHECK_BOX,
        SLIDER
    }
}
