/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.settings;

import cz.cuni.mff.hurkovalu.flocksim.descriptors.SliderDescriptor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 * Class representing a settings item in FlockSim GUI containing a JSlider.
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class SliderItem extends SettingsItem {
    
    JSlider slider;
    
    /**
     * Creates a new {@link SliderItem} based on a specified descriptor.
     * @param descriptor descriptor describing a simulation parameter that will be
     * graphically represented by this {@link SliderItem}
     */
    public SliderItem(SliderDescriptor descriptor) {
        super(descriptor);
        int min = descriptor.getMin();
        int max = descriptor.getMax();
        if (min < max) {
            slider = new JSlider(min, max, descriptor.getDefaultValue());
        } else {
            slider = new JSlider(max, min, descriptor.getDefaultValue());
            slider.setInverted(true);
        }
        slider.setMinorTickSpacing(1);
        JLabel minLabel = new JLabel("min");
        minLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
        JLabel maxLabel = new JLabel("max");
        maxLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
        Dictionary labels = new Hashtable();
        labels.put(min, minLabel);
        labels.put(max, maxLabel);
        slider.setLabelTable(labels);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        JLabel description = new JLabel(descriptor.getDescription());
        add(description);
        add(slider);
    }
    
    /**
     * Gets a currently selected value in the component.
     * @return currently selected value
     */
    public int getResult() {
        return slider.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getResultAsObject() {
        return getResult();
    }
    
}
