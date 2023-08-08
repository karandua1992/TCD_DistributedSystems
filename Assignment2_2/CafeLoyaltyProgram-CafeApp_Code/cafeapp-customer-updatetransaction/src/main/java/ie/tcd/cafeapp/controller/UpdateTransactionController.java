package ie.tcd.cafeapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.tcd.cafeapp.collection.AddTransactionPojo;
import ie.tcd.cafeapp.service.UpdateTransactionService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cafeapp")
@Slf4j
public class UpdateTransactionController 
{
	@Autowired
	private  UpdateTransactionService updateTransactionService;
	
	@PostMapping("/upatetransaction")
	public ResponseEntity<?> updateTransaction(@RequestBody AddTransactionPojo transactionDetails, @RequestHeader Map<String, String> headers)
	{
		log.info("Update transaction request received for session id:" + headers.get("session-id"));
		return ResponseEntity.ok(updateTransactionService.updateTransaction(transactionDetails, headers));
	}
}
