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

case class UpdateOficina(item: Oficina, event: String) extends Action

case class GetOficina(id: String) extends Action

case class SaveOficina(item: Oficina) extends Action

case class Insert(item: Oficina) extends Action

case class DeleteOficina(id: String) extends Action

case class SearchOficina(str: String) extends Action

case object CleanOficina extends Action

//case object GetAllOficinas extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class OficinaHandler[M](modelRW: ModelRW[M, Pot[(Oficina, String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateOficina(item, event) =>
      if(item.clave == "")  updated(Unavailable)
      else  updated(Ready((item, event)))
    case CleanOficina =>  updated(Empty)
    //case SaveOficina(item) => effectOnly(Effect(AjaxClient[Api].savePartida(item).call().map( p => UpdatePartida(p._1,p._2))))
    //case InsertOficina(item) => effectOnly(Effect(AjaxClient[Api].insertPartida(art).call().map(p => UpdatePartida(p._1,p._2))))
    case GetOficina(id) => effectOnly(Effect(AjaxClient[Api].getOficina(id).call().map( p => UpdateOficina( p._1, p._2))))
    //case GetAllOficinas => effectOnly(Effect(AjaxClient[Api].getAllOficinas().call().map( p => UpdateOficina( p._1, p._2))))
    ///case SearchPartida(str) => effectOnly(Effect(AjaxClient[Api].getArticulo(str).call().map(UpdateArticulo)))*/
  }
}