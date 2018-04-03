package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, Articulo}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateArticulo(item: Seq[Articulo]) extends Action

case class GetArticulo(Cve: String) extends Action

case class SaveArticulo(art: Articulo) extends Action

case class InsertArticulo(art: Articulo) extends Action

case class DeleteArticulos(Cve: String) extends Action

case class BajaArticulo(Cve: String) extends Action

case class SearchArticulo(str: String) extends Action

case object SetNewArticulo extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class ArticulosHandler[M](modelRW: ModelRW[M, Pot[Articulo]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateArticulo(art) =>
      if (art.isEmpty) updated(Unavailable)
      else updated(Ready(art.head))
    case SetNewArticulo =>
      updated(Empty)
    case SaveArticulo(art) =>
      effectOnly(Effect(AjaxClient[Api].saveArtic(art).call().map(UpdateArticulo)))
    case InsertArticulo(art) =>
      effectOnly(Effect(AjaxClient[Api].insertArtic(art).call().map(UpdateArticulo)))
    case GetArticulo(cve) =>
      effectOnly(Effect(AjaxClient[Api].getArticulo(cve).call().map(UpdateArticulo)))
    case SearchArticulo(str) =>
      effectOnly(Effect(AjaxClient[Api].getArticulo(str).call().map(UpdateArticulo)))
  }
}