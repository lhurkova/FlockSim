/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package cz.cuni.mff.hurkovalu.vflockagent;

import cz.cuni.mff.hurkovalu.flocksim.spi.Point;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class VFlockAgentTest {
    
    private AgentSettings settings;
    
    public VFlockAgentTest() {
    }
    
    @Before
    public void setUp() {
        settings = new AgentSettings(20, 5, 180, 18, 10, 10, 10);
    }

    /**
     * Test of averageVector method, of class VFlockAgent.
     */
    @Test
    public void testAverageVector() {
        List<Point> vectors = new ArrayList<>();
        Point point1 = new Point(5, 4, -2);
        Point point2 = new Point(-3, 2, 0);
        vectors.add(point1);
        vectors.add(point2);
        VFlockAgent instance = new VFlockAgent(point1, point2, null, settings);
        Point expResult = new Point(1, 3, -1);
        Point result = instance.averageVector(vectors);
        assertEquals(expResult, result);
    }

    /**
     * Test of separationVector method, of class VFlockAgent.
     */
    @Test
    public void testSeparationVector() {
        List<Point> neighbours = new ArrayList<>();
        Point point1 = new Point(0, 0, 0);
        Point point2 = new Point(1, 1, 0);
        neighbours.add(point2);
        VFlockAgent instance = new VFlockAgent(point1 , point2, null, settings);
        Point distanceVector = point1.getDistanceVector(point2);
        double size = (1 - distanceVector.getSize()/5) * 10;
        Point expResult = distanceVector.getOppositeVector().changeSizeTo(size);
        Point result = instance.separationVector(neighbours);
        assertEquals(expResult, result);
    }
    
}
