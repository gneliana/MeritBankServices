package com.meritamerica.CapstoneBankApp.models;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DiscriminatorValue("CDAccount") 
public class CDAccount extends BankAccount	 {   //inherits balance, dateOpened & getters/setters


	
	@NotNull // validation for int
	int term;
	
	// map instance of CDOffering to list of holder's cd accounts
	@ManyToOne   // each specific cd account derives from a list of many offerings (many-to-one)
	@JoinColumn(name = "offering_id")
	private CDOffering offering;
	
	public CDAccount() {  // all persistent classes must have default constructor for Hibernate to instantiate
		super();
	}
	
	// ---getters & setters---

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}		
	@JsonBackReference(value = "cdAccount")
	public CDOffering getcDOffering() {
		return offering;
	}	
	public void setCDOffering(CDOffering offering) {
		this.offering = offering;
	}
}
