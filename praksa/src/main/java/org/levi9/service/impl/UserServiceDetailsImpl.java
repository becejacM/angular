package org.levi9.service.impl;

import org.levi9.DTO.SecurityUser;
import org.levi9.model.User;
import org.levi9.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDetailsImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = this.userRepository.findByName(name);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", name));
		} else {
			return new SecurityUser(user);
		}
	}
}
