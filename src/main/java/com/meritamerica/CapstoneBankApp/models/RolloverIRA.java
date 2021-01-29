package com.meritamerica.CapstoneBankApp.models;

public class RolloverIRA extends IRAAccount {
	
	final static double INTEREST_RATE = .002;
	
	public RolloverIRA() {
		super();
		super.setInterestRate(INTEREST_RATE);
		super.setMaxAccountsAllowed(1);
	}


}
