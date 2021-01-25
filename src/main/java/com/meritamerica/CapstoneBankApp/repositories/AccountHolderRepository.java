package com.meritamerica.CapstoneBankApp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.CapstoneBankApp.models.AccountHolder;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Integer> {
	
	Optional<AccountHolder> findById(Integer Id);

}
