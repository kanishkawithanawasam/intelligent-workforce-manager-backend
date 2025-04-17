package com.iwm.backend.modules.schedule_engine.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a real-time employee shift schedule along with an associated fitness score
 * used in optimisation algorithms such as Simulated Annealing or Genetic Algorithms.
 *
 * <p>The {@code RealTimeSchedule} contains a list of {@link ShiftGO} objects and a fitness
 * value that reflects how well the schedule satisfies constraints and optimisation goals.</p>
 */
public class RealTimeSchedule{

    /**
     * The fitness score of this schedule.
     * Higher values typically indicate better-quality solutions.
     */
    double fitness;

    /**
     * The list of shifts assigned in this real-time schedule.
     */
    private final List<ShiftGO> shifts = new ArrayList<>();


    /**
     * Returns the fitness score of the schedule.
     *
     * @return the fitness value
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Sets the fitness score of the schedule.
     *
     * @param fitness the fitness value to assign
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }


    /**
     * Returns the list of shifts in the real-time schedule.
     *
     * @return a list of {@link ShiftGO} objects
     */
    public List<ShiftGO> getShifts() {
        return shifts;
    }

}
