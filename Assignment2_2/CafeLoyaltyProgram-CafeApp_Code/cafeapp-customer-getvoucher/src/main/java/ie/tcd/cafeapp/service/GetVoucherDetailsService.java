package ie.tcd.cafeapp.service;

import java.util.Map;

import ie.tcd.cafeapp.collection.ResponsePojo;

public interface GetVoucherDetailsService {

	public ResponsePojo getVocuherDetails(Map<String, String> headers);

}
