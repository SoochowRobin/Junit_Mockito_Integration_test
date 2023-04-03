package com.antra.springboot.service.impl;

import com.antra.springboot.model.Employee;
import com.antra.springboot.repository.EmployeeRepository;
import com.antra.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());

        if(savedEmployee.isPresent()){
            throw new RuntimeException("Employee already exists with given email: " + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }


    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }
}
