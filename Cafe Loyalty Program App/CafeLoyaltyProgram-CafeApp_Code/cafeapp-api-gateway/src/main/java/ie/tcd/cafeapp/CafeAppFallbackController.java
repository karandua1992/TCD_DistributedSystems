package ie.tcd.cafeapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CafeAppFallbackController {
	
	@PostMapping("/createServiceFallback")
    public ResponseEntity<String> createServiceFallback() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Creation Service is currently unavailable. Please try again later.");
    }
	
	@PostMapping("/loginServiceFallback")
    public ResponseEntity<String> loginServiceFallback() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Login Service is currently unavailable. Please try again later.");
    }
	
	@PostMapping("/fetchdetailsServiceFallback")
    public ResponseEntity<String> fetchdetailsServiceFallback() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Fetch Details Service is currently unavailable. Please try again later.");
    }
	
	@PostMapping("/upatetransactionServiceFallback")
    public ResponseEntity<String> upatetransactionServiceFallback() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Upate Transaction Service is currently unavailable. Please try again later.");
    }

	@PostMapping("/generatevoucherServiceFallback")
    public ResponseEntity<String> generatevoucherServiceFallback() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Generate Voucher Service is currently unavailable. Please try again later.");
    }
	
	@PostMapping("/getvoucherdetailsServiceFallback")
    public ResponseEntity<String> getvoucherdetailsServiceFallback() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Get Voucher Details Service is currently unavailable. Please try again later.");
    }
	
	@PostMapping("/gettransactionhistoryServiceFallback")
    public ResponseEntity<String> gettransactionhistoryServiceFallback() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Get Transaction History Service is currently unavailable. Please try again later.");
    }
	
	@PostMapping("/redeemVocuherServiceFallback")
    public ResponseEntity<String> redeemVocuherServiceFallback() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Redeem Voucher Service is currently unavailable. Please try again later.");
    }
}
