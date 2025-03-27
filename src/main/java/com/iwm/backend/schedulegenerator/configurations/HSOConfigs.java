package com.iwm.backend.schedulegenerator.configurations;


/**
 * Configuration class for controlling the parameters of the Simulated Annealing algorithm
 * used in the Hourly Scheduling Optimizer (HSO).
 */
public class HSOConfigs {

    /**
     * Defines the starting temperature for the Simulated Annealing algorithm.
     * A higher initial temperature allows the algorithm to explore a wider solution space
     * by accepting worse solutions more frequently in the early stages.
     */
    private final double INITIAL_TEMPERATURE ;

    /**
     * Defines stopping temperature for the Simulated Annealing algorithm.
     * Once the current temperature falls below this threshold, the algorithm terminates.
     * A lower minimum temperature results in a more exhaustive search.
     */
    private final double MINIMUM_TEMPERATURE;

    /**
     * Defines the rate at which the temperature is reduced during each iteration.
     * The temperature is multiplied by this cooling rate after each cycle.
     * A value closer to 1.0 causes slower cooling, allowing more thorough exploration,
     * while a value closer to 0.0 causes faster convergence.
     */
    private final double COOLING_RATE;


    public HSOConfigs() {
        this.INITIAL_TEMPERATURE = 100.0;
        this.MINIMUM_TEMPERATURE = 25.0;
        this.COOLING_RATE = 0.9;
    }

    public HSOConfigs(double INITIAL_TEMPERATURE, double MINIMUM_TEMPERATURE, double COOLING_RATE) {
        this.INITIAL_TEMPERATURE = INITIAL_TEMPERATURE;
        this.MINIMUM_TEMPERATURE = MINIMUM_TEMPERATURE;
        this.COOLING_RATE = COOLING_RATE;
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
}
