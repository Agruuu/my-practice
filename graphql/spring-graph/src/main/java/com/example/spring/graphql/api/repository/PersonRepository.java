package com.example.spring.graphql.api.repository;

import com.example.spring.graphql.api.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByEmail(String email);
}
