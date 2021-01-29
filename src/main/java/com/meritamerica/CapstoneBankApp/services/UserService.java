package com.meritamerica.CapstoneBankApp.services;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.Jwt.JwtUtil;
import com.meritamerica.CapstoneBankApp.exceptions.ExceedsCombinedBalanceLimitException;
import com.meritamerica.CapstoneBankApp.models.AccountHolder;
import com.meritamerica.CapstoneBankApp.models.CDAccount;
import com.meritamerica.CapstoneBankApp.models.DBAChecking;
import com.meritamerica.CapstoneBankApp.models.PersonalChecking;
import com.meritamerica.CapstoneBankApp.models.SavingsAccount;
import com.meritamerica.CapstoneBankApp.models.User;
import com.meritamerica.CapstoneBankApp.repositories.AccountHolderRepository;
import com.meritamerica.CapstoneBankApp.repositories.CDAccountRepository;
import com.meritamerica.CapstoneBankApp.repositories.DBACheckingAccountRepository;
import com.meritamerica.CapstoneBankApp.repositories.PersonalCheckingAccountRepository;
import com.meritamerica.CapstoneBankApp.repositories.SavingsAccountRepository;
import com.meritamerica.CapstoneBankApp.repositories.UserRepository;

@Service
public class UserService  {


	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountHolderRepository accountHolderRepository;
	@Autowired
	private SavingsAccountRepository savingsAccountRepository;
	@Autowired
	private PersonalCheckingAccountRepository personalCheckingRepository;
	@Autowired
	private DBACheckingAccountRepository dbaCheckingRepository;
	@Autowired
	private CDAccountRepository cdAccountRepository;
	@Autowired
	private JwtUtil jwtUtil;
	
	
	public AccountHolder getMyAccountInfo(HttpServletRequest request) {
		final String authorizationHeader = request.getHeader("Authorization");
		
		String username = null;
		String jwt = null;
		AccountHolder accountHolder = null;
		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		
		if (username != null) {
			User user = this.userRepository.findByUserName(username).orElseThrow(null);
			accountHolder = user.getAccountHolder();
			accountHolder = accountHolderRepository.findById(user.getId()).orElseThrow(null);
		}
		
		return accountHolder;
	}

	
	
	public List<DBAChecking> getMyDbaCheckingAccounts(HttpServletRequest request) {
		AccountHolder accountHolder = getMyAccountInfo(request);
		return accountHolder.getDBACheckingAccounts();
	}
	
	public List<PersonalChecking> getMyPersonalCheckingAccounts(HttpServletRequest request) {
		AccountHolder accountHolder = getMyAccountInfo(request);
		return accountHolder.getPersonalCheckingAccounts();
	}
	
	public PersonalChecking postMyPersonalCheckingAccount(HttpServletRequest request, PersonalChecking personalChecking)
											throws ExceedsCombinedBalanceLimitException {
		AccountHolder accountHolder = getMyAccountInfo(request);
	
		accountHolder.setPersonalCheckingAccounts(Arrays.asList(personalChecking));
		personalChecking.setAccountHolder(accountHolder);
		personalCheckingRepository.save(personalChecking);
		
		return personalChecking;
	}

	
	public DBAChecking postMyDbaCheckingAccount(HttpServletRequest request, DBAChecking dbaChecking)
											throws ExceedsCombinedBalanceLimitException {
		AccountHolder accountHolder = getMyAccountInfo(request);
	
		accountHolder.setDBACheckingAccounts(Arrays.asList(dbaChecking));
		dbaChecking.setAccountHolder(accountHolder);
		dbaCheckingRepository.save(dbaChecking);
		
		return dbaChecking;
	}
	
	public List<SavingsAccount> getMySavingsAccounts(HttpServletRequest request) {
		AccountHolder accountHolder = getMyAccountInfo(request);
		return accountHolder.getSavingsAccounts();
	}
	
	public SavingsAccount postMySavingsAccount(HttpServletRequest request, SavingsAccount savingsAccount)
											throws ExceedsCombinedBalanceLimitException {
		AccountHolder accountHolder = getMyAccountInfo(request);
		
		accountHolder.setSavingsAccounts(Arrays.asList(savingsAccount));
		savingsAccount.setAccountHolder(accountHolder);
		savingsAccountRepository.save(savingsAccount);
		
		return savingsAccount;
	}
	
	public List <CDAccount> getMyCdAccounts(HttpServletRequest request) {
		
		AccountHolder accountHolder = getMyAccountInfo(request);
		return accountHolder.getCdAccounts();
	}
	
	public CDAccount postMyCdSavingsAccount(HttpServletRequest request, CDAccount cdAccount)
			throws ExceedsCombinedBalanceLimitException {
		AccountHolder accountHolder = getMyAccountInfo(request);

		accountHolder.setCdAccounts(Arrays.asList(cdAccount));
		cdAccount.setAccountHolder(accountHolder);
		cdAccountRepository.save(cdAccount);

		return cdAccount;
	}
	
}
