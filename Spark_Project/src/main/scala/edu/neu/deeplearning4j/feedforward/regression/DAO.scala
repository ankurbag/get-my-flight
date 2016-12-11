package edu.neu.deeplearning4j.feedforward.regression

import java.sql.{Connection, DriverManager}

/**
  * Created by Ankur on 12/8/2016.
  */
object DAO extends App {

  def insertToDb(params:Array[String]):Int={
    // connect to the database named "mysql" on the localhost
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://aasqt87uhdjiu9.cpokblqfh08u.us-west-2.rds.amazonaws.com:3306/flightdb";
    val username = "root"
    val password = "root1234"
    var insertedRow = 0
    // there's probably a better way to do this
    var connection:Connection = null

    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.prepareStatement("INSERT into flight(source,destination,carrier,dateOfTravel,dateOfPriceFall,actualPrice,predictedPrice,latitude,longitude,MONTH_OF_TRAVEL,DAY_OF_TRAVEL,MONTH_OF_PRICEFALL,DAY_OF_PRICEFALL) values (?,?,?,?,?,?,?,?,?,?,?,?,?)")
      statement.setString(1,params(0))
      statement.setString(2,params(1))
      statement.setString(3,params(2))
      statement.setString(4,params(3))
      statement.setString(5,params(4))
      statement.setDouble(6,params(5).toDouble)
      statement.setDouble(7,params(6).toDouble)
      statement.setString(8,params(7))
      statement.setString(9,params(8))
      statement.setInt(10,params(9).toInt)
      statement.setInt(11,params(10).toInt)
      statement.setInt(12,params(11).toInt)
      statement.setInt(13,params(12).toInt)

      insertedRow = statement.executeUpdate();
      if(insertedRow != 0){
        println("Inserted " + insertedRow + " row successfully!!!")
      }

    } catch {
      case e => e.printStackTrace
    }
    connection.close()
    insertedRow
  }

  override def main(args: Array[String]): Unit = {
    val params = Array("Boston", "New York","CA", "10/11/2016","10/11/2016", "1234","256.8","70.23343", "67.2323232323","1","2","3","4")
    insertToDb(params)
  }
}