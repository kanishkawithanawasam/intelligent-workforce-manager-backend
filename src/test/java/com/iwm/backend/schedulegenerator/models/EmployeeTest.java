package com.iwm.backend.schedulegenerator.models;

import com.iwm.backend.api.models.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    Employee emp1 = new Employee(
            1,
            "TestName1",
            "TestRole1",
            30.0,
            35.0,
            11.45
    );

    Employee emp2 = new Employee(
            1,
            "TestName2",
            "TestRole2",
            25.0,
            35.0,
            12.30
    );

    @Test
    void getId() {
        assertEquals(1, emp1.getId());
    }

    @Test
    void getName() {
        assertEquals("TestName1", emp1.getName());
    }

    @Test
    void setName() {
        emp1.setName("TestName3");
        assertEquals("TestName3", emp1.getName());
    }

    @Test
    void getRole() {
        assertEquals("TestRole1", emp1.getRole());
    }

    @Test
    void setRole() {
        emp1.setRole("TestRole3");
        assertEquals("TestRole3", emp1.getRole());
    }

    @Test
    void getWeeklyHoursPreference() {
        emp1.setHoursPreference(33.5);
        assertEquals(33.5, emp1.getHoursPreference());
    }

    @Test
    void setWeeklyHoursPreference() {
        emp1.setHoursPreference(30.5);
        assertEquals(30.5, emp1.getHoursPreference());
    }

    @Test
    void getMaxWeeklyHours() {
        assertEquals(35.0, emp1.getMaxHoursPerWeek());
    }

    @Test
    void getCost() {
        assertEquals(11.45, emp1.getCost());
    }

    @Test
    void getCurrentWorkingDays() {
        emp1.getCurrentWorkingDays().add("2025-03-12");
        emp1.getCurrentWorkingDays().add("2025-03-13");
        assertEquals(2, emp1.getCurrentWorkingDays().size());
    }

    @Test
    void getTotalWorkingHours() {
        emp1.setTotalWorkedHours(12.11);
    }

    @Test
    void setTotalWorkingHours() {
        emp1.setTotalWorkedHours(22.11);
        assertEquals(22.11, emp1.getTotalWorkedHours());
    }

    @Test
    void isAvailable() {
        emp1.getCurrentWorkingDays().add("2025-03-14");
        assertEquals(false,emp1.isAvailable("2025-03-14",4.5));
        emp1.setTotalWorkedHours(36.0);
        assertEquals(false,emp1.isAvailable("2025-03-14",4.5));
    }
}