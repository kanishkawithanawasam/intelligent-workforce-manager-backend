package com.iwm.backend.modules.shift;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShiftService {

    private final ShiftRepository shiftRepository;

    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }


    public List<ShiftDTO> getWeeklySchedule(EmployeeScheduleShiftDTO requestDTO) {
        List<ShiftEM> shiftEMList = shiftRepository.
                findByWeeklyScheduleAndEmployee(requestDTO.getScheduleId(),requestDTO.getEmployeeId());
        return ShiftDTOMapper.toShiftDTOList(shiftEMList);
    }

    public ShiftDTO saveShift(ShiftDTO shiftDTO) {
        ShiftEM shiftEM = ShiftDTOMapper.toShiftEM(shiftDTO);
        return ShiftDTOMapper.toShiftDTO(shiftRepository.save(shiftEM));
    }

    public List<ShiftDTO> saveAllShifts(List<ShiftDTO> shiftDTOList) {
        List<ShiftEM> shiftEMList = ShiftDTOMapper.toShiftEMList(shiftDTOList);
        return ShiftDTOMapper.toShiftDTOList(shiftRepository.saveAll(shiftEMList));
    }

    public List<ShiftDTO> findByDate(LocalDate date) {
        return ShiftDTOMapper.toShiftDTOList(shiftRepository.findShiftEMByDate(date));
    }
}
