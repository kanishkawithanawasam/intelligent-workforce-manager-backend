package com.iwm.backend.modules.availability;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * This class represents daily availability from 0000 hours to 2359 hours.
 * @version 1.0
 * @author kanishka withanawasam
 */
@Entity
@Getter
@Setter
public class Availability {

    @Id
    private long dateId;
    private long empId;
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
