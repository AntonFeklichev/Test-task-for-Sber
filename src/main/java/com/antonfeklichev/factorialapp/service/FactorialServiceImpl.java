package com.antonfeklichev.factorialapp.service;

import com.antonfeklichev.factorialapp.dto.FactorialRequestDto;
import com.antonfeklichev.factorialapp.dto.FactorialResponseDto;
import com.antonfeklichev.factorialapp.entity.Factorial;
import com.antonfeklichev.factorialapp.exception.NegativeNumberException;
import com.antonfeklichev.factorialapp.repository.FactorialRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class FactorialServiceImpl implements FactorialService {

    private final FactorialRepository factorialRepository;

    public FactorialServiceImpl(FactorialRepository factorialRepository) {
        this.factorialRepository = factorialRepository;
    }


    @Override
    public FactorialResponseDto calculateFactorial(FactorialRequestDto requestDto) {

        Integer factorialNum = requestDto.factorialNum();
        if (factorialNum < 0) {
            throw new NegativeNumberException("Can not calculate factorial for negative numbers");
        }
            Optional<Factorial> factorialEntity = factorialRepository.findById(factorialNum);

        if (factorialEntity.isEmpty()) {
            BigInteger factorial = getFactorial(factorialNum);
            factorialRepository.save(new Factorial(factorialNum, factorial));
            return new FactorialResponseDto(factorial);

        }
        return new FactorialResponseDto(factorialEntity.get().getFactorial());
    }

    private BigInteger getFactorial(Integer n) {

        BigInteger factorial = BigInteger.ONE;

        for (int i = 1; i <= n; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));

        }
        return factorial;
    }
}
