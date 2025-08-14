package com.iwm.backend.modules.employee;

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
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository,
                           EmployeeMapper employeeMapper) {

        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Generates a list of employees formatted for schedule engine processing.
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
     * @return {@link EmployeeDto} containing employee details
     * @throws EmployeeNotFoundException If an employee is not found with given ID.
     */
    @Transactional
    public EmployeeDto getEmployeeDTObyId(long employeeId) {
        EmployeeEM employeeEM = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        return EmployeeMapper.toEmployeeDTO(employeeEM);
    }

    /**
     * Saves or updates employee information.
     *
     * @param dto EmployeeDTO containing employee data to save
     * @return EmployeeDTO containing the saved employee data
     */
    @Transactional
    public EmployeeDto saveEmployee(EmployeeDto dto) {
        EmployeeEM emp=employeeRepository.save(EmployeeMapper.toEmployeeEM(dto));
        return EmployeeMapper.toEmployeeDTO(emp);
    }


    @Transactional
    public EmployeeEM getEmployeeEmByIdem(long id){
        return employeeRepository.findById(id).orElse(null);
    }

}
