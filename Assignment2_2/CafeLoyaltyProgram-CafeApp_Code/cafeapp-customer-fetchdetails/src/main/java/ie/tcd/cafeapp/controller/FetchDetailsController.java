package ie.tcd.cafeapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.tcd.cafeapp.collection.Customer;
import ie.tcd.cafeapp.service.FetchDetailsService;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/cafeapp")
@Slf4j
public class FetchDetailsController 
{
	@Autowired
	private  FetchDetailsService fetchDetailsService;
	
	@PostMapping("/fetchdetails")
	public ResponseEntity<?> fetchDetails(@RequestHeader Map<String, String> headers)
	{
		log.info("Fetch details request received for session id:" + headers.get("session-id"));
		return ResponseEntity.ok(fetchDetailsService.getCustomerDetails(headers));
	}
	
	
	@PostMapping("/updateCache")
	public  ResponseEntity<?> updateTransactionCacheController(@RequestBody Customer updatedCustomer)
	{
		log.info("Update cache request for update cache received for session-id:" + updatedCustomer.getSessionDetails().getSessionId());
		return ResponseEntity.ok(fetchDetailsService.updateCustomerCacheService(updatedCustomer));
	}

}
