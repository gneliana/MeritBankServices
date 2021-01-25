package com.meritamerica.CapstoneBankApp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.meritamerica.CapstoneBankApp.exceptions.OfferingNotFoundException;
import com.meritamerica.CapstoneBankApp.models.CDOffering;
import com.meritamerica.CapstoneBankApp.services.CDOfferingService;

//controller for cd offerings
//cd offering is much different than a bank account or an account holder
@RestController 
public class CDOfferingController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	// this is the only service this controller controls
	@Autowired			// finds anything needed to be injected in this constructor and injects them for you
	CDOfferingService offeringService;  
	
	// add cd offering
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/CDOfferings")
	public CDOffering addCdOffering(@Valid @RequestBody CDOffering offering) {
		offeringService.addOffering(offering);
		return offering;
	}
	
	// get all cd offerings
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "CDOfferings")
	public List<CDOffering> getOfferings() {
		return offeringService.findAll();
	}
	
	// get an offering by its id
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "CDOfferings/{id}")
	public CDOffering getOfferingById(@PathVariable Integer id) throws OfferingNotFoundException {
		return offeringService.findById(id);
	}
}
