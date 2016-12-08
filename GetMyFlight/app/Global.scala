import play.api._

import models._
import anorm._

object Global extends GlobalSettings {
  
  override def onStart(app: Application) {
    InitialData.insert()
  }
  
}

/**
 * Initial set of data to be imported 
 * in the sample application.
 */
object InitialData {
  
  def date(str: String) = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(str)
  
  def insert() = {
    
  
      
      Seq(
        Flights(12,"jagsvx", "Guillaume Bort","CA", "10/11/2016","10/11/2016", 1234,"70.23343", "67.2323232323", "234", 340),
        Flights(13,"jhsbdcjhsdb", "Guillaume Bort","MA", "10/11/2016","10/11/2016", 1234,"70.23343", "67.2323232323", "234", 340),
        Flights(14,"mxbvnsx", "Guillaume Bort","FAA", "10/11/2016","10/11/2016", 1234,"70.23343", "67.2323232323", "234", 340),
        Flights(15,"mhfbvksdj", "Guillaume Bort","CAA", "10/11/2016","10/11/2016", 1234,"70.23343", "67.2323232323", "234", 340)
      ).foreach(Flights.create)
      
      
      
      
      
    
    
  }
  
}