package com.antra.springboot.repository;


import com.antra.springboot.model.Employee;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setUp(){
        employee = Employee.builder()
                .firstName("Robin")
                .lastName("Chen")
                .email("sudacgb@gmail.com")
                .build();
    }




    @DisplayName("Junit test for save employee operation")
    @Test
    // Junit test for save employee operation
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        // given -- precondition or setup
//        Employee employee = Employee.builder()
//                                    .firstName("Robin")
//                                    .lastName("Chen")
//                                    .email("sudacgb@gmail.com")
//                                    .build();

        // when -- action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        // then -- verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }


       // Junit test for
       @Test
       @DisplayName("Junit test for get all employees operation")
       public void givenEmployeeList_whenFindAll_thenEmployeeList(){
           // given - precondition or setup
//           Employee employee1 = Employee.builder()
//                   .firstName("Robin")
//                   .lastName("Chen")
//                   .email("sudacgb@gmail.com")
//                   .build();

           Employee employee2 = Employee.builder()
                   .firstName("Robin2")
                   .lastName("Chen2")
                   .email("sudacgb2222@gmail.com")
                   .build();

           employeeRepository.save(employee);
           employeeRepository.save(employee2);

           // when - action or behavior that we are going to test
           List<Employee> employeeList = employeeRepository.findAll();

           // then verify the output
           // import assertThat() as static
           assertThat(employeeList).isNotNull();
           assertThat(employeeList.size()).isEqualTo(2);
       }


        // Junit test for get employee by operation
       @Test
       public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
           // given - precondition or setup
//           Employee employee1 = Employee.builder()
//                   .firstName("Robin")
//                   .lastName("Chen")
//                   .email("sudacgb@gmail.com")
//                   .build();


           employeeRepository.save(employee);
           // when - action or behavior that we are going to test
           Employee employeeDB = employeeRepository.findById(employee.getId()).get();

           // then verify the output
           assertThat(employeeDB).isNotNull();

       }

       // Junit test for get employee by email operation
       @DisplayName("junit test for get employee by email")
       @Test
       public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
           // given - precondition or setup
//           Employee employee1 = Employee.builder()
//                   .firstName("Robin")
//                   .lastName("Chen")
//                   .email("sudacgb@gmail.com")
//                   .build();

           employeeRepository.save(employee);

           // when - action or behavior that we are going to test
           Employee findByEmail = employeeRepository.findByEmail("sudacgb@gmail.com").get();
           Employee shouldBeNull = employeeRepository.findByEmail("xxxxx").orElse(null);

           // then verify the output
           assertThat(findByEmail).isNotNull();
           assertThat(shouldBeNull).isNull();

       }


       // Junit test for update employee operation
    @DisplayName("junit test for update employee operation")
       @Test
       public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
           // given - precondition or setup
//           Employee employee = Employee.builder()
//                   .firstName("Robin")
//                   .lastName("Chen")
//                   .email("sudacgb@gmail.com")
//                   .build();

           employeeRepository.save(employee);

           // when - action or behavior that we are going to test
           Employee savedEmployee = employeeRepository.findById(employee.getId()).get();

           // update several fields
           savedEmployee.setFirstName("RobinUpdated");
           savedEmployee.setEmail("robinchen@gmail.com");

           Employee updatedEmployee = employeeRepository.save(savedEmployee);

           // then verify the output
           assertThat(updatedEmployee.getEmail()).isEqualTo("robinchen@gmail.com");
           assertThat(updatedEmployee.getFirstName()).isEqualTo("RobinUpdated");

       }


       // Junit test for JPQL customize query language
       @Test
       public void givenFirstNameAndLast_whenFindByJPQL_thenEmployeeObject(){
           // given - precondition or setup
//           Employee employee = Employee.builder()
//                   .firstName("Robin")
//                   .lastName("Chen")
//                   .email("sudacgb@gmail.com")
//                   .build();

           employeeRepository.save(employee);

           // when - action or behavior that we are going to test
           Employee byJPQL = employeeRepository.findByJPQL("Robin", "Chen");

           // then verify the output
           assertThat(byJPQL).isNotNull();
           assertThat(byJPQL.getFirstName()).isEqualTo("Robin");

       }


    // Junit test for JPQL customize query language with named parameters
    @Test
    public void givenFirstNameAndLast_whenFindByJPQLNamed_thenEmployeeObject(){
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Robin")
//                .lastName("Chen")
//                .email("sudacgb@gmail.com")
//                .build();

        employeeRepository.save(employee);

        // when - action or behavior that we are going to test
        Employee byJPQL = employeeRepository.findByJPQLNamedParams("Robin", "Chen");

        // then verify the output
        assertThat(byJPQL).isNotNull();
        assertThat(byJPQL.getFirstName()).isEqualTo("Robin");

    }

    // Junit test for query using native SQL with index
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){
        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Robin")
//                .lastName("Chen")
//                .email("sudacgb@gmail.com")
//                .build();

        employeeRepository.save(employee);

        // when - action or behavior that we are going to test
        Employee byNativeSQL = employeeRepository.findByNativeSQL("Robin", "Chen");

        // then verify the output
        assertThat(byNativeSQL).isNotNull();

    }



}
