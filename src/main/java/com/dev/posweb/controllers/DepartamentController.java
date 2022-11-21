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

import com.dev.posweb.domain.Departament;
import com.dev.posweb.repository.DepartamentRepository;

@Controller
public class DepartamentController {

	@Autowired
	private DepartamentRepository departamentRepository;

	@GetMapping("/admin/departaments")
	public String departaments(Model model) {
		model.addAttribute("listDepartament", departamentRepository.findAll());
		return "admin/departament/list";
	}

	@GetMapping("/admin/departament/new")
	public String newDepartament(Model model) {
		model.addAttribute("departament", new Departament());
		return "/admin/departament/form";
	}

	@PostMapping("/admin/departament/save")
	public String saveDepartament(@Valid @ModelAttribute("departament") Departament departament,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "/admin/departament/form";
		}
		departamentRepository.save(departament);
		return "redirect:/departaments";
	}

	@GetMapping("/admin/departament/change/{id}")
	public String changeDepartament(@PathVariable("id") Long id, Model model) {
		Optional<Departament> departamentOp = departamentRepository.findById(id);
		if (departamentOp.isEmpty()) {
			throw new IllegalArgumentException("Invalid Departament");
		}
		model.addAttribute("departament", departamentOp.get());
		return "/admin/departament/form";
	}

}
