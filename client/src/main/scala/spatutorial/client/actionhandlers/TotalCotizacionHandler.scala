package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, TotalCotizacion}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateTotalCotizacion(item: Seq[TotalCotizacion], event: String) extends Action

case class GetTotalCotizacion(id: String) extends Action

case object CleanTotalCotizacion extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class TotalCotizacionHandler[M](modelRW: ModelRW[M, Pot[(Seq[TotalCotizacion], String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateTotalCotizacion(item, event) =>
      if(item.isEmpty)
          updated(Unavailable)
      else
          updated(Ready((item, event)))
    case GetTotalCotizacion(id) =>
      effectOnly(Effect(AjaxClient[Api].getTotalCotizacion(id).call().map( p => UpdateTotalCotizacion(p._1, p._2))))
    case CleanTotalCotizacion =>
      updated(Empty)
  }
}