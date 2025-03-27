package com.iwm.backend.schedulegenerator.configurations;

/**
 * Configuration class for controlling parameters used by the Genetic Algorithm
 * in generating optimized employee schedules.
 *
 * <p>These parameters influence the algorithm's evolution behavior such as
 * population size, number of generations, tournament selection size, and penalty
 * values for constraint violations (e.g., exceeding maximum weekly hours or
 * multiple shifts per day).</p>
 *
 * @author Kanishka Withanawasam
 * @version 1.0
 */
public class FGAConfigs {

    /**
     * The number of generations (iterations) the genetic algorithm will evolve through.
     */
    public static final int NUMBER_OF_ITERATIONS = 50000;

    /**
     * The number of individuals in the population per generation.
     */
    public static final int POPULATION_SIZE = 20;

    /**
     * The number of individuals selected to compete in tournament selection.
     * Higher values increase selection pressure.
     */
    public static int TOURNAMENT_SIZE = 20;

    /**
     * Penalty applied when an employee's total scheduled hours exceed their weekly hour limit.
     */
    public static final int WEEKLY_HOURS_VIOLATION_PENALTY = 100;

    /**
     * Penalty applied when an employee is scheduled for more than one shift on the same day.
     */
    public static final int DAILY_HOURS_VIOLATION_TOURNAMENT = 50;

}
