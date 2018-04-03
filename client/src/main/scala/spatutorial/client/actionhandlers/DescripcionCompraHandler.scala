package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, DescripcionCompra}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateDescripcionCompra(item: Seq[DescripcionCompra], event: String) extends Action

case class GetDescripcionCompra(id: String) extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class DescripcionCompraHandler[M](modelRW: ModelRW[M, Pot[(DescripcionCompra, String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateDescripcionCompra(item, event) =>
      if(item.isEmpty)
        updated(Unavailable)
      else
        updated(Ready((item.head, event)))
    case GetDescripcionCompra(id) =>
      effectOnly(Effect(AjaxClient[Api].getDescripcionCompra(id).call()
        .map( r => UpdateDescripcionCompra(r._1, r._2))))
  }
}