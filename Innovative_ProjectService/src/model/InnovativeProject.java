package model;

import java.sql.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.CommunicationService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class InnovativeProject {

	private int innovativeProjectID;
	private String projectName;
	private double projectPrice;
	private String imageURL;
	private String projectType;
	private String projectDescription;
	private int researcherID;
	private int quantity;

	public InnovativeProject() {

	}

	public InnovativeProject(int innovativeProjectID, String projectName, double projectPrice, String imageURL,
			String projectType, String projectDescription, int researcherID, int quantity) {
		super();
		this.innovativeProjectID = innovativeProjectID;
		this.projectName = projectName;
		this.projectPrice = projectPrice;
		this.imageURL = imageURL;
		this.projectType = projectType;
		this.projectDescription = projectDescription;
		this.researcherID = researcherID;
		this.quantity = quantity;
	}

	public int getInnovativeProjectID() {
		return innovativeProjectID;
	}

	public String getProjectName() {
		return projectName;
	}

	public double getProjectPrice() {
		return projectPrice;
	}

	public String getImageURL() {
		return imageURL;
	}

	public String getProjectType() {
		return projectType;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public int getResearcherID() {
		return researcherID;
	}

	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	// DB Connection method

	@Override
	public String toString() {
		return "InnovativeProject [innovativeProjectID=" + innovativeProjectID + ", projectName=" + projectName
				+ ", projectPrice=" + projectPrice + ", imageURL=" + imageURL + ", projectType=" + projectType
				+ ", projectDescription=" + projectDescription + ", researcherID=" + researcherID + ", quantity="
				+ quantity + "]";
	}

	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/innovativeprojectsdb", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	// insert method

	public String uploadProject(String pname, String pPrice, String pUrl, String pType, String pDescription,String pQuantity) {
		
		CommunicationService comObj = new CommunicationService();	
		String currentUserDetails =comObj.getCurrentLoggedUserinfo();		
		JsonObject userJSONobj = new JsonParser().parse(currentUserDetails).getAsJsonObject();	
		int reseacherID = userJSONobj.get("UId").getAsInt();

		String output = "";

		// connect to the database

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database";

			}

			// insert query

			String query = "insert into innovativeprojects (`innovativeProjectID`,`projectName`,`projectPrice`,`imageURL`,`projectType`,`projectDescription`,`researcherID`,quantity)"
					+ " values(?, ?, ?, ?, ?, ?, ?, ?)";

			// create a prepared statement

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, pname);
			preparedStmt.setDouble(3, Double.parseDouble(pPrice));
			preparedStmt.setString(4, pUrl);
			preparedStmt.setString(5, pType);
			preparedStmt.setString(6, pDescription);
			preparedStmt.setInt(7, reseacherID);
			preparedStmt.setInt(8, Integer.parseInt(pQuantity)); 

			// execute the statement

			preparedStmt.execute();

			// close the connection

			con.close();

			
			String newItems = readInnovativeProjects();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the project.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}// end of insert method

	
	
	// read all innovativeProjects method

	public String readInnovativeProjects() {

		String output = "";

		// connect to the database

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for reading";

			}

			// read innovativeProjects from DB and assign values for result set variable

			String query = "select* from innovativeprojects";
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set

			while (rs.next()) {

				String iprojectid = Integer.toString(rs.getInt("innovativeProjectID"));
				String iprojectname = rs.getString("projectName");
				String iprojectprice = Double.toString(rs.getDouble("projectPrice"));
				String iprojectimageurl = rs.getString("imageURL");
				String itprojectType = rs.getString("projectType");
				String iprojectdescription = rs.getString("projectDescription");
				String iresaecrhid = Integer.toString(rs.getInt("researcherID"));
				String iquantity = Integer.toString(rs.getInt("quantity"));

			
						
				
				output += "";
				output += "<div class=\"student card bg-light m-2 \" style=\"max-width: 30rem; float: left;\">";

				output += "<div class=\"card-body\">";
				output += "<input id='hidItemIDUpdate' type='hidden' name='hidItemIDUpdate'  value='" + iprojectid + "'>";
				output += "<label class='pname'>"+iprojectname+"</label> || Remaining <label class'='pqty'>"+iquantity+"</label>";
				output += "<br>"; 
				output += "<label class='pprice'>"+iprojectprice+"</label>";
				output += "<br>";
				output += "<label class='ptype'>"+itprojectType+"</label>";
				output += "<br>";
				output += "<label class='pdesc'>"+iprojectdescription+"</label>";				
				output += "</div>";
				
				
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-itemid='" + iprojectid + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-itemid='" + iprojectid + "'></td></tr>";
				
				
				output += "</div>";

			}
			con.close();

		} catch (Exception e) {

			output = "Error while reading innovativeProjects";
			System.out.println(e.getMessage());

		}
		return output;
	}// end of read method

	// read one innovativeProject method

	public String readOneInnovativeProject(int iProjID) {

		String output = "";
		// connect to the database
		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for reading";

			}

			// read particular innovativeProject from DB and assign values for result set
			// variable

			String query = "select* from innovativeprojects where innovativeProjectID  = " + iProjID;
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set

			while (rs.next()) {

				String iprojectid = Integer.toString(rs.getInt("innovativeProjectID"));
				String iprojectname = rs.getString("projectName");
				String iprojectprice = Double.toString(rs.getDouble("projectPrice"));
				String iprojectimageurl = rs.getString("imageURL");
				String itprojectType = rs.getString("projectType");
				String iprojectdescription = rs.getString("projectDescription");
				String iresaecrhid = Integer.toString(rs.getInt("researcherID"));
				String iquantity = Integer.toString(rs.getInt("quantity"));

				// create a JSON String
				
				output += "{";

				output += "innovativeProjectID : \" " + iprojectid + "\", ";
				output += "projectName : \" " + iprojectname + "\", ";
				output += "projectPrice : \" " + iprojectprice + "\", ";
				output += "imageURL : \" " + iprojectimageurl + "\", ";
				output += "projectType : \" " + itprojectType + "\", ";
				output += "projectDescription : \" " + iprojectdescription + "\", ";
				output += "researcherID : \" " + iresaecrhid + "\", ";
				output += "quantity : \" " + iquantity + "\" }";

				output += "\n";

			}
			con.close();

		} catch (Exception e) {

			output = "Error while reading innovativeProjects";
			System.out.println(e.getMessage());

		}
		return output;
	}// end of read one particular innovativeProject method

	// remove innovativeProject method

	public String removeInnnovativeProjects(String iProjID) {

		String output = "";

		// connect to the database

		try {

			Connection con = connect();
			if (con == null) {

				return "Error while connecting to the database for removing";
			}

			// delete query

			String query = "delete from innovativeprojects where innovativeProjectID = " + Integer.parseInt(iProjID);

			// create a prepared statement

			PreparedStatement stmt = con.prepareStatement(query);

			// execute the statement

			stmt.executeUpdate();
			con.close();
			String newItems = readInnovativeProjects();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while removing the project.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}// end of remove method


	
	// update innovativeProject method
	
	public String updateInnovativeProject(String IProjectID, String pname, String pPrice, String pUrl, String pType,
			String pDescription, String pQuantity) {

		String output = "";
		CommunicationService comObj = new CommunicationService();	
		String currentUserDetails =comObj.getCurrentLoggedUserinfo();		
		JsonObject userJSONobj = new JsonParser().parse(currentUserDetails).getAsJsonObject();	
		int reseacherID = userJSONobj.get("UId").getAsInt();

		try {

			Connection con = connect();
			if (con == null) {

				output = "Error while connecting to the database for updating an InnovativeProject";
			}

			// update query

			String query = "update innovativeprojects set projectName = ?,	projectPrice = ?,imageURL = ?,	"
					+ "projectType= ?, projectDescription= ?, researcherID= ?, 	quantity = ? where innovativeProjectID = ?";

			// create a prepare statement

			PreparedStatement stmt = con.prepareStatement(query);

			// binding values

			int iProjID = Integer.parseInt(IProjectID);

			stmt.setString(1, pname);
			stmt.setDouble(2, Double.parseDouble(pPrice));
			stmt.setString(3, pUrl);
			stmt.setString(4, pType);
			stmt.setString(5, pDescription);
			stmt.setInt(6, reseacherID);
			stmt.setInt(7, Integer.parseInt(pQuantity));

			stmt.setInt(8, iProjID);

			stmt.execute();

			output = "Project details updated  successfully";

			// close the connection

			con.close();

			String newItems = readInnovativeProjects();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the project.\"}";
			System.err.println(e.getMessage());
		}


		return output;

	}// end of update method

}
