package com.antra.springboot;

import com.antra.springboot.controller.EmployeeController;
import com.antra.springboot.dto.EmployeeDto;
import com.antra.springboot.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EmployeeService employeeService;

    @Test
    @DisplayName("test post controller by using webflux")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee(){

        // given - preconditions
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("robin");
        employeeDto.setLastName("chen");
        employeeDto.setEmail("suadcgb@gmail.com");

        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(EmployeeDto.class)))
                .willReturn(Mono.just(employeeDto));

        // when - action behavior
        WebTestClient.ResponseSpec exchange = webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        // then - verify the result of output
        exchange.expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }


    @Test
    public void givenEmployeeId_whenGetEmployee_thenReturnEmployeeObject(){
        // given -- precondition
        String employeeId = "123";
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("robin");
        employeeDto.setLastName("chen");
        employeeDto.setEmail("suadcgb@gmail.com");


        BDDMockito.given(employeeService.getEmployee(employeeId))
                .willReturn(Mono.just(employeeDto));

        // when -- action
        WebTestClient.ResponseSpec id = webTestClient.get()
                .uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .exchange();

        // then -- verify the output
        id.expectStatus().isOk()
                        .expectBody()
                        .consumeWith(System.out::println)
                        .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                        .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                        .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }



}
