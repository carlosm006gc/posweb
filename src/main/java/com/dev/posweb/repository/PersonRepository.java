package com.dev.posweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.posweb.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
