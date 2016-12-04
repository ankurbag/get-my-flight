package dal

import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import models.{Flight}

import scala.concurrent.{ExecutionContext, Future}

/**
 * A repository for people.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class FlightRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import driver.api._

  /**
   * Here we define the table. It will have a name of people
   */
  private class FlightTable(tag: Tag) extends Table[Flight](tag, "flight") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)


    def source = column[String]("source")
    def destination = column[String]("destination")
    def carrier = column[String]("carrier")
    def dateOfTravel = column[String]("dateOfTravel")
    def dateOfPriceFall = column[String]("dateOfPriceFall")
    def predictedPrice = column[Int]("predictedPrice")
    def latitude = column[String]("latitude")
    def longitude = column[String]("longitude")
    def distance = column[String]("distance")
    def seats = column[Int]("seats")


    /**
     * This is the tables default "projection".
     *
     * It defines how the columns are converted to and from the Person object.
     *
     * In this case, we are simply passing the id, name and page parameters to the Person case classes
     * apply and unapply methods.
     */
    def * = (id, source, destination, carrier, dateOfTravel, dateOfPriceFall, predictedPrice, latitude,longitude, distance,seats) <> ((Flight.apply _).tupled, Flight.unapply)
  }

  /**
   * The starting point for all queries on the people table.
   */
  private val flight = TableQuery[FlightTable]

  /**
   * Create a person with the given name and age.
   *
   * This is an asynchronous operation, it will return a future of the created person, which can be used to obtain the
   * id for that person.
   */
  /*def create(name: String, age: Int): Future[Person] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    (people.map(p => (p.name, p.age))
      // Now define it to return the id, because we want to know what id was generated for the person
      returning people.map(_.id)
      // And we define a transformation for the returned value, which combines our original parameters with the
      // returned id
      into ((nameAge, id) => Person(id, nameAge._1, nameAge._2))
    // And finally, insert the person into the database
    ) += (name, age)
  }
*/
  /**
   * List all the people in the database.
   */
  def list(): Future[Seq[Flight]] = db.run {
    flight.result
  }
}
