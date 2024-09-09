/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.descriptors;

import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.ComboBoxDescriptor;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class ComboBoxDescriptorTest {
    
    public ComboBoxDescriptorTest() {
    }

    /**
     * Test of constructor, of class ComboBoxDescriptor.
     */
    @Test
    public void testCreateComboBoxDescriptor() {
        Exception e = assertThrows(IllegalArgumentException.class,
            () -> new ComboBoxDescriptor("Test", new String[] {"test"}, 2));
        String expected1 = "Default value";
        String expected2 = "out of bounds";
        assertTrue(e.getMessage().contains(expected1));
        assertTrue(e.getMessage().contains(expected2));
    }
    
}
