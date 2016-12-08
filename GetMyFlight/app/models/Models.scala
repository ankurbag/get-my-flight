package models

case class Employee(name: String, email: String, companyName: String,position:String, id: Option[Int]=None)

case class Flight(source: String, destination: String, carrier: String, dateOfTravel: String, dateOfPriceFall:String, predictedPrice: String, latitude: String, longitude: String, distance: String, seats: String,id:Option[Int]=None)


