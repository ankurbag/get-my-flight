package utils



import models._
import play.api.libs.json.Json


object JsonFormat {

 
  implicit val flightFormat = Json.format[Flight]


}


