 package com.meritamerica.CapstoneBankApp.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.repositories.UserRepository;

@Entity
@Table(name ="Users")
@JsonPropertyOrder({"id", "firstName", "middleName", "lastName", "ssn", "email", "phoneNumber", "address", "city", "state", "zipCode",
	"savingsAccounts", "personalCheckingAccounts", "dbaCheckingAccounts", "cdAccounts", "rothIraAccounts", "regularIraAccounts", "rolloverIraAccounts",
	"numberOfCheckingAccounts", "combinedCheckingBalance", 
	"numberOfSavingsAccounts", "combinedSavingsBalance", 
	"numberOfCdAccounts", "combinedCDBalance", 
	"getTotalCombinedBalances"})
public class User {
	// Required details for each Account

	@Id //This will be the id used to reference each specific user
	@GeneratedValue(strategy=GenerationType.AUTO)  // Auto-generation of the ids
	@Column(name = "users_id")  // this column is used to link to each of the account types for each account holder
	private Integer id;
	
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private boolean active;
	
	@NotNull(message = "First Name cannot be null")     // @notblank/@notnull to ensure required fields 
	@NotBlank(message = "First Name cannot be blank")
	private String firstName;
	
	// middle name not necessary
	private String middleName;
	
	@NotNull(message = "Last Name cannot be null")
	@NotBlank(message = "Last Name cannot be blank")
	private String lastName;
	@NotNull(message = "Social Security Number Name cannot be null")
	@NotBlank(message = "Social Security Number cannot be blank")
	private String ssn;
	@NotBlank(message = "Email cannot be null")
	@NotNull(message = "Email cannot be null")
	private String email;
	@NotBlank(message = "Phone Number cannot be null")
	@NotNull(message = "Phone Number cannot be null")
	private Integer phoneNumber;
	@NotBlank(message = "Address cannot be null")
	@NotNull(message = "Address cannot be null")
	private String address;
	@NotBlank(message = "City cannot be null")
	@NotNull(message = "City cannot be null")
	private String city;
	@NotBlank(message = "State cannot be null")
	@NotNull(message = "State Number cannot be null")
	private String state;
	@NotBlank(message = "Zip Code cannot be null")
	@NotNull(message = "Zip Code cannot be null")
	private String zipCode;
	
	private double totalBalance;
	
	
	@Autowired // creates variables for this instance
	private UserRepository userRepository;  // used for findById()
	
	
	//----- table relationships and array lists ----
	
	// this maps each account holder to each of the following types of accounts
	// (will also help keep track of combined balances)
	// CascadeType.ALL - any change that happens to this(user) entity, must update the associated account types as well
	// 
	// ?? Why keep these as one-to-many?
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Users", fetch = FetchType.LAZY)
	private List<PersonalChecking> personalCheckingAccounts;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Users", fetch = FetchType.LAZY)
	private List<DBAChecking> dbaCheckingAccounts;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Users", fetch = FetchType.LAZY)
	private List<SavingsAccount> savingsAccounts;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Users", fetch = FetchType.LAZY)
	private List<CDAccount> cdAccounts;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Users", fetch = FetchType.LAZY)
	private List<IRAAccount> iraAccounts;	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Users", fetch = FetchType.LAZY)
	private List<RothIRA> rothIraAccounts;	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Users", fetch = FetchType.LAZY)
	private List<RegularIRA> regularIraAccounts;	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Users", fetch = FetchType.LAZY)
	private List<RolloverIRA> rolloverIraAccounts;	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Users", fetch = FetchType.LAZY)
	private List<Transaction> transactions;
	
	
	@Column
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name="users_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	
	
	
	
	public User() {  // all persistent classes in must have default constructor for Hibernate to instantiate
		
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	

	// find user by their id
		// ** this will be referenced in other classes any time they need to locate holder by id **
	public User findById(Integer id) throws AccountNotFoundException {
		if(!(userRepository.existsById(id))) {
			throw new AccountNotFoundException("Account not found");
		}
		return userRepository.findById(id).orElse(null);
	}
	
	
	//---Field Getters/Setters--- ** this is the information that will be returned upon requests**

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public double getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	
	
	// change user's details
	
	public User updateContactDetails(User user) {
		this.firstName = user.getFirstName();
		this.middleName = user.getMiddleName();
		this.lastName = user.getLastName();
		this.ssn = user.getSsn();
		this.email = user.getEmail();
		this.phoneNumber = user.getPhoneNumber();
		
		return user;
	}
	
	
	
	
	// GETTERS--- lists of accounts
	
	@JsonManagedReference   // used to get out of endless recursion 
	public List<SavingsAccount> getSavingsAccounts() {
		return savingsAccounts;
	}
	@JsonManagedReference   // used to get out of endless recursion 
	public List<PersonalChecking> getPersonalCheckingAccounts() {
		return personalCheckingAccounts;
	}
	@JsonManagedReference   // used to get out of endless recursion 
	public List<DBAChecking> getDbaCheckingAccounts() {
		return dbaCheckingAccounts;
	}
	@JsonManagedReference   // used to get out of endless recursion 
	public List<CDAccount> getCdAccounts() {
		return cdAccounts;
	}
	@JsonManagedReference   // used to get out of endless recursion 
	public List<IRAAccount> getIraAccounts() {
		return iraAccounts;
	}
	@JsonManagedReference   // used to get out of endless recursion 
	public List<RothIRA> getRothIraAccounts() {
		return rothIraAccounts;
	}
	@JsonManagedReference   // used to get out of endless recursion 
	public List<RegularIRA> getRegularIraAccounts() {
		return regularIraAccounts;
	}
	@JsonManagedReference   // used to get out of endless recursion 
	public List<RolloverIRA> getRolloverIraAccounts() {
		return rolloverIraAccounts;
	}	
	
	
	
	// ---SETTERS--- lists of accounts
	
	public void setPersonalCheckingAccounts(List<PersonalChecking> personalCheckingAccounts) {
		this.personalCheckingAccounts = personalCheckingAccounts;
	}
	public void setDbaCheckingAccounts(List<DBAChecking> dbaCheckingAccounts) {
		this.dbaCheckingAccounts = dbaCheckingAccounts;
	}
	public void setSavingsAccounts(List<SavingsAccount> savingsAccounts) {
		this.savingsAccounts = savingsAccounts;
	}
	public void setCdAccounts(List<CDAccount> cdAccounts) {
		this.cdAccounts = cdAccounts;
	}
	public void setIraAccounts(List<IRAAccount> iraAccounts) {
		this.iraAccounts = iraAccounts;
	}
	public void setRothIraAccounts(List<RothIRA> rothIraAccounts) {
		this.rothIraAccounts = rothIraAccounts;
	}
	public void setRegularIraAccounts(List<RegularIRA> regularIraAccounts) {
		this.regularIraAccounts = regularIraAccounts;
	}
	public void setRolloverIraAccounts(List<RolloverIRA> rolloverIraAccounts) {
		this.rolloverIraAccounts = rolloverIraAccounts;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	
	
	//---- get combined balances & number of accounts---


	// savings balance
	public double getSavingsBalance() {
		double combinedSavingsBalance = 0;
		if (savingsAccounts != null) {
			for (BankAccount savAcct : savingsAccounts) {
				combinedSavingsBalance += savAcct.getBalance();
			}
			return combinedSavingsBalance;
		}
		return 0;
	}
	
	// get number of DBA checking accounts
	public int getNumberOfDBACheckingAccounts() {
		int total = 0;
		if (dbaCheckingAccounts != null) {
			for(DBAChecking dbaCheck : dbaCheckingAccounts) {
				total++;
			}
			return total;
		}
		return total;
	}
	
	// get DBA checking balance
	public double getCombinedDBACheckingBalance() {
		double combinedDBACheckingBalance = 0;
		if (dbaCheckingAccounts != null) {
			for (BankAccount dbaCheck : dbaCheckingAccounts) {
				combinedDBACheckingBalance += dbaCheck.getBalance();
			}
			return combinedDBACheckingBalance;
		}
		return 0;
	}
	
	// get personal checking balance
	public double getCombinedPersonalCheckingBalance() {
		double combinedPersonalCheckingBalance = 0;
		if (personalCheckingAccounts != null) {
			for (BankAccount personalCheck : personalCheckingAccounts) {
				combinedPersonalCheckingBalance += personalCheck.getBalance();
			}
			return combinedPersonalCheckingBalance;
		}
		return 0;
	}
	
	// number of cd accounts
	public int getNumberOfCdAccounts() {
		int total = 0;
		if (cdAccounts != null) {
			for(CDAccount cdAcct : cdAccounts) {
				total++;
			}
			return total;
		}
		return total;
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
	
	// regular IRA balance
	public double getRegularIRABalance() {
		double regularIRABalance = 0;
		if (regularIraAccounts != null) {
			for (RegularIRA acct : regularIraAccounts) {
				regularIRABalance += acct.getBalance();
			}
			return regularIRABalance;
		}
		return 0;
	}
	
	
	// roth IRA balance
	public double getRothIRABalance() {
		double rothIRABalance = 0;
		if (rothIraAccounts != null) {
			for (RothIRA acct : rothIraAccounts) {
				rothIRABalance += acct.getBalance();
			}
			return rothIRABalance;
		}
		return 0;
	}
	
	// rollover IRA balance
	public double getRolloverIRABalance() {
		double rolloverIRABalance = 0;
		if (rolloverIraAccounts != null) {
			for (RolloverIRA acct : rolloverIraAccounts) {
				rolloverIRABalance += acct.getBalance();
			}
			return rolloverIRABalance;
		}
		return 0;
	}
	
	// combined IRA balance
	public double getCombinedIRABalance() {
		return getRegularIRABalance() + getRothIRABalance() + getRolloverIRABalance();
	}

	// total of all balances
	public double getTotalCombinedBalances() {
		return getSavingsBalance() + getCombinedDBACheckingBalance() + getCombinedPersonalCheckingBalance() + getCombinedCDBalance() + getCombinedIRABalance();
	}
}
