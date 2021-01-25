package com.meritamerica.CapstoneBankApp.models;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.DiscriminatorOptions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
	LocalDate dateOpened;
	
	@DecimalMin("0.0")
	@DecimalMax("0.999999")
	private double interestRate;
	
	
	// join BankAccount to Account Holder
	@ManyToOne
	@JoinColumn(name = "accountHolder_id")  
	private AccountHolder accountHolder;
	
	// default constructor
	public BankAccount(){		
		this.dateOpened = LocalDate.now();
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
	public LocalDate getDateOpened() {
		return dateOpened;
	}	
	public void setDateOpened(LocalDate dateOpened) {
		this.dateOpened = dateOpened;
	}

	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}
