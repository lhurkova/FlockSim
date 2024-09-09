/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package cz.cuni.mff.hurkovalu.boidsagent;

import cz.cuni.mff.hurkovalu.flocksim.spi.Point;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class BoidsAgentTest {
    
    AgentSettings settings;
    
    public BoidsAgentTest() {
    }
    
    @Before
    public void setUp() {
        settings = new AgentSettings(20, 5, 90, 10, 10, 10);
    }

    /**
     * Test of averageVector method, of class BoidsAgent.
     */
    @Test
    public void testAverageVector() {
        List<Point> vectors = new ArrayList<>();
        Point point1 = new Point(5, 4, -2);
        Point point2 = new Point(-3, 2, 0);
        vectors.add(point1);
        vectors.add(point2);
        BoidsAgent instance = new BoidsAgent(point1, point2, null, settings);
        Point expResult = new Point(1, 3, -1);
        Point result = instance.averageVector(vectors);
        assertEquals(expResult, result);
    }

    /**
     * Test of alignmentVector method, of class BoidsAgent.
     */
    @Test
    public void testAlignmentVector() {
        List<Point> neighbourVectors = new ArrayList<>();
        Point point1 = new Point(5, 4, 4);
        Point point2 = new Point(-5, 2, 0);
        neighbourVectors.add(point1);
        neighbourVectors.add(point2);
        BoidsAgent instance = new BoidsAgent(point1, point2, null, settings);
        Point expResult = new Point(0, 3, 2);
        Point result = instance.alignmentVector(neighbourVectors);
        assertEquals(expResult, result);
    }

    /**
     * Test of separationVector method, of class BoidsAgent.
     */
    @Test
    public void testSeparationVector() {
        List<Point> neighbours = new ArrayList<>();
        Point point1 = new Point(0, 0, 0);
        Point point2 = new Point(1, 1, 0);
        neighbours.add(point2);
        BoidsAgent instance = new BoidsAgent(point1 , point2, null, settings);
        Point distanceVector = point1.getDistanceVector(point2);
        Point expResult = distanceVector.getOppositeVector().changeSizeTo(10);
        Point result = instance.separationVector(neighbours);
        assertEquals(expResult, result);
    }

    /**
     * Test of cohesionVector method, of class BoidsAgent.
     */
    @Test
    public void testCohesionVector() {
        List<Point> neighbours = new ArrayList<>();
        Point point1 = new Point(0, 0, 0);
        Point point2 = new Point(2, 2, 0);
        neighbours.add(point1);
        neighbours.add(point2);
        BoidsAgent instance = new BoidsAgent(point1 , point2, null, settings);
        Point expResult = point1.getDistanceVector(new Point(1, 1, 0));
        Point result = instance.cohesionVector(neighbours);
        assertEquals(expResult, result);
    }
    
}
