/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.settings;

import cz.cuni.mff.hurkovalu.flocksim.descriptors.IntFieldDescriptor;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class IntFieldItem extends SettingsItem {
    
    private JTextField textField;
    
    public IntFieldItem(IntFieldDescriptor descriptor) {
        super(descriptor);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JLabel(descriptor.getDescription(), JLabel.TRAILING));
        textField = new JTextField();
        textField.setText(Integer.toString(descriptor.getDefaultValue()));
        add(textField);
    }
    
    public int getResult() {
        return Integer.parseInt(textField.getText());
    }

    @Override
    public Object getResultAsObject() {
        return getResult();
    }
    
}
