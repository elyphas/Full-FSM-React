package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, Seguimiento}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateSeguimiento(item: Seq[Seguimiento], event: String) extends Action

case class GetSeguimiento(id: String) extends Action

case class GetBetweenDatesSeguimiento(start: String, end: String) extends Action

case object GetAllSeguimiento extends Action

case object CleanSeguimiento extends Action

class SeguimientoHandler[M](modelRW: ModelRW[M, Pot[(Seq[Seguimiento], String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateSeguimiento(item, event) =>
      if(item.isEmpty) updated(Unavailable)
      else updated(Ready((item, event)))
    case GetAllSeguimiento => effectOnly(Effect(AjaxClient[Api].getAllSeguimiento()
      .call().map( rcd => UpdateSeguimiento(rcd._1, rcd._2)  )))
    case GetBetweenDatesSeguimiento(start, end) =>
      effectOnly(Effect(AjaxClient[Api].getBetweenDatesSeguimiento(start, end)
        .call().map(rcd => UpdateSeguimiento(rcd._1, rcd._2))))
    case CleanSeguimiento =>  updated(Empty)
  }
}