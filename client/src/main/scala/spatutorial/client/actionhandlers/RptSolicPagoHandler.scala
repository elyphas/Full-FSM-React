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

case class UpdateRptSolicitudPago(item: Seq[RptSolicitudPago], event: String) extends Action

case class GetRptSolicitudPago(id: String) extends Action

case class GetBetweenDatesRptSolicitudPago(start: String, end: String) extends Action

case object GetAllRptSolicitudPago extends Action

case object CleanRptSolicitudPago extends Action

class RptSolicPagoHandler[M](modelRW: ModelRW[M, Pot[(Seq[RptSolicitudPago], String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateRptSolicitudPago(item, event) =>
      if(item.isEmpty) updated(Unavailable)
      else updated(Ready((item, event)))
    /*case GetAllRptSolicitudPago => effectOnly(Effect(AjaxClient[Api].getAllRptSolicitudPago()
      .call().map( rcd => UpdateRptSolicitudPago(rcd._1, rcd._2)  )))*/
    case GetBetweenDatesRptSolicitudPago(start, end) =>
      effectOnly(Effect(AjaxClient[Api].getBetweenDatesRptSolicitudPago(start, end)
        .call().map(rcd => UpdateRptSolicitudPago(rcd._1, rcd._2))))
    case CleanRptSolicitudPago =>  updated(Empty)
  }
}