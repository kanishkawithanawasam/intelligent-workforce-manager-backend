package com.iwm.backend.api.services;


import com.iwm.backend.api.dtos.EmployeeBasicInfoDTO;
import com.iwm.backend.api.dtos.EmployeeFileDTO;
import com.iwm.backend.api.dtos.mappers.EmployeeDomainMapper;
import com.iwm.backend.api.models.ContractDataEM;
import com.iwm.backend.api.models.EmployeeEM;
import com.iwm.backend.api.repository.EmployeeRepository;
import com.iwm.backend.schedulegenerator.models.Employee;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDomainMapper employeeDomainMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeDomainMapper employeeDomainMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeDomainMapper = employeeDomainMapper;
    }

    @Transactional
    public List<Employee> getEmployeesForScheduling(){
        return employeeDomainMapper.toDomainList(employeeRepository.findAll());
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
    public EmployeeFileDTO getEmployeeFile(long employeeId) {
        EmployeeFileDTO employeeFileDTO = new EmployeeFileDTO();

        EmployeeEM employeeEM = employeeRepository.findById(employeeId).orElse(null);
        if (employeeEM != null) {
            employeeFileDTO.setId(employeeEM.getId());
            employeeFileDTO.setFirstName(employeeEM.getFirstName());
            employeeFileDTO.setLastName(employeeEM.getLastName());
            employeeFileDTO.setAddress(employeeEM.getAddress());
            employeeFileDTO.setContact(employeeEM.getContact());

            ContractDataEM contractDataEM = employeeEM.getContractData()
                    .get(employeeEM.getContractData().size() - 1);
            employeeFileDTO.setCurrentRole(contractDataEM.getRole());
            employeeFileDTO.setStartDate(contractDataEM.getStartDate());
            employeeFileDTO.setEndDate(contractDataEM.getEndDate());
            employeeFileDTO.setHourlyRate(contractDataEM.getHorlyRate());
            employeeFileDTO.setMaxHoursPerWeek(contractDataEM.getMaxHoursPerWeek());
            employeeFileDTO.setContractedHours(contractDataEM.getContractedHours());

            return employeeFileDTO;
        }
        return null;
    }

}
