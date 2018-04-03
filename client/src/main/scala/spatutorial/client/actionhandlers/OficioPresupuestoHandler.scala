package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, OficioPresupuesto}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector
import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateOficioPresupuesto(item: OficioPresupuesto, event: String) extends Action

case class GetOficioPresupuesto(id: String) extends Action

case class SaveOficioPresupuesto(item: OficioPresupuesto) extends Action

case class InsertOficioPresupuesto(item: OficioPresupuesto) extends Action

case class DeleteOficioPresupuesto(id: String) extends Action

case class SearchOficioPresupuesto(str: String) extends Action

case object CleanOficioPresupuesto extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class OficioPresupuestoHandler[M](modelRW: ModelRW[M, Pot[(OficioPresupuesto, String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateOficioPresupuesto(item, event) =>
      if(item.id_comparativo == "")
        updated(Unavailable)
      else  updated(Ready((item, event)))
    //case CleanOficioPresupuesto =>  updated(Empty)
    //case SavePartida(item) => effectOnly(Effect(AjaxClient[Api].savePartida(item).call().map( p => UpdatePartida(p._1,p._2))))
    case InsertOficioPresupuesto(item) =>
      effectOnly(Effect(AjaxClient[Api].insertOficioPresupuesto(item).call().map(p => UpdateOficioPresupuesto(p._1, p._2))))
    case GetOficioPresupuesto(id) =>
      effectOnly(Effect(AjaxClient[Api].getOficioPresupuesto(id).call()
                .map( p => UpdateOficioPresupuesto( p._1, p._2))))
    ///case SearchPartida(str) => effectOnly(Effect(AjaxClient[Api].getArticulo(str).call().map(UpdateArticulo)))*/
  }
}