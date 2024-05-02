package com.antonfeklichev.factorialapp.service;

import com.antonfeklichev.factorialapp.dto.FactorialRequestDto;
import com.antonfeklichev.factorialapp.dto.FactorialResponseDto;
import com.antonfeklichev.factorialapp.entity.Factorial;
import com.antonfeklichev.factorialapp.exception.NegativeNumberException;
import com.antonfeklichev.factorialapp.repository.FactorialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigInteger;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@SpringBootTest
@Testcontainers
public class FactorialServiceImplIntegrationTest {

    @SpyBean
    FactorialRepository factorialRepository;
    @Autowired
    FactorialServiceImpl factorialService;

    @Test
    void testFactorialCalculationAndStorage() {

        // Arrange
        FactorialRequestDto requestDto = new FactorialRequestDto(5);

        // Act
        FactorialResponseDto responseDto = factorialService.calculateFactorial(requestDto);

        // Assert
        Assertions.assertEquals(new BigInteger("120"), responseDto.result());

        // Verify that factorial stored in repository
        Assertions.assertTrue(factorialRepository.findById(5).isPresent());
        Assertions.assertEquals(new BigInteger("120"), factorialRepository.findById(5).get().getFactorial());
    }


    @Test
    public void testFactorialCalculationReturnSavedFactorial() {
        // Arrange
        Integer factorialNumber = 6;
        BigInteger savedFactorialValue = BigInteger.valueOf(720);
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

    @Test
    void testFactorialCalculationForNegativeNumber() {
        // Arrange
        FactorialRequestDto requestDto = new FactorialRequestDto(-1);

        // Act & Assert
        Assertions.assertThrows(NegativeNumberException.class,
                () -> factorialService.calculateFactorial(requestDto));
    }

}
