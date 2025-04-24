package com.iwm.backend.modules.day_schedules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iwm.backend.modules.contract.ContractDataEM;
import com.iwm.backend.modules.demand.DemandService;
import com.iwm.backend.modules.shift.ShiftDTO;
import com.iwm.backend.modules.shift.ShiftDTOMapper;
import com.iwm.backend.modules.shift.ShiftEM;
import com.iwm.backend.modules.shift.ShiftService;
import com.iwm.schedule_engine.engine.HourlyScheduleOptimiser;
import com.iwm.schedule_engine.models.HourlyDemand;
import com.iwm.schedule_engine.models.dtos.SchedEngShiftDTO;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;


/**
 * Service class responsible for managing and optimizing daily work schedules.
 * <p>
 * This service provides functionality to:
 * <ul>
 *   <li>Retrieve and calculate costs for today's work schedule</li>
 *   <li>Optimise shift schedules based on hourly demand requirements</li>
 * </ul>
 */
@Service
public class DayScheduleService {

    private final ShiftService shiftService;
    private final DemandService demandService;

    /**
     * Constructs a new DayScheduleService with required dependencies.
     *
     * @param shiftService  service for managing employee shifts
     * @param demandService service for managing staffing demand data
     */
    public DayScheduleService(ShiftService shiftService, DemandService demandService) {
        this.shiftService = shiftService;
        this.demandService =demandService;
    }

    /**
     * Retrieves and constructs the schedule for the current day, including total labor cost.
     * <p>
     * This method queries the shift database for all shifts scheduled on the current date
     * using the {@link ShiftService}. It then calculates the total cost of these shifts by:
     * <ul>
     *   <li>Retrieving each employee's most recent hourly rate from their contract history.</li>
     *   <li>Computing the duration of each shift in hours.</li>
     *   <li>Multiplying the duration by the hourly rate to get the shift's cost.</li>
     * </ul>
     * The total cost is accumulated and returned along with the list of shift DTOs in a
     * {@link DayScheduleDTO} object.
     *
     * @return a {@link DayScheduleDTO} containing the list of today's shifts and their total cost
     * @see ShiftEM
     * @see ContractDataEM
     * @see ShiftDTOMapper
     * @see DayScheduleDTO
     */
    public DayScheduleDTO getTodaySchedule(){
        DayScheduleDTO dayScheduleDTO = new DayScheduleDTO();
        List<ShiftEM> shifts = shiftService.findByDate(LocalDate.now());

        double cost = 0;
        for (ShiftEM shift : shifts) {
            List<ContractDataEM> contracts = shift.getEmployee().getContractData();
            double rate = contracts.get(contracts.size()-1).getHourlyRate();
            cost+=(Duration.between(shift.getStartTime(),
                    shift.getEndTime()).toMinutes()/60.0)*rate;
        }
        dayScheduleDTO.setCost(cost);
        dayScheduleDTO.setShifts(ShiftDTOMapper.toShiftDTOList(shifts));
        return dayScheduleDTO;
    }

    /**
     * Optimises the given list of shifts for the current day based on predefined hourly demand.
     * <p>
     * This method takes a list of {@link ShiftDTO} objects representing the current schedule
     * and adjusts the shifts to better match a hardcoded hourly demand profile.
     * The demand profile currently covers the hours of 13:00 to 15:00 with corresponding
     * required staffing levels. The optimisation is performed by delegating to the
     * {@link HourlyScheduleOptimiser}, which applies internal scheduling heuristics or algorithms
     * to balance the shift allocations against the demand.
     * <p>
     * The method performs the following steps:
     * <ol>
     *   <li>Constructs a hardcoded {@link HourlyDemand} object for the current day.</li>
     *   <li>Maps the input {@code ShiftDTO} list to a list of {@code SchedEngShiftDTO}.</li>
     *   <li>Initialises an {@code HourlyScheduleOptimiser} with the shift data and demand.</li>
     *   <li>Retrieves the optimised shift list from the optimiser.</li>
     *   <li>Maps the result back to a list of {@code ShiftDTO} and returns it.</li>
     * </ol>
     *
     * @param dtos the list of {@link ShiftDTO} objects representing the original day schedule
     * @return a list of {@link ShiftDTO} objects representing the optimised schedule
     *         that aligns with the predefined hourly demand
     * @see HourlyScheduleOptimiser
     * @see HourlyDemand
     * @see ShiftDTO
     * @see SchedEngShiftDTO
     */
    public List<ShiftDTO> optimiseDaySchedule(List<ShiftDTO> dtos){

        List<SchedEngShiftDTO> schedEngInput = ShiftDTOMapper.toSchedEngShiftDTOList(dtos);

        // Prepared the demand
        HourlyDemand demand;
        try {
            demand= new HourlyDemand(LocalDate.now(),demandService.getWeeklyDemand(10,3));
        }catch (JsonProcessingException e) {
            throw new RuntimeException("Error retrieving hourly demand");
        }
        HourlyScheduleOptimiser optimiser = new HourlyScheduleOptimiser(schedEngInput, demand);
        return ShiftDTOMapper.toShiftDTOList(optimiser.getOptimisedRealTimeSchedule());
    }
}
