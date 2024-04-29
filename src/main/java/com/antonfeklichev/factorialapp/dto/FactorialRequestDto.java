package com.antonfeklichev.factorialapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FactorialRequestDto(@JsonProperty("factorial_num") Integer factorialNum) {
}
