package com.meritamerica.CapstoneBankApp.services;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.exceptions.NegativeBalanceException;
import com.meritamerica.CapstoneBankApp.models.AccountHolder;
import com.meritamerica.CapstoneBankApp.models.BankAccount;
import com.meritamerica.CapstoneBankApp.models.Transaction;
import com.meritamerica.CapstoneBankApp.models.User;
import com.meritamerica.CapstoneBankApp.repositories.TransactionRepository;

@Service 
public class TransactionService {

	BankAccount targetAccount;
	BankAccount sourceAccount;
	double amount;
	Date transactionDate;

	@Autowired 
	private TransactionRepository transactionRepository;  // constructor needed to update/search databases
	@Autowired
	private UserService userService;   // constructor needed to find holder by id
	
	
	// simple transaction or multiple accounts
	public Transaction transactionType(BankAccount sourceAccount, BankAccount targetAccount, Transaction transaction) 
			throws NegativeBalanceException {
		
		if(sourceAccount.equals(targetAccount)){
			transaction = simpleTransaction(sourceAccount, transaction);
		
		} else {
			transaction = transferTransaction(sourceAccount, targetAccount, transaction);
		}
		
		return transaction;
	}
	
	// withdraw 
    public void withdraw(BankAccount targetAccount, double amount) throws NegativeBalanceException {
        if(amount > targetAccount.getBalance()) {
            throw new NegativeBalanceException("Amount exceeds available balance");
        } 
         if(amount < 0) {
            throw new NegativeBalanceException("Cannot withdraw negative amount");
        }
         targetAccount.setBalance(targetAccount.getBalance()-amount);
    }

    // deposit
    public void deposit(BankAccount targetAccount, double amount) throws NegativeBalanceException {
      
       if(amount < 0) {
          throw new NegativeBalanceException("Cannot deposit negative amount");
      }
       targetAccount.setBalance(targetAccount.getBalance()+amount);
    }

    // method for transaction involving only 1 account (withdraw or deposit)
	public Transaction simpleTransaction(BankAccount targetAccount, Transaction transaction) throws NegativeBalanceException {
		
		
		if(transaction.getAmount() > 0){
			this.deposit(targetAccount, transaction.getAmount());
		}
		
		if(transaction.getAmount() < 0) {
			this.withdraw(targetAccount, transaction.getAmount() * -1);
		}

		transactionRepository.save(transaction);
		return transaction;
	}
	
	// method of transaction that involves multiple accounts (a transfer of balance)
	// each transfer will have a source account AND target account & each transaction will complete a withdraw AND deposit
	public Transaction transferTransaction(BankAccount sourceAccount, BankAccount targetAccount, Transaction transaction) {
		boolean success = false;
		
		try {
			sourceAccount.withdraw(sourceAccount, transaction.getAmount());
			success = true;
			targetAccount.deposit(targetAccount, transaction.getAmount());
		} catch (Exception e) {
			if(success) {
				sourceAccount.setBalance(sourceAccount.getBalance() + transaction.getAmount());
			}
		}
		return transaction;
	}
	
	// add transaction to database
	public Transaction addTransaction(Transaction transaction, Integer id) throws AccountNotFoundException {
		User user = userService.findById(id);   // use id given to locate holder
		user.setTransactions(Arrays.asList(transaction));   // use Arrays class to convert array of transactions to List 
		transaction.setUser(user);  					 // set list of transactions to belong to holder
		transactionRepository.save(transaction);			// add given transaction to that holder's list of transactions
		return transaction;		
	}
	
//	public Transaction closeAccount(Transaction transaction, AccountHolderInteger id) throws AccountNotFoundException {
//		
//		BankAccount targetAccount = 
//		
//	}
}
