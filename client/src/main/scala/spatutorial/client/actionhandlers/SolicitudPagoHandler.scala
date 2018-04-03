package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, IdSolicitudPago, SolicitudPago}
import spatutorial.client.services.AjaxClient
import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector
import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateSolicitudPago(item: SolicitudPago, event: String) extends Action

case class GetSolicitudPago(id: IdSolicitudPago) extends Action

case class GetSolicitudPagoByIdComparativo(id: String) extends Action

case class InsertSolicitudPago(item: SolicitudPago) extends Action

case class DeleteSolicitudPago(id: IdSolicitudPago) extends Action

case object CleanSolicitudPago extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class SolicitudPagoHandler[M](modelRW: ModelRW[M, Pot[(SolicitudPago, String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateSolicitudPago(item, event) =>
        if (item.folio == "") updated(Unavailable)
        else updated(Ready((item, event)),
          Effect.action(GetDetSolicitudPago(IdSolicitudPago(folio = item.folio, ejercicio = item.ejercicio)))
        )
    case GetSolicitudPago(id) => effectOnly(Effect(AjaxClient[Api].getSolicitudPago(id).call().map( rcd => UpdateSolicitudPago(rcd._1, rcd._2))))
    case GetSolicitudPagoByIdComparativo(id) => effectOnly(Effect(AjaxClient[Api].getSolicitudPagoByIdComparativo(id)
      .call().map( rcd => UpdateSolicitudPago(rcd._1, rcd._2))))
    case InsertSolicitudPago(item) => effectOnly(Effect(AjaxClient[Api].insertSolicitudPago(item).call().map(rcd => UpdateSolicitudPago(rcd._1, rcd._2))))
    case DeleteSolicitudPago(id) => effectOnly(Effect((AjaxClient[Api].deleteSolicitudPago(id).call().map{
      rcd =>
        if ( rcd > 0  )
          CleanDetSolicitudPago
        else
          UpdateSolicitudPago(SolicitudPago(), "Borrado")
    } )))
    case CleanSolicitudPago => updated(Empty, Effect.action(CleanDetSolicitudPago))
  }
}