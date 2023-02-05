package com.greatlearning.studentmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.greatlearning.studentmgmt.entity.User;
import com.greatlearning.studentmgmt.repository.UserRepository;
import com.greatlearning.studentmgmt.security.StudUserDetails;

@Service
public class UserDetailsImpl implements UserDetailsService {

	@Autowired
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username + "is not available");
		}
		return new StudUserDetails(user);
	}

}
