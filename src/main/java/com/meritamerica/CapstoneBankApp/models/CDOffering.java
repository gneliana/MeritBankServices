package com.meritamerica.CapstoneBankApp.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "CDOffering")
public class CDOffering {
	
	@Id  //This will be the id used to reference the specific offering
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "offering_id") // this column name is used to link to the cd accounts
	Integer id;
	
	@Min(value = 1, message = "Term must be at least 1 year")
	Integer term;	
	
	// insures interest rate is a positive number that is less than 1
	@DecimalMin(value = "0.0", inclusive = false, message = "interest rate must be greater than zero") 
	@DecimalMax(value = "1.0", inclusive = false, message = "interest rate must be less than one")
	private double interestRate;
	
	// map instance of offering to cd account
	// cascade ALL - any change that happens to this (offering) entity, must update the cd account as well
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "offering", fetch = FetchType.LAZY)
	private List <CDAccount> cdAccounts;
		
	public CDOffering(){  // all persistent classes in must have default constructor for Hibernate to instantiate
	}
	
	//--- getters/setters	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTerm() {
		return term;
	}	
	public void setTerm(Integer term) {
		this.term = term;
	}	
	public double getInterestRate() {
		return interestRate;
	}	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}	
	@JsonManagedReference(value="cdAccount") // used to end infinite recursion
    public List<CDAccount> getcDAccounts() {
        return cdAccounts;
    }	
	public void setCDAccounts(List<CDAccount> accounts) {
		this.cdAccounts = new ArrayList<CDAccount>(cdAccounts);
	}
}
