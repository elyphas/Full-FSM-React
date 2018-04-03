package spatutorial.client.actionhandlers

//import autowire._
//import spatutorial.shared.{Api, ActiveID}
//import spatutorial.client.services.AjaxClient

import diode._
//import diode.data._
//import diode.util._
//import diode.react.ReactConnector

//import boopickle.Default._

//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

//case class UpdateUsuarios(item: Seq[Usuarios]) extends Action

case class SetActiveID(id: String) extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class ActiveIDHandler[M](modelRW: ModelRW[M, String]) extends ActionHandler(modelRW) {
  override def handle = {
    case SetActiveID(id) => updated(id)

  }
}