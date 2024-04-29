package com.antonfeklichev.factorialapp.service;

import com.antonfeklichev.factorialapp.dto.FactorialRequestDto;
import com.antonfeklichev.factorialapp.dto.FactorialResponseDto;

public interface FactorialService {

    FactorialResponseDto getFactorial(FactorialRequestDto requestDto);
}
