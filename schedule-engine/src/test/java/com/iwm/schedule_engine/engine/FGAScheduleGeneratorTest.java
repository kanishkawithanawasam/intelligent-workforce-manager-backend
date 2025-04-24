package com.iwm.schedule_engine.engine;

import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;
import com.iwm.schedule_engine.models.dtos.SchedEngShiftDTO;
import com.iwm.schedule_engine.support.DemandReader;
import com.iwm.schedule_engine.support.EmloyeesReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@DisplayName("Fuzzy Genetic Schedules Generator Test")
class FGAScheduleGeneratorTest {


    @Test
    @DisplayName("Schedules Generator output test")
    void genSchedule() throws IOException {

        FGAScheduleGenerator generator = new FGAScheduleGenerator(
                EmloyeesReader.readEmployees(),LocalDate.now()
        );

        List<SchedEngShiftDTO> shifts = generator.genSchedule().getShifts();
        Assertions.assertNotNull(shifts, "Generated schedule must contain shifts list!");
        Assertions.assertFalse(shifts.isEmpty(), "Generated schedule's shift list must not be empty!");

        for(SchedEngShiftDTO shift : shifts) {
            Assertions.assertNotNull(shift.getDate(), "Shift date must not be null!");
            Assertions.assertNotNull(shift.getEmployee(), "Shift employee must not be null!");
        }
    }

}