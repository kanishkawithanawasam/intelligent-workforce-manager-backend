package com.iwm.backend.modules.employee;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public List<EmployeeForScheduleEngine> generateEmployeeForSchedule(){
        List<EmployeeEM> employees = employeeRepository.findAll();
        return EmployeeMapper.toEmployeeForScheduleEngineList(employees);
    }

    @Transactional
    public List<EmployeeBasicInfoDTO> getEmployeesBasicInfo(){
        List<EmployeeBasicInfoDTO> employeeBasicInfoDTOS = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> {
            EmployeeBasicInfoDTO employeeBasicInfoDTO = new EmployeeBasicInfoDTO();
            employeeBasicInfoDTO.setId(employee.getId());
            employeeBasicInfoDTO.setFirstName(employee.getFirstName());
            employeeBasicInfoDTO.setLastName(employee.getLastName());
            employeeBasicInfoDTOS.add(employeeBasicInfoDTO);
        });
        return employeeBasicInfoDTOS;
    }

    @Transactional
    public EmployeeDTO getEmployee(long employeeId) {
        EmployeeEM employeeEM = employeeRepository.findById(employeeId).orElse(null);
        if (employeeEM != null) {
           return EmployeeMapper.toEmployeeDTO(employeeEM);
        }
        return null;
    }

    @Transactional
    public EmployeeDTO saveEmployee(EmployeeDTO dto) {
        EmployeeEM emp=employeeRepository.save(EmployeeMapper.toEmployeeEM(dto));
        return EmployeeMapper.toEmployeeDTO(emp);
    }

}
