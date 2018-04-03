package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, Partida}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdatePartidas(items: Seq[Partida]) extends Action

case object GetPartidas extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class CatPartidasHandler[M](modelRW: ModelRW[M, Pot[Seq[Partida]]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdatePartidas(partidas) =>
      if (partidas.isEmpty) updated(Unavailable)
      else updated(Ready(partidas))
    case GetPartidas =>
      effectOnly(Effect(AjaxClient[Api].getPartidas().call().map(UpdatePartidas)))

  }
}