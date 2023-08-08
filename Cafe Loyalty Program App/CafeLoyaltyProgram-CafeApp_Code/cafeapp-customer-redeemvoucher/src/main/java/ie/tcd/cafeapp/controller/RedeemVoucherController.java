package ie.tcd.cafeapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.tcd.cafeapp.collection.RedeemVoucherPojo;
import ie.tcd.cafeapp.service.RedeemVoucherService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cafeapp")
@Slf4j
public class RedeemVoucherController 
{
	@Autowired
	private  RedeemVoucherService redeemVoucherService;
	
	@PostMapping("/redeemVocuher")
	public ResponseEntity<?> updateTransaction(@RequestBody RedeemVoucherPojo transactionDetails, @RequestHeader Map<String, String> headers)
	{
		log.info("Redeem Voucher request received for session id:" + headers.get("session-id"));
		return ResponseEntity.ok(redeemVoucherService.redeemVoucher(transactionDetails, headers));
	}
}
