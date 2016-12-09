package bootstrap

import com.google.inject.Inject
import javax.inject.Singleton
import repo.FlightRepository
import models.Flight
import java.util.Date
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.Logger
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class InitialData @Inject() (flightRepo: FlightRepository) {

  def insert = for {
    flights <- flightRepo.getAll() if (flights.length == 0)
    _ <- flightRepo.insertAll(Data.flights)
  } yield {}

  try {
    Logger.info("DB initialization.................")
    Await.result(insert, Duration.Inf)
  } catch {
    case ex: Exception =>
      Logger.error("Error in database initialization ", ex)
  }

}

object Data {
  val flights = List(
    Flight("B", "N","CA", "10/11/2016","10/11/2016", "1234","70.23343", "67.2323232323"),
    Flight("N", "B","CA", "10/11/2016","10/11/2016", "1234","70.23343", "67.2323232323"),
	Flight("F", "York","CA", "10/11/2016","10/11/2016", "1234","70.23343", "67.2323232323"),
    Flight("GH", "RH","CA", "10/11/2016","10/11/2016", "1234","70.23343", "67.2323232323"))
}

