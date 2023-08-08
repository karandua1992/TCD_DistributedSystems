package ie.tcd.cafeapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.tcd.cafeapp.collection.CreateCustomerPojo;
import ie.tcd.cafeapp.collection.Customer;
import ie.tcd.cafeapp.collection.LoginDetails;
import ie.tcd.cafeapp.collection.ResponsePojo;
import ie.tcd.cafeapp.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreateCustomerServiceImpl implements CreateCustomerService 
{
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public ResponsePojo createCustomer(CreateCustomerPojo customer) 
	{
		Customer newCustomer = new Customer();
		LoginDetails credentials = new LoginDetails();
		ResponsePojo response = new ResponsePojo();
		
		if(customer == null)
		{
			response.setResponseMessage("Invalid Details. Mandatory paramteres are empty");
			return response;
		}
		log.info("Customer creation finished for:" + customer.getLoginCredentials().getUsername());
		
		if(checkUsernameExists(customer))
		{
			log.info("Customer creation finished");
			response.setResponseMessage("Invalid Details. Username already exists");
			return response;
		}
		
		if(customer.getFirstName() != null && !customer.getFirstName().isEmpty())
		{
			newCustomer.setFirstName(customer.getFirstName());
		}
		else
		{
			log.info("Customer creation finished");
			response.setResponseMessage("Invalid Details. First name cannot be empty");
			return response;
		}
		
		if(customer.getLastName() != null && !customer.getLastName().isEmpty())
		{
			newCustomer.setLastName(customer.getLastName());
		}
		
		
		if(customer.getLoginCredentials() != null && customer.getLoginCredentials().getUsername() != null && !customer.getLoginCredentials().getUsername().isEmpty())
		{
			credentials.setUsername(customer.getLoginCredentials().getUsername());
		}
		else
		{
			log.info("Customer creation finished");
			response.setResponseMessage("Invalid Details. Username cannot be empty");
			return response;
		}
		
		if(customer.getLoginCredentials() != null && customer.getLoginCredentials().getPassword() != null && !customer.getLoginCredentials().getPassword().isEmpty())
		{
			credentials.setPassword(customer.getLoginCredentials().getPassword());
		}
		else
		{
			log.info("Customer creation finished");
			response.setResponseMessage("Invalid Details. Password cannot be empty");
			return response;
		}
		
		
		newCustomer.setLoginCredentials(credentials);
		newCustomer.setRewardPoints(1000);
		
		String membershipId = customerRepository.save(newCustomer).getMembershipId();
		
		if(membershipId != null && !membershipId.isEmpty())
		{
			response.setResponseMessage("Dear " + newCustomer.getFirstName() + " " + newCustomer.getLastName() + ", "
					+ "You have successfully joined our Loyalty Customer Program. "
					+ "Your Username is: " + newCustomer.getLoginCredentials().getUsername() + " and your membership id is: " + membershipId);
			log.info("Customer creation finished");
			return response;
		}
		else
		{
			response.setResponseMessage("Some error occured. Please try later.");
		}
		log.info("Customer creation finished");
		return response;
	}

	private boolean checkUsernameExists(CreateCustomerPojo customer) 
	{
		List<Customer> allCustomers = customerRepository.findAll();
		
		if(allCustomers != null && !allCustomers.isEmpty())
		{
			for(Customer custr : allCustomers)
			{
				if(custr.getLoginCredentials() != null && custr.getLoginCredentials().getUsername() != null 
						&& custr.getLoginCredentials().getUsername().equals(customer.getLoginCredentials().getUsername()))
				{
					return true;
				}
			}
			return false;
		}
		else
		{
			return false;
		}
	}

}
