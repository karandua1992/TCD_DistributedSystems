package ie.tcd.cafeapp.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ie.tcd.cafeapp.collection.AddTransactionPojo;
import ie.tcd.cafeapp.collection.Customer;
import ie.tcd.cafeapp.collection.RedeemVoucherPojo;
import ie.tcd.cafeapp.collection.ResponsePojo;
import ie.tcd.cafeapp.collection.UpdateTransactionResponsePojo;
import ie.tcd.cafeapp.collection.VoucherDetails;
import ie.tcd.cafeapp.repository.RedeemVoucherRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedeemVoucherServiceImpl implements RedeemVoucherService {

	@Autowired
	private RedeemVoucherRepository redeemVocuherRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Override
	public ResponsePojo redeemVoucher(RedeemVoucherPojo transactionDetails, Map<String, String> headers) 
	{
	
		ResponsePojo response = new ResponsePojo();

		if(headers.get("session-id") == null || headers.get("session-id").isEmpty())
		{
			response.setResponseMessage("Invalid Details. Session-id cannot be empty. To get seesion-id, please login into the app");
			response.setFinalBalanceAmount(transactionDetails.getTransactionAmount());
			return response;
		}
		log.info("Redeem Voucher request started for session id:" + headers.get("session-id"));
		if(transactionDetails.getTransactionAmount() == null)
		{
			response.setResponseMessage("Invalid Details. Transaction amount cannot be empty.");
			response.setFinalBalanceAmount(transactionDetails.getTransactionAmount());
			log.info("Redeem Voucher request finished for session id:" + headers.get("session-id"));
			return response;
		}
		else if(transactionDetails.getTransactionAmount() == 0)
		{
			response.setResponseMessage("Invalid Details. Transaction amount cannot be 0.");
			response.setFinalBalanceAmount(transactionDetails.getTransactionAmount());
			log.info("Redeem Voucher request finished for session id:" + headers.get("session-id"));
			return response;
		}
		
		if(transactionDetails.getVoucherCode() == null || transactionDetails.getVoucherCode().isEmpty())
		{
			response.setResponseMessage("Invalid Details. Vocuher code cannot be empty.");
			response.setFinalBalanceAmount(transactionDetails.getTransactionAmount());
			log.info("Redeem Voucher request finished for session id:" + headers.get("session-id"));
			return response;
		}

		List<Customer> opCustomer = redeemVocuherRepository.findBySessionDetails(headers.get("session-id"));
		
		if(opCustomer != null && !opCustomer.isEmpty())
		{
			Customer customer = opCustomer.get(0);
			
			if(!headers.get("session-id").equals(customer.getSessionDetails().getSessionId()))
			{
				response.setResponseMessage("Invalid session-id. To get seesion-id, please login into the app");
				response.setFinalBalanceAmount(transactionDetails.getTransactionAmount());
				log.info("Redeem Voucher request finished for session id:" + headers.get("session-id"));
				return response;
			}
			
			if(customer.getVoucher() == null || customer.getVoucher().isEmpty())
			{
				response.setResponseMessage("Customer does not have a voucher.");
				response.setFinalBalanceAmount(transactionDetails.getTransactionAmount());
				log.info("Redeem Voucher request finished for session id:" + headers.get("session-id"));
				return response;
			}
			
			boolean voucherFound = false;
			String voucherCode = "";
			String validTill = "";
			Double voucherAmount = 0.0;
			for(VoucherDetails voucher : customer.getVoucher())
			{
				if(voucher.getVoucherCode() != null && voucher.getVoucherCode().equals(transactionDetails.getVoucherCode()))
				{
					voucherFound = true;
					voucherCode = voucher.getVoucherCode();
					validTill = voucher.getValidTill();
					voucherAmount = voucher.getVoucherAmount();
					break;
				}
			}
			
			if(voucherFound)
			{
				if(voucherAmount >= transactionDetails.getTransactionAmount())
				{
					response.setResponseMessage("Voucher amount is greater than or equal to billing amount. Please try a differet voucher.");
					response.setFinalBalanceAmount(transactionDetails.getTransactionAmount());
					log.info("Redeem Voucher request finished for session id:" + headers.get("session-id"));
					return response;
				}
				
				LocalDateTime voucherExpiryDate = LocalDateTime.parse(validTill);

				if(LocalDateTime.now().isAfter(voucherExpiryDate))
				{
					response.setResponseMessage("This voucher has expired. Please try a differet voucher.");
					response.setFinalBalanceAmount(transactionDetails.getTransactionAmount());
					log.info("Redeem Voucher request finished for session id:" + headers.get("session-id"));
					return response;
				}
				
				Double remainingBalance = transactionDetails.getTransactionAmount() - voucherAmount;
				
				Iterator<VoucherDetails> itr = customer.getVoucher().iterator();
				
				while(itr.hasNext())
				{
					if(voucherCode.equals(itr.next().getVoucherCode()))
					{
						itr.remove();
					}
				}
				
				AddTransactionPojo updateTxn = new AddTransactionPojo();
				updateTxn.setTransactionAmount(remainingBalance.floatValue());
				
				HttpHeaders updateHeaders = new HttpHeaders();

				updateHeaders.setContentType(MediaType.APPLICATION_JSON);
				updateHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				updateHeaders.add("session-id", headers.get("session-id"));

				HttpEntity<AddTransactionPojo> requestEntity = new HttpEntity<AddTransactionPojo>(updateTxn, updateHeaders);
				
				ResponseEntity<UpdateTransactionResponsePojo> updatTransactionResponse = restTemplate.postForEntity("http://CAFEAPP-CUSTOMER-UPDATETRANSACTION/cafeapp/upatetransaction", requestEntity, UpdateTransactionResponsePojo.class);

				
				if (updatTransactionResponse.getStatusCode() == HttpStatus.OK) 
				{
					Customer updatedCustomer = redeemVocuherRepository.findById(customer.getMembershipId()).get();
					
					customer.setTransactionHistory(updatedCustomer.getTransactionHistory());
					
					redeemVocuherRepository.save(customer);
					response.setFinalBalanceAmount(remainingBalance);
					response.setResponseMessage("Voucher has been successfully applied.");
					
					try 
					{
						log.info("Cache update started for session id:" + headers.get("session-id"));
						
						HttpEntity<Customer> requestEntityForCache = new HttpEntity<Customer>(customer);
						
						restTemplate.postForEntity("http://CAFEAPP-CUSTOMER-FETCHDETAILS/cafeapp/updateCache", requestEntityForCache, String.class);
						
						log.info("Cache update finished for session id:" + headers.get("session-id"));
					}
					catch(Exception e)
					{
						log.info("Cache update failed for session id:" + headers.get("session-id"));
					}
					
					log.info("Redeem Voucher request finished for session id:" + headers.get("session-id"));
					return response;    
				} 
				else 
				{
					response.setFinalBalanceAmount(transactionDetails.getTransactionAmount());
					response.setResponseMessage("Failed to update transaction. Please try after some time.");
					log.info("Redeem Voucher request finished for session id:" + headers.get("session-id"));
					return response;    
				}
				
				
			}
			else
			{
				response.setResponseMessage("This is not a valid voucher. Please check vocuher details and try again.");
				response.setFinalBalanceAmount(transactionDetails.getTransactionAmount());
				log.info("Redeem Voucher request finished for session id:" + headers.get("session-id"));
				return response;
			}
			
		}
		else
		{
			response.setResponseMessage("No session found for this user. To get seesion-id, please login into the app");
			log.info("Redeem Voucher request finished for session id:" + headers.get("session-id"));
			return response;
		}

	
	}

}
