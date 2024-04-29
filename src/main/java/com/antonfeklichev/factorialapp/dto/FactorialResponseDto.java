package com.antonfeklichev.factorialapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

public record FactorialResponseDto(@JsonProperty("result") BigInteger result) {
}
