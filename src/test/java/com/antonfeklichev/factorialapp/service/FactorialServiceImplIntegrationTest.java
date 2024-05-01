package com.antonfeklichev.factorialapp.service;

import com.antonfeklichev.factorialapp.dto.FactorialRequestDto;
import com.antonfeklichev.factorialapp.dto.FactorialResponseDto;
import com.antonfeklichev.factorialapp.entity.Factorial;
import com.antonfeklichev.factorialapp.exception.NegativeNumberException;
import com.antonfeklichev.factorialapp.repository.FactorialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FactorialServiceImplIntegrationTest {

    @Autowired
    FactorialRepository factorialRepository;
    @Autowired
    FactorialServiceImpl factorialService;

//    @MockBean
//    FactorialServiceImpl mockFactorialService;

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
    void testFactorialCalculationWhenRepeatableNumber() {

        //Arrange
        FactorialRequestDto requestDto = new FactorialRequestDto(6);
        //mockFactorialService.calculateFactorial(requestDto);
        factorialRepository.save(new Factorial(requestDto.factorialNum(), BigInteger.valueOf(720)));

        //Mockito.when(mockFactorialService.calculateFactorial(requestDto)).thenCallRealMethod();

        //Act
        FactorialResponseDto responseDto = factorialService.calculateFactorial(requestDto);

        //Assert
        Assertions.assertEquals(new BigInteger("720"), responseDto.result());
        //Mockito.verify(mockFactorialService, Mockito.never()).getFactorial(requestDto.factorialNum());

    } //TODO Разобраться с тестом

    @Test
    void testFactorialCalculationForNegativeNumber() {
        // Arrange
        FactorialRequestDto requestDto = new FactorialRequestDto(-1);

        // Act & Assert
        Assertions.assertThrows(NegativeNumberException.class,
                () -> factorialService.calculateFactorial(requestDto));
    }

}
