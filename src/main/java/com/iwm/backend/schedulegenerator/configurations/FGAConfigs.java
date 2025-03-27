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
    private final int NUMBER_OF_ITERATIONS = 50000;

    /**
     * The number of individuals in the population per generation.
     */
    private final int POPULATION_SIZE = 20;

    /**
     * The number of individuals selected to compete in tournament selection.
     * Higher values increase selection pressure.
     */
    private final int TOURNAMENT_SIZE = 20;

    /**
     * Penalty applied when an employee's total scheduled hours exceed their weekly hour limit.
     */
    private final int WEEKLY_HOURS_VIOLATION_PENALTY = 5000;

    /**
     * Penalty applied when an employee is scheduled for more than one shift on the same day.
     */
    private final int DAILY_HOURS_VIOLATION_TOURNAMENT = 5000;

    public int getNUMBER_OF_ITERATIONS() {
        return NUMBER_OF_ITERATIONS;
    }

    public int getPOPULATION_SIZE() {
        return POPULATION_SIZE;
    }

    public int getTOURNAMENT_SIZE() {
        return TOURNAMENT_SIZE;
    }

    public int getWEEKLY_HOURS_VIOLATION_PENALTY() {
        return WEEKLY_HOURS_VIOLATION_PENALTY;
    }

    public int getDAILY_HOURS_VIOLATION_TOURNAMENT() {
        return DAILY_HOURS_VIOLATION_TOURNAMENT;
    }
}
