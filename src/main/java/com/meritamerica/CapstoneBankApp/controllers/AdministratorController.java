package com.meritamerica.CapstoneBankApp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.exceptions.ExceedsCombinedBalanceLimitException;
import com.meritamerica.CapstoneBankApp.exceptions.InterestRateOutOfBoundsException;
import com.meritamerica.CapstoneBankApp.exceptions.NegativeBalanceException;
import com.meritamerica.CapstoneBankApp.models.CDAccount;
import com.meritamerica.CapstoneBankApp.models.DBAChecking;
import com.meritamerica.CapstoneBankApp.models.PersonalChecking;
import com.meritamerica.CapstoneBankApp.models.RegularIRA;
import com.meritamerica.CapstoneBankApp.models.RolloverIRA;
import com.meritamerica.CapstoneBankApp.models.RothIRA;
import com.meritamerica.CapstoneBankApp.models.SavingsAccount;
import com.meritamerica.CapstoneBankApp.models.SignupRequest;
import com.meritamerica.CapstoneBankApp.models.User;
import com.meritamerica.CapstoneBankApp.services.CDAccountService;
import com.meritamerica.CapstoneBankApp.services.CDOfferingService;
import com.meritamerica.CapstoneBankApp.services.DBACheckingAccountService;
import com.meritamerica.CapstoneBankApp.services.PersonalCheckingAccountService;
import com.meritamerica.CapstoneBankApp.services.RegisterUserService;
import com.meritamerica.CapstoneBankApp.services.RegularIRAAccountService;
import com.meritamerica.CapstoneBankApp.services.RolloverIRAAccountService;
import com.meritamerica.CapstoneBankApp.services.RothIRAAccountService;
import com.meritamerica.CapstoneBankApp.services.SavingsService;
import com.meritamerica.CapstoneBankApp.services.TransactionService;
import com.meritamerica.CapstoneBankApp.services.UserService;

@RestController
public class AdministratorController {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	@Autowired
	private CDAccountService cdAccountService;
	@Autowired
	private DBACheckingAccountService dbaCheckingService;
	@Autowired
	private PersonalCheckingAccountService personalCheckingService;
	@Autowired
	private RegularIRAAccountService regularIraService;
	@Autowired
	private RothIRAAccountService rothIraService;
	@Autowired
	private RolloverIRAAccountService rolloverIraService;
	@Autowired
	private SavingsService savingsService;
	@Autowired
	RegisterUserService registerUserService;
	@Autowired
	private TransactionService transactionService;
	
	
	// --- CRUD METHODS ---
	
//	@PreAuthorize("hasAuthority('admin')")
//	@PostMapping("/authenticate/createUser")
//	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//		return registerUserService.registerUser(signUpRequest);
//	}	

	//--find an user by id
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}")
	public User findById(@PathVariable Integer id) throws AccountNotFoundException {
		return userService.findById(id);
	}
	
	//--add user
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders")
	public User addAccountHolder(@Valid @RequestBody User user) {
		userService.addUser(user);
		return user;
	}

	//--find users
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/AccountHolders")
	public List<User> findAllUsers() {
		return userService.findAllUsers();
	}
	
//	//--add contact details
//	@PreAuthorize("hasAuthority('admin')")
//	@ResponseStatus(HttpStatus.CREATED)
//	@PostMapping(value = "/AccountHolders/{id}/Details")
//	public AccountHolderContactDetails addDetails(@Valid @PathVariable Integer id,
//			@RequestBody AccountHolderContactDetails details) {
//		return detailsService.addDetails(details);
//	}
//
//	//--find contact details
//	@PreAuthorize("hasAuthority('admin')")
//	@ResponseStatus(HttpStatus.OK)
//	@GetMapping(value = "AccountHolders/{id}/Details")
//	public Object getDetails(Integer id) throws AccountNotFoundException {
//		return detailsService.findById(id);
//	}
//	
	
	
	
	//-- PERSONAL CHECKING
	
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/PersonalChecking")
	public PersonalChecking addPersonalCheckingAccount(@Valid @RequestBody PersonalChecking account, @PathVariable Integer id)
			throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException {
		
		//--balance must not be negative & an account holders combined balances may not exceed 250_000
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		if (account.getBalance() + ((User) findById(id)).getTotalCombinedBalances() > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		return personalCheckingService.addCheckingAccount(account, id);
	}

	//--show list of user's checking accounts using the user's id
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/PersonalChecking")
	public List<PersonalChecking> findPersonalCheckingById(@PathVariable Integer id) throws AccountNotFoundException {
		return personalCheckingService.findAccounts(id);
	}

	
	//--DBA CHECKING
	
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/DBAChecking")
	public DBAChecking addDbaCheckingAccount(@Valid @RequestBody DBAChecking account, @PathVariable Integer id)
			throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException {
		
		//--balance must not be negative & an account holders combined balances may not exceed 250_000
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		if (account.getBalance() + ((User) findById(id)).getTotalCombinedBalances() > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		return dbaCheckingService.addDbaCheckingAccount(account, id);
	}

	//--show list of user's checking accounts using the user's id
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/DBAChecking")
	public List<DBAChecking> findDbaCheckingById(@PathVariable Integer id) throws AccountNotFoundException {
		return dbaCheckingService.findAccounts(id);
	}
	
	
	//--- SAVINGS ACCOUNT
	
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	public SavingsAccount addSavingsAccount(@Valid @PathVariable Integer id, @RequestBody SavingsAccount account)
			throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException {		
	
		//--balance must not be negative & an account holders combined balances may not exceed 250_000
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		if (account.getBalance() + ((User) findById(id)).getTotalCombinedBalances() > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		return savingsService.addSavingsAccount(account, id);
	}

	//--find savings account by id
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/SavingsAccounts")
	public List<SavingsAccount> findSavingsById(@PathVariable Integer id) throws AccountNotFoundException {
		return savingsService.findAccounts(id);
	}
	
	
	//--- CD ACCOUNTS

	//--add cd account to holder's list of cd accounts
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping(value = "/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCDAccount(@Valid @PathVariable Integer id, @RequestBody CDAccount account) 
			throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException, InterestRateOutOfBoundsException {		
		
		// balance must not be negative & an account holders combined balances may not exceed 250_000
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		if (account.getBalance() + ((User) findById(id)).getTotalCombinedBalances() > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		if (account.getInterestRate() <= 0 || account.getInterestRate() >=1) {
			throw new InterestRateOutOfBoundsException("Interest Rate must ;greater than zero & less than one");
		}
		return cdAccountService.addAccount(account, id);
	}

	//--show list of cd accounts for holder
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/CDAccounts")
	public List<CDAccount> getCDAccounts(@PathVariable Integer id) throws AccountNotFoundException {
		return cdAccountService.findAccounts(id);
	}
	
	
	//--- REGULAR IRA ACCOUNT
	
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/RegularIRA")
	public RegularIRA addRegularIRA(@Valid @PathVariable Integer id, @RequestBody RegularIRA account)
			throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException {		
	
		//--balance must not be negative & an account holders combined balances may not exceed 250_000
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		if (account.getBalance() + ((User) findById(id)).getTotalCombinedBalances() > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		return regularIraService.addRegularIRA(account, id);
	}

	//--find savings account by id
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/RegularIRA")
	public List<RegularIRA> findRegularIraById(@PathVariable Integer id) throws AccountNotFoundException {
		return regularIraService.findAccount(id);
	}
	
	
	
	//--- ROTH IRA ACCOUNT
	
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/RothIRA")
	public RothIRA addRothIRA(@Valid @PathVariable Integer id, @RequestBody RothIRA account)
			throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException {		
	
		//--balance must not be negative & an account holders combined balances may not exceed 250_000
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		if (account.getBalance() + ((User) findById(id)).getTotalCombinedBalances() > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		return rothIraService.addRothIRA(account, id);
	}

	//--find savings account by id
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/RothIRA")
	public List<RothIRA> findRothIraById(@PathVariable Integer id) throws AccountNotFoundException {
		return rothIraService.findAccount(id);
	}
	
	
	
	//--- ROLLOVER IRA ACCOUNT
	
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/AccountHolders/{id}/RolloverIRA")
	public RolloverIRA addRolloverIRA(@Valid @PathVariable Integer id, @RequestBody RolloverIRA account)
			throws NegativeBalanceException, ExceedsCombinedBalanceLimitException, AccountNotFoundException {		
	
		//--balance must not be negative & an account holders combined balances may not exceed 250_000
		if (account.getBalance() < 0) {
			throw new NegativeBalanceException("Cannot have negative balance");
		}
		if (account.getBalance() + ((User) findById(id)).getTotalCombinedBalances() > 250000) {
			throw new ExceedsCombinedBalanceLimitException("Balance exceeds limit");
		}
		return rolloverIraService.addRolloverIRA(account, id);
	}

	//--find savings account by id
	@PreAuthorize("hasAuthority('admin')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "AccountHolders/{id}/RothIRA")
	public List<RolloverIRA> findRolloverIraById(@PathVariable Integer id) throws AccountNotFoundException {
		return rolloverIraService.findAccount(id);
	}
}
