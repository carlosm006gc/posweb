package com.dev.posweb;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dev.posweb.domain.City;
import com.dev.posweb.domain.Departament;
import com.dev.posweb.domain.Person;
import com.dev.posweb.domain.Role;
import com.dev.posweb.domain.Users;
import com.dev.posweb.repository.CityRepository;
import com.dev.posweb.repository.DepartamentRepository;
import com.dev.posweb.repository.PersonRepository;
import com.dev.posweb.repository.UserRepository;

@Component
@Transactional
public class InitialPerson implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private DepartamentRepository departamentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {

		Departament d1 = new Departament(null, "TI", "Tecnologia da Informação");
		Departament d2 = new Departament(null, "TE", "Tecnologia de Envio");

		
		City c1 = new City(null, "73850000", "Cristalina", "GO");
				
		cityRepository.save(c1);
		departamentRepository.save(d1);
		departamentRepository.save(d2);
		
		Person p1 = new Person(null, "Carlos",LocalDate.of(2000, 10, 11), "05573075110", "campos@gmail.com", "999656158", c1, d1);

		Person p2 = new Person(null, "Marya",LocalDate.of(2004, 05, 25), "05573075111", "marya@gmail.com", "999656159", c1, d2);
		
		personRepository.save(p1);
		personRepository.save(p2);

		Users u1 = new Users("carlos" , passwordEncoder.encode("carlos824500"), Role.ADMIN.getName()); 
		
		userRepository.save(u1);
	}

}
