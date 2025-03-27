package com.iwm.backend.schedulegenerator.configurations;


/**
 * Configuration class for controlling the parameters of the Simulated Annealing algorithm
 * used in the Hourly Scheduling Optimizer (HSO).
 */
public class HSOConfigs {

    /**
     * Defines the starting temperature for the Simulated Annealing algorithm.
     *
     * <p>A higher initial temperature allows the algorithm to explore a wider solution space,
     * by accepting worse solutions more frequently in the early stages.</p>
     */
    public static final double INITIAL_TEMPERATURE = 100.0;

    /**
     * Defines stopping temperature for the Simulated Annealing algorithm.
     *
     * <p> Once the current temperature falls below this threshold, the algorithm terminates.
     * A lower minimum temperature results in a more exhaustive search.</p>
     */
    public static final double MINIMUM_TEMPERATURE = 10.0;

    /**
     * Defines the rate at which the temperature is reduced during each iteration.
     *
     * <p>The temperature is multiplied by this cooling rate after each cycle.
     * A value closer to 1.0 causes slower cooling, allowing more thorough exploration,
     * while a value closer to 0.0 causes faster convergence.</p>
     */
    public static final double COOLING_RATE = 0.9;

    /**
     * Defines the penalty applied when an employee's total hours exceeds their weekly maximum hours.
     *
     * <p>This penalty is used to control the impact of weekly maximum hours violations and discourage violations
     * of weekly maximum hours. Higher values will lower the fitness rapidly with respect to number of violations
     * while lower values will slower the fitness downfall rate caused by such violations.</p>
     */
    public static final double VIOLATIONS_PENALTY_HOURS = 10;
}
