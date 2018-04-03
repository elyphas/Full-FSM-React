package spatutorial.client.actionhandlers

//import autowire._
//import spatutorial.client.services.AjaxClient
import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector
//**************************
//import boopickle.Default._
import spatutorial.shared.{ /*Api,*/ PedidoId, /*Pedido,*/ DetallePedido}
//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateDetPedido(items: Seq[DetallePedido], event: String) extends Action

case class GetDetPedido(pedido: PedidoId) extends Action

//case class SaveDetPedido(pedido: Pedido) extends Action

//case class GetPedidoByID(id: String) extends Action

class DetPedidosHandler[M](modelRW: ModelRW[M, Pot[(Seq[DetallePedido], String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateDetPedido(detpedido, event) =>
      if (detpedido.isEmpty) updated(Unavailable)
      else updated(Ready((detpedido, event)))
    /*case GetDetPedido(idPedido)=>
      effectOnly(Effect(AjaxClient[Api].getDetPedido(idPedido)
        .call().map( rcd => UpdateDetPedido(rcd._1, rcd._2))))*/
  }
}