package com.meritamerica.CapstoneBankApp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.CapstoneBankApp.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String userName);
	
	Boolean existsByUsername(String username);
	
}
