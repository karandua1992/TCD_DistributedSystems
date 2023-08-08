package ie.tcd.cafeapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.tcd.cafeapp.service.GenerateVoucherService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cafeapp")
@Slf4j
public class GenerateVoucherController 
{
	@Autowired
	private  GenerateVoucherService generateVoucherService;
	
	@PostMapping("/generatevoucher")
	public ResponseEntity<?> getVoucherDetails(@RequestHeader Map<String, String> headers)
	{
		log.info("Generate Voucher request received for session id:" + headers.get("session-id"));
		return ResponseEntity.ok(generateVoucherService.generateVoucher(headers));
	}
}
