package com.meritamerica.CapstoneBankApp.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.models.AccountHolder;
import com.meritamerica.CapstoneBankApp.models.SavingsAccount;
import com.meritamerica.CapstoneBankApp.repositories.SavingsAccountRepository;

//this class is specifically for the tasks of adding to/searching the savings repository (database)

@Service
public class SavingsService {

	@Autowired // creates variables for this instance
	private SavingsAccountRepository repository;  // constructor needed to update/search databases
	@Autowired
	private AccountHolderService holderService;   // constructor needed to find holder by id

	// add savings account to database
	public SavingsAccount addSavingsAccount(SavingsAccount account, Integer id) throws AccountNotFoundException {
		AccountHolder holder = holderService.findById(id);   // use id given to locate holder
		holder.setSavingsAccounts(Arrays.asList(account));   // use Arrays class to convert array of account to List 
		account.setAccountHolder(holder);  					 // set list of savings accounts to belong to holder
		repository.save(account);							 // add given account to that holder's list of savings accounts
		return account;
	}

	// show all of this holder's savings accounts
	public List<SavingsAccount> findAccounts(Integer id) throws AccountNotFoundException {	
		return holderService.findById(id).getSavingsAccounts();
	}
}
