package com.iwm.backend.modules.employee;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(long id) {
      super("Employee with id " + id + " not found !");
    }
}
