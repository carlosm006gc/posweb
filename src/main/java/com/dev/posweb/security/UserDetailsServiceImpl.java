package com.dev.posweb.security;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import com.dev.posweb.repository.UserRepository;

import com.dev.posweb.domain.Users;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByUsername(username);
		if(user != null) {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(authority);
		User userSpring = new User(user.getUsername(), user.getPassword(), authorities);
		return userSpring;
		}
		return null;
	}

}
