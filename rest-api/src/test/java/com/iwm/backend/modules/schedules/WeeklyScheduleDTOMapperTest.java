package com.iwm.backend.modules.schedules;

import com.iwm.backend.modules.employee.EmployeeEM;
import com.iwm.backend.modules.shift.ShiftDTO;
import com.iwm.backend.modules.shift.ShiftEM;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WeeklyScheduleDTOMapper Test")
class WeeklyScheduleDTOMapperTest {


    @Test
    @DisplayName("Test Converting WeeklyScheduleDTO to WeeklyScheduleEM")
    void toWeeklyScheduleEM() {
        WeeklyScheduleDTO dto = new WeeklyScheduleDTO();
        dto.setScheduleId(1L);
        dto.setScheduleStartDate(LocalDate.now());
        ShiftDTO shiftDTO = new ShiftDTO();
        shiftDTO.setScheduleId(1L);
        shiftDTO.setEmployeeId(1L);
        dto.setShifts(List.of(shiftDTO));

        WeeklyScheduleEM result = WeeklyScheduleDTOMapper.toWeeklyScheduleEM(dto);

        assertNotNull(result, "Converted WeeklyScheduleEM should not be null");
        assertEquals(dto.getScheduleId(), result.getId(), "Schedule ID should match");
        assertEquals(dto.getScheduleStartDate(), result.getScheduleStartDate(), "Schedule start date should match");
        assertNotNull(result.getShifts(), "Shifts list should not be null");
        assertEquals(1, result.getShifts().size(), "Should contain exactly one shift");
        result.getShifts().forEach(shift ->
                assertEquals(result, shift.getWeeklySchedule(), "Each shift should reference the parent schedule"));
    }

    @Test
    @DisplayName("Test Converting WeeklyScheduleEM to WeeklyScheduleDTO")
    void testToWeeklyScheduleDTO() {
        WeeklyScheduleEM em = new WeeklyScheduleEM();
        em.setId(1L);
        em.setScheduleStartDate(LocalDate.now());
        EmployeeEM employeeEM = new EmployeeEM();
        ShiftEM shiftEM = new ShiftEM();
        shiftEM.setEmployee(employeeEM);
        shiftEM.setWeeklySchedule(em);
        em.setShifts(List.of(shiftEM));

        WeeklyScheduleDTO result = WeeklyScheduleDTOMapper.toWeeklyScheduleDTO(em);

        assertNotNull(result, "Converted WeeklyScheduleDTO should not be null");
        assertEquals(em.getId(), result.getScheduleId(), "Schedule ID should match");
        assertEquals(em.getScheduleStartDate(), result.getScheduleStartDate(), "Schedule start date should match");
        assertNotNull(result.getShifts(), "Shifts list should not be null");
        assertEquals(1, result.getShifts().size(), "Should contain exactly one shift");
        result.getShifts().forEach(shift ->
                assertEquals(em.getId(), shift.getScheduleId(), "Each shift should reference the parent schedule ID"));
    }
}