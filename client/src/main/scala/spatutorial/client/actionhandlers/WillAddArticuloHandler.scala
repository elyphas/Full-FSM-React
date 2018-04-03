package spatutorial.client.actionhandlers

//import autowire._
import spatutorial.shared.{ /*Api,*/ RenglonListBienes}
//import spatutorial.client.services.AjaxClient

import diode._
//import diode.data._
//import diode.util._
//import diode.react.ReactConnector

//import boopickle.Default._

//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class WillAddArticulo(artic: RenglonListBienes) extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class WillAddArticuloHandler[M](modelRW: ModelRW[M, RenglonListBienes]) extends ActionHandler(modelRW) {
  override def handle = {
    case WillAddArticulo(art) => updated(art)
    // make a local update and inform server
    /*case WillAddArticulo(art) =>
        updated(value.map(_.updated(item)), Effect(AjaxClient[Api].updateTodo(item).call().map(UpdateAllTodos)))
    }*/
  }
}