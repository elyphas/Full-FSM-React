package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, Programa}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdatePrograma(item: Programa, event: String) extends Action

case class GetPrograma(id: String) extends Action

case class SavePrograma(item: Programa) extends Action

case class InsertPrograma(item: Programa) extends Action

case class DeletePrograma(id: String) extends Action

case class SearchPrograma(str: String) extends Action

case object CleanPrograma extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class ProgramaHandler[M](modelRW: ModelRW[M, Pot[Programa]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdatePrograma(item, event) =>
      if(item.programa == "")
        updated(Unavailable)
      else
        updated(Ready(item))
    case CleanPrograma =>  updated(Empty)
    case SavePrograma(item) =>
      effectOnly(Effect(AjaxClient[Api].savePrograma(item).call()
        .map(rcd => UpdatePrograma(rcd, "falta"))))
    case InsertPrograma(item) =>
      effectOnly(Effect(AjaxClient[Api].insertPrograma(item).call()
        .map(rcd => UpdatePrograma(rcd, "falta"))))
    case GetPrograma(id) =>
      effectOnly(Effect(AjaxClient[Api].getPrograma(id).call().map( rcd => UpdatePrograma(rcd._1, rcd._2))))
  }
}