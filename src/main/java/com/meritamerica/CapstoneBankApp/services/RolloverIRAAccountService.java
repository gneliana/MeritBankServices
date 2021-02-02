package com.meritamerica.CapstoneBankApp.services;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.models.RolloverIRA;
import com.meritamerica.CapstoneBankApp.models.User;
import com.meritamerica.CapstoneBankApp.repositories.RolloverIRAAccountRepository;

@Service
public class RolloverIRAAccountService {

	@Autowired
	private RolloverIRAAccountRepository repository;
	@Autowired
	private UserService userService;
	
	//add account to database
	public RolloverIRA addRolloverIRA(RolloverIRA rolloverIra, Integer id) throws AccountNotFoundException {
		User user = userService.findById(id);
		user.setRolloverIraAccounts(Arrays.asList(rolloverIra));
		rolloverIra.setUser(user);
		repository.save(rolloverIra);
		return rolloverIra;
	}
	
	//find accounts
	public List<RolloverIRA> findAccount(Integer id) throws AccountNotFoundException{
		return userService.findById(id).getRolloverIraAccounts();
	}
}
