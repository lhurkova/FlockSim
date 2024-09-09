/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import cz.cuni.mff.hurkovalu.flocksim.spi.Agent;
import cz.cuni.mff.hurkovalu.flocksim.spi.Point;
import cz.cuni.mff.hurkovalu.flocksim.spi.AgentInfo;
import cz.cuni.mff.hurkovalu.flocksim.spi.FlockModel;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.Descriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class FlockTest {
    
    private Flock f;
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    
    public FlockTest() {
    }

    @Before
    public void setUp() {
        f = new Flock(HEIGHT, WIDTH, 10, new FlockModelImpl(), null);
    }
    
    /**
     * Test of getNeighbours method, of class Flock.
     */
    @Test
    public void testGetNeighbours() {
        int view = 30;
        List<AgentInfo> agentInfos = f.getAgentInfos();
        compareNeighbours(view, f, agentInfos, HEIGHT, WIDTH);
        agentInfos = f.doStep();
        compareNeighbours(view, f, agentInfos, HEIGHT, WIDTH);
        for (int i = 0; i < 20; i++) {
            agentInfos = f.doStep();
        }
        compareNeighbours(view, f, agentInfos, HEIGHT, WIDTH);
    }
    
     private static void compareNeighbours(int view, Flock flock, List<AgentInfo> agentInfos, int winHeight, int winWidth) {
        for (int i = 0; i < agentInfos.size(); i++) {
            AgentInfo agent = agentInfos.get(i);
            int difx = 0, dify = 0;
            double posX = agent.getPosition().getX();
            double posY = agent.getPosition().getY();
            if (posX + view > winWidth) {
                difx = winWidth;
            }
            if (posX - view < 0) {
                difx = -winWidth;
            }
            if (posY + view > winHeight) {
                dify = winHeight;
            }
            if (posY - view < 0) {
                dify = -winHeight;
            }
            List<AgentInfo> neighbours = flock.getNeighbours(agent.getPosition(), view);
            Set<AgentInfo> expectedNeighbours = new HashSet<>();
            for (AgentInfo potencialNeighbour: agentInfos){
                Point position = potencialNeighbour.getPosition();
                double distance = agent.getPosition().getDistanceVector(position).getSize();
                if (distance <= view) {
                    AgentInfo neighbour = new AgentInfo(position, potencialNeighbour.getVelocityVector());
                    expectedNeighbours.add(neighbour);
                    continue;
                }
                position = position.add(new Point(difx, 0, 0));
                distance = agent.getPosition().getDistanceVector(position).getSize();
                if (distance <= view) {
                    AgentInfo neighbour = new AgentInfo(position, potencialNeighbour.getVelocityVector());
                    expectedNeighbours.add(neighbour);
                    continue;
                }

                position = position.add(new Point(0, dify, 0));
                distance = agent.getPosition().getDistanceVector(position).getSize();
                if (distance <= view) {
                    AgentInfo neighbour = new AgentInfo(position, potencialNeighbour.getVelocityVector());
                    expectedNeighbours.add(neighbour);
                    continue;
                }
                
                position = position.add(new Point(-difx, 0, 0));
                distance = agent.getPosition().getDistanceVector(position).getSize();
                if (distance <= view) {
                    AgentInfo neighbour = new AgentInfo(position, potencialNeighbour.getVelocityVector());
                    expectedNeighbours.add(neighbour);
                }
            }

            for (AgentInfo neighbour: neighbours) {
                assertTrue(expectedNeighbours.remove(neighbour));
            }
            assertEquals(0, expectedNeighbours.size());
            assertTrue(neighbours.size() <= agentInfos.size());
        }
    }

    /**
     * Test of doStep method, of class Flock.
     */
    @Test
    public void testDoStep() {
        List<AgentInfo> initial = f.getAgentInfos();
        List<Point> expectedPositions = new ArrayList<>();
        for (AgentInfo a: initial) {
            Point newPos = a.getPosition().add(a.getVelocityVector());
            newPos = f.normalizePosition(newPos);
            expectedPositions.add(newPos);
        }
        List<AgentInfo> actualPositions = f.doStep();
        assertTrue(expectedPositions.size() == actualPositions.size());
        for (int i = 0; i < expectedPositions.size(); i++) {
            assertEquals(expectedPositions.get(i), actualPositions.get(i).getPosition());
        }
    }

    /**
     * Test of normalizePosition method, of class Flock.
     */
    @Test
    public void testNormalizePosition() {
        Point position = new Point(110, -10, 0);
        Point expResult = new Point(10, 90, 0);
        Point result = f.normalizePosition(position);
        assertEquals(expResult, result);
    }
    
    public static class FlockModelImpl implements FlockModel {

        @Override
        public Agent createAgent(Point position, Point velocityVector, Flock flock) {
            return new AgentImpl(position, velocityVector, flock);
        }

        @Override
        public String getName() {
            return "Test model";
        }

        @Override
        public List<Descriptor> getDescriptors() {
            return new ArrayList<>();
        }
        
    }
    
    public static class AgentImpl implements Agent {
        
        private Point position;
        private Point vector;
        private Flock flock;
        
        public AgentImpl(Point position, Point vector, Flock flock) {
            this.position = position;
            this.vector = vector;
            this.flock = flock;
        }

        @Override
        public AgentInfo doStep() {
            position = position.add(vector);
            position = flock.normalizePosition(position);
            return new AgentInfo(position, vector);
        }

        @Override
        public AgentInfo getInfo() {
            return new AgentInfo(position, vector);
        }
        
    }
}
