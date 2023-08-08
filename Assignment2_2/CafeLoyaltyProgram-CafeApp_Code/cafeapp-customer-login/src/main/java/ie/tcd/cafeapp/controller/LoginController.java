package ie.tcd.cafeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.tcd.cafeapp.collection.CredentialsPojo;
import ie.tcd.cafeapp.service.LoginService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cafeapp")
@Slf4j
public class LoginController 
{
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/login")
	public ResponseEntity<?> cutomerLogin(@RequestBody CredentialsPojo credentials)
	{
		log.info("Login request received for username:" + credentials.getUsername());
		return ResponseEntity.ok(loginService.validateCredentials(credentials));
	}
}
