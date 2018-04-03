package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, PedidoId, DetallePedido}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateQryDetPedido(item: Seq[DetallePedido]) extends Action

case class GetQryDetPedido(idPedido: PedidoId) extends Action

//case class SavePedido(ped: Pedido) extends Action

//case class CancelPedido(idPedido: PedidoId, reason: String) extends Action

//case class BuscarPedido(idPedido: PedidoId) extends Action

//case class DeletePedido( no: String, compra: String, ejercicio: Int) extends Action

//case object SetNewPedido extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class QryDetPedidosHandler[M](modelRW: ModelRW[M, Pot[Seq[DetallePedido]]]) extends ActionHandler(modelRW) {
  override def handle = {
    case GetQryDetPedido(idPedido)=>
      effectOnly(Effect(AjaxClient[Api].getQryDetPedido(idPedido).call().map(UpdateQryDetPedido)))
    case UpdateQryDetPedido(detpedido) => {
      if (detpedido.isEmpty)
        updated(Unavailable)
      else
        updated(Ready(detpedido))
    }
  }
}