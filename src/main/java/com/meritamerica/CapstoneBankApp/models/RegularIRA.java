package com.meritamerica.CapstoneBankApp.models;

public class RegularIRA extends IRAAccount {
	
	final static double INTEREST_RATE = .002;
	
	public RegularIRA() {
		super();
		super.setInterestRate(INTEREST_RATE);
		super.setMaxAccountsAllowed(1);
	}

}
