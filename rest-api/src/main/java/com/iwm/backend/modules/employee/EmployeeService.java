package com.iwm.backend.modules.employee;

import com.iwm.schedule_engine.models.dtos.SchedEngEmpDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing employee-related operations and business logic.
 * Handles employee data transformation, retrieval, and persistence operations.
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Generates a list of employees formatted for schedule engine processing.
     *
     * @return List of SchedEngEmpDTO containing employee data for scheduling
     */
    @Transactional
    public List<SchedEngEmpDTO> generateEmployeeForSchedule(){
        List<EmployeeEM> employees = employeeRepository.findAll();
        return EmployeeMapper.toEmployeeForScheduleEngineList(employees);
    }

    /**
     * Retrieves basic information for all employees.
     *
     * @return List of EmployeeBasicInfoDTO containing basic employee information
     */
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

    /**
     * Retrieves detailed employee information by ID.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return EmployeeDTO containing employee details, or null if not found
     */
    @Transactional
    public EmployeeDTO getEmployeeDTObyId(long employeeId) {
        EmployeeEM employeeEM = employeeRepository.findById(employeeId).orElse(null);
        if (employeeEM != null) {
           return EmployeeMapper.toEmployeeDTO(employeeEM);
        }
        return null;
    }

    /**
     * Retrieves employee entity by ID.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return EmployeeEM entity, or null if not found
     */
    @Transactional
    public EmployeeEM getEmployeeEMbyId(long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    /**
     * Saves or updates employee information.
     *
     * @param dto EmployeeDTO containing employee data to save
     * @return EmployeeDTO containing the saved employee data
     */
    @Transactional
    public EmployeeDTO saveEmployee(EmployeeDTO dto) {
        EmployeeEM emp=employeeRepository.save(EmployeeMapper.toEmployeeEM(dto));
        return EmployeeMapper.toEmployeeDTO(emp);
    }

}
