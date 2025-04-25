package com.iwm.backend.modules.preferences;

import com.iwm.backend.modules.employee.EmployeeEM;

public class PreferenceMapper{
    
    
    public static EmployeePreferenceDTO toDTO(EmployeePreferencesEM em){
        EmployeePreferenceDTO dto = new EmployeePreferenceDTO();
        if (em == null){
            return dto;
        }
        dto.setEmployeeId(em.getEmployee().getId());
        dto.setPreferredHours(em.getPreferredHours());
        dto.setPreferenceId(em.getId());
        return dto;
    }


    public static EmployeePreferencesEM toEM(EmployeePreferenceDTO dto){
        EmployeePreferencesEM em = new EmployeePreferencesEM();
        em.setPreferredHours(dto.getPreferredHours());
        em.setId(dto.getPreferenceId());
        EmployeeEM empEm = new EmployeeEM();
        empEm.setId(dto.getEmployeeId());
        em.setEmployee(empEm);
        return em;

    }
}
