/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.settings;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.ComboBoxDescriptor;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * Class representing a settings item in FlockSim GUI containing a {@link JComboBox}.
 * @author Lucie Hurkova
 */
public class ComboBoxItem extends SettingsItem {
    
    private JComboBox<String> comboBox;

    /**
     * Creates a new {@link ComboBoxItem} based on a specified descriptor.
     * @param descriptor descriptor describing a simulation parameter that will be
     * graphically represented by this {@link ComboBoxItem}
     */
    public ComboBoxItem(ComboBoxDescriptor descriptor) {
        super(descriptor);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JLabel(descriptor.getDescription()+" ", JLabel.TRAILING));
        comboBox = new JComboBox<>(descriptor.getOptions());
        comboBox.setSelectedIndex(descriptor.getDefaultValue());
        add(comboBox);
    }
    
    /**
     * Gets a currently selected value in the component.
     * @return currently selected value
     */
    public String getResult() {
        return (String) comboBox.getSelectedItem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getResultAsObject() {
        return getResult();
    }

}
