/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.boidsagent;

import cz.cuni.mff.hurkovalu.flocksim.spi.Agent;
import cz.cuni.mff.hurkovalu.flocksim.Flock;
import cz.cuni.mff.hurkovalu.flocksim.spi.Point;
import cz.cuni.mff.hurkovalu.flocksim.spi.FlockModel;
import cz.cuni.mff.hurkovalu.flocksim.spi.Parameters;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.Descriptor;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.IntFieldDescriptor;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.SliderDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating {@link BoidsAgent} instances and getting information about
 * {@link BoidsAgent} class.
 * @author Lucie Hurkova
 */
public class FlockModelImpl implements FlockModel {
    
    private final SliderDescriptor alignment;
    private final SliderDescriptor separation;
    private final SliderDescriptor cohesion;
    private final IntFieldDescriptor maxAngle;
    private final IntFieldDescriptor minDistance;
    private final IntFieldDescriptor view;
    private final List<Descriptor> descriptors;

    /**
     * Creates a new {@link FlockModelImpl}.
     */
    public FlockModelImpl() {
        view = new IntFieldDescriptor("View of a bird", 50, 300, 90);
        minDistance = new IntFieldDescriptor("Minimum distance between birds", 10, 150, 40);
        maxAngle = new IntFieldDescriptor("Maximum turnig angle of a bird", 0, 180, 45);
        cohesion = new SliderDescriptor("Cohesion force", 30, 1, 15);
        separation = new SliderDescriptor("Separation force", 1, 30, 15);
        alignment = new SliderDescriptor("Alignment force", 18, 2, 10);
        descriptors = new ArrayList<>();
        descriptors.add(view);
        descriptors.add(minDistance);
        descriptors.add(maxAngle);
        descriptors.add(cohesion);
        descriptors.add(separation);
        descriptors.add(alignment);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Agent createAgent(Point position, Point velocityVector, Flock flock) {
        Parameters params = flock.getParams();
        AgentSettings settings = createSettings(params);
        return new BoidsAgent(position, velocityVector, flock, settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "BOIDS Model";
    }

    @Override
    public String toString() {
        return getName();
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
        int maxAngleValue = params.getInteger(maxAngle);
        int cohesionValue = params.getInteger(cohesion);
        int separationValue = params.getInteger(separation);
        int alignmentValue = params.getInteger(alignment);
        return new AgentSettings(viewValue, minDistanceValue, maxAngleValue,
                cohesionValue, separationValue, alignmentValue);
    }
}
