package com.dev.posweb.domain;

import java.util.ArrayList;
import java.util.List;

public enum Role {
	
	ADMIN("ADMIN"),
	USER("USER");

	private String name;
	
	Role(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return getName();
	}

	public static List<String> getRoles() {
		List<String> roles = new ArrayList<String>(); 
		
		for (Role role : values()) {
			roles.add(role.getName());
		}
		
		return roles;
	}	
}
