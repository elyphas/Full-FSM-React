package spatutorial.client.actionhandlers
import autowire._
import spatutorial.client.services.AjaxClient
import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector
//**************************
import boopickle.Default._
import spatutorial.shared.{Api, PartidaAnexo}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdatePartidaAnexo(item: Seq[PartidaAnexo], event: String ) extends Action

case class GetPartidaAnexo(id: String) extends Action

class PartidaAnexoHandler[M](modelRW: ModelRW[M, Pot[(PartidaAnexo, String)]])  extends ActionHandler(modelRW) {

  override def handle = {
    case UpdatePartidaAnexo(partidas, event) =>
      if(partidas.isEmpty)
        updated(Unavailable)
      else {
        //val part = partidas.foldLeft("")( (a, b) => a + "; " + b)
        updated(Ready((partidas.head, event)))
      }
    case GetPartidaAnexo(id) =>
      effectOnly(Effect(AjaxClient[Api].getPartidaAnexo(id).call()
        .map(r => UpdatePartidaAnexo(r._1,r._2))))
  }
}