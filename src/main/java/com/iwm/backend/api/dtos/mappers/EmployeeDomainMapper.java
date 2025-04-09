package com.iwm.backend.api.dtos.mappers;

import com.iwm.backend.api.models.ContractDataEM;
import com.iwm.backend.api.models.EmployeeEM;
import com.iwm.backend.api.models.EmployeePreferencesEM;
import com.iwm.backend.schedulegenerator.models.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeDomainMapper {

    public static Employee toDomain(EmployeeEM em){

        if(em.getContractData().isEmpty()){
            return new Employee();
        }else{
            int lastContractEntry = em.getContractData().size()-1;
            int lastPreferenceEntry = em.getPreferences().size()-1;

            ContractDataEM contractData =  em.getContractData().get(lastContractEntry);
            EmployeePreferencesEM preferences = em.getPreferences().get(lastPreferenceEntry);
            return new Employee(
                    em.getId(),
                    em.getFirstName()+ " "+ em.getFirstName(),
                    contractData.getRole(),
                    preferences.getPreferredHours(),
                    contractData.getMaxHoursPerWeek(),
                    contractData.getHorlyRate()
            );
        }
    }

    public static List<Employee> toDomainList(List<EmployeeEM> employeeEMList){
        return new ArrayList<>(employeeEMList.stream().map(EmployeeDomainMapper::toDomain).toList());
    }
}
