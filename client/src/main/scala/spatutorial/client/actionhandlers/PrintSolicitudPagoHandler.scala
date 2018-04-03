package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, RptSolicitudPago}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdatePrintSolicitudPago(item: RptSolicitudPago, event: String) extends Action

case class GetPrintSolicitudPago(id: String) extends Action

case class PrintSolicitudPagoByFolio(folio: String, ejercicio: Int) extends Action

case object GetAllPrintSolicitudPago extends Action

case object CleanPrintSolicitudPago extends Action

class PrintSolicitudPagoHandler[M](modelRW: ModelRW[M, Pot[(RptSolicitudPago, String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdatePrintSolicitudPago(item, event) =>
        if(item.folio == "") updated(Unavailable)
        else updated(Ready((item, event)))
    case PrintSolicitudPagoByFolio(folio, ejercicio) => effectOnly(Effect(AjaxClient[Api]
      .printSolicitudPagoByFolio(folio, ejercicio)
      .call().map( rcd => UpdatePrintSolicitudPago(rcd._1, rcd._2)  )))

    case CleanPrintSolicitudPago => updated(Empty)
  }
}