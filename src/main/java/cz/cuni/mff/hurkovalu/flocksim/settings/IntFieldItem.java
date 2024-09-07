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
 * Class representing a settings item in FlockSim GUI containing a JTextFiled that
 * allows only integer values inside a specified range.
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class IntFieldItem extends SettingsItem implements Savable {
    
    private JTextField textField;
    private JLabel errorLabel;
    private Verifier verifier;
    private int savedValue;
    private int min;
    private int max;
    
    /**
     * Creates a new {@link IntFieldItem} based on a specified descriptor.
     * @param descriptor descriptor describing a simulation parameter that will be
     * graphically represented by this {@link IntFieldItem}
     */
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
    
    /**
     * Gets the latest valid value that was written in the component.
     * @return the latest valid value
     */
    public int getResult() {
        return savedValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getResultAsObject() {
        return getResult();
    }

    /**
     * {@inheritDoc}
     */
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
