package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, IdSolicitudPago, DetSolicitudPago}
import spatutorial.client.services.AjaxClient
import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector
import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateDetSolicitudPago(item: Seq[DetSolicitudPago], event: String) extends Action

case class GetDetSolicitudPago(id: IdSolicitudPago) extends Action

case class GetDetSolicitudPagoByIdComparativo(id: String) extends Action

case class InsertDetSolicitudPago(item: DetSolicitudPago) extends Action

case class SaveDetSolicitudPago(item: DetSolicitudPago) extends Action

case class DeleteDetSolicitudPago(id: DetSolicitudPago) extends Action

case object CleanDetSolicitudPago extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class DetSolicitudPagoHandler[M](modelRW: ModelRW[M, Pot[(Seq[DetSolicitudPago], String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateDetSolicitudPago(item, event) =>
        if (item.isEmpty) updated(Unavailable) else updated(Ready((item, event)))
    case GetDetSolicitudPago(id) => effectOnly(Effect(AjaxClient[Api].getDetSolicitudPago(id)
            .call().map( rcd => UpdateDetSolicitudPago(rcd._1, rcd._2))))
    case InsertDetSolicitudPago(item) => effectOnly(Effect(AjaxClient[Api].insertDetSolicitudPago(item)
                  .call().map(rcd => UpdateDetSolicitudPago(rcd._1, rcd._2))))
    case SaveDetSolicitudPago(item) => effectOnly(Effect(AjaxClient[Api].saveDetSolicitudPago(item)
      .call().map(rcd => UpdateDetSolicitudPago(rcd._1, rcd._2))))

    case CleanDetSolicitudPago => updated(Empty)
    case DeleteDetSolicitudPago(item) =>
      effectOnly(Effect((AjaxClient[Api].deleteDetSolicitudPago(item).call().map( rcd => UpdateDetSolicitudPago(rcd._1, rcd._2)))))

    //case DeleteSolicitudPago(id) => effectOnly(Effect((AjaxClient[Api].deleteSolicitudPago(id).call().map(UpdateSolicitudPago))))
  }
}