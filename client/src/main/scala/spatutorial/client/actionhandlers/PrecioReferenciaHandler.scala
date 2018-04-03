package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, PrecioReferencia}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdatePrecioReferencia(item: Seq[PrecioReferencia], event: String) extends Action

case class GetPrecioReferencia(rfc: String) extends Action

case object CleanPrecioReferencia extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class PrecioReferenciaHandler[M](modelRW: ModelRW[M, Pot[(PrecioReferencia, String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdatePrecioReferencia(item, event) =>
      if (item.isEmpty) updated(Unavailable)
      else updated(Ready((item.head, event)))
    case GetPrecioReferencia(rfc) =>
      effectOnly(Effect(AjaxClient[Api].getPrecioReferencia(rfc).call().map(rcd => UpdatePrecioReferencia(rcd._1, rcd._2))))
    case CleanPrecioReferencia =>  updated(Empty)
  }
}