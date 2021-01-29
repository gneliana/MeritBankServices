package com.meritamerica.CapstoneBankApp.models;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.DiscriminatorOptions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.meritamerica.CapstoneBankApp.exceptions.NegativeBalanceException;

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
	
	// uses local date - will only be valuable if info is input same day, and also locally
	private Date dateOpened;
	private int maxAccountsAllowed;
	
	@DecimalMin("0.0")
	@DecimalMax("0.999999")
	private double interestRate;
	
	
	// join BankAccount to Account Holder
	@ManyToOne
	@JoinColumn(name = "accountHolder_id")  
	private AccountHolder accountHolder;
	
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
	
	@JsonBackReference  // gets rid of infinite recursion
	public AccountHolder getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
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
	
    public void withdraw(double amount) throws NegativeBalanceException {
        if(amount > this.balance) {
            throw new NegativeBalanceException("Amount exceeds available balance");
        } 
         if(amount < 0) {
            throw new NegativeBalanceException("Cannot withdraw negative amount");
        }
        this.balance -= amount;
    }

    public void deposit(double amount) throws NegativeBalanceException {
      
       if(amount < 0) {
          throw new NegativeBalanceException("Cannot deposit negative amount");
      }
      this.balance += amount;
    }
	
	protected Transaction simpleTransaction(Transaction transaction) throws NegativeBalanceException {
		
			if(transaction.getAmount() > 0){
				this.deposit(transaction.getAmount());
			}
			// turn number negative
			if(transaction.getAmount() < 0) {
				this.withdraw(-1 * transaction.getAmount());
			}

		return transaction;
	}
	
	protected Transaction multipleAccountTransaction(Transaction transaction, BankAccount sourceAccount, BankAccount targetAccount) {
		boolean success = false;
		
		try {
			sourceAccount.withdraw(transaction.getAmount());
			success = true;
			targetAccount.deposit(transaction.getAmount());
		} catch (Exception e) {
			if(success) {
				sourceAccount.setBalance(sourceAccount.getBalance() + transaction.getAmount());
			}
		}
		return transaction;
	}
}
