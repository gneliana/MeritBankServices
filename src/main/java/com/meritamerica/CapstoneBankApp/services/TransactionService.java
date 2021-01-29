package com.meritamerica.CapstoneBankApp.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.exceptions.NegativeBalanceException;
import com.meritamerica.CapstoneBankApp.models.BankAccount;
import com.meritamerica.CapstoneBankApp.models.Transaction;

@Service 
public class TransactionService {

	BankAccount targetAccount;
	BankAccount sourceAccount;
	double amount;
	Date transactionDate;
	
	public Transaction withdraw( BankAccount targetAcount, double amount) throws NegativeBalanceException {
		
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.transactionDate = new Date();
		
		return null;
	}
		
	
	
	 
}
