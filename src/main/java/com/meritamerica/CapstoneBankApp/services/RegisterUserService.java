package com.meritamerica.CapstoneBankApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.Jwt.JwtUtil;
import com.meritamerica.CapstoneBankApp.repositories.RoleRepository;
import com.meritamerica.CapstoneBankApp.repositories.UserRepository;

@Service
public class RegisterUserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MyUserDetailsService myUserService;
	@Autowired
	private JwtUtil jwtUtil;
}
