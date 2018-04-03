package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, RenglonListBienes, Renglon/*, ListadoBienes*/}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

//case class UpdateListBienes(item: Seq[RenglonListBienes]) extends Action

//case class UpdateListBienes(item: ListadoBienes) extends Action
case class UpdateListBienes(item: Seq[RenglonListBienes]) extends Action

case class GetListBienes(id: String) extends Action

case class AddItemLstBienes(rengl: Renglon) extends Action

case class EditItemLstBienes(rengl: Renglon) extends Action

case class DeleteItemLstBienes(rengl: Renglon) extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class LstBienesHandler[M](modelRW: ModelRW[M, Pot[Seq[RenglonListBienes]]]) extends ActionHandler(modelRW) {
  override def handle = {
    case GetListBienes(id) =>
      effectOnly(Effect(AjaxClient[Api].getListBienes(id).call().map( UpdateListBienes)))
    case UpdateListBienes(lstbienes) =>
        val lastRenglon = lstbienes.length
        if (lstbienes.isEmpty) updated(Unavailable)
        else updated(Ready(lstbienes), Effect.action(SetLastRenglon(lastRenglon)))
    case AddItemLstBienes(rengl) =>
      effectOnly(Effect(AjaxClient[Api].addItemLstBienes(rengl).call().map(res => UpdateListBienes(res))))
    case EditItemLstBienes(rengl) =>
      effectOnly(Effect(AjaxClient[Api].editItemLstBienes(rengl).call().map(UpdateListBienes)))
    case DeleteItemLstBienes(rengl) =>
      effectOnly(Effect(AjaxClient[Api].deleteItemLstBienes(rengl).call().map(UpdateListBienes)))
  }
}