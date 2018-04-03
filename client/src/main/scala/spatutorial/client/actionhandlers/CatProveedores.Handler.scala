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

case class UpdateProveedores(items: Seq[Proveedor]) extends Action

case object GetProveedores extends Action

case class SearchProveedores(str: String) extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class CatProveedoresHandler[M](modelRW: ModelRW[M, Pot[Seq[Proveedor]]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateProveedores(proveedores) =>
      if (proveedores.isEmpty)
        updated(Unavailable)
      else
        updated(Ready(proveedores))
    /*case GetProveedores =>
      effectOnly(Effect(AjaxClient[Api].getAllProveedores().call().map(UpdateProveedores)))*/
    case SearchProveedores(str) => {
      effectOnly(Effect(AjaxClient[Api].searchProveedores(str).call().map(UpdateProveedores)))
    }
  }
}