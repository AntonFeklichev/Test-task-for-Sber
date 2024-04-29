package com.antonfeklichev.factorialapp.repository;

import com.antonfeklichev.factorialapp.entity.Factorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactorialRepository extends JpaRepository<Factorial, Integer> {

}
