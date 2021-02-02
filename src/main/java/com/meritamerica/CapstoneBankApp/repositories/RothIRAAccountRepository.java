package com.meritamerica.CapstoneBankApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.CapstoneBankApp.models.RothIRA;

@Repository 
public interface RothIRAAccountRepository extends JpaRepository<RothIRA, Integer> {

}
