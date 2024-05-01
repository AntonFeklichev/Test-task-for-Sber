package com.antonfeklichev.factorialapp.service;

import com.antonfeklichev.factorialapp.dto.FactorialRequestDto;
import com.antonfeklichev.factorialapp.dto.FactorialResponseDto;
import com.antonfeklichev.factorialapp.entity.Factorial;
import com.antonfeklichev.factorialapp.repository.FactorialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigInteger;
import java.util.Optional;

@SpringBootTest
public class TestFactorialCalculationWhenRepeatableNumber {

    @Autowired
    private FactorialServiceImpl factorialService;

    @MockBean
    private FactorialRepository factorialRepository;

    @Test
    public void testFactorialCalculationReturnSavedFactorial() {
        // Arrange
        Integer factorialNumber = 5;
        BigInteger savedFactorialValue = BigInteger.valueOf(120);
        Factorial savedFactorial = new Factorial(factorialNumber, savedFactorialValue);

        // Customizing mock behavior
        Mockito.when(factorialRepository.findById(factorialNumber)).thenReturn(Optional.of(savedFactorial));

        // Act
        FactorialRequestDto requestDto = new FactorialRequestDto(factorialNumber);
        FactorialResponseDto response = factorialService.calculateFactorial(requestDto);

        // Assert
        Assertions.assertEquals(savedFactorialValue, response.result(), "Factorial must be returned from DB");
        Mockito.verify(factorialRepository, Mockito.never()).save(Mockito.any(Factorial.class));
    }

}
