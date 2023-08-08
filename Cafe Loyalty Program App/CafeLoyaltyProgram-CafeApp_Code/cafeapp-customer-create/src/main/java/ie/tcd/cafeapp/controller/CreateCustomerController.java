package ie.tcd.cafeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.tcd.cafeapp.collection.CreateCustomerPojo;
import ie.tcd.cafeapp.service.CreateCustomerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cafeapp")
@Slf4j
public class CreateCustomerController 
{
	@Autowired
	private CreateCustomerService createCustomerService;
	
	@PostMapping("/create")
	public ResponseEntity<?> cutomerLogin(@RequestBody CreateCustomerPojo customer)
	{
		log.info("Received customer creation request for customer:" + customer.getLoginCredentials().getUsername());
		return ResponseEntity.ok(createCustomerService.createCustomer(customer));
	}

}
