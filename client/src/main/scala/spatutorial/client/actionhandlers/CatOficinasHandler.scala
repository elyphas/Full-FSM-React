package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, Oficina}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateAllOficinas(item: Seq[Oficina]) extends Action

case object GetAllOficinas extends Action

case object CleanLstOficinas extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class CatOficinasHandler[M](modelRW: ModelRW[M, Pot[Seq[Oficina]]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateAllOficinas(oficinas) =>
      if (oficinas.isEmpty)
        updated(Unavailable)
      else
        updated(Ready(oficinas))
    case GetAllOficinas => effectOnly(Effect(AjaxClient[Api].getAllOficinas().call().map(UpdateAllOficinas)))
    case CleanLstOficinas => updated(Empty)
  }
}