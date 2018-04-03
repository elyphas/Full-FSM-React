package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api/*, Calendario*/}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateTotalDias(item: Int) extends Action

case class GetTotalDias(fecha: String) extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class TotalDiasHandler[M](modelRW: ModelRW[M, Pot[Int]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateTotalDias(item) =>
      if(item == 0) updated(Unavailable)
      else updated(Ready(item))
    case GetTotalDias(fecha) =>
      effectOnly(Effect(AjaxClient[Api].calcularDias_aPartirDe(fecha).call().map(UpdateTotalDias)))
  }
}