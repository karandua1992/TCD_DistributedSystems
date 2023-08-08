package ie.tcd.cafeapp.service;

import java.util.Map;

import ie.tcd.cafeapp.collection.RedeemVoucherPojo;
import ie.tcd.cafeapp.collection.ResponsePojo;

public interface RedeemVoucherService {

	public ResponsePojo redeemVoucher(RedeemVoucherPojo transactionDetails, Map<String, String> headers);

}
