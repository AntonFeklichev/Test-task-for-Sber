package com.antonfeklichev.factorialapp.service;

import com.antonfeklichev.factorialapp.dto.FactorialRequestDto;
import com.antonfeklichev.factorialapp.dto.FactorialResponseDto;
import com.antonfeklichev.factorialapp.entity.Factorial;
import com.antonfeklichev.factorialapp.exception.NegativeNumberException;
import com.antonfeklichev.factorialapp.repository.FactorialRepository;
import io.micrometer.core.annotation.Timed;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@Getter
public class FactorialServiceImpl implements FactorialService {

    private final FactorialRepository factorialRepository;

    public FactorialServiceImpl(FactorialRepository factorialRepository) {
        this.factorialRepository = factorialRepository;
    }


    @Override
    @Timed(value = "factorial.timer")
    public FactorialResponseDto calculateFactorial(FactorialRequestDto requestDto) {

        Integer numberForFactorialCalc = requestDto.factorialNum();
        if (numberForFactorialCalc < 0) {
            throw new NegativeNumberException("Can not calculate factorial for negative numbers");
        }

        return factorialRepository.findById(numberForFactorialCalc)
                .map(factorial -> new FactorialResponseDto(factorial.getFactorial()))
                .orElseGet(() -> {
                    BigInteger factorial = getFactorial(numberForFactorialCalc);
                    factorialRepository.save(new Factorial(numberForFactorialCalc, factorial));
                    return new FactorialResponseDto(factorial);
                });
    }

    private BigInteger getFactorial(Integer n) {

        BigInteger factorial = BigInteger.ONE;

        for (int i = 1; i <= n; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));

        }
        return factorial;
    }
}
