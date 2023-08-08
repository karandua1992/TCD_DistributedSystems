package ie.tcd.cafeapp.service;

import ie.tcd.cafeapp.collection.CreateCustomerPojo;
import ie.tcd.cafeapp.collection.ResponsePojo;

public interface CreateCustomerService {

	public ResponsePojo createCustomer(CreateCustomerPojo customer);

}
