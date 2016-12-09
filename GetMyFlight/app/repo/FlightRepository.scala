package repo

import javax.inject.{Inject, Singleton}

import models.Flight
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.Future

@Singleton()
class FlightRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends FlightTable with HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._
    
    //For future use of inserting data directly into the application
  def insert(flight: Flight): Future[Int] = db.run {
    flightTableQueryInc += flight
  }

  def insertAll(flights: List[Flight]): Future[Seq[Int]] = db.run {
    flightTableQueryInc ++= flights
  }

  def update(flight: Flight): Future[Int] = db.run {
    flightTableQuery.filter(_.id === flight.id).update(flight)
  }


  def getAll(): Future[List[Flight]] = db.run {
    flightTableQuery.to[List].result
  }

 
  def getById(id: Int): Future[Option[Flight]] = db.run {
    flightTableQuery.filter(_.id === id).result.headOption
  }

  def ddl = flightTableQuery.schema

}

private[repo] trait FlightTable {
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import driver.api._

  lazy protected val flightTableQuery = TableQuery[FlightTable]
  lazy protected val flightTableQueryInc = flightTableQuery returning flightTableQuery.map(_.id)

  private[FlightTable] class FlightTable(tag: Tag) extends Table[Flight](tag, "flight") {
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
	 val source = column[String]("source")
    val destination = column[String]("destination")
    val carrier = column[String]("carrier")
    val dateOfTravel = column[String]("dateOfTravel")
    val dateOfPriceFall = column[String]("dateOfPriceFall")
    val actualPrice = column[String]("actualPrice")
    val predictedPrice = column[String]("predictedPrice")
    val latitude = column[String]("latitude")
    val longitude = column[String]("longitude")



   def * = (source, destination, carrier, dateOfTravel, dateOfPriceFall, actualPrice, predictedPrice, latitude,longitude,id.?) <> ((Flight.apply _).tupled, Flight.unapply)
  }

}

