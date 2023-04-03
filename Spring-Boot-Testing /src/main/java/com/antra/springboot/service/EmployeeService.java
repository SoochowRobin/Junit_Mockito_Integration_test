package com.antra.springboot.service;

import com.antra.springboot.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployee();
}
