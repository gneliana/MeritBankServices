package com.meritamerica.CapstoneBankApp.services;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.meritamerica.CapstoneBankApp.Jwt.JwtUtil;
import com.meritamerica.CapstoneBankApp.exceptions.AccountNotFoundException;
import com.meritamerica.CapstoneBankApp.exceptions.ExceedsCombinedBalanceLimitException;
import com.meritamerica.CapstoneBankApp.models.AccountHolder;
import com.meritamerica.CapstoneBankApp.models.CDAccount;
import com.meritamerica.CapstoneBankApp.models.CDOffering;
import com.meritamerica.CapstoneBankApp.models.DBAChecking;
import com.meritamerica.CapstoneBankApp.models.PersonalChecking;
import com.meritamerica.CapstoneBankApp.models.RegularIRA;
import com.meritamerica.CapstoneBankApp.models.RolloverIRA;
import com.meritamerica.CapstoneBankApp.models.RothIRA;
import com.meritamerica.CapstoneBankApp.models.SavingsAccount;
import com.meritamerica.CapstoneBankApp.models.User;
import com.meritamerica.CapstoneBankApp.repositories.AccountHolderRepository;
import com.meritamerica.CapstoneBankApp.repositories.CDAccountRepository;
import com.meritamerica.CapstoneBankApp.repositories.DBACheckingAccountRepository;
import com.meritamerica.CapstoneBankApp.repositories.PersonalCheckingAccountRepository;
import com.meritamerica.CapstoneBankApp.repositories.RegularIRAAccountRepository;
import com.meritamerica.CapstoneBankApp.repositories.RolloverIRAAccountRepository;
import com.meritamerica.CapstoneBankApp.repositories.RothIRAAccountRepository;
import com.meritamerica.CapstoneBankApp.repositories.SavingsAccountRepository;
import com.meritamerica.CapstoneBankApp.repositories.UserRepository;

@Service
public class UserService  {


	@Autowired
	private UserRepository userRepository;
//	@Autowired
//	private AccountHolderRepository accountHolderRepository;
	@Autowired
	private SavingsAccountRepository savingsAccountRepository;
	@Autowired
	private PersonalCheckingAccountRepository personalCheckingRepository;
	@Autowired
	private DBACheckingAccountRepository dbaCheckingRepository;
	@Autowired
	private CDAccountRepository cdAccountRepository;
	@Autowired
	private RegularIRAAccountRepository regularIraAccountRepository;
	@Autowired
	private RothIRAAccountRepository rothIraAccountRepository;
	@Autowired
	private RolloverIRAAccountRepository rolloverIraAccountRepository;
	@Autowired
	private JwtUtil jwtUtil;
	
	
	
	public User getMyAccountInfo(HttpServletRequest request) {
		final String authorizationHeader = request.getHeader("Authorization");
		
		String username = null;
		String jwt = null;
		User userInfo = null;
		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		
		if (username != null) {
			User user = this.userRepository.findByUserName(username).orElseThrow(null);
//			accountHolder = user.getAccountHolder();
			userInfo = userRepository.findById(user.getId()).orElseThrow(null);
		}		
		return userInfo;
	}
	
	
	// add user to database
	public User addUser(User user) {
		userRepository.save(user);
		return user;
	}

	// return list of all users 
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	// find user by their id
	// ** this will be referenced in other classes any time they need to locate holder by id **
	public User findById(Integer id) throws AccountNotFoundException {
	if(!(userRepository.existsById(id))) {
		throw new AccountNotFoundException("Account not found");
	}
	return userRepository.findById(id).orElse(null);
	}


	// post/get SAVINGS
	
	public SavingsAccount postMySavingsAccount(HttpServletRequest request, SavingsAccount savingsAccount)
											throws ExceedsCombinedBalanceLimitException {
		User user = getMyAccountInfo(request);
		
		user.setSavingsAccounts(Arrays.asList(savingsAccount));
		savingsAccount.setUser(user);
		savingsAccountRepository.save(savingsAccount);
		
		return savingsAccount;
	}	
	
	public List<SavingsAccount> getMySavingsAccounts(HttpServletRequest request) {
		User user = getMyAccountInfo(request);
		return user.getSavingsAccounts();
	}

	
	// post/get PERSONAL CHECKING
	
	public PersonalChecking postMyPersonalCheckingAccount(HttpServletRequest request, PersonalChecking personalChecking)
											throws ExceedsCombinedBalanceLimitException {
		User user = getMyAccountInfo(request);
	
		user.setPersonalCheckingAccounts(Arrays.asList(personalChecking));
		personalChecking.setUser(user);
		personalCheckingRepository.save(personalChecking);
		
		return personalChecking;
	}
	
	public List<PersonalChecking> getMyPersonalCheckingAccounts(HttpServletRequest request) {
		User user = getMyAccountInfo(request);
		return user.getPersonalCheckingAccounts();
	}
	
	
	// post/get DBA CHECKING
	
	public List<DBAChecking> getMyDbaCheckingAccounts(HttpServletRequest request) {
		User user = getMyAccountInfo(request);
		return user.getDbaCheckingAccounts();
	}
	
	public DBAChecking postMyDbaCheckingAccount(HttpServletRequest request, DBAChecking dbaChecking)
											throws ExceedsCombinedBalanceLimitException {
		User user = getMyAccountInfo(request);
	
		user.setDbaCheckingAccounts(Arrays.asList(dbaChecking));
		dbaChecking.setUser(user);
		dbaCheckingRepository.save(dbaChecking);
		
		return dbaChecking;
	}

	
	// post/get CD ACCOUNT

	public List <CDAccount> getMyCdAccounts(HttpServletRequest request) {
		
		User user = getMyAccountInfo(request);
		return user.getCdAccounts();
	}
	
	public CDAccount postMyCdAccount(HttpServletRequest request, CDAccount cdAccount)
			throws ExceedsCombinedBalanceLimitException {
		User user = getMyAccountInfo(request);

		user.setCdAccounts(Arrays.asList(cdAccount));
		cdAccount.setUser(user);
		cdAccountRepository.save(cdAccount);

		return cdAccount;
	}

	
	// post/get REGULAR IRA ACCOUNT

	public List <RegularIRA> getMyRegularIRAAccounts(HttpServletRequest request) {
		
		User user = getMyAccountInfo(request);
		return user.getRegularIraAccounts();
	}
	
	public RegularIRA postMyRegularIRAAccount(HttpServletRequest request, RegularIRA regularIra)
			throws ExceedsCombinedBalanceLimitException {
		User user = getMyAccountInfo(request);

		user.setRegularIraAccounts(Arrays.asList(regularIra));
		regularIra.setUser(user);
		regularIraAccountRepository.save(regularIra);

		return regularIra;
	}
	
	// post/get ROTH IRA ACCOUNT

	public List <RothIRA> getMyRothIRAAccounts(HttpServletRequest request) {
		
		User user = getMyAccountInfo(request);
		return user.getRothIraAccounts();
	}
	
	public RothIRA postMyRothIRAAccount(HttpServletRequest request, RothIRA rothIra)
			throws ExceedsCombinedBalanceLimitException {
		User user = getMyAccountInfo(request);

		user.setRothIraAccounts(Arrays.asList(rothIra));
		rothIra.setUser(user);
		rothIraAccountRepository.save(rothIra);

		return rothIra;
	}
	
	// post/get ROLLOVER IRA ACCOUNT

	public List <RolloverIRA> getMyRolloverIRAAccounts(HttpServletRequest request) {
		
		User user = getMyAccountInfo(request);
		return user.getRolloverIraAccounts();
	}
	
	public RolloverIRA postMyRolloverIRAAccount(HttpServletRequest request, RolloverIRA rolloverIra)
			throws ExceedsCombinedBalanceLimitException {
		User user = getMyAccountInfo(request);

		user.setRolloverIraAccounts(Arrays.asList(rolloverIra));
		rolloverIra.setUser(user);
		rolloverIraAccountRepository.save(rolloverIra);

		return rolloverIra;
	}

	
	
	//--- GET BEST & 2ND BEST CD OFFERINGS
	
	//best cd offering
	public CDOffering getBestOffering(List<CDOffering> offerings) {
		
		if(offerings.size() == 0) 
			return null;
		
		
		//need to return an offering, not an interest rate. need to have an offering variable each new best offering
		CDOffering bestOffering = null; // must be initialized to return value 
		double bestInterestRate = 0.00;
		
		
		for(int i = 0; i < offerings.size(); i ++) {
			if (offerings.get(i).getInterestRate() > bestInterestRate) {
				bestInterestRate = offerings.get(i).getInterestRate();
				bestOffering = offerings.get(i);				
			}
		}
		return bestOffering;
	}
	
	//2nd best cd offering
	public CDOffering getSecondBestOffering(List<CDOffering> offerings) {
		
		if(offerings.size() <= 1)
			return null;
		
		//need to return 2nd best offering, so need to also find best offering
		CDOffering bestOffering = getBestOffering(offerings);
		CDOffering secondBestOffering = null;
		double secondBestRate = 0.00;
		
		for(int i = 0; i < offerings.size(); i++) {
			if(offerings.get(i) == bestOffering)
				continue;
			if(offerings.get(i).getInterestRate() > secondBestRate) {
				secondBestRate = offerings.get(i).getInterestRate();
				secondBestOffering = offerings.get(i);
			}
		}
		return secondBestOffering;
	}
}
