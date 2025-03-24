package com.iwm.backend.schedulegenerator.configurations;

/**
 * This class represents optimisation parameters used by the genetic algorithm to generate employee schedules.
 * @author kanishka withanawasam
 * @version 1.0
 */
public class FGAConfigs {

    private int numberOfIterations;
    private int populationSize;

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }
}
