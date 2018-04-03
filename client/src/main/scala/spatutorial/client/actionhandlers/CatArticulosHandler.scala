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

case class UpdateArticulos(items: Seq[Articulo]) extends Action

case object GetArticulos extends Action

case class SearchArticulos(str: String) extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class CatArticulosHandler[M](modelRW: ModelRW[M, Pot[Seq[Articulo]]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateArticulos(articulos) =>
      if (articulos.isEmpty)
        updated(Unavailable)
      else
        updated(Ready(articulos))
    case GetArticulos =>
      effectOnly(Effect(AjaxClient[Api].getAllArticulos().call().map(UpdateArticulos)))
    case SearchArticulos(str) => {
      effectOnly(Effect(AjaxClient[Api].searchArticulo(str).call().map(UpdateArticulos)))
    }
  }
}