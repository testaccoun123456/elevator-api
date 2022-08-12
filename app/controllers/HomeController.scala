package controllers

import javax.inject._
import play.api._
import play.api.libs.json._
import play.api.mvc._

/**
 * This controller handles all of the elevator requests.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  // Floors called by people waiting to get into an elevator.
  var calledFloors = Set[Int]()

  // Floors selected as a destination from within the elevator.
  // This is a Seq because I'm assuming the floor destinations are server FIFO, 
  // since there's nothing specified yet about actually simulating the elevator in this project,
  // and there are many different possible strategies for servicing floors in which order.
  var selectedFloors = IndexedSeq[Int]()

  /**
   * POST handler to request an elevator via a call button outside the elevator.
   * Expects a post body of the form: {"floor": n} where n is an Int.
   */
  def requestElevator() = Action { implicit request: Request[AnyContent] =>
    try {
      val floor = (request.body.asJson.get \ "floor").as[Int]
      calledFloors = calledFloors + floor
      Ok(Json.toJson("ok"))
    } catch {
      case _: Throwable => BadRequest("Invalid floor")
    }
  }

  /**
   * POST handler to request a destination floor via a button inside the elevator.
   * Expects a post body of the form: {"floor": n} where n is an Int.
   * Requested floors stored in order, assuming we want to go to those in order.
   */
  def requestFloor() = Action { implicit request: Request[AnyContent] =>
    try {
      val floor = (request.body.asJson.get \ "floor").as[Int]
      selectedFloors = selectedFloors :+ floor
      Ok(Json.toJson("ok"))
    } catch {
      case _: Throwable => BadRequest("Invalid floor")
    }
  }

  /**
   * GET handler to retrieve all requested floors.
   * Result is a JSON list.
   */
  def requestedFloors() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.toJson(selectedFloors))
  }

  /**
   * GET handler to retrieve the next floors to service. Current strategy for that is unspecified, 
   * so defaulting to FIFO according to the called floors.
   * Result is a JSON string, "None" if not floor has been called yet.
   */
  def nextFloor() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.toJson(selectedFloors.headOption.map(_.toString).getOrElse("None")))
  }

}
