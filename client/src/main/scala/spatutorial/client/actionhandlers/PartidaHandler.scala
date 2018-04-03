package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, Partida}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdatePartida(item: Seq[Partida], event: String) extends Action

case class GetPartida(id: String) extends Action

case class SavePartida(item: Partida) extends Action

case class InsertPartida(item: Partida) extends Action

case class DeletePartida(id: String) extends Action

case class SearchPartida(str: String) extends Action

case object CleanPartida extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class PartidaHandler[M](modelRW: ModelRW[M, Pot[(Partida, String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdatePartida(item, event) =>
      if(item.isEmpty)  updated(Unavailable)
      else  updated(Ready((item.head, event)))
    case CleanPartida =>  updated(Empty)
    case SavePartida(item) => effectOnly(Effect(AjaxClient[Api].savePartida(item).call().map( p => UpdatePartida(p._1,p._2))))
    case InsertPartida(art) => effectOnly(Effect(AjaxClient[Api].insertPartida(art).call().map(p => UpdatePartida(p._1,p._2))))
    case GetPartida(id) => effectOnly(Effect(AjaxClient[Api].getPartida(id).call().map( p => UpdatePartida( p._1, p._2))))
    ///case SearchPartida(str) => effectOnly(Effect(AjaxClient[Api].getArticulo(str).call().map(UpdateArticulo)))*/
  }
}