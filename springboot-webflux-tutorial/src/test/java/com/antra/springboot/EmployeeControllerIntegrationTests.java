package com.antra.springboot;

import com.antra.springboot.dto.EmployeeDto;
import com.antra.springboot.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testSaveEmployee(){
        EmployeeDto employeeDto = new EmployeeDto();
        // create a new employee DTO
        employeeDto.setFirstName("robin");
        employeeDto.setLastName("chen");
        employeeDto.setEmail("suadcgb@gmail.com");

        webTestClient.post().uri("/api/employees")
                     .contentType(MediaType.APPLICATION_JSON)
                     .accept(MediaType.APPLICATION_JSON)
                     .body(Mono.just(employeeDto), EmployeeDto.class)
                     .exchange()
                     .expectStatus().isCreated()
                     .expectBody()
                     .consumeWith(System.out::println)
                     .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                     .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                     .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }
}
