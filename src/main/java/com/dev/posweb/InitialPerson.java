package com.dev.posweb;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dev.posweb.domain.Person;
import com.dev.posweb.repository.PersonRepository;

@Component
@Transactional
public class InitialPerson implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public void run(String... args) throws Exception {

		Person p1 = new Person(null, "Carlos",LocalDate.of(2000, 10, 11), "05573075110", "campos@gmail.com", "999656158");
		
		personRepository.save(p1);
	}

}
