package com.meritamerica.CapstoneBankApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.CapstoneBankApp.models.AccountHolderContactDetails;

@Repository
public interface AccountHolderContactDetailsRepository extends JpaRepository<AccountHolderContactDetails, Integer>{

}
