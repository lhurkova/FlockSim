/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.settings;
import cz.cuni.mff.hurkovalu.flocksim.descriptors.ComboBoxDescriptor;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class ComboBoxItem extends SettingsItem {
    
    private JComboBox<String> comboBox;

    /**
     * 
     * @param descriptor 
     */
    public ComboBoxItem(ComboBoxDescriptor descriptor) {
        super(descriptor);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JLabel(descriptor.getDescription(), JLabel.TRAILING));
        comboBox = new JComboBox<>(descriptor.getOptions());
        comboBox.setSelectedIndex(descriptor.getDefaultValue());
        add(comboBox);
    }
    
    public String getResult() {
        return (String) comboBox.getSelectedItem();
    }

    @Override
    public Object getResultAsObject() {
        return getResult();
    }

}
