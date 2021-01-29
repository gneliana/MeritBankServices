package com.meritamerica.CapstoneBankApp.models;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Transaction {

	private long id;
	
	private long sourceAccount;
	private long targetAccount;
	private Date transactionDate;
	private double amount;
	private String returnMessage;
	private double newBalance;
	
	private boolean success;
	
	// join Transaction to Account Holder
	@ManyToOne
	@JoinColumn(name = "accountHolder_id")  
	private AccountHolder accountHolder;

	public Transaction(Date transactionDate, double amount, String returnMessage) {
		super();
		this.transactionDate = new Date();
		this.amount = 0;
		this.returnMessage = "";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(long sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public long getTargetAccount() {
		return targetAccount;
	}

	public void setTargetAccount(long targetAccount) {
		this.targetAccount = targetAccount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public double getNewBalance() {
		return newBalance;
	}

	public void setNewBalance(double newBalance) {
		this.newBalance = newBalance;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public AccountHolder getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(AccountHolder accountHolder) {
		this.accountHolder = accountHolder;
	}	
	
	
}
