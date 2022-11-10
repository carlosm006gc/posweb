package com.dev.posweb.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dev.posweb.domain.Person;
import com.dev.posweb.repository.PersonRepository;

@Controller
public class PersonController {

	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping("/person")	
	public String persons(Model model) {
		model.addAttribute("listPerson", personRepository.findAll());
		return "person/index";
	}
	
	@GetMapping("/person/new")
	public String newPerson(@ModelAttribute("person") Person person) {
		return "person/form";
	}
	
	@PostMapping("/person/save")
	public String savePerson(@ModelAttribute("person") Person person) {
		personRepository.save(person);
		return "redirect:/person";
	}
	@GetMapping("/person/change/{id}")
	public String changePerson(@PathVariable("id") Long id, Model model) {
		Optional<Person> personOp = personRepository.findById(id);
		if(personOp.isEmpty()) {
			throw new IllegalArgumentException("Invalid Person");
		}
		model.addAttribute("person", personOp.get());
		return "person/form";
		}
	@GetMapping("/person/delete/{id}")
	public String deletePerson(@PathVariable("id") Long id) {
		Optional<Person> personOp = personRepository.findById(id);
		if(personOp.isEmpty()) {
			throw new IllegalArgumentException("Invalid Person");
		}
		personRepository.delete(personOp.get());
		return "redirect:/person";
		}
}