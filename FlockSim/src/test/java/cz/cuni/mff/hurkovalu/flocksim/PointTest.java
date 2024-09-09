/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import cz.cuni.mff.hurkovalu.flocksim.spi.Point;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class PointTest {
    
    public PointTest() {
    }
    
    /**
     * Test of add method, of class Point.
     */
    @Test
    public void testAdd() {
        Point vector1 = new Point(3, 6, -1);
        Point vector2 = new Point(-3, 0, 15);
        Point expResult = new Point(0, 6, 14);
        Point result = vector1.add(vector2);
        assertEquals(expResult, result);
    }

    /**
     * Test of subtract method, of class Point.
     */
    @Test
    public void testSubtract() {
        Point vector1 = new Point(8, -1, 0);
        Point vector2 = new Point(4, -4, 5);
        Point expResult = new Point(4, 3, -5);
        Point result = vector1.subtract(vector2);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceVector method, of class Point.
     */
    @Test
    public void testGetDistanceVector() {
        Point point1 = new Point(4, 5, 6);
        Point point2 = new Point(3, 2, 1);
        Point expResult = new Point(-1, -3, -5);
        Point result = point1.getDistanceVector(point2);
        assertEquals(expResult, result);
    }

    /**
     * Test of getSquaredSize method, of class Point.
     */
    @Test
    public void testGetSquaredSize() {
        Point instance = new Point(1, 2, -1);
        double expResult = 6;
        double result = instance.getSquaredSize();
        assertEquals(expResult, result, 1e-5);
    }

    /**
     * Test of getSize method, of class Point.
     */
    @Test
    public void testGetSize() {
        Point instance = new Point(2, -2, 1);
        double expResult = 3;
        double result = instance.getSize();
        assertEquals(expResult, result, 1e-5);
    }

    /**
     * Test of changeSizeTo method, of class Point.
     */
    @Test
    public void testChangeSizeTo() {
        double newSize = 5;
        Point instance = new Point(2, 2, 1);
        Point result = instance.changeSizeTo(newSize);
        double actualSize = result.getSize();
        assertEquals(newSize, actualSize, 1e-5);
    }

    /**
     * Test of divide method, of class Point.
     */
    @Test
    public void testDivide() {
        double divider = 2;
        Point instance = new Point(8, -2, 6);
        Point expResult = new Point(4, -1, 3);
        Point result = instance.divide(divider);
        assertEquals(expResult, result);
    }

    /**
     * Test of getOppositeVector method, of class Point.
     */
    @Test
    public void testGetOppositeVector() {
        Point instance = new Point(-1, 3, 0);
        Point expResult = new Point(1, -3, 0);
        Point result = instance.getOppositeVector();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOrtogonalVector method, of class Point.
     */
    @Test
    public void testGetOrtogonalVector() {
        Point instance = new Point(4, -1, 0);
        Point expResult = new Point(-1, -4, 0);
        Point result = instance.getOrtogonalVector();
        assertEquals(expResult, result);
    }

    /**
     * Test of getVectorTurnedBy method, of class Point.
     */
    @Test
    public void testGetVectorTurnedBy() {
        double angle = Math.PI / 2;
        Point instance = new Point(1, 0, 0);
        Point expResult = new Point(0, 1, 0);
        Point result = instance.getVectorTurnedBy(angle);
        result = result.changeSizeTo(1);
        assertEquals(expResult, result);
    }

    /**
     * Test of getOrientedAngle method, of class Point.
     */
    @Test
    public void testGetOrientedAngle() {
        Point instance = new Point(1, 0, 0);
        Point vector = new Point(0, 1, 0);
        double expResult = Math.PI / 2;
        double result = instance.getOrientedAngle(vector);
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of get2DPoint method, of class Point.
     */
    @Test
    public void testGet2DPoint() {
        Point instance = new Point(3, -5, 10);
        Point expResult = new Point(3, -5, 0);
        Point result = instance.get2DPoint();
        assertEquals(expResult, result);
    }
    
}
