package com.meritamerica.CapstoneBankApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.CapstoneBankApp.models.CDOffering;

@Repository
public interface CDOfferingRepository extends JpaRepository<CDOffering, Integer> {
	
	CDOffering findByTerm(Integer term);
}
