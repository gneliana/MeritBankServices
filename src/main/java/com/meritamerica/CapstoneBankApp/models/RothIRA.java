package com.meritamerica.CapstoneBankApp.models;

public class RothIRA extends IRAAccount {
	
	final static double INTEREST_RATE = .002;
	
	public RothIRA() {
		super();
		super.setInterestRate(INTEREST_RATE);
		super.setMaxAccountsAllowed(1);
	}


}
