package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import model.Purchase;

@Path("/Purchase")
public class PurchaseServiceAPI {
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String proceedToPayment(String ProjectDetails) {

		// create iproject JSON Object

		JsonObject innovativeProjectObject = new JsonParser().parse(ProjectDetails).getAsJsonObject();

		// get by postman

		int iProjID = innovativeProjectObject.get("innovativeProjectID").getAsInt();
		String projectname = innovativeProjectObject.get("projectName").getAsString();
		double projectprice = innovativeProjectObject.get("projectPrice").getAsDouble();

		return sendDeatailsToPayment(iProjID,projectname,projectprice);

	}

	public String sendDeatailsToPayment(int i_projectid, String pname, double pPrice) {

		
		//  create CommunicationService class object
		
		CommunicationService comObj = new CommunicationService();
		
		// call the method and store in a string variable

		String currentUserDetails = comObj.getCurrentLoggedUserinfo();
		
		// create user JSON Object
		
		JsonObject userJSONobj = new JsonParser().parse(currentUserDetails).getAsJsonObject();
		// get by user account communication

		int customerID = userJSONobj.get("UId").getAsInt();
		String customerName = userJSONobj.get("UserName").getAsString();
		String cusmail = userJSONobj.get("Email").getAsString();

		// communicate with Payment Service.send order project details to payment.

		Client client = Client.create();
		String url = "http://localhost:8083/PaymentService/Payment_Service/Payments/purchase";
		WebResource resource = client.resource(url);
		String input = "{\"iProjectID\":\"" + i_projectid + "\",\"projectName\":\"" + pname + "\",\"projectPrice\":\""
				+ pPrice + "\",\"customerid\":\"" + customerID + "\", \"customerName\":\"" + customerName + "\",\"mail\":\"" + cusmail
				+ "\"}";
		ClientResponse response = resource.type("application/json").post(ClientResponse.class, input);
		String output = response.getEntity(String.class);
		
		return output;

	}

	// this method will be call by informProjectService method of payment service

	@POST
	@Path("/confirm")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String getPurcchaseDetails(String PurchaseDetails) {

		// create a JSON Object

		JsonObject innovativeProjectObject = new JsonParser().parse(PurchaseDetails).getAsJsonObject();

		Purchase purchaseObj = new Purchase();

		int iProjID = innovativeProjectObject.get("innovativeProjectID").getAsInt();
		int customerID = innovativeProjectObject.get("customerID").getAsInt();
		String pDate = innovativeProjectObject.get("date").getAsString();
		String pTime = innovativeProjectObject.get("time").getAsString();

		String output = purchaseObj.insertPurchase(iProjID, customerID, pDate, pTime);

		return output;

	}

	

}
