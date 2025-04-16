package com.iwm.backend.api.shift;

import com.iwm.backend.api.dtos.EmployeeScheduleShiftDTO;
import com.iwm.backend.api.dtos.ShiftDTO;
import com.iwm.backend.api.dtos.mappers.ShiftDTOMapper;
import org.springframework.stereotype.Service;

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
}
