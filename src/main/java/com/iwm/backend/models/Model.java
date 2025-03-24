package com.iwm.backend.models;

import org.springframework.stereotype.Component;


@Component
public class Model {

    private ScheduleTransferObject schedule;

    public ScheduleTransferObject getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleTransferObject schedule) {
        this.schedule = schedule;
    }
}
