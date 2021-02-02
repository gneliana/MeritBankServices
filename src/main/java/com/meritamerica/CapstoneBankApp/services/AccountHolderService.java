package com.meritamerica.CapstoneBankApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.models.AccountHolder;
import com.meritamerica.CapstoneBankApp.repositories.AccountHolderRepository;

//** This class is for updating/searching the account holder database **

//@Service 
public class AccountHolderService {

//	@Autowired // creates variables for this instance
//	AccountHolderRepository accountHolderRepository;  // constructor needed to update/search the database
//	
//	// add account holder to database
//	public AccountHolder addAccountHolder(AccountHolder account) {
//		accountHolderRepository.save(account);
//		return account;
//	}
//
//	// find holder by their id
//		// ** this will be referenced in other classes any time they need to locate holder by id **
//	public AccountHolder findById(int id) throws AccountNotFoundException {
//		if(!(accountHolderRepository.existsById(id))) {
//			throw new AccountNotFoundException("Account not found");
//		}
//		return accountHolderRepository.findById(id).orElse(null);
//	}
//
//	// return list of all holders
//	public List<AccountHolder> findAllAccountHolders(){
//		return accountHolderRepository.findAll();
//	}
}
