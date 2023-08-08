package ie.tcd.cafeapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ie.tcd.cafeapp.collection.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> 
{

}
