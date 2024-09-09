/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.settings;

import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.ComboBoxDescriptor;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.Descriptor;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.IntFieldDescriptor;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.SliderDescriptor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class SettingsItemTest {
    
    public SettingsItemTest() {
    }

    /**
     * Test of createCorrectSettingsItem method, of class SettingsItem.
     */
    @Test
    public void testCreateCorrectSettingsItem1() {
        Descriptor desc = new IntFieldDescriptor("IntField descriptor", 10, 20, 15);
        SettingsItem result = SettingsItem.createCorrectSettingsItem(desc);
        assertTrue(result instanceof IntFieldItem);
    }
    
    /**
     * Test of createCorrectSettingsItem method, of class SettingsItem.
     */
    @Test
    public void testCreateCorrectSettingsItem2() {
        Descriptor desc = new SliderDescriptor("Slider descriptor", 10, 20, 15);
        SettingsItem result = SettingsItem.createCorrectSettingsItem(desc);
        assertTrue(result instanceof SliderItem);
    }
    
    /**
     * Test of createCorrectSettingsItem method, of class SettingsItem.
     */
    @Test
    public void testCreateCorrectSettingsItem3() {
        Descriptor desc = new ComboBoxDescriptor("ComboBox descriptor", new String[]
        {"Test1", "Test2"}, 0);
        SettingsItem result = SettingsItem.createCorrectSettingsItem(desc);
        assertTrue(result instanceof ComboBoxItem);
    }
    
}
