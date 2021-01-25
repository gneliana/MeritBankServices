package com.meritamerica.CapstoneBankApp.models;

import javax.persistence.*;

@Entity
@DiscriminatorValue("DBAChecking")
public class DBAChecking extends BankAccount {
	
	static final double INTEREST_RATE = .0001;   //specific to dba checking accounts

	// all persistent classes in must have default constructor for Hibernate to instantiate
	public DBAChecking() {	
		super();
		super.setInterestRate(INTEREST_RATE);
	}
}