package com.iwm.backend.modules.employee;


import com.iwm.backend.modules.schedule_engine.models.Employee;
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
    public List<Employee> getEmployeesForScheduling(){
        return EmployeeDomainMapper.toDomainList(employeeRepository.findAll());
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
        EmployeeDTO employeeDTO = new EmployeeDTO();

        EmployeeEM employeeEM = employeeRepository.findById(employeeId).orElse(null);
        if (employeeEM != null) {
           return EmployeeDTOMapper.toEmployeeDTO(employeeEM);
        }
        return null;
    }

    @Transactional
    public EmployeeDTO saveEmployee(EmployeeDTO dto) {
        EmployeeEM emp=employeeRepository.save(EmployeeDTOMapper.toEmployeeEM(dto));
        return EmployeeDTOMapper.toEmployeeDTO(emp);
    }

}
