package ie.tcd.cafeapp.service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.cache.Cache;
import ie.tcd.cafeapp.collection.Customer;
import ie.tcd.cafeapp.collection.ResponsePojo;
import ie.tcd.cafeapp.collection.TransactionHistory;
import ie.tcd.cafeapp.collection.VoucherDetails;
import ie.tcd.cafeapp.repository.FetchDetailsRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FetchDetailsServiceImpl implements FetchDetailsService {
	
	@Autowired
	private FetchDetailsRepository fetchDetailsRepository;

	@Autowired
    private CacheManager cacheManager;
	
	public ResponsePojo getCustomerDetails(Map<String, String> headers) 
	{
		
		ResponsePojo response = new ResponsePojo();

		if(headers.get("session-id") == null || headers.get("session-id").isEmpty())
		{
			response.setResponseMessage("Invalid Details. Session-id cannot be empty. To get seesion-id, please login into the app");
			return response;
		}
		log.info("Fetch details started for session id:" + headers.get("session-id"));
		
		Cache cache = cacheManager.getCache("customerCache");

		if (cache != null && cache.get(headers.get("session-id")) != null) {
			log.info("Fetch details finished from cache for session id:" + headers.get("session-id"));
            return cache.get(headers.get("session-id"), ResponsePojo.class);
        } 
		
		List<Customer> opCustomer = fetchDetailsRepository.findBySessionDetails(headers.get("session-id"));

		if(opCustomer != null && !opCustomer.isEmpty())
		{
			Customer customer = opCustomer.get(0);

			if(!headers.get("session-id").equals(customer.getSessionDetails().getSessionId()))
			{
				log.info("Fetch details started for session id:" + headers.get("session-id"));
				response.setResponseMessage("Invalid session-id. To get seesion-id, please login into the app");
				return response;
			}

			response.setFirstName(customer.getFirstName());
			response.setLastName(customer.getLastName());
			response.setMembershipId(customer.getMembershipId());
			response.setResponseMessage("Customer Details fetched successfully");
			response.setRewardPoints(customer.getRewardPoints());
			response.setTransactionHistory(customer.getTransactionHistory());

			if(customer.getVoucher() != null && !customer.getVoucher().isEmpty()) 
			{
				Iterator<VoucherDetails> itr = customer.getVoucher().iterator();
				while(itr.hasNext())
				{
					LocalDateTime voucherExpiryDate = LocalDateTime.parse(itr.next().getValidTill());

					if(LocalDateTime.now().isAfter(voucherExpiryDate))
					{
						itr.remove();
					}
				}
				fetchDetailsRepository.save(customer);
				response.setVoucher(customer.getVoucher());
			}
			else
			{
				response.setVoucher(customer.getVoucher());
			}

			response.setUsername(customer.getLoginCredentials().getUsername());
			log.info("Fetch details finished for session id:" + headers.get("session-id"));
			
			//Cache Logic
			int count = 0;
			if(customer.getTransactionHistory() == null || customer.getTransactionHistory().size() == 0)
			{
				cache.evict(response);
			}
			else
			{
				for(TransactionHistory txn : customer.getTransactionHistory())
				{
					LocalDateTime txnTime = LocalDateTime.parse(txn.getDate());
					LocalDateTime now = LocalDateTime.now();
			    	LocalDateTime oneMonthAgo = now.minusMonths(1);;

					if(txnTime.isAfter(oneMonthAgo) && txnTime.isBefore(now))
					{
						count++;
					}
				}
				if(count > 10)
				{
					cache.put(headers.get("session-id"),response);
				}
				else
				{
					cache.evict(response);
				}
			}
			return response;

		}
		else
		{
			log.info("Fetch details finished for session id:" + headers.get("session-id"));
			response.setResponseMessage("No session found for this user. To get seesion-id, please login into the app");
			return response;
		}
	}

	@Override
	public String updateCustomerCacheService(Customer updatedCustomer) 
	{
		Cache cache = cacheManager.getCache("customerCache");
		log.info("Update cache started for session id:" + updatedCustomer.getSessionDetails().getSessionId());
		if (cache != null) 
		{
			ResponsePojo customer = new ResponsePojo();
			customer.setFirstName(updatedCustomer.getFirstName());
			customer.setLastName(updatedCustomer.getLastName());
			customer.setMembershipId(updatedCustomer.getMembershipId());
			customer.setRewardPoints(updatedCustomer.getRewardPoints());
			customer.setTransactionHistory(updatedCustomer.getTransactionHistory());
			customer.setUsername(updatedCustomer.getLoginCredentials().getUsername());
			customer.setVoucher(updatedCustomer.getVoucher());
			
			cache.put(updatedCustomer.getSessionDetails().getSessionId(), customer);
        } 
		log.info("Update cache finished for session id:" + updatedCustomer.getSessionDetails().getSessionId());
		return "Success";
	}

}
