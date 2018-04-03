package spatutorial.client.actionhandlers

import autowire._
import spatutorial.client.services.AjaxClient
import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector
//**************************
import boopickle.Default._
import spatutorial.shared.{Api, EnviarAlmacen}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateEnviarAlmacen(item: Boolean) extends Action

case class SendEnviarAlmacen(idPedido: EnviarAlmacen) extends Action

class EnviarAlmacenHandler[M](modelRW: ModelRW[M, Pot[EnviarAlmacen]])  extends ActionHandler(modelRW) {

  override def handle = {
    case UpdateEnviarAlmacen(resultado) =>
      updated(Ready(EnviarAlmacen(no_pedido = "0013", compra = "PEDI", ejercicio = 2017, fechaAprobado = "01/01/2017")))

    case SendEnviarAlmacen(idPedido) => {
      effectOnly(Effect(AjaxClient[Api].enviarAlmacen(idPedido).call().map(UpdateEnviarAlmacen)))
    }
  }

}