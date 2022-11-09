package com.dev.posweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dev.posweb.repository.PersonRepository;

@Controller
public class PersonController {

	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping("/people")
	public String persons(Model model) {
		model.addAttribute("listPerson", personRepository.findAll());
		return "person/index";
	}
}
