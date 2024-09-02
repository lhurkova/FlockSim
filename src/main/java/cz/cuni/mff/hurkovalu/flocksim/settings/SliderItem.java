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
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class SliderItem extends SettingsItem {
    
    JSlider slider;
    
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
    
    public int getResult() {
        return slider.getValue();
    }

    @Override
    public Object getResultAsObject() {
        return getResult();
    }
    
}
