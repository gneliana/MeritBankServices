package com.meritamerica.CapstoneBankApp.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.models.MyUserDetails;
import com.meritamerica.CapstoneBankApp.models.User;
import com.meritamerica.CapstoneBankApp.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByUserName(userName);
		
		user.orElseThrow(() -> new UsernameNotFoundException("User not found " + userName));
		
		return user.map(MyUserDetails::new).get();
	}
}
