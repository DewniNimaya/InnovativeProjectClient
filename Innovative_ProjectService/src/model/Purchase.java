package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Date;

public class Purchase {

	private int iProjectID;
	private int customerID;
	private String date;
	private String time;

	public int getiProjectID() {
		return iProjectID;
	}

	public void setiProjectID(int iProjectID) {
		this.iProjectID = iProjectID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Purchase() {
		super();
	}

	public Purchase(int iProjectID, int customerID, String date, String time) {
		super();
		this.iProjectID = iProjectID;
		this.customerID = customerID;
		this.date = date;
		this.time = time;
	}

	// DB Connection method

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

	// method to store purchase details in the purchase table

	public String insertPurchase(int i_ProjectID, int customer_ID, String pDate, String pTime) {

		String output = "";

		// connect to the database

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database";

			}

			// insert query

			String query = "insert into purchase (`iProjectID`,`customerID`,`date`,`time`)" + " values(?, ?, ?, ?)";

			// create a prepared statement

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setInt(1, i_ProjectID);
			preparedStmt.setInt(2, customer_ID);

			Date date1 = Date.valueOf(pDate);
			preparedStmt.setDate(3, date1);

			Time time1 = Time.valueOf(pTime);
			preparedStmt.setTime(4, time1);

			// execute the statement

			preparedStmt.execute();

			// close the connection

			con.close();

			output = "Project purchase details inserted Successfully";

		} catch (Exception e) {

			output = "Error while inserting";
			System.out.println(e.getMessage());

		}

		return output;
	}// end of insert method

}
