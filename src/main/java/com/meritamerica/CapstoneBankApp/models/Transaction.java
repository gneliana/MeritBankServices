package com.meritamerica.CapstoneBankApp.models;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Transaction {

	private Integer id;
	
	private Integer sourceAccount;
	private Integer targetAccount;
	private Date transactionDate;
	private double amount;
	private String returnMessage;
	private double newBalance;
	
	private boolean success;
	
	// join Transaction to Account Holder
	@ManyToOne
	@JoinColumn(name = "users_id")  
	private User user;

	public Transaction(Date transactionDate, double amount, String returnMessage) {
		super();
		this.transactionDate = new Date();
		this.amount = 0;
		this.returnMessage = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(Integer sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public Integer getTargetAccount() {
		return targetAccount;
	}

	public void setTargetAccount(Integer targetAccount) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	
	
}
