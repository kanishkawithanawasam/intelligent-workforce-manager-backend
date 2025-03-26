package com.iwm.backend.schedulegenerator.configurations;

/**
 * This class represents optimisation parameters used by the genetic algorithm to generate employee schedules.
 * @author kanishka withanawasam
 * @version 1.0
 */
public class FGAConfigs {

    private final int numberOfIterations = 50000;
    private final int populationSize = 20;

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public int getPopulationSize() {
        return populationSize;
    }
}
