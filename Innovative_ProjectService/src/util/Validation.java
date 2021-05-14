package util;

import java.net.URL;

public class Validation {

	public String validateProjectDetails(String imageUrl, double iProjectprice, String iprojectType, int iQuantity) {

		String status = "";

		if (!isValid(imageUrl)) {

			status = "Please enter valid URL for innovative project image";

		} else if (iProjectprice <= 0) {

			status = "Innovative Project price should be greater than 0";

		} else if (!(iprojectType.equals("Proposal Project") || iprojectType.equals("Innovative Project"))) {

			status = "Invalid project type!!!";

		}else if(iQuantity>10) {
			
			status = "Qunatity should be less than or equal to 10";
		}else {
			
			status = "OK";
		}

		return status;

	}

	public static boolean isValid(String url) {

		try {
			new URL(url).toURI();
			return true;
		}

		catch (Exception e) {
			return false;
		}

	}
}
