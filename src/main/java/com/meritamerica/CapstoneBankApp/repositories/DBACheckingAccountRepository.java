package com.meritamerica.CapstoneBankApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.CapstoneBankApp.models.DBAChecking;

public interface DBACheckingAccountRepository extends JpaRepository<DBAChecking, Long> {
	

}
