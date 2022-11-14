package com.dev.posweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.posweb.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

}
