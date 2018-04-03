package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, DatosGralesRequisicion}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateDatosGralesRequisicion(item: Seq[DatosGralesRequisicion]) extends Action

case class GetDatosGrales(CveDepto: String) extends Action

case class SaveDatosGrales(item: DatosGralesRequisicion) extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class DatosGralesRequisicionHandler[M](modelRW: ModelRW[M, Pot[DatosGralesRequisicion]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateDatosGralesRequisicion(datos) =>
      if (datos.isEmpty)
        updated(Unavailable)
      else
        updated(Ready(datos.head))
    case GetDatosGrales(str) =>
      effectOnly(Effect(AjaxClient[Api].getArticulo(str).call().map(UpdateArticulo)))
  }
}