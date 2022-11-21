package com.dev.posweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministrativoController {

	@GetMapping({"/admin/administrative"})
	public String home() {
		return "/admin/adm";
	}
}
