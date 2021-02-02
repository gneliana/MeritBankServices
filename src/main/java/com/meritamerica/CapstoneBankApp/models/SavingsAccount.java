package com.meritamerica.CapstoneBankApp.models;

import javax.persistence.*;

@Entity
@DiscriminatorValue("SavingsAccount")
public class SavingsAccount extends BankAccount {

	static final double INTEREST_RATE = .01;   //specific to personal savings accounts
	
	// all persistent classes in must have default constructor for Hibernate to instantiate
	public SavingsAccount() {
		super();
		super.setInterestRate(INTEREST_RATE);		
	}
	
}
