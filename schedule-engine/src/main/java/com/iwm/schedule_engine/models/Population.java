package com.iwm.schedule_engine.models;

import com.iwm.schedule_engine.configurations.FGAConfigs;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


/**
 * This class represents a population of schedules required for the genetic algorithm.
 * @version 1
 */
public class Population {

    @Setter
    @Getter
    List<WeeklyScheduleChromosome> population = new ArrayList<>();

    /**
     * Defines size of the population of candidate solutions for HO.
     */
    private final int POPULATION_SIZE ;

    /**
     * List of employees that will be assigned in shifts.
     */
    private final List<Employee> employees;

    /**
     * List of dates that shifts needs to be generated.
     */
    private final List<LocalDate> SHIFT_DATES;

    /**
     * Defines the business start time in minutes.
     */
    private final int BUSINESS_START_TIME;

    /**
     * Defines the business end time in minutes.
     */
    private final int BUSINESS_END_TIME;

    /**
     * Defines the minimum number of employees per shift.
     */
    private final int MINIMUM_EMPLOYEES_PER_SHIFT;

    private final double MINIMUM_HOURS_PER_SHIFT;
    private final double MAXIMUM_HOURS_PER_SHIFT;

    /**
     * Random number generator
     */
    private final Random random = new Random();

    /**
     * This class represents a population of schedules required for the genetic algorithm.
     * @param employees List of employees.
     * @param startDate Start date of the schedule.
     * @param endDate End date of the schedule
     */
    public Population(List<Employee> employees, LocalDate startDate,
                      LocalDate endDate, BusinessConfigs configs) throws IOException {

        this.POPULATION_SIZE = FGAConfigs.POPULATION_SIZE;
        this.employees = employees;
        this.SHIFT_DATES = getDatesBetween(startDate, endDate);
        this.BUSINESS_START_TIME = configs.businessOpeningTime().getMinute();
        this.BUSINESS_END_TIME = configs.businessClosingTime().getMinute();
        this.MINIMUM_EMPLOYEES_PER_SHIFT = configs.minimumEmployeesPerShift();
        this.MINIMUM_HOURS_PER_SHIFT = configs.minimumHoursPerShift();
        this.MAXIMUM_HOURS_PER_SHIFT = configs.maximumHoursPerShift();
        this.generatePopulation();
    }

    /**
     * Generates a list of dates between two given dates
     * @param startDate Start of the dates range.
     * @param endDate End of the dates range (to be inclusive in the list)
     * @return A list of dates.
     */
     private List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dateList = new ArrayList<>();
        int numDays = (int) ChronoUnit.DAYS.between(startDate, endDate)+1; // +1 to include end day.
        for (int i = 0; i < numDays; i++) {
            dateList.add(startDate.plusDays(i));
        }
        return dateList;
    }

    /**
     * This function initialises a pool of randomly generated shifts.
     */
    private void generatePopulation() {

        // Until population size if POPULATION_SIZE
        //      1. Generate a random Schedule for the week
        //      2. Add it to the population
        for(int i=1;i<=POPULATION_SIZE;i++){
            population.add(generateRandomSchedule());
        }
    }

    /**
     * This function generates a random schedule.
     * @return A schedule generated randomly.
     */
    private WeeklyScheduleChromosome generateRandomSchedule() {

        WeeklyScheduleChromosome weeklyScheduleChromosome = new WeeklyScheduleChromosome();
        Map<Employee, List<LocalDate>> employeeDateMap = new HashMap<>();

        // Generate shifts for each day on the demand list
        for(LocalDate date: SHIFT_DATES) {
            for (int type = 0; type < 4; type++) {


                /*
                    Business
                 */
                for (int i = (BUSINESS_START_TIME /6); i < MINIMUM_EMPLOYEES_PER_SHIFT; i++) {
                    Shift shift = generateRandomShift(type,date,employeeDateMap);
                    weeklyScheduleChromosome.addShift(shift);
                }
            }
        }
        return weeklyScheduleChromosome;

    }


    /**
     * This function generates a random Shift in a given quarter of the day,
     * @param quarter Quarter of the day in which shift is starting:
     * <ul>
     *   <li>{@code 0} — 00:00 to 06:00</li>
     *   <li>{@code 1} — 06:00 to 12:00</li>
     *   <li>{@code 2} — 12:00 to 18:00</li>
     *   <li>{@code 3} — 18:00 to 24:00</li>
     * </ul>
     * Intervals are half-open: {@code [start, end)} unless stated otherwise.
     * @param date Date of the shift
     * @return A Shift object with given data.
     */
     public Shift generateRandomShift(int quarter,
                                      LocalDate date,
                                      Map<Employee, List<LocalDate>> employeeDateMap) {

        // Determine start and end time of the shifts depending on the type
        int startTimeInMinutes;
        int endTimeInMinutes = switch (quarter) {
            case  0-> {
                startTimeInMinutes = getRandomStartTime(0,6);
                yield getRandomEndTime(startTimeInMinutes);
            }
            case 1 -> {
                startTimeInMinutes = getRandomStartTime(6,12);
                yield getRandomEndTime(startTimeInMinutes);
            }
            case 2 -> {
                startTimeInMinutes = getRandomStartTime(12,18);
                yield getRandomEndTime(startTimeInMinutes);
            }
            case 3 -> {
                startTimeInMinutes = getRandomStartTime(18,24);
                yield 23 * 60;
            }
            default -> throw new RuntimeException("Something went wrong");
        };


        Collections.shuffle(employees);
        Employee selectedEmployee=null;

        for(Employee emp :  employees){
            if (!employeeDateMap.getOrDefault(emp,new ArrayList<>()).contains(date)) {
                selectedEmployee = emp;
                List<LocalDate> employeeDateList = employeeDateMap.getOrDefault(emp,new ArrayList<>());
                employeeDateList.add(date);
                employeeDateMap.put(emp,employeeDateMap.getOrDefault(emp,employeeDateList));
                break;
            }
        }

        if (selectedEmployee == null){
            throw new RuntimeException("The selected employee is null");
        }

        return new Shift(date,startTimeInMinutes,endTimeInMinutes,selectedEmployee);

    }


    /**
     * Calculates an end time in minutes for a given start time in minutes
     * @param startTime Start time in minutes
     * @return End time in minutes.
     */
    public int getRandomEndTime(int startTime) {
        return startTime + (int)(MINIMUM_HOURS_PER_SHIFT*60)+
                random.nextInt(0,(int)((MAXIMUM_HOURS_PER_SHIFT-MINIMUM_HOURS_PER_SHIFT)/0.25))*15;
    }


    /**
     * Generates a random shift start time (in minutes since midnight) within a 6-hour bucket,
     * then applies a random backward/forward jitter of {@code 0}, {@code 15}, {@code 30}, or {@code 45} minutes.
     *
     * <p><strong>Bucket semantics:</strong> the base <em>hour-of-day</em> is chosen uniformly from the
     * half-open interval {@code [periodStart, periodEnd)}. Valid bucket boundaries are:
     * <ul>
     *   <li>{@code periodStart} ∈ {0, 6, 12, 18}</li>
     *   <li>{@code periodEnd}   ∈ {6, 12, 18, 24}</li>
     *   <li>{@code periodEnd - periodStart == 6}</li>
     * </ul>
     * The returned value is computed as {@code baseHour * 60 ± offset}, where
     * {@code offset} ∈ {0, 15, 30, 45} minutes.</p>
     *
     * <p><strong>Notes:</strong>
     * <ul>
     *   <li>Because of the jitter, results can fall <em>before</em> {@code periodStart} or
     *       <em>after</em> {@code periodEnd}. For example, with {@code periodStart = 0} a
     *       negative result (e.g., {@code -15}) is possible; near {@code 24:00} values can
     *       exceed {@code 1440}. Clamp or wrap as needed for your use case.</li>
     *   <li>Base hour selection uses {@link java.util.Random#nextInt(int, int)} with an exclusive
     *       upper bound (requires Java 17+).</li>
     * </ul>
     *
     * @param periodStart inclusive lower bound hour-of-day for the bucket (must be one of 0, 6, 12, 18)
     * @param periodEnd   exclusive upper bound hour-of-day for the bucket (must be one of 6, 12, 18, 24 and exactly 6 hours after {@code periodStart})
     * @return start time expressed as minutes since midnight, after applying a ±(0|15|30|45) minute jitter
     * @throws IllegalArgumentException if the provided boundaries are not valid 6-hour bucket limits
     */
    public int getRandomStartTime(int periodStart, int periodEnd ) {

        if (!Set.of(0,6,12,18).contains(periodStart)) {
            throw new IllegalArgumentException("Incorrect period start");
        }
        if (!Set.of(6,12,18,24).contains(periodStart+6)) {
            throw new IllegalArgumentException("Incorrect period end");
        }
        final int base = random.nextInt(periodStart, periodEnd) * 60;
        return base+getSignedJitter();
    }

    /**
     * Produces a signed jitter in minutes to nudge a base time earlier or later.
     *
     * <p>The magnitude is chosen uniformly from {@code {0, 15, 30, 45}} minutes
     * and the sign is chosen uniformly from {@code {-, +}}. The resulting value
     * is therefore one of:
     * {@code {-45, -30, -15, 0, 15, 30, 45}}.
     * Note that {@code 0} occurs with higher probability because both signs yield 0.</p>
     *
     * <p>Intended to be added to a “minutes since midnight” base time.</p>
     *
     * @return a jitter in minutes in the set {@code {-45, -30, -15, 0, 15, 30, 45}}
     *
     * @implNote Uses {@link java.util.Random#nextInt(int, int)} with an exclusive upper
     * bound (available in Java 17+). If this method may be called from multiple threads,
     * consider {@link java.util.concurrent.ThreadLocalRandom} or otherwise guard access
     * to the shared {@code random} instance.
     */
    private int getSignedJitter(){

        final int jitter = 15 * random.nextInt(0, 4);
        return switch (random.nextInt(0, 2)) {
            case 0 -> -jitter;
            case 1 -> +jitter;
            default -> 0;
        };
    }



}