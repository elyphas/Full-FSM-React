package spatutorial.client.actionhandlers

import autowire._
import spatutorial.client.services.AjaxClient
import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector
//**************************
import boopickle.Default._
import spatutorial.shared.{Api, Pedido /*, PedidoId,  DetallePedido*/}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateDatosGralesPedido(item: Pedido, event: String) extends Action

case class GetDatosGralesPedidoByID(id: String) extends Action

class DatosGralesPedidoHandler[M](modelRW: ModelRW[M, Pot[(Pedido, String)]])  extends ActionHandler(modelRW) {

  override def handle = {
    case UpdateDatosGralesPedido(pedido, event) =>
      updated(Ready((pedido, event)))
    case GetDatosGralesPedidoByID(id) =>
      effectOnly(Effect(AjaxClient[Api].getPedidoByID(id)
        .call().map( rcd => UpdateDatosGralesPedido(rcd._1, rcd._2) )))
  }
}