package ie.tcd.cafeapp.service;

import java.util.Map;

import ie.tcd.cafeapp.collection.ResponsePojo;

public interface GetTransactionHistoryService {

	public ResponsePojo getTransactionHistory(Map<String, String> headers);

}
