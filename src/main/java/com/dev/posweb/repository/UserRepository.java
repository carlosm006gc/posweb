package com.dev.posweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.posweb.domain.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

	Users findByUsername(String username);

}
