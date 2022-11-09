package com.dev.posweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
