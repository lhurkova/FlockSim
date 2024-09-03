/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim.descriptors;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class SliderDescriptorTest {
    
    public SliderDescriptorTest() {
    }

    /**
     * Test of  constructor, of class SliderDescriptor.
     */
    @Test
    public void testCreateSliderDescriptor() {
        Exception e = assertThrows(IllegalArgumentException.class,
            () -> new SliderDescriptor("Test", 10, 20, 40));
        String expected1 = "Default value";
        String expected2 = "out of bounds";
        assertTrue(e.getMessage().contains(expected1));
        assertTrue(e.getMessage().contains(expected2));
    }
    
}
