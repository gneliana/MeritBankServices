package com.meritamerica.CapstoneBankApp.models;

import javax.persistence.*;

@Entity
@DiscriminatorValue("PersonalChecking")
public class PersonalChecking extends BankAccount{

	static final double INTEREST_RATE = .0001;   //specific to personal checking accounts
	
	// all persistent classes in must have default constructor for Hibernate to instantiate
	public PersonalChecking() {
		super();
		super.setInterestRate(INTEREST_RATE);		
	}
}
