package com.antra.springboot.controller;

import com.antra.springboot.dto.EmployeeDto;
import com.antra.springboot.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    // build reactive save employee REST API
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> savedEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.saveEmployee(employeeDto);
    }

    // build reactive Get single employee user API
    @GetMapping("{id}")
    public Mono<EmployeeDto> getEmployee(@PathVariable("id") String employeeId){
        return employeeService.getEmployee(employeeId);
    }

    // build reactive get all employees rest api
    @GetMapping
    public Flux<EmployeeDto> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    // build reactive update rest api
    @PutMapping("{id}")
    public Mono<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto,
                                            @PathVariable("id") String employeeId){
        return employeeService.updateEmployee(employeeDto, employeeId);
    }

    // build reactive delete employee rest api
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteEmployee(@PathVariable("id") String employeeId){
        return employeeService.deleteEmployee(employeeId);
    }
}
