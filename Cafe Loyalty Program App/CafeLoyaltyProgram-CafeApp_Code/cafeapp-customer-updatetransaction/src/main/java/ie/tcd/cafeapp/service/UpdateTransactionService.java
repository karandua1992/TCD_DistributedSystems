package ie.tcd.cafeapp.service;

import java.util.Map;

import ie.tcd.cafeapp.collection.AddTransactionPojo;
import ie.tcd.cafeapp.collection.ResponsePojo;

public interface UpdateTransactionService {

	public ResponsePojo updateTransaction(AddTransactionPojo transactionDetails, Map<String, String> headers);

}
