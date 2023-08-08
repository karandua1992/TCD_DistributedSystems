package ie.tcd.cafeapp.service;

import ie.tcd.cafeapp.collection.CredentialsPojo;
import ie.tcd.cafeapp.collection.ResponsePojo;

public interface LoginService {

	public ResponsePojo validateCredentials(CredentialsPojo credentials);

}
