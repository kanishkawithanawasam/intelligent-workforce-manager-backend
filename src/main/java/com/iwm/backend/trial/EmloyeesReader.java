package com.iwm.backend.trial;

import com.iwm.backend.modules.schedule_engine.models.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmloyeesReader {

    public static List<Employee> readEmployees() {
        InputStream stream  = EmloyeesReader.class.getResourceAsStream("/data/Employee_List.csv");
        List<Employee> employees;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            br.readLine();
            String line;
            employees = new ArrayList<Employee>();
            Random random = new Random();
            while ((line =br.readLine()) != null) {
                String[] split = line.split(",");
                Employee employee = new Employee(Integer.parseInt(split[0]), split[1],split[2],
                       Double.parseDouble( split[3]),Double.parseDouble(split[4]),random.nextDouble(10.00,20.00));
                        employees.add(employee);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }

    /*
    public static void main(String[] args) {
        InputStream stream = EmloyeesReader.class.getResourceAsStream("/data/Employee_List.csv");
        List<Employee> employees;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            br.readLine();
            String line;
            employees = new ArrayList<Employee>();
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                Employee employee = new Employee(Integer.parseInt(split[0]), split[1], split[2],
                        Double.parseDouble(split[3]), Double.parseDouble(split[4]), 0);
                employees.add(employee);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

}
