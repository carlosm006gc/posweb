package com.dev.posweb.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dev.posweb.domain.Person;
import com.dev.posweb.repository.CityRepository;
import com.dev.posweb.repository.DepartamentRepository;
import com.dev.posweb.repository.PersonRepository;

@Controller
public class PersonController {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private DepartamentRepository departamentRepository;

	@GetMapping("/person")
	public String persons(Model model) {
		model.addAttribute("listPerson", personRepository.findAll());
		return "/rh/person/index";
	}

	@GetMapping("/person/new")
	public String newPerson(Model model) {
		model.addAttribute("person", new Person());
		model.addAttribute("cities", cityRepository.findAll());
		model.addAttribute("departaments", departamentRepository.findAll());
		return "/rh/person/form";
	}

	@PostMapping("person/save")
	public String savePerson(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("cities", cityRepository.findAll());
			model.addAttribute("departaments", departamentRepository.findAll());
			return "/rh/person/form";
		}
		personRepository.save(person);
		return "redirect:/person";
	}

	@GetMapping("/person/change/{id}")
	public String changePerson(@PathVariable("id") Long id, Model model) {
		Optional<Person> personOp = personRepository.findById(id);
		if (personOp.isEmpty()) {
			throw new IllegalArgumentException("Invalid Person");
		}
		model.addAttribute("person", personOp.get());
		model.addAttribute("cities", cityRepository.findAll());
		model.addAttribute("departaments", departamentRepository.findAll());
		return "/rh/person/form";
	}

	@GetMapping("/person/delete/{id}")
	public String deletePerson(@PathVariable("id") Long id) {
		Optional<Person> personOp = personRepository.findById(id);
		if (personOp.isEmpty()) {
			throw new IllegalArgumentException("Invalid Person");
		}
		personRepository.delete(personOp.get());
		return "redirect:/person";
	}

}