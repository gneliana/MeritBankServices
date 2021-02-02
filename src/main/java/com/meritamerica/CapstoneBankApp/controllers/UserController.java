package com.meritamerica.CapstoneBankApp.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.CapstoneBankApp.exceptions.ExceedsCombinedBalanceLimitException;
import com.meritamerica.CapstoneBankApp.exceptions.OfferingNotFoundException;
import com.meritamerica.CapstoneBankApp.models.CDAccount;
import com.meritamerica.CapstoneBankApp.models.CDOffering;
import com.meritamerica.CapstoneBankApp.models.DBAChecking;
import com.meritamerica.CapstoneBankApp.models.PersonalChecking;
import com.meritamerica.CapstoneBankApp.models.SavingsAccount;
import com.meritamerica.CapstoneBankApp.models.User;
import com.meritamerica.CapstoneBankApp.services.UserService;

@RestController 
public class UserController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	
	
	// --moved this method to admin controller
//	@Autowired
//	private RegisterUserService registerUserService;
//	
//	
//	@PreAuthorize("hasAuthority('admin')")
//	@PostMapping("/authenticate/createUser")
//	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
//		return registerUserService.registerUser(signUpRequest);
//	}	
//	
	
	// manditory authentication for URLs due to spring security
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/Me")
	public User getMyAccountInfo(HttpServletRequest request) {
		return userService.getMyAccountInfo(request);
	}
	
	
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/Me/PersonalChecking")
	public PersonalChecking postMyPersonalCheckingAccont(HttpServletRequest request, @Valid @RequestBody PersonalChecking personalChecking)
												throws ExceedsCombinedBalanceLimitException {
		return userService.postMyPersonalCheckingAccount(request, personalChecking);
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/Me/PersonalChecking")
	public List<PersonalChecking> getMyPersonalCheckingAccounts(HttpServletRequest request) {
		return userService.getMyPersonalCheckingAccounts(request);
	}

	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/Me/DBAChecking")
	public DBAChecking postMyDBACheckingAccont(HttpServletRequest request, @Valid @RequestBody DBAChecking dbaChecking)
												throws ExceedsCombinedBalanceLimitException {
		return userService.postMyDbaCheckingAccount(request, dbaChecking);
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/Me/DBAChecking")
	public List<DBAChecking> getMyDCheckingAccounts(HttpServletRequest request) {
		return userService.getMyDbaCheckingAccounts(request);
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/Me/SavingsAccount")
	public SavingsAccount postMySavingsAccount(HttpServletRequest request, @Valid @RequestBody SavingsAccount savingsAccount)
												throws ExceedsCombinedBalanceLimitException {
		return userService.postMySavingsAccount(request, savingsAccount);
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/Me/SavingsAccount")
	public List<SavingsAccount> getMySavingsAccount(HttpServletRequest request) {
		return userService.getMySavingsAccounts(request);
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/Me/CDAccount")
	public CDAccount postMyCDAccount(HttpServletRequest request, @Valid @RequestBody CDAccount cdAccount)
												throws ExceedsCombinedBalanceLimitException {
		return userService.postMyCdAccount(request, cdAccount);
	}
	
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/Me/CDAccount")
	public List<CDAccount> getMyCdAccounts(HttpServletRequest request) {
		return userService.getMyCdAccounts(request);
	}
	
	
	
	// future value
	// use CDAccount because BankAccount is abstractS
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/FutureValue")
	public double futureValue(@RequestBody CDAccount cdAccount) throws OfferingNotFoundException {
		double futureVal = cdAccount.futureValue(cdAccount.getTerm());
		//multiply floor by 100 & divide by 100 to get a whole double
		futureVal = Math.floor(futureVal * 100);
		return futureVal/100;
	}
	
	
	
	//best cd offer
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/BestOffer")
	public CDOffering getBestOffer(@RequestBody List<CDOffering> offerings) {
		return userService.getBestOffering(offerings);
	}
	
	//2nd best cd offer
	@PreAuthorize("hasAuthority('AccountHolder')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/SecondBestOffer")
	public CDOffering getSecondBestOffer(@RequestBody List<CDOffering> offerings) {
		return userService.getSecondBestOffering(offerings);
	}
	
}
