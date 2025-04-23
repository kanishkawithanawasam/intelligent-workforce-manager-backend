package com.iwm.backend.modules.demand;


import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DemandService {

    public Map<LocalDate, Map<Integer,Integer>> getWeeklyDemand(LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, Map<Integer,Integer>> map = new HashMap<>();
        return  null;
    }
}
