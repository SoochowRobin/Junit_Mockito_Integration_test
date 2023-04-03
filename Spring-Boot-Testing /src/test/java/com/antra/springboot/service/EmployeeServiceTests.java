package com.antra.springboot.service;

import com.antra.springboot.exception.ResourceNotFoundException;
import com.antra.springboot.model.Employee;
import com.antra.springboot.repository.EmployeeRepository;
import com.antra.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){

//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);

        employee =  Employee.builder()
                            .id(1L)
                            .firstName("Robin")
                            .lastName("chen")
                            .email("sudacgb213@gmail.com")
                            .build();
    }


    // Junit test for
    @DisplayName("Junit test for savedEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        // given - precondition or setup
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                                           .willReturn(Optional.empty());

        BDDMockito.given(employeeRepository.save(employee))
                                           .willReturn(employee);
        // when - action or behavior that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then verify the output
        Assertions.assertThat(savedEmployee).isNotNull();

    }

    // Junit test for throwing exception
    @Test
    @DisplayName("Junit test for saveEmployee method which throws exception")
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException(){
        // given - precondition or setup
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                                            .willReturn(Optional.of(employee));

        // when - action or behavior that we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> employeeService.saveEmployee(employee) );

        // then verify the output
        BDDMockito.verify(employeeRepository, Mockito.never()).save(Mockito.any(Employee.class));
    }

    // Junit test for getAllEmployee method
    @DisplayName("Junit test for getAllEmployees method")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList(){
        // given - precondition or setup

        Employee employee2 = employee =  Employee.builder()
                .id(2L)
                .firstName("Robin12")
                .lastName("chen21")
                .email("sudacgb@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findAll())
                                            .willReturn(List.of(employee, employee2));

        // when - action or behavior that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployee();

        // then verify the output
        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }





}
