package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, Licitante, ProvLicitante}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateLicitantes(items: Seq[Licitante], event: String) extends Action

case class GetLicitantes(id: String) extends Action

case class SaveLicitantes(item: Licitante) extends Action

case class InsertLicitante(item: ProvLicitante) extends Action

case class DeleteLicitante(item: ProvLicitante) extends Action

case class DeleteLicitantes(id: String) extends Action

case class SearchLicitantes(str: String) extends Action

case object CleanLicitantes extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class LicitantesHandler[M](modelRW: ModelRW[M, Pot[(Seq[Licitante], String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateLicitantes(items, event) =>
      if(items.isEmpty)  updated(Unavailable)
      else  updated(Ready((items, event)))
    //case CleanPartida =>  updated(Empty)
    //case SavePartida(item) => effectOnly(Effect(AjaxClient[Api].savePartida(item).call().map(UpdatePartida)))
    case DeleteLicitante(item) =>
      effectOnly(Effect(AjaxClient[Api].deleteLicitante(item).call().map(p => UpdateLicitantes(p._1,p._2))))
    case InsertLicitante(item) =>
      effectOnly(Effect(AjaxClient[Api].insertLicitante(item).call().map(p => UpdateLicitantes(p._1,p._2))))
    case GetLicitantes(id) =>
      effectOnly(Effect(AjaxClient[Api].getLicitantes(id).call().map( p => UpdateLicitantes( p._1, p._2))))
    ///case SearchPartida(str) => effectOnly(Effect(AjaxClient[Api].getArticulo(str).call().map(UpdateArticulo)))*/
  }
}