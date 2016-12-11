package com.neu.getmyflight.dao;

/**
 * Class Name - Connection DAO 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.neu.getmyflight.*;

import com.neu.getmyflight.ProjectConstants;
import com.neu.getmyflight.response.TravelOutput;

public class ConnectionDao {

	private static ConnectionDao connectionDao;
	private static final String QUERY="SELECT * from flight where 	MONTH_OF_TRAVEL=? and DAY_OF_TRAVEL=? and MONTH_OF_PRICEFALL>=? and DAY_OF_PRICEFALL>=? and source like ? and destination like ? and ? > predictedPrice order by predictedPrice ASC LIMIT 1";
	private ConnectionDao() {

	}

	public static ConnectionDao getConnectionDao() {
		if (connectionDao == null)
			connectionDao = new ConnectionDao();
		return connectionDao;
	}

	/**
	 * method - gets Connection
	 * 
	 * @param void
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection connection = null;
		// STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// STEP 3: Open a connection
		System.out.println("Connecting to database...");
		try {
			connection = DriverManager.getConnection(ProjectConstants.DB_URL, ProjectConstants.DB_USER,
					ProjectConstants.DB_PASS);
			System.out.println("Success!!!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	
	
	/**
	 * Login Service
	 * 
	 * @param conn
	 * @param account
	 * @return
	 */
	public static TravelOutput getFlightOnDateOfTravel(Connection conn,String []params) {
		
		String query = QUERY;
		
		ResultSet rs = null;
		TravelOutput  travelOutput = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, Integer.parseInt(params[0]));
			pstmt.setInt(2, Integer.parseInt(params[1]));
			pstmt.setInt(3, Integer.parseInt(params[2]));
			pstmt.setInt(4, Integer.parseInt(params[3]));
			pstmt.setString(5, params[4]);
			pstmt.setString(6, params[5]);
			pstmt.setDouble(7, Double.parseDouble(params[6]));
			
			rs = pstmt.executeQuery();
			System.out.println("Resultset.. " + rs);
			
			if (rs != null && rs.next()) {
				travelOutput = new TravelOutput();
				//source,destination,predictedPrice
				travelOutput.setCarrier(rs.getString("carrier"));
				travelOutput.setActualPrice(params[6]);
				travelOutput.setDateOfTravel(""+params[0]+"/"+params[1]+"/2017");
				travelOutput.setPredictedPrice(String.valueOf(rs.getDouble("predictedPrice")));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return travelOutput;
	}
	public static void main(String[] args) {
		//params[0] = "1"
		/*Random r = new Random();
		double randomValue = 100 + (400 - 100) * r.nextDouble();
		String params[] = new String[]{"1","1","12","9","%New York%","%Fort Lauderdale FL%",String.valueOf(randomValue)};
		
		TravelOutput to = getFlightOnDateOfTravel(getConnection(),params);
		System.out.println(to.getPredictedPrice());*/
		DateFormat dateFormat = new SimpleDateFormat("MM/dd");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		String day[] = dateFormat.format(date).split("/");
		System.out.println(day[1]);
	}
}
