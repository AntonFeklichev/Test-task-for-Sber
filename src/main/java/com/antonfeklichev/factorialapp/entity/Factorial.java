package com.antonfeklichev.factorialapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "factorial")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Factorial {

    @Id
    private Integer number = 0;
    @Column(columnDefinition = "numeric")
    private BigInteger factorial = BigInteger.ZERO;
}
