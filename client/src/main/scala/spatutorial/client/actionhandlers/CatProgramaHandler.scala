package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, Programa}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateAllProgramas(item: Seq[Programa]) extends Action

case object GetAllPrograma extends Action

case object CleanLstProgramas extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class CatProgramaHandler[M](modelRW: ModelRW[M, Pot[Seq[Programa]]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateAllProgramas(programas) =>
      if (programas.isEmpty)
        updated(Unavailable)
      else
        updated(Ready(programas))
    case GetAllPrograma => effectOnly(Effect(AjaxClient[Api].getAllPrograma().call().map(UpdateAllProgramas)))
    case CleanLstProgramas => updated(Empty)
  }
}