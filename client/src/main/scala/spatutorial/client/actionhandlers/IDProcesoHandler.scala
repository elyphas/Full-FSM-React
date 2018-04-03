package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, LastID}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateLastIDProceso(item: Seq[LastID] ) extends Action

case class GetLastIDProceso(ejercicio: Int) extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class IDProcesoHandler[M](modelRW: ModelRW[M, Pot[LastID]]) extends ActionHandler(modelRW) {

  override def handle = {
    case UpdateLastIDProceso(id) =>
        if (id.isEmpty)
          updated(Unavailable)
        else
          updated(Ready(id.head))
    case GetLastIDProceso(ejercicio) =>
      effectOnly(Effect(AjaxClient[Api].getLastId(ejercicio).call().map(UpdateLastIDProceso)))
  }

}