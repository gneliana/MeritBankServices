package com.meritamerica.CapstoneBankApp.models;

import com.meritamerica.CapstoneBankApp.exceptions.NegativeBalanceException;
import com.meritamerica.CapstoneBankApp.services.TransactionService;




// *** add closeAccount override method

public abstract class IRAAccount extends BankAccount {

	TransactionService transactionService;
	
	@Override
	public Transaction simpleTransaction(BankAccount targetAccount, Transaction transaction) throws NegativeBalanceException {
		
		if(transaction.getAmount() > 0){
			this.deposit(targetAccount, transaction.getAmount());
		}
		
		if(transaction.getAmount() < 0) {
			this.withdraw(targetAccount, transaction.getAmount() * -1);
			
			//pay 20% to IRS when withdrawing from any IRA account
			double irsDeduction = (transaction.getAmount() * -1) * .2; 
			// get a whole double
			irsDeduction = (Math.floor(irsDeduction * 100)/100);
		}
//		transactionService.save(transaction);
		return transaction;
	}
	
// NEED TO ADD 20% TAX
	@Override
	public Transaction transferTransaction(BankAccount sourceAccount, BankAccount targetAccount,
			Transaction transaction) {
		
		if(transaction.getAmount() > 0){
			this.deposit(targetAccount, transaction.getAmount());
		}
		
		if(transaction.getAmount() < 0) {
			try {
				this.withdraw(targetAccount, transaction.getAmount() * -1);
			} catch (NegativeBalanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return transaction;
	}

	// need to ensure you can cover cost of penalties before withdrawing from any ira account
	@Override
	public void withdraw(BankAccount targetAccount, double amount) throws NegativeBalanceException {
		
		// withdraw amount + penalty must be greater than the amount available for withdraw
		double penatlyCoverage = Math.floor((amount * 1.2) * 100)/100; // must use to get a whole, rounded double
		
		if(penatlyCoverage > super.getBalance()) 
			throw new NegativeBalanceException("Available balance must cover withdraw amount and 20% IRS fee");
		if(amount > super.getBalance())
			throw new NegativeBalanceException("Withdraw amount exceeds availabe balance");
		if(amount < 0)
			throw new NegativeBalanceException("Cannot withdraw negative amount");

		transactionService.withdraw(targetAccount, amount);
	}
	
	
}
