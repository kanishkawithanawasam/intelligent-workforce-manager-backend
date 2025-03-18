package com.iwm.backend.trial;

import com.iwm.backend.schedulegenerator.models.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmloyeesReader {

    public static List<Employee> readEmployees() {
        InputStream stream  = EmloyeesReader.class.getResourceAsStream("/data/Employee_List.csv");
        List<Employee> employees;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            br.readLine();
            String line;
            employees = new ArrayList<Employee>();
            while ((line =br.readLine()) != null) {
                String[] split = line.split(",");
                Employee employee = new Employee(Integer.parseInt(split[0]), split[1],split[2],
                       Double.parseDouble( split[3]),Double.parseDouble(split[4]));
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
