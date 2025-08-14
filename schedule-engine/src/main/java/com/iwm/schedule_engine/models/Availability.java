package com.iwm.schedule_engine.models;

import java.time.LocalTime;


/**
 * This class defines the availability of a single day
 * @param day Day of the week: {@code 0 = Monday} -> {@code 6 = Sunday}
 * @param start Availability start time
 * @param end availability end time
 */
public record Availability(
        int day,
        LocalTime start,
        LocalTime end
){
}
