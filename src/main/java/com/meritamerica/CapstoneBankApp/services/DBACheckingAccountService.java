package com.meritamerica.CapstoneBankApp.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.models.AccountHolder;
import com.meritamerica.CapstoneBankApp.models.DBAChecking;
import com.meritamerica.CapstoneBankApp.models.User;
import com.meritamerica.CapstoneBankApp.repositories.DBACheckingAccountRepository;

//this class is specifically for the tasks of adding to/searching the checking repository (database)

@Service
public class DBACheckingAccountService {

	@Autowired // creates variables for this instance
	private DBACheckingAccountRepository repository; // constructor needed to update/search databases
	@Autowired 
	private UserService userService;  // constructor needed to find holder by id
	
	// add checking to database
	public DBAChecking addDbaCheckingAccount(DBAChecking account, Integer id) throws AccountNotFoundException {
		User user = userService.findById(id);   // use id given to locate holder
		user.setDbaCheckingAccounts(Arrays.asList(account));  // use Arrays class to convert array of account to List 
		account.setUser(user);  					 // set list of checking accounts to belong to holder
		repository.save(account);							 // add given account to that holder's list of checking accounts
		return account;
	}
	
	// show all of this holder's checking accounts
	public List<DBAChecking> findAccounts(Integer id) throws AccountNotFoundException {
		return userService.findById(id).getDbaCheckingAccounts();		
	}
}
