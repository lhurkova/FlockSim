/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.settings;

import cz.cuni.mff.hurkovalu.flocksim.descriptors.ComboBoxDescriptor;
import cz.cuni.mff.hurkovalu.flocksim.descriptors.Descriptor;
import cz.cuni.mff.hurkovalu.flocksim.descriptors.IntFieldDescriptor;
import cz.cuni.mff.hurkovalu.flocksim.descriptors.SliderDescriptor;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public abstract class SettingsItem extends JPanel {
    
    private Descriptor descriptor;
    
    public SettingsItem(Descriptor descriptor) {
        this.descriptor = descriptor;
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }
    
    public Descriptor getDescriptor() {
        return descriptor;
    }
    
    abstract public Object getResultAsObject();
    
    public static SettingsItem createCorrectSettingsItem(Descriptor desc) {
        switch (desc.getType()) {
            case INT_FIELD:
                if (desc instanceof IntFieldDescriptor intFieldDescriptor) {
                    return new IntFieldItem(intFieldDescriptor);
                }
                throw new IllegalArgumentException();

            case COMBO_BOX:
                if (desc instanceof ComboBoxDescriptor comboBoxDescriptor) {
                    return new ComboBoxItem(comboBoxDescriptor);
                }
                throw new IllegalArgumentException();
            
            case SLIDER:
                if (desc instanceof SliderDescriptor sliderDescriptor) {
                    return new SliderItem(sliderDescriptor);
                }
                throw new IllegalArgumentException();
            default:
                throw new IllegalArgumentException();
        }
    }
}
