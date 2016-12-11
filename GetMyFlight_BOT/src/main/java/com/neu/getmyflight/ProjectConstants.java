/**
 * 
 */
package com.neu.getmyflight;

/**
 * @author Ankur
 *
 */
public interface ProjectConstants {
	// DB Constants
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://aasqt87uhdjiu9.cpokblqfh08u.us-west-2.rds.amazonaws.com:3306/flightdb";
	static final String DB_USER = "root";
	static final String DB_PASS = "root1234";
    
	//USER-PROFILE CONSTANTS
	static final String USER_NAME ="userName";
	static final String PASSWORD = "password";
	static final String FIRST_NAME = "first_name";
	static final String LAST_NAME = "last_name";
	static final String PH_NUM = "phone_Num";
	static final String EMAIL_ADDRESS ="email_Address";
	static final String ADDRESS1 = "address1";
	static final String ADDRESS2 = "address2";
	static final String CITY ="city";
	static final String STATE ="state";
	static final String ZIP ="zip_Code";
	static final String COUNTRY="country";
	static final String LATITUDE="latitude";
	static final String LONGITUDE="longitude";
}
