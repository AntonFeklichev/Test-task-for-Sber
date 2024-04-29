package com.antonfeklichev.factorialapp.service;

import com.antonfeklichev.factorialapp.dto.FactorialRequestDto;
import com.antonfeklichev.factorialapp.dto.FactorialResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FactorialServiceImpl implements FactorialService {

    Map<Integer, BigInteger> cache = new ConcurrentHashMap<>();


    @Override
    public FactorialResponseDto getFactorial(FactorialRequestDto requestDto) {

        Integer factorialNum = requestDto.factorialNum();

        BigInteger factorial = cache.computeIfAbsent(factorialNum, this::calculateFactorial);

        return new FactorialResponseDto(factorial);
    }

    private BigInteger calculateFactorial(Integer n) {

        BigInteger factorial = BigInteger.ONE;

        for (int i = 1; i <= n; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));

        }
        return factorial;
    }
}
