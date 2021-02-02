package com.meritamerica.CapstoneBankApp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.CapstoneBankApp.models.BankAccount;
import com.meritamerica.CapstoneBankApp.models.CDAccount;
import com.meritamerica.CapstoneBankApp.models.Transaction;
import com.meritamerica.CapstoneBankApp.services.CDAccountService;
import com.meritamerica.CapstoneBankApp.services.DBACheckingAccountService;
import com.meritamerica.CapstoneBankApp.services.PersonalCheckingAccountService;
import com.meritamerica.CapstoneBankApp.services.RegularIRAAccountService;
import com.meritamerica.CapstoneBankApp.services.RolloverIRAAccountService;
import com.meritamerica.CapstoneBankApp.services.RothIRAAccountService;
import com.meritamerica.CapstoneBankApp.services.TransactionService;
import com.meritamerica.CapstoneBankApp.services.UserService;

//@RestController
public class TransactionController {

//	CDAccountService cdAccountService;
//	DBACheckingAccountService dbaCheckingService;
//	PersonalCheckingAccountService personalCheckingService;
//	RegularIRAAccountService regularIraService;
//	RothIRAAccountService rothIraService;
//	RolloverIRAAccountService rolloverService;
//	TransactionService transactionService;
//	UserService userService;
	
//	@PreAuthorize("hasAuthority('AccountHolder')")
//	@ResponseStatus(HttpStatus.CREATED)
//	@GetMapping(value = "/Me/CDAccount/Transaction")
//	public Transaction addTransaction(HttpServletRequest request, @Valid @RequestBody Transaction transaction) {
//		
//		BankAccount sourceAccount = userService.findById(transaction.getSourceAccount());
//	}
	
}
