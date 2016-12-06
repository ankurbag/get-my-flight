package models

import play.api.libs.json._
import play.api.db._

case class Flights(id: Long, source: String, destination: String, carrier: String, dateOfTravel: String, dateOfPriceFall:String, predictedPrice: Int, latitude: String, longitude: String, distance: String, seats: Int)

object Flights {
  
  implicit val flightFormat = Json.format[Flights]
  
  
  def create(flights: Flights): Flights = {
  DB.withConnection { implicit connection =>
    SQL(
      """
        insert into flights values (
          {source}, {destination}, {carrier},{dateOfTravel}, {dateOfPriceFall}, {predictedPrice}, {latitude},{longitude},{distance},{seats}
        )
      """
    ).on(
      'source -> flights.source,
      'destination -> flights.destination,
      'carrier -> flights.carrier,
	  'dateOfTravel -> flights.dateOfTravel,
	  'dateOfPriceFall -> flights.dateOfPriceFall,
	  'predictedPrice -> flights.predictedPrice,
	  'latitude -> flights.latitude,
	  'longitude -> flights.longitude,
	  'distance -> flights.distance,
	  'seats -> flights.seats
    ).executeUpdate()
    
    flights
    
  }
}

}
