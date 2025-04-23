package com.iwm.schedule_engine.models.mappers;

import com.iwm.schedule_engine.models.Employee;
import com.iwm.schedule_engine.models.Shift;
import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;
import com.iwm.schedule_engine.models.dtos.SchedEngShiftDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Shift Mapper Test")
class ShiftMapperTest {


    @Test
    @DisplayName("Shift Mapper: ShedEngShiftDTO to Shift test")
    void toShifts() {

        SchedEngEmpDTO dumEmp = new SchedEngEmpDTO();
        dumEmp.setId(1);
        dumEmp.setName("John");
        dumEmp.setRole("Manager");
        dumEmp.setHoursPreference(1.0);
        dumEmp.setMaxHoursPerWeek(10.0);
        dumEmp.setCost(10.0);

        LocalDate date = LocalDate.now();

        SchedEngShiftDTO schedEngShiftDTO = new SchedEngShiftDTO();
        schedEngShiftDTO.setShiftId(1);
        schedEngShiftDTO.setScheduleId(2);
        schedEngShiftDTO.setEmployee(dumEmp);
        schedEngShiftDTO.setDate(date);
        schedEngShiftDTO.setStartTimeInMinutes(10);
        schedEngShiftDTO.setEndTimeInMinutes(20);

        Shift shift =  ShiftMapper.toShift(schedEngShiftDTO);

        assertEquals(shift.getShiftId(), schedEngShiftDTO.getShiftId(), "Shift ID must match!");
        assertEquals(shift.getDate(), schedEngShiftDTO.getDate(), "Shift Date must match!");
        assertEquals(shift.getEmployee().getId(),
                schedEngShiftDTO.getEmployee().getId(), "Shift Employee ID must match!");
        assertEquals(shift.getStartTimeInMinutes(),
                schedEngShiftDTO.getStartTimeInMinutes(), "Shift Start Time must match!");
        assertEquals(shift.getEndTimeInMinutes(),
                schedEngShiftDTO.getEndTimeInMinutes(), "Shift End Time must match!");
        assertEquals(shift.getScheduleId(),
                schedEngShiftDTO.getScheduleId(), "Shift Schedule ID must match!");


    }


    @Test
    @DisplayName("Shift Mapper: Shift to ShedEngShiftDTO test")
    void toSchedEngShiftDTO() {
        LocalDate date = LocalDate.now();

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John");
        employee.setRole("Manager");
        employee.setHoursPreference(1.0);
        employee.setMaxHoursPerWeek(10.0);
        employee.setCost(10.0);


        Shift shift = new Shift();
        shift.setShiftId(1);
        shift.setDate(date);
        shift.setStartTimeInMinutes(123);
        shift.setEndTimeInMinutes(456);
        shift.setScheduleId(2);
        shift.setEmployee(employee);

        SchedEngShiftDTO schedEngShiftDTO = ShiftMapper.toSchedEngShiftDTO(shift);

        assertEquals(shift.getShiftId(), schedEngShiftDTO.getShiftId(), "Shift ID must match!");
        assertEquals(shift.getDate(), schedEngShiftDTO.getDate(), "Shift Date must match!");
        assertEquals(shift.getEmployee().getId(),
                schedEngShiftDTO.getEmployee().getId(), "Shift Employee ID must match!");
        assertEquals(shift.getStartTimeInMinutes(),
                schedEngShiftDTO.getStartTimeInMinutes(), "Shift Start Time must match!");
        assertEquals(shift.getEndTimeInMinutes(),
                schedEngShiftDTO.getEndTimeInMinutes(), "Shift End Time must match!");
        assertEquals(shift.getScheduleId(), schedEngShiftDTO.getScheduleId(), "Shift Schedule ID must match!");
    }
}