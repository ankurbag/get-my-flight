package controllers

import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json.Json
import models._
import dal._

import scala.concurrent.{ ExecutionContext, Future }

import javax.inject._

class FlightController @Inject() (repo: FlightRepository, val messagesApi: MessagesApi)
                                 (implicit ec: ExecutionContext) extends Controller with I18nSupport{
                                     
   /**
   * The mapping for the flight form.
   */
  val flightForm: Form[CreateFlightForm] = Form {
    mapping(
      "source" -> nonEmptyText,
      "destination" -> nonEmptyText,
      "dateOfTravel" -> nonEmptyText
    )(CreateFlightForm.apply)(CreateFlightForm.unapply)
  }
  
                                 
                                     
 def index = Action {
    Ok(views.html.index(flightForm))
  }  
  
  def searchFlights = Action { implicit request =>
  flightForm.bindFromRequest().fold(
    formWithErrors => BadRequest(views.html.index(formWithErrors)),
    flight => Ok(s"Flight ${flight.source} created successfully"))
}
                          
                                     
    }
            
  /**
 * The create person form.
 *
 * Generally for forms, you should define separate objects to your models, since forms very often need to present data
 * in a different way to your models.  In this case, it doesn't make sense to have an id parameter in the form, since
 * that is generated once it's created.
 */
case class CreateFlightForm(source: String, destination: String, dateOfTravel: String)                                
                                     
                                     
                      
                                 