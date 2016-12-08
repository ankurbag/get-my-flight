package models

import play.api.libs.json._

case class Flight(source: String, destination: String, dateOfTravel: String)
object Flight {
  
  implicit val flightFormat = Json.format[Flight]
}