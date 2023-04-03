package com.antra.springboot.controller;

import com.antra.springboot.model.Employee;
import com.antra.springboot.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    // @MockBean annotation tells Spring to create a mock instance of
    // EmployeeService and add it to the application context so that it is
    // injected into EmployeeController
    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given -- precondition
        Employee employee = Employee.builder()
                                    .firstName("robin")
                                    .lastName("chen")
                                    .email("sudacgb@gmail.com")
                                    .build();
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                  .willAnswer((invocation) -> invocation.getArgument(0));

        // when -- action or behavior that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(employee)));

        // then -- verify the result or output using assert statements
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())));
    }


}
