package controllers

import com.google.inject.Inject
import models.Flight
import play.api.Logger
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json._
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc._
import repo.FlightRepository
import utils.Constants
import utils.JsonFormat._

import scala.concurrent.Future

/**
  * Handles all requests related to Flight
  */
class FlightController @Inject()(flightRepository: FlightRepository, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  import Constants._

  val logger = Logger(this.getClass())

  /**
    * Handles request for getting all flights from the database
    */
  def list() = Action.async {
    flightRepository.getAll().map { res =>
      logger.info("Flight list: " + res)
      Ok(successResponse(Json.toJson(res), Messages("flight.success.flightList")))
    }
  }

  


  private def errorResponse(data: JsValue, message: String) = {
    obj("status" -> ERROR, "data" -> data, "msg" -> message)
  }

 
  
  def search(flightId: Int) = Action.async(parse.json) {request =>
	logger.info("Flight Json ===> " + request.body)
	request.body.validate[Flight].fold(
	error => Future.successful(BadRequest(JsError.toJson(error))),
	{
	flight =>
		flightRepository.getById(flightId).map { res => Ok(successResponse(Json.toJson("{}"), Messages("flight.success.selected"))) }
	})
  }
 
  
  
 

  private def successResponse(data: JsValue, message: String) = {
    obj("status" -> SUCCESS, "data" -> data, "msg" -> message)
  }

}
