package com.antonfeklichev.factorialapp.controller;

import com.antonfeklichev.factorialapp.dto.FactorialRequestDto;
import com.antonfeklichev.factorialapp.dto.FactorialResponseDto;
import com.antonfeklichev.factorialapp.exception.NegativeNumberException;
import com.antonfeklichev.factorialapp.service.FactorialService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;

@ExtendWith(MockitoExtension.class)
public class FactorialControllerTest {

    @Mock
    FactorialService factorialService;
    @InjectMocks
    FactorialController factorialController;

    @Test
    void testCalculateFactorialForPositiveNumber() {

        // Arrange
        FactorialRequestDto requestDto = new FactorialRequestDto(5);
        FactorialResponseDto responseDto = new FactorialResponseDto(BigInteger.valueOf(120));
        Mockito.when(factorialService.calculateFactorial(requestDto)).thenReturn(responseDto);

        // Act
        ResponseEntity<FactorialResponseDto> responseEntity = factorialController.calculateFactorial(requestDto);

        // Assert
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(responseDto, responseEntity.getBody());

    }

    @Test
    void testCalculateFactorialForNegativeNumber() {

        //Arrange
        FactorialRequestDto requestDto = new FactorialRequestDto(-5);
        Mockito.when(factorialService.calculateFactorial(requestDto))
                .thenThrow(NegativeNumberException.class);

        //Act & Assert
        Assertions.assertThrows(NegativeNumberException.class,
                () -> factorialController.calculateFactorial(requestDto));
    }

}
