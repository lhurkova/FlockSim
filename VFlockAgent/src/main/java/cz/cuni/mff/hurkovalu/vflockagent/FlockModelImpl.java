/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.vflockagent;

import cz.cuni.mff.hurkovalu.flocksim.spi.Agent;
import cz.cuni.mff.hurkovalu.flocksim.Flock;
import cz.cuni.mff.hurkovalu.flocksim.spi.FlockModel;
import cz.cuni.mff.hurkovalu.flocksim.spi.Parameters;
import cz.cuni.mff.hurkovalu.flocksim.spi.Point;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.Descriptor;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.IntFieldDescriptor;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.SliderDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating {@link VFlockAgent} instances and getting information about
 * the {@link VFlockAgent} class.
 * @author Lucie Hurkova
 */
public class FlockModelImpl implements FlockModel {
    
    private final IntFieldDescriptor view;
    private final IntFieldDescriptor minDistance;
    private final IntFieldDescriptor viewAngle;
    private final SliderDescriptor agentSize;
    private final SliderDescriptor cohesion;
    private final SliderDescriptor separation;
    private final SliderDescriptor clearView;
    private final List<Descriptor> descriptors;

    /**
     * Creates a new {@link FlockModelImpl}.
     */
    public FlockModelImpl() {
        descriptors = new ArrayList<>();
        view = new IntFieldDescriptor("View of a bird", 50, 150, 100);
        minDistance = new IntFieldDescriptor("Minimum distance between two birds", 10, 50, 20);
        viewAngle = new IntFieldDescriptor("Field of view in degrees", 160, 180, 180);
        agentSize = new SliderDescriptor("Wingspan", 10, 26, 18);
        cohesion = new SliderDescriptor("Cohesion force", 5, 15, 10);
        separation = new SliderDescriptor("Separation force", 15, 5, 10);
        clearView = new SliderDescriptor("Clear view force", 15, 5, 10);
        descriptors.add(view);
        descriptors.add(minDistance);
        descriptors.add(viewAngle);
        descriptors.add(agentSize);
        descriptors.add(cohesion);
        descriptors.add(separation);
        descriptors.add(clearView);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Agent createAgent(Point position, Point velocityVector, Flock flock) {
        Parameters params = flock.getParams();
        AgentSettings settings = createSettings(params);
        return new VFlockAgent(position, velocityVector, flock, settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "V-Flock Model";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Descriptor> getDescriptors() {
        return descriptors;
    }
    
    private AgentSettings createSettings(Parameters params) {
        int viewValue = params.getInteger(view);
        int minDistanceValue = params.getInteger(minDistance);
        int viewAngleValue = params.getInteger(viewAngle);
        int agentSizeValue = params.getInteger(agentSize);
        int cohesionValue = params.getInteger(cohesion);
        int separationValue = params.getInteger(separation);
        int clearViewValue = params.getInteger(clearView);
        return new AgentSettings(viewValue, minDistanceValue, viewAngleValue,
            agentSizeValue, cohesionValue, separationValue, clearViewValue);
    }
    
}
