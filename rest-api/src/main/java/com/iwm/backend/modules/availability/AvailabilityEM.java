package com.iwm.backend.modules.availability;

import com.iwm.backend.modules.employee.EmployeeEM;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

/**
 * This class represents daily availability from 0000 hours to 2359 hours.
 * @version 1.0
 * @author kanishka withanawasam
 */
@Entity
@Data
@Table(name = "Availability")
public class AvailabilityEM {

    @Id
    private long dateId;

    @ManyToOne
    private EmployeeEM empId;

    @NotNull
    private LocalTime fromTime;
    @NotNull
    private LocalTime toTime;

    @PrePersist
    @PreUpdate
    private void validate() {
        if (this.fromTime == null || this.toTime == null) {
            throw new IllegalStateException("fromTime and toTime cannot be null!");
        }

        if (!this.fromTime.isBefore(this.toTime)) {
            throw new IllegalStateException("fromTime must be before toTime!");
        }
    }
}
