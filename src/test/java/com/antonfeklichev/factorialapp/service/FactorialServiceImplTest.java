package com.antonfeklichev.factorialapp.service;

import com.antonfeklichev.factorialapp.dto.FactorialRequestDto;
import com.antonfeklichev.factorialapp.dto.FactorialResponseDto;
import com.antonfeklichev.factorialapp.entity.Factorial;
import com.antonfeklichev.factorialapp.exception.NegativeNumberException;
import com.antonfeklichev.factorialapp.repository.FactorialRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FactorialServiceImplTest {
    @Mock
    FactorialRepository factorialRepository;
    @InjectMocks
    FactorialServiceImpl factorialServiceImpl;


    @Test
    void testCalculateFactorialForPositiveNumber() {

        //Arrange
        FactorialRequestDto requestDto = new FactorialRequestDto(5);
        Mockito.when(factorialRepository.findById(5)).thenReturn(Optional.empty());

        //Act
        FactorialResponseDto responseDto = factorialServiceImpl.calculateFactorial(requestDto);

        //Assert
        Assertions.assertEquals(new BigInteger("120"), responseDto.result());
        Mockito.verify(factorialRepository, Mockito.times(1))
                .save(Mockito.any(Factorial.class));
    }

    @Test
    void testCalculateFactorialForCachedValue() {

        //Arrange
        Mockito.when(factorialRepository.findById(5))
                .thenReturn(Optional.of(new Factorial(5, new BigInteger("120"))));

        //Act
        FactorialResponseDto responseDto = factorialServiceImpl
                .calculateFactorial(new FactorialRequestDto(5));

        //Assert
        Assertions.assertEquals(new BigInteger("120"), responseDto.result());
        Mockito.verify(factorialRepository, Mockito.never()).save(Mockito.any(Factorial.class));
    }

    @Test
    void testCalculateFactorialForNegativeNumber() {

        //Arrange
        FactorialRequestDto requestDto = new FactorialRequestDto(-5);

        //Act & Assert
        Assertions.assertThrows(NegativeNumberException.class,
                () -> factorialServiceImpl.calculateFactorial(requestDto));
    }


}
