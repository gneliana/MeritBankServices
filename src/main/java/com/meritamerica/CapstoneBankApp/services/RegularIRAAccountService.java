package com.meritamerica.CapstoneBankApp.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.models.RegularIRA;
import com.meritamerica.CapstoneBankApp.models.User;
import com.meritamerica.CapstoneBankApp.repositories.RegularIRAAccountRepository;

@Service 
public class RegularIRAAccountService {

	@Autowired
	private RegularIRAAccountRepository repository;
	@Autowired 
	private UserService userService;
	
	
	//add account to database
	public RegularIRA addRegularIRA(RegularIRA regularIra, Integer id) throws AccountNotFoundException {
		User user = userService.findById(id);
		user.setRegularIraAccounts(Arrays.asList(regularIra));
		regularIra.setUser(user);
		repository.save(regularIra);
		return regularIra;
	}
	
	//find accounts
	public List<RegularIRA> findAccount(Integer id) throws AccountNotFoundException{
		return userService.findById(id).getRegularIraAccounts();
	}
	
}
