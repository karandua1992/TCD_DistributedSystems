package ie.tcd.cafeapp.service;

import java.util.Map;

import ie.tcd.cafeapp.collection.ResponsePojo;

public interface GenerateVoucherService {

	public ResponsePojo generateVoucher(Map<String, String> headers);

}
