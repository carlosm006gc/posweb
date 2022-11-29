package com.dev.posweb.controllers;

import java.security.InvalidParameterException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dev.posweb.domain.Role;
import com.dev.posweb.domain.Users;
import com.dev.posweb.domain.dtos.RoleUserForm;
import com.dev.posweb.repository.UserRepository;

@Controller
public class UsersController {

	private PasswordEncoder encoder;
	private UserRepository userRepository;
	private final List<String> roles;
	
	public UsersController(UserRepository userRepository, PasswordEncoder encoder) {
		this.encoder = encoder;
		this.userRepository = userRepository;
		this.roles = Role.getRoles();
	}

	@GetMapping("/login")
	public String login(Principal principal) {
		if (principal != null) {
			return "redirect:/home";
		}
		return "/login";
	}

	@GetMapping("/admin/users")
	public String users(Model model) {
		model.addAttribute("listUsers", userRepository.findAll());
		return "admin/users/list";
	}

	@GetMapping("/admin/users/new")
	public String newUsers(Model model) {

		model.addAttribute("users", new Users("", "", ""));
		model.addAttribute("roles", roles);

		return "admin/users/form";
	}

	@PostMapping("admin/users/save")
	public String saveUsers(@Valid @ModelAttribute("users") Users users, BindingResult bindingResult, Model model) {

		Users userFound = userRepository.findByUsername(users.getUsername());
		if (userFound != null && userFound.getId() != users.getId()) {
			bindingResult.addError(new FieldError("users", "username", "Username is already in use."));
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("roles", roles);
			return "admin/users/new";
		}

		users.setPassword(encoder.encode(users.getPassword()));

		userRepository.save(users);
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/users/delete/{id}")
	public String deleteUsers(@PathVariable("id") long id) {
		Optional<Users> usersOpt = userRepository.findById(id);
		if (usersOpt.isEmpty()) {
			throw new IllegalArgumentException("Invalid User.");
		}

		userRepository.delete(usersOpt.get());
		return "redirect:/admin/users";
	}

	@GetMapping("/admin/users/change/role/{id}")
	public String getAlterarPapelUsuario(@PathVariable("id") long id, Model model) {
		Optional<Users> usersOpt = userRepository.findById(id);
		if (!usersOpt.isPresent()) {
			throw new IllegalArgumentException("Invalid User.");
		}

		RoleUserForm roleUserForm = new RoleUserForm(usersOpt.get());

		model.addAttribute("roleUserForm", roleUserForm);
		model.addAttribute("roles", roles);

		return "admin/users/change_role";
	}

	@PostMapping("/admin/users/change/role")
	public String changeUserRole(@Valid @ModelAttribute("roleUserForm") RoleUserForm roleUserForm,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("roles", roles);
			return "admin/users/change_role";
		}

		Users changeUser = userRepository.findById(roleUserForm.getId())
				.orElseThrow(() -> new InvalidParameterException("Invalid User!"));
		changeUser.setRole(roleUserForm.getRole());

		userRepository.save(changeUser);

		return "redirect:/admin/users";
	}
}