package com.iwm.backend.modules.shift;

import com.iwm.backend.modules.employee.EmployeeEM;
import com.iwm.backend.modules.schedules.WeeklyScheduleEM;
import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;
import com.iwm.schedule_engine.models.dtos.SchedEngShiftDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShiftDTOMapperTest {

    @Test
    @DisplayName("SchedEngShiftDTO to ShiftDTO Test")
    void toShiftDTO() {
        SchedEngEmpDTO employee = new SchedEngEmpDTO();
        employee.setId(1L);
        employee.setName("John");

        SchedEngShiftDTO input = new SchedEngShiftDTO();
        input.setShiftId(1L);
        input.setStartTimeInMinutes(540); // 9:00
        input.setEndTimeInMinutes(1020); // 17:00
        input.setDate(LocalDate.now());
        input.setEmployee(employee);
        input.setScheduleId(1L);

        ShiftDTO result = ShiftDTOMapper.toShiftDTO(input);

        assertEquals(input.getShiftId(), result.getShiftId(), "Shift ID should match");
        assertEquals(LocalTime.of(9, 0), result.getStartTime(), "Start time should be 9:00");
        assertEquals(LocalTime.of(17, 0), result.getEndTime(), "End time should be 17:00");
        assertEquals(input.getDate(), result.getDate(), "Date should match");
        assertEquals(input.getEmployee().getId(), result.getEmployeeId(), "Employee ID should match");
        assertEquals(input.getEmployee().getName(), result.getEmployeeName(), "Employee name should match");
        assertEquals(input.getScheduleId(), result.getScheduleId(), "Schedule ID should match");
    }

    @Test
    @DisplayName("ShiftDTO to ShiftEM Test")
    void toShiftEM() {
        ShiftDTO input = new ShiftDTO();
        input.setShiftId(1L);
        input.setStartTime(LocalTime.of(9, 0));
        input.setEndTime(LocalTime.of(17, 0));
        input.setDate(LocalDate.now());
        input.setEmployeeId(1L);
        input.setScheduleId(1L);

        ShiftEM result = ShiftDTOMapper.toShiftEM(input);

        assertEquals(input.getShiftId(), result.getId(), "Shift ID should match");
        assertEquals(input.getStartTime(), result.getStartTime(), "Start time should match");
        assertEquals(input.getEndTime(), result.getEndTime(), "End time should match");
        assertEquals(input.getDate(), result.getDate(), "Date should match");
        assertEquals(input.getEmployeeId(), result.getEmployee().getId(), "Employee ID should match");
        assertEquals(input.getScheduleId(), result.getWeeklySchedule().getId(), "Schedule ID should match");
    }

    @Test
    @DisplayName("ShiftEM to ShiftDTO Test")
    void testToShiftDTO() {
        EmployeeEM employee = new EmployeeEM();
        employee.setId(1L);
        employee.setFirstName("John");

        WeeklyScheduleEM schedule = new WeeklyScheduleEM();
        schedule.setId(1L);

        ShiftEM input = new ShiftEM();
        input.setId(1L);
        input.setStartTime(LocalTime.of(9, 0));
        input.setEndTime(LocalTime.of(17, 0));
        input.setDate(LocalDate.now());
        input.setEmployee(employee);
        input.setWeeklySchedule(schedule);

        ShiftDTO result = ShiftDTOMapper.toShiftDTO(input);

        assertEquals(input.getId(), result.getShiftId(), "Shift ID should match");
        assertEquals(input.getStartTime(), result.getStartTime(), "Start time should match");
        assertEquals(input.getEndTime(), result.getEndTime(), "End time should match");
        assertEquals(input.getDate(), result.getDate(), "Date should match");
        assertEquals(input.getEmployee().getId(), result.getEmployeeId(), "Employee ID should match");
        assertEquals(input.getEmployee().getFirstName(), result.getEmployeeName(), "Employee name should match");
        assertEquals(input.getWeeklySchedule().getId(), result.getScheduleId(), "Schedule ID should match");
    }

    @Test
    @DisplayName("ShiftDTO to SchedEngShiftDTO Test")
    void toSchedEngShiftDTO() {
        ShiftDTO input = new ShiftDTO();
        input.setShiftId(1L);
        input.setStartTime(LocalTime.of(9, 0));
        input.setEndTime(LocalTime.of(17, 0));
        input.setDate(LocalDate.now());
        input.setEmployeeId(1L);
        input.setEmployeeName("John");
        input.setScheduleId(1L);

        SchedEngShiftDTO result = ShiftDTOMapper.toSchedEngShiftDTO(input);

        assertEquals(input.getShiftId(), result.getShiftId(), "Shift ID should match");
        assertEquals(540, result.getStartTimeInMinutes(), "Start time should be 540 minutes (9:00)");
        assertEquals(1020, result.getEndTimeInMinutes(), "End time should be 1020 minutes (17:00)");
        assertEquals(input.getDate(), result.getDate(), "Date should match");
        assertEquals(input.getEmployeeId(), result.getEmployee().getId(), "Employee ID should match");
        assertEquals(input.getEmployeeName(), result.getEmployee().getName(), "Employee name should match");
        assertEquals(input.getScheduleId(), result.getScheduleId(), "Schedule ID should match");
    }

    @Test
    @DisplayName("ShiftDTO List to List of ShiftEM Test")
    void toShiftEMList() {
        List<ShiftDTO> input = List.of(
                new ShiftDTO() {{
                    setShiftId(1L);
                    setStartTime(LocalTime.of(9, 0));
                    setEndTime(LocalTime.of(17, 0));
                    setDate(LocalDate.now());
                    setEmployeeId(1L);
                    setScheduleId(1L);
                }}
        );

        List<ShiftEM> result = ShiftDTOMapper.toShiftEMList(input);

        assertEquals(1, result.size(), "Result list should contain exactly one item");
        assertEquals(input.get(0).getShiftId(), result.get(0).getId(), "Shift ID should match");
    }

    @Test
    @DisplayName("List of ShiftEM to List of ShiftDTO Test")
    void toShiftDTOList() {
        EmployeeEM employee = new EmployeeEM();
        employee.setId(1L);
        employee.setFirstName("John");

        WeeklyScheduleEM schedule = new WeeklyScheduleEM();
        schedule.setId(1L);

        List<ShiftEM> input = List.of(
                new ShiftEM() {{
                    setId(1L);
                    setStartTime(LocalTime.of(9, 0));
                    setEndTime(LocalTime.of(17, 0));
                    setDate(LocalDate.now());
                    setEmployee(employee);
                    setWeeklySchedule(schedule);
                }}
        );

        List<ShiftDTO> result = ShiftDTOMapper.toShiftDTOList(input);

        assertEquals(1, result.size(), "Result list should contain exactly one item");
        assertEquals(input.get(0).getId(), result.get(0).getShiftId(), "Shift ID should match");
    }

    @Test
    @DisplayName("ShiftDTO List to List of SchedEngShiftDTO Test")
    void toSchedEngShiftDTOList() {
        List<ShiftDTO> input = List.of(
                new ShiftDTO() {{
                    setShiftId(1L);
                    setStartTime(LocalTime.of(9, 0));
                    setEndTime(LocalTime.of(17, 0));
                    setDate(LocalDate.now());
                    setEmployeeId(1L);
                    setEmployeeName("John");
                    setScheduleId(1L);
                }}
        );

        List<SchedEngShiftDTO> result = ShiftDTOMapper.toSchedEngShiftDTOList(input);

        assertEquals(1, result.size(), "Result list should contain exactly one item");
        assertEquals(input.get(0).getShiftId(), result.get(0).getShiftId(), "Shift ID should match");
    }
}