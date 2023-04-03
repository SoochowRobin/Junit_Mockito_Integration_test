package com.antra.springboot.service.impl;

import com.antra.springboot.dto.EmployeeDto;
import com.antra.springboot.entity.Employee;
import com.antra.springboot.mappper.EmployeeMapper;
import com.antra.springboot.repository.EmployeeRepository;
import com.antra.springboot.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {

        // convert EmployeeDTO into Employee Entity
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Mono<Employee> savedEmployee = employeeRepository.save(employee);

        return savedEmployee.map(employeeEntity -> EmployeeMapper.mapToEmployeeDto(employeeEntity));
    }

    @Override
    public Mono<EmployeeDto> getEmployee(String employeeId) {

        Mono<Employee> employeeFindById = employeeRepository.findById(employeeId);
        return employeeFindById.map(employee -> EmployeeMapper.mapToEmployeeDto(employee));
    }

    @Override
    public Flux<EmployeeDto> getAllEmployees() {

        Flux<Employee> employeeFlux = employeeRepository.findAll();

        return employeeFlux.map(employee -> EmployeeMapper.mapToEmployeeDto(employee))
                            .switchIfEmpty(Flux.empty()); // if non in db, then return Flux.empty
    }

    @Override
    public Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto, String employeeId) {
        Mono<Employee> employeeMono = employeeRepository.findById(employeeId);

        Mono<Employee> updatedEmployee = employeeMono.flatMap(existingEmployee -> {
            existingEmployee.setFirstName(employeeDto.getFirstName());
            existingEmployee.setLastName(employeeDto.getLastName());
            existingEmployee.setEmail(employeeDto.getEmail());

            return employeeRepository.save(existingEmployee);
        });

        return updatedEmployee.map(employee -> EmployeeMapper.mapToEmployeeDto(employee));
    }

    @Override
    public Mono<Void> deleteEmployee(String employeeId) {

        return employeeRepository.deleteById(employeeId);
    }


}
