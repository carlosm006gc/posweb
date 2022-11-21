package com.dev.posweb.controllers;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.posweb.domain.Departament;
import com.dev.posweb.domain.Person;
import com.dev.posweb.domain.dtos.AutoCompleteDTO;
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

	private List<Departament> suggestionsDepartament = new ArrayList<>();

	@GetMapping("/person")
	public String persons(Model model) {
		model.addAttribute("listPerson", personRepository.findAll());
		return "/rh/person/index";
	}

	@GetMapping("/person/new")
	public String newPerson(Model model) {
		model.addAttribute("person", new Person());
		model.addAttribute("cities", cityRepository.findAll());
		return "/rh/person/form";
	}

	@PostMapping("/person/save")
	public String savePerson(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("cities", cityRepository.findAll());
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

	@RequestMapping("/person/departamentNameAutoComplete")
	@ResponseBody
	public List<AutoCompleteDTO> departamentNameAutoComplete(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
		List<AutoCompleteDTO> suggestions = 	new ArrayList<>();
		try {
			if (term.length() >= 3) {
				suggestionsDepartament = departamentRepository.searchByName(term);
			}
			for (Departament departament : suggestionsDepartament) {
				if (departament.getName().toLowerCase().contains(term.toLowerCase())) {
					suggestions.add(new AutoCompleteDTO(departament.getName(), Long.toString(departament.getId())));

				}
			}
		} catch (Exception ex) {

			ex.printStackTrace();

		}
		return suggestions;
	}
}