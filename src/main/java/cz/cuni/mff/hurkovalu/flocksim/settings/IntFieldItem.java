/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.settings;

import cz.cuni.mff.hurkovalu.flocksim.descriptors.IntFieldDescriptor;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class IntFieldItem extends SettingsItem implements Savable {
    
    private JTextField textField;
    private JLabel errorLabel;
    private Verifier verifier;
    private int savedValue;
    private int min;
    private int max;
    
    public IntFieldItem(IntFieldDescriptor descriptor) {
        super(descriptor);
        min = descriptor.getMin();
        max = descriptor.getMax();
        savedValue = descriptor.getDefaultValue();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(new JLabel(descriptor.getDescription(), JLabel.TRAILING));
        textField = new JTextField();
        textField.setText(Integer.toString(descriptor.getDefaultValue()));
        verifier = new Verifier();
        textField.getDocument().addDocumentListener(verifier);
        add(textField);
        errorLabel = new JLabel("X");
        errorLabel.setForeground(Color.red);
        errorLabel.setVisible(false);
        add(errorLabel);
    }
    
    public int getResult() {
        return savedValue;
    }

    @Override
    public Object getResultAsObject() {
        return getResult();
    }

    @Override
    public void save() {
        if (verifier.verify()) {
            savedValue = Integer.parseInt(textField.getText());
        }
    }
    
    private class Verifier implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (verify()) {
                errorLabel.setVisible(false);
                errorLabel.setToolTipText(null);
            } else {
                errorLabel.setVisible(true);
                errorLabel.setToolTipText("Value has to be an integer between "+min+" and "+max);
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (verify()) {
                errorLabel.setVisible(false);
            } else {
                errorLabel.setVisible(true);
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
        
        public boolean verify() {
            String value = textField.getText();
                try {
                    int intValue = Integer.parseInt(value);
                    return intValue >= min && intValue <= max;
                } catch (NumberFormatException e) {
                    return false;
                }
        }
        
    }
    
}
