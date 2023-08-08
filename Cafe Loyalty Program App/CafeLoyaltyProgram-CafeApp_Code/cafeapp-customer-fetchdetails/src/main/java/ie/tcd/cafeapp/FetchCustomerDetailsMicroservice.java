package ie.tcd.cafeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FetchCustomerDetailsMicroservice 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(FetchCustomerDetailsMicroservice.class, args);
	}
}
