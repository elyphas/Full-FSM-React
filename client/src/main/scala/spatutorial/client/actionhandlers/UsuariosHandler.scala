package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, Usuarios}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateUsuarios(item: Seq[Usuarios]) extends Action

case class GetUsuarios(user: String) extends Action

case class Loggear(item: Usuarios) extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class UsuariosHandler[M](modelRW: ModelRW[M, Pot[Usuarios]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateUsuarios(usuario) =>
        if (usuario.isEmpty) updated(Unavailable)
        else updated(Ready(usuario.head))
    case GetUsuarios(user) =>
      effectOnly(Effect(AjaxClient[Api].getUsuario(user).call().map(UpdateUsuarios)))
    case Loggear(item) =>
      effectOnly(Effect(AjaxClient[Api].logear(item).call().map { rcd =>
        if( rcd._1.usuario == "") UpdateUsuarios(Seq(Usuarios())) else UpdateUsuarios(Seq(rcd._1))
      })) //arreglar esto del Seq()
  }
}