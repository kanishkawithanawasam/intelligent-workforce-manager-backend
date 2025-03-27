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
    private final double INITIAL_TEMPERATURE ;

    /**
     * Defines stopping temperature for the Simulated Annealing algorithm.
     *
     * <p> Once the current temperature falls below this threshold, the algorithm terminates.
     * A lower minimum temperature results in a more exhaustive search.</p>
     */
    private final double MINIMUM_TEMPERATURE;

    /**
     * Defines the rate at which the temperature is reduced during each iteration.
     *
     * <p>The temperature is multiplied by this cooling rate after each cycle.
     * A value closer to 1.0 causes slower cooling, allowing more thorough exploration,
     * while a value closer to 0.0 causes faster convergence.</p>
     */
    private final double COOLING_RATE;

    /**
     * Defines the penalty applied when an employee's total hours exceeds their weekly maximum hours.
     *
     * <p>This penalty is used to control the impact of weekly maximum hours violations and discourage violations
     * of weekly maximum hours. Higher values will lower the fitness rapidly with respect to number of violations
     * while lower values will slower the fitness downfall rate caused by such violations.</p>
     */
    private final double VIOLATIONS_PENALTY;

    public HSOConfigs() {
        this.INITIAL_TEMPERATURE = 100.0;
        this.MINIMUM_TEMPERATURE = 25.0;
        this.COOLING_RATE = 0.9;
        this.VIOLATIONS_PENALTY = 10.0;
    }

    /**
     * Configuration class for controlling the parameters of the Simulated Annealing algorithm.
     *
     *
     * @param INITIAL_TEMPERATURE Initial temperature for simulated annealing.
     * @param MINIMUM_TEMPERATURE Minimum temperature for simulated annealing.
     * @param COOLING_RATE Cooling rate for simulated annealing.
     * @param VIOLATIONS_PENALTY Violations penalty for constraint violations.
     */
    public HSOConfigs(double INITIAL_TEMPERATURE, double MINIMUM_TEMPERATURE, double COOLING_RATE
    , double VIOLATIONS_PENALTY) {
        this.INITIAL_TEMPERATURE = INITIAL_TEMPERATURE;
        this.MINIMUM_TEMPERATURE = MINIMUM_TEMPERATURE;
        this.COOLING_RATE = COOLING_RATE;
        this.VIOLATIONS_PENALTY = VIOLATIONS_PENALTY;
    }

    /**
     * Returns the initial temperature for the Simulated Annealing algorithm.
     *
     * @return The initial temperature value.
     */
    public double getINITIAL_TEMPERATURE() {
        return INITIAL_TEMPERATURE;
    }

    /**
     * Returns the minimum temperature for the Simulated Annealing algorithm.
     *
     * @return The minimum temperature value.
     */
    public double getMINIMUM_TEMPERATURE() {
        return MINIMUM_TEMPERATURE;
    }

    /**
     * Returns the cooling rate used to reduce the temperature during the optimization process.
     *
     * @return The cooling rate value.
     */
    public double getCOOLING_RATE() {
        return COOLING_RATE;
    }


    /**
     * Returns the violations penalty used to control the impact of maximum weekly hours violations.
     *
     * @return The violations' penalty.
     */
    public double getVIOLATIONS_PENALTY() {
        return VIOLATIONS_PENALTY;
    }
}
