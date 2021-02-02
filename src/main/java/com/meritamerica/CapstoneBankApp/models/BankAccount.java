package com.meritamerica.CapstoneBankApp.models;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.DiscriminatorOptions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.exceptions.NegativeBalanceException;
import com.meritamerica.CapstoneBankApp.services.TransactionService;



//*** ADD closeAccount method

//Directions hinted at using @MappedSuperClass, however, using @Entity/@inheritance allows each subclass to be a table
@Entity(name = "BankAccount")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="account_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorOptions(force=true)
@JsonPropertyOrder({"id", "balance", "interestRate", "dateOpened"})
public abstract class BankAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	// ensure positive value
	@Min(value = 0, message = "Cannot have negative balance.")
	double balance;
	
	private Date dateOpened;
	private int maxAccountsAllowed; //each acct type has a limit on number each user may hold
	
	@DecimalMin("0.0")
	@DecimalMax("0.999999")
	private double interestRate;
	
	private TransactionService transactionService;
	
//	// join BankAccount to Account Holder
//	@ManyToOne
//	@JoinColumn(name = "accountHolder_id")  
//	private AccountHolder accountHolder;

	// join BankAccount to User
	@ManyToOne
	@JoinColumn(name = "users_id")  
	private User user;
	
	
	
	// default constructor
	public BankAccount(){		
		this.dateOpened = new Date();
	}	
	
	// ---- Future Value ----
	// FutureValue = PresentValue(1+interestRate)^{term}
	// use this recursively
	
	public double futureValue(int term) {
		if(term == 0)
			return this.balance;
		return futureValue(term - 1) * (1 + this.interestRate);
	}

	//----------Getters/Setters
	
//	@JsonBackReference  // gets rid of infinite recursion
//	public AccountHolder getAccountHolder() {
//		return accountHolder;
//	}
//	public void setAccountHolder(AccountHolder accountHolder) {
//		this.accountHolder = accountHolder;
//	}	
	
	
	@JsonBackReference  // gets rid of infinite recursion
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	public double getBalance() {
		return balance;
	}	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Date getDateOpened() {
		return dateOpened;
	}	
	public void setDateOpened(Date dateOpened) {
		this.dateOpened = dateOpened;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public int getMaxAccountsAllowed() {
		return maxAccountsAllowed;
	}
	public void setMaxAccountsAllowed(int maxAccountsAllowed) {
		this.maxAccountsAllowed = maxAccountsAllowed;
	}
	
	
	
	// ------ TRANSACTIONS -----
	
	public Transaction transactionType(BankAccount sourceAccount, BankAccount targetAccount, Transaction transaction) {
		try {
			transaction = transactionService.transactionType(sourceAccount, targetAccount, transaction);
		} catch (NegativeBalanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transaction;
	}	
	public Transaction simpleTransaction(BankAccount targetAccount, Transaction transaction) throws NegativeBalanceException {
		try {
			transaction = transactionService.simpleTransaction(targetAccount, transaction);
		} catch (NegativeBalanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transaction;
	}	
	public Transaction transferTransaction(BankAccount sourceAccount, BankAccount targetAccount, Transaction transaction) {
		transaction = transactionService.transferTransaction(sourceAccount, targetAccount, transaction);
		return transaction;
	}	
	public void withdraw(BankAccount targetAccount, double amount) throws NegativeBalanceException {
		try {
			transactionService.withdraw(targetAccount, amount);
		} catch (NegativeBalanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deposit(BankAccount targetAccount, double amount) {
		try {
			transactionService.deposit(targetAccount, amount);
		} catch (NegativeBalanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Transaction addTransaction(Transaction transaction, Integer id) {
		try {
			transactionService.addTransaction(transaction, id);
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transaction;
	}
	
}
