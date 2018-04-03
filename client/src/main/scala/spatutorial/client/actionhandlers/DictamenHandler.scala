package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, RenglonDictamen}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateDictamen(items: Seq[RenglonDictamen], event: String) extends Action

case class GetDictamen(id: String) extends Action

case object CleanDictamen extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class DictamenHandler[M](modelRW: ModelRW[M, Pot[(Seq[RenglonDictamen], String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateDictamen(items, event) =>
      if(items.isEmpty)  updated(Unavailable)
      else  updated(Ready((items, event)))
    case GetDictamen(id) =>
      effectOnly(Effect(AjaxClient[Api].getDictamen(id).call().map( p => UpdateDictamen( p._1, p._2))))
  }
}