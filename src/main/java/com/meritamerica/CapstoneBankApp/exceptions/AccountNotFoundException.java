package com.meritamerica.CapstoneBankApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//if id used to find an offering does not exist in the datatbase

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends Exception {
	
	public AccountNotFoundException(String errorMessage){
		super(errorMessage);
	}
}
