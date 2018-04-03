package spatutorial.client.actionhandlers

//import autowire._
//import spatutorial.shared.{Api, Articulo}
//import spatutorial.client.services.AjaxClient

import diode._
//import diode.data._
//import diode.util._
//import diode.react.ReactConnector

//import boopickle.Default._

//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class SetLastRenglon(renglon: Int) extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class LastRenglonHandler[M](modelRW: ModelRW[M, Int]) extends ActionHandler(modelRW) {
  override def handle = {
    case SetLastRenglon(renglon) => updated(renglon)
  }
}