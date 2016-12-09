package models



case class Flight(source: String, destination: String, carrier: String, dateOfTravel: String, dateOfPriceFall:String, actualPrice: String, predictedPrice: String, latitude: String, longitude: String,id:Option[Int]=None)


