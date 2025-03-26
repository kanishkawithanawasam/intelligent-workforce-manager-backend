package com.iwm.backend.schedulegenerator.configurations;

public class HSOConfigs {

    private final int iterations = 100;
    private final int populationSize = 200;
    private final double mutationIntensity = 0.5;


    public int getIterations() {
        return iterations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public double getMutationIntensity() {
        return mutationIntensity;
    }
}
