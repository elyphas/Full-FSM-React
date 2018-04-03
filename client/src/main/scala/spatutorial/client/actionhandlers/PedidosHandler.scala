package spatutorial.client.actionhandlers
import autowire._
import spatutorial.client.services.AjaxClient
import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector
//**************************
import boopickle.Default._
import spatutorial.shared.{Api, PedidoId, Pedido/*, DetallePedido*/}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdatePedido(item: Pedido, event: String) extends Action

case class GetPedido(pedido: PedidoId) extends Action

case class SavePedido(pedido: Pedido) extends Action

case class GetPedidoByID(id: String) extends Action

class PedidosHandler[M](modelRW: ModelRW[M, Pot[(Pedido, String)]])  extends ActionHandler(modelRW) {
  override def handle = {
    case UpdatePedido(pedido, event) =>
      if (pedido.no_pedido == "") updated(Unavailable)
      else updated(Ready((
        pedido, event)),
        Effect.action(GetProveedor(pedido.cve_provedor))
      )
    case GetPedido(pedidoId) =>
        effectOnly(Effect(AjaxClient[Api]
          .getPedido(pedidoId.no_pedido, pedidoId.compra, pedidoId.ejercicio).call().map(rcd => UpdatePedido(rcd._1, rcd._2))))
    case GetPedidoByID(id) =>
      effectOnly(Effect(AjaxClient[Api].getPedidoByID(id).call().map( rcd => UpdatePedido(rcd._1, rcd._2))))
  }
}