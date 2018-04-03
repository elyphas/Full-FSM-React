package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, Proveedor}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateProveedor(item: Seq[Proveedor]) extends Action

case class GetProveedor(rfc: String) extends Action

case class UpdateProveedor2(item: Proveedor) extends Action

case class AddNewProveedor(item: Proveedor) extends Action

case class DeleteProveedor(id: String) extends Action

case class UpdateDeletedProveedor(number: Int) extends Action

case object CleanProveedor extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class ProveedorHandler[M](modelRW: ModelRW[M, Pot[Proveedor]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateProveedor(proveedor) =>
      if (proveedor.isEmpty)  updated(Unavailable)
      else  updated(Ready(proveedor.head))
    case GetProveedor(rfc) =>
      effectOnly(Effect(AjaxClient[Api].getProveedor(rfc).call().map(UpdateProveedor)))
    case UpdateProveedor2(item) =>
      effectOnly(Effect(AjaxClient[Api].updateProveedor(item).call().map(UpdateProveedor)))
    case AddNewProveedor(item) =>
      effectOnly(Effect(AjaxClient[Api].addNewProveedor(item).call().map(UpdateProveedor)))
    case UpdateDeletedProveedor(number) =>
      if(number > 0) updated(Empty)
      else updated(value)
    case DeleteProveedor(id) =>
      effectOnly(Effect((AjaxClient[Api].deleteProveedor(id).call().map(UpdateDeletedProveedor))))
    case CleanProveedor =>
      updated(Empty)
  }
}