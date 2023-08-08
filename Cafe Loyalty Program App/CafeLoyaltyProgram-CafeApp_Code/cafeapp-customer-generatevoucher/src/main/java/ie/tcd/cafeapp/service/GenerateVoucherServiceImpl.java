package ie.tcd.cafeapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ie.tcd.cafeapp.collection.Customer;
import ie.tcd.cafeapp.collection.ResponsePojo;
import ie.tcd.cafeapp.collection.VoucherDetails;
import ie.tcd.cafeapp.repository.GenerateVoucherRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GenerateVoucherServiceImpl implements GenerateVoucherService 
{
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private GenerateVoucherRepository generateVoucherRepository;
	
	@Override
	public ResponsePojo generateVoucher(Map<String, String> headers) 
	{
		ResponsePojo response = new ResponsePojo();

		if(headers.get("session-id") == null || headers.get("session-id").isEmpty())
		{
			response.setResponseMessage("Invalid Details. Session-id cannot be empty. To get seesion-id, please login into the app");
			return response;
		}
		log.info("Vocuher genreation request started for session id:" + headers.get("session-id"));
		List<Customer> opCustomer = generateVoucherRepository.findBySessionDetails(headers.get("session-id"));
		
		if(opCustomer != null && !opCustomer.isEmpty())
		{
			Customer customer = opCustomer.get(0);

			if(!headers.get("session-id").equals(customer.getSessionDetails().getSessionId()))
			{
				log.info("Vocuher genreation request started for session id:" + headers.get("session-id"));
				response.setResponseMessage("Invalid session-id. To get seesion-id, please login into the app");
				return response;
			}
			

			Integer currentRewardPoints = customer.getRewardPoints();
			
			if(currentRewardPoints < 100)
			{
				response.setMembershipId(customer.getMembershipId());
				response.setUsername(customer.getLoginCredentials().getUsername());
				response.setRemainingRewardPoints(currentRewardPoints);
				response.setResponseMessage("You do not have enough reward points balance to generate a voucher");
				log.info("Vocuher genreation request started for session id:" + headers.get("session-id"));
				return response;
			}
			else
			{
				response.setMembershipId(customer.getMembershipId());
				response.setUsername(customer.getLoginCredentials().getUsername());
				response.setResponseMessage("Voucher generated successfully");
				
				UUID uuid = UUID.randomUUID();
				VoucherDetails generatedVoucher = new VoucherDetails();
				
				Double voucherAmount = 0.0;
				Integer remainingRewardPoints = 0;
				if(currentRewardPoints < 500)
				{
					voucherAmount = (double) (currentRewardPoints / 100);
					remainingRewardPoints = currentRewardPoints % 100;
				}
				else
				{
					voucherAmount = 5.0;
					remainingRewardPoints = currentRewardPoints - 500;
				}
			
				LocalDateTime voucherValidTill = LocalDateTime.now().plusMonths(1);

				generatedVoucher.setVoucherCode(uuid.toString());
				generatedVoucher.setValidTill(voucherValidTill.toString());
				generatedVoucher.setVoucherAmount(voucherAmount);

				response.setGeneratedVoucher(generatedVoucher);
				response.setRemainingRewardPoints(remainingRewardPoints);

				customer.setRewardPoints(remainingRewardPoints);

				List<VoucherDetails> vouchersList = customer.getVoucher();
				if(vouchersList == null)
				{
					vouchersList = new ArrayList<VoucherDetails>();
				}
				vouchersList.add(generatedVoucher);
				customer.setVoucher(vouchersList);

				generateVoucherRepository.save(customer);
				
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
				
				log.info("Vocuher genreation request finished for session id:" + headers.get("session-id"));
				return response;
			}
		}
		else
		{
			log.info("Vocuher genreation request finished for session id:" + headers.get("session-id"));
			response.setResponseMessage("No session found for this user. To get seesion-id, please login into the app");
			return response;
		}
	}

}
