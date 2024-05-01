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
public class FactorialControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    FactorialService factorialService;

    @Test
    void testCalculateFactorialForPositiveNumber() throws Exception {

        // Arrange
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/factorial")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"factorial_num\": 5}");


        // Act & Assert
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.content().json("{\"result\": 120}"));
    }

    @Test
    void testCalculateFactorialForNegativeNumber() throws Exception {

        // Arrange
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/factorial")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"factorial_num\": -1}");

        // Act & Assert
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        MockMvcResultMatchers.status().isBadRequest(),
                        MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.content()
                                .json("{\"msg\": \"Can not calculate factorial for negative numbers\"}"));
    }
}
