package com.dev.posweb.domain.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.dev.posweb.domain.Users;

public class RoleUserForm {

	private String username;
	@NotNull
	private long id;
	@NotBlank
	private String role;
	
	public RoleUserForm() {}

	public RoleUserForm(Users users) {
		this.username = users.getUsername();
		this.id = users.getId();
		this.role = users.getRole();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
