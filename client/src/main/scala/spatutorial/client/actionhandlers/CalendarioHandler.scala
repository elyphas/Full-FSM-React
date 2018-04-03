package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, Calendario}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateCalendario(item: Seq[Calendario], event: String) extends Action

case class GetCalendario(id: String) extends Action

case class SaveCalendario(item: Calendario) extends Action

case class InsertCalendario(item: Calendario) extends Action

case class DeleteCalendario(id: String) extends Action

case class SearchCalendario(str: String) extends Action

case object CleanCalendario extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class CalendarioHandler[M](modelRW: ModelRW[M, Pot[(Calendario, String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateCalendario(item, event) =>
      if(item.isEmpty)  updated(Unavailable)
      else  updated(Ready((item.head, event)))
    case GetCalendario(id) =>
      effectOnly(Effect(AjaxClient[Api].getCalendario(id).call().map( p => UpdateCalendario( p._1, p._2))))
    case CleanCalendario =>
      updated(Empty)
    case SaveCalendario(item) =>
      effectOnly(Effect(AjaxClient[Api].saveCalendario(item).call().map( p => UpdateCalendario( p._1, p._2))))
    case InsertCalendario(item) =>
      effectOnly(Effect(AjaxClient[Api].insertCalendario(item).call().map( p => UpdateCalendario( p._1, p._2))))
  }
}