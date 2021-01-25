package com.meritamerica.CapstoneBankApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.models.AccountHolderContactDetails;
import com.meritamerica.CapstoneBankApp.repositories.AccountHolderContactDetailsRepository;

//**This class is specifically for the tasks of adding/searching the database holding account holder details**

@Service
public class AccountHolderContactDetailsService {

	@Autowired // creates variables for this instance
	private AccountHolderContactDetailsRepository repository;

	// add service
	public AccountHolderContactDetails addDetails(AccountHolderContactDetails details) {
		repository.save(details);
		return details;
	}

	// find by one account by id service
	public AccountHolderContactDetails findById(int id) throws AccountNotFoundException {
		if(!(repository.existsById(id))) {
			throw new AccountNotFoundException("Account not found");
		}
		return repository.findById(id).orElse(null);
	}
}
