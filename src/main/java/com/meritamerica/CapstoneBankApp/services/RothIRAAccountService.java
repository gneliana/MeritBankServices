package com.meritamerica.CapstoneBankApp.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.models.RothIRA;
import com.meritamerica.CapstoneBankApp.models.User;
import com.meritamerica.CapstoneBankApp.repositories.RothIRAAccountRepository;

@Service
public class RothIRAAccountService {

	@Autowired
	private RothIRAAccountRepository repository;
	@Autowired
	private UserService userService;
	
	//add account to database
	public RothIRA addRothIRA(RothIRA rothIra, Integer id) throws AccountNotFoundException {
		User user = userService.findById(id);
		user.setRothIraAccounts(Arrays.asList(rothIra));
		rothIra.setUser(user);
		repository.save(rothIra);
		return rothIra;
	}
	
	//find accounts
	public List<RothIRA> findAccount(Integer id) throws AccountNotFoundException{
		return userService.findById(id).getRothIraAccounts();
	}
	
}
