package com;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CommunicationService {
	
	// communicate with user account service
	
	public String getCurrentLoggedUserinfo() {

		Client client = Client.create();

		WebResource webResource = client.resource("http://localhost:8083/UserAccounts/UserAccountService/User_logins");

		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		String output = response.getEntity(String.class);

		System.out.println(output);

		return output;

	}
	

}
