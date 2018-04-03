package spatutorial.client.actionhandlers

import autowire._
import spatutorial.client.services.AjaxClient
import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector
//**************************
import boopickle.Default._
import spatutorial.shared.{Api, PedidoId, QryPedidoDatosGrales, DetallePedido}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateQryPedidoDatosGrales(item: (Seq[QryPedidoDatosGrales], Seq[DetallePedido], String) ) extends Action

case class GetQryPedidoDatGrales(pedido: PedidoId) extends Action

case object CleanQryPedidos extends Action

class QryPedidoDatosGralesHandler[M](modelRW: ModelRW[M, Pot[(Seq[QryPedidoDatosGrales], Seq[DetallePedido], String)]])  extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateQryPedidoDatosGrales(pedido) =>

      if (pedido._1.isEmpty)
        updated(Unavailable)
      else {
          val pedi = pedido._1.head
          updated(Ready(pedido),
            Effect.action(GetDescripcionCompra(pedi.id_comparativo))
            +
            Effect.action(GetCalendario(pedi.id_comparativo))
            +
            Effect.action(GetListBienes(pedi.id_comparativo))
            +
            Effect.action(GetPartidaAnexo(pedi.id_comparativo))
          )
      }
    case GetQryPedidoDatGrales(pedidoId) =>
      effectOnly(Effect(AjaxClient[Api].getQryPedidoDatosGrales(pedidoId.no_pedido, pedidoId.compra, pedidoId.ejercicio)
        .call().map(UpdateQryPedidoDatosGrales)
      ))
    case CleanQryPedidos => updated(Empty)
  }
}