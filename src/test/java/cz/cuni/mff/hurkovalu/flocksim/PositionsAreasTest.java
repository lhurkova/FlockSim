/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class PositionsAreasTest {
    
    public PositionsAreasTest() {
    }

    /**
     * Test of putAgentInArea and getAgentInArea method, of class PositionsAreas.
     */
    @Test
    public void testPutAndGetAgentInArea() {
        PositionsAreas pa = new PositionsAreas(20, 20);
        Point2D pos = new Point2D(3, 5);
        AgentInfo agent = new AgentInfo(new Point(30, 50, 0), new Point(5, 10, -1));
        pa.putAgentInArea(pos, agent);
        List<AgentInfo> actual = pa.getAgentsInArea(pos);
        assertTrue(actual.size() == 1);
        assertTrue(actual.get(0) == agent);
    }
    
}
