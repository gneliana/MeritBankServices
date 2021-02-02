package com.meritamerica.CapstoneBankApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meritamerica.CapstoneBankApp.models.RolloverIRA;

@Repository
public interface RolloverIRAAccountRepository extends JpaRepository<RolloverIRA, Integer>{

}
