package com.meritamerica.CapstoneBankApp.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.models.AccountHolder;
import com.meritamerica.CapstoneBankApp.models.PersonalChecking;
import com.meritamerica.CapstoneBankApp.repositories.PersonalCheckingAccountRepository;

//this class is specifically for the tasks of adding to/searching the checking repository (database)

@Service
public class PersonalCheckingAccountService {

	@Autowired // creates variables for this instance
	private PersonalCheckingAccountRepository repository; // constructor needed to update/search databases
	@Autowired 
	private AccountHolderService holderService;  // constructor needed to find holder by id
	
	// add checking to database
	public PersonalChecking addCheckingAccount(PersonalChecking account, Integer id) throws AccountNotFoundException {
		AccountHolder holder = holderService.findById(id);   // use id given to locate holder
		holder.setPersonalCheckingAccounts(Arrays.asList(account));  // use Arrays class to convert array of account to List 
		account.setAccountHolder(holder);  					 // set list of checking accounts to belong to holder
		repository.save(account);							 // add given account to that holder's list of checking accounts
		return account;
	}
	
	// show all of this holder's checking accounts
	public List<PersonalChecking> findAccounts(Integer id) throws AccountNotFoundException {
		return holderService.findById(id).getPersonalCheckingAccounts();		
	}
}

