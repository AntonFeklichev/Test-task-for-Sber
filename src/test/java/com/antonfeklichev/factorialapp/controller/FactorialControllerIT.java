package com.antonfeklichev.factorialapp.controller;

import com.antonfeklichev.factorialapp.service.FactorialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class FactorialControllerIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    FactorialService factorialService;

    @Test
    void calculateFactorial_ReturnsFactorialResponseDto() throws Exception {

        // Arrange
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/factorial")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"factorial_num\": 5}");


        // Act
        this.mockMvc.perform(requestBuilder)

                // Assert
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.content().json("{\"result\": 120}"));
    }
}
