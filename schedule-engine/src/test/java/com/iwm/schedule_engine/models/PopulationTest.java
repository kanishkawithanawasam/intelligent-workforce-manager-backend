package com.iwm.schedule_engine.models;


import com.iwm.schedule_engine.models.mappers.EmployeeMapper;
import com.iwm.schedule_engine.support.EmloyeesReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

@DisplayName( "Population Test")
class PopulationTest {

    @Test
    @DisplayName("Population: getPopulation test")
    void getPopulation() throws IOException {

        Population population = new Population(
                EmployeeMapper.toEmployees(EmloyeesReader.readEmployees()), LocalDate.now());
        Assertions.assertNotNull(population.getPopulation(), "Population must not be null!");
        Assertions.assertFalse(population.getPopulation().isEmpty(), "Population must not be empty!");

    }

}