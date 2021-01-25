package com.meritamerica.CapstoneBankApp.models;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//** This class has lists of accounts of each type and basic information on each account holder

@Entity
@Table(name = "AccountHolder")				// sets the order in which the fields of account holder object are returned in Json
@JsonPropertyOrder({"id", "firstName", "middleName", "lastName", "ssn", 
					"personalChecking", "savingsAccounts", "cdAccounts",
					"numberOfCheckingAccounts", "combinedCheckingBalance", 
					"numberOfSavingsAccounts", "combinedSavingsBalance", 
					"numberOfCdAccounts", "combinedCDBalance", 
					"getTotalCombinedBalances"})
public class AccountHolder {
	// Required details for each Account
	
	@Id //This will be the id used to reference each specific account holder
	@GeneratedValue(strategy = GenerationType.AUTO)  // Auto-generation of the ids
	@Column(name = "accountHolder_id") // this column is used to link to each of the account types for each account holder
	Integer id;
	
	@NotNull(message = "First Name can not be null")     // @notblank/@notnull to ensure required fields 
	@NotBlank(message = "First Name can not be blank")
	String firstName;
	
	// middle name not necessary
	String middleName;
	
	@NotNull(message = "Last Name can not be null")
	@NotBlank(message = "Last Name can not be blank")
	String lastName;
	
	@NotNull(message = "Social Security Number Name can not be null")
	@NotBlank(message = "Social Security Number can not be blank")
	String ssn;
	
	
	//----- table relationships and array lists ----
	
	// this maps each account holder to each of the following types of accounts
	// (will also help keep track of combined balances)
	// CascadeType.ALL - any change that happens to this(accountHolder) entity, must update the associated account types as well
	
	// each accountHolder has 1 set of Details & each set of Details belongs to only 1 AccountHolder (one-to-one)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_holders_contact_Details_id", referencedColumnName = "account_holders_contact_details_id")
	private AccountHolderContactDetails contactDetails;

	// an AccountHolder may have numerous CheckingAccounts, but each list of CheckingAccounts can only have 1 AccountHolder (one-to-many)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	private List<PersonalChecking> personalCheckingAccounts;
	
	// an AccountHolder may have numerous CheckingAccounts, but each list of CheckingAccounts can only have 1 AccountHolder (one-to-many)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	private List<DBAChecking> dbaCheckingAccounts;
		
	// an AccountHolder may have numerous SavingsAccounts, but each list of SavingsAccounts can only have 1 AccountHolder (one-to-many)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	private List<SavingsAccount> savingsAccounts;
	
	// an AccountHolder may have numerous CDAccounts, but each list of CDAccounts can only have 1 AccountHolder (one-to-many)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accountHolder", fetch = FetchType.LAZY)
	private List<CDAccount> cdAccounts;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "users_id")
	private User user;
	
	
	public AccountHolder() { // all persistent classes in must have default constructor for Hibernate to instantiate
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getSsn() {
		return ssn;
	}


	public void setSsn(String ssn) {
		this.ssn = ssn;
	}


	public AccountHolderContactDetails getContactDetails() {
		return contactDetails;
	}


	public void setContactDetails(AccountHolderContactDetails contactDetails) {
		this.contactDetails = contactDetails;
	}

	@JsonManagedReference   // used to get out of endless recursion 
	public List<PersonalChecking> getPersonalCheckingAccounts() {
		return personalCheckingAccounts;
	}


	public void setPersonalCheckingAccounts(List<PersonalChecking> personalChecking) {
		this.personalCheckingAccounts = personalChecking;
	}
	
	@JsonManagedReference   // used to get out of endless recursion 
	public List<DBAChecking> getDBACheckingAccounts() {
		return dbaCheckingAccounts;
	}


	public void setDBACheckingAccounts(List<DBAChecking> dbaCheckingAccounts) {
		this.dbaCheckingAccounts = dbaCheckingAccounts;
	}

	@JsonManagedReference   // used to get out of endless recursion 
	public List<SavingsAccount> getSavingsAccounts() {
		return savingsAccounts;
	}


	public void setSavingsAccounts(List<SavingsAccount> savingsAccounts) {
		this.savingsAccounts = savingsAccounts;
	}

	@JsonManagedReference   // used to get out of endless recursion 
	public List<CDAccount> getCdAccounts() {
		return cdAccounts;
	}


	public void setCdAccounts(List<CDAccount> cdAccounts) {
		this.cdAccounts = cdAccounts;
	}

	@JsonManagedReference   // used to get out of endless recursion 
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	//---- get combined balances & number of accounts---
	
	// get number of savings 
	public int getNumberOfSavingsAccounts() {
		if (savingsAccounts != null) {
			return savingsAccounts.size();
		}
		return 0;
	}
	
	// get number personal of checking
	public int getNumberOfCheckingAccounts() {
		if (personalCheckingAccounts != null) {
			return personalCheckingAccounts.size();
		}
		return 0;
	}
	
	// get number of dba checking
	public int getNumberOfDbaCheckingAccounts() {
		if (dbaCheckingAccounts != null) {
			return dbaCheckingAccounts.size();
		}
		return 0;
	}
	
	// number of cd accounts
	public int getNumberOfCdAccounts() {
		if (cdAccounts != null) {
			return cdAccounts.size();
		}
		return 0;
	}
	
	// combined savings
	public double getCombinedSavingsBalance() {
		double combinedSavingsBalance = 0;
		if (savingsAccounts != null) {
			for (BankAccount svAcct : savingsAccounts) {
				combinedSavingsBalance += svAcct.getBalance();
			}
			return combinedSavingsBalance;
		}
		return 0;
	}
	
	
	// get combined checking
	public double getCombinedPersonalCheckingBalance() {
		double combinedPersonalCheckingBalance = 0;
		if (personalCheckingAccounts != null) {
			for (BankAccount persCheckAcct : personalCheckingAccounts) {
				combinedPersonalCheckingBalance += persCheckAcct.getBalance();
			}
			return combinedPersonalCheckingBalance;
		}
		return 0;
	}
	
	// get combined checking
	public double getCombinedDBACheckingBalance() {
		double combinedDbaCheckingBalance = 0;
		if (dbaCheckingAccounts != null) {
			for (BankAccount dbaCheckAcct : dbaCheckingAccounts) {
				combinedDbaCheckingBalance += dbaCheckAcct.getBalance();
			}
			return combinedDbaCheckingBalance;
		}
		return 0;
	}
	
	// combined cd balance
	public double getCombinedCDBalance() {
		double combinedCDBalance = 0;
		if (cdAccounts != null) {
			for (BankAccount cdAcct : cdAccounts) {
				combinedCDBalance += cdAcct.getBalance();
			}
			return combinedCDBalance;
		}
		return 0;
	}
	
	// total of all balances
	public double getTotalCombinedBalances() {
		return getCombinedSavingsBalance() + getCombinedPersonalCheckingBalance() + getCombinedCDBalance();
	}
	
	

}
