package com.antra.springboot.repository;

import com.antra.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // write customized query in JpaRepository interface
    Optional<Employee> findByEmail(String email);

    // use JPQL to customize query language with index
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);

    // use JPQL to customize query language with named parameters
    @Query("select e from Employee e where e.firstName = :firstName and e.lastName = :lastName")
    Employee findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);


    // use native query, nativeQuery
    @Query(value = "select * from employees e where e.first_name = ?1 and e.last_name =?2", nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);
}
