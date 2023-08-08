package ie.tcd.cafeapp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.tcd.cafeapp.collection.CredentialsPojo;
import ie.tcd.cafeapp.collection.Customer;
import ie.tcd.cafeapp.collection.ResponsePojo;
import ie.tcd.cafeapp.collection.Session;
import ie.tcd.cafeapp.repository.LoginRepository;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class LoginServiceImpl implements LoginService 
{
	@Autowired
	private LoginRepository logiRepository;

	@Override
	public ResponsePojo validateCredentials(CredentialsPojo credentials) 
	{
		ResponsePojo response = new ResponsePojo();

		log.info("Login request started for username:" + credentials.getUsername());
		
		if(credentials.getUsername() == null || credentials.getUsername().isEmpty())
		{
			log.info("Login request finished for username:" + credentials.getUsername());
			response.setResponseMessage("Invalid Credentials. Username cannot be empty");
			return response;
		}
		if(credentials.getPassword() == null || credentials.getPassword().isEmpty())
		{
			log.info("Login request finished for username:" + credentials.getUsername());
			response.setResponseMessage("Invalid Credentials. Password cannot be empty");
			return response;
		}

		List<Customer> opCustomer = logiRepository.findByUsername(credentials.getUsername());

		if(opCustomer != null && !opCustomer.isEmpty())
		{
			Customer customer = opCustomer.get(0);
			if(credentials.getPassword().equals(customer.getLoginCredentials().getPassword()))
			{
				UUID uuid = UUID.randomUUID();
				Session session = new Session();
				session.setSessionId(uuid.toString());
				customer.setSessionDetails(session);
				logiRepository.save(customer);

				response.setResponseMessage("Your session-id is:" + uuid.toString());
				log.info("Login request finished for username:" + credentials.getUsername());
				return response;
			}
			else
			{
				log.info("Login request finished for username:" + credentials.getUsername());
				response.setResponseMessage("Invalid Credentials. Password does not match");
				return response;
			}

		}
		else
		{
			log.info("Login request finished for username:" + credentials.getUsername());
			response.setResponseMessage("User not found");
			return response;
		}
	}
}
