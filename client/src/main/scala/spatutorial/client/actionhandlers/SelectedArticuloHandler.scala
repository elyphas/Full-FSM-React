package spatutorial.client.actionhandlers

//import autowire._
import spatutorial.shared.{ /*Api,*/ Articulo}
//import spatutorial.client.services.AjaxClient

import diode._
//import diode.data._
//import diode.util._
//import diode.react.ReactConnector

//import boopickle.Default._

//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class SetSelectedArticulo(artic: Articulo) extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class SelectedArticuloHandler[M](modelRW: ModelRW[M, Articulo]) extends ActionHandler(modelRW) {
  override def handle = {
    case SetSelectedArticulo(art) => updated(art)
  }
}