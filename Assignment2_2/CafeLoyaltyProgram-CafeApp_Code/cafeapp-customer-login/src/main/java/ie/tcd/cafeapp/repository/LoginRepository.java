package ie.tcd.cafeapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ie.tcd.cafeapp.collection.Customer;

@Repository
public interface LoginRepository extends MongoRepository<Customer, String> 
{
	@Query("{'loginCredentials.username': ?0}")
    List<Customer> findByUsername(String username);
}
