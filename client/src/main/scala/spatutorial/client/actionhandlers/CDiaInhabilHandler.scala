package spatutorial.client.actionhandlers
import autowire._
import spatutorial.client.services.AjaxClient
import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector
//**************************
import boopickle.Default._
import spatutorial.shared.{Api, Dia_Inhabil}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateDiaInhabil(item: Seq[Dia_Inhabil], event: String ) extends Action

case class GetDiaInhabil(id: String) extends Action

case class InsertDiaInhabil(item: Dia_Inhabil) extends Action

class DiaInhabilHandler[M](modelRW: ModelRW[M, Pot[(Dia_Inhabil, String)]])  extends ActionHandler(modelRW) {

  override def handle = {
    case UpdateDiaInhabil(partidas, event) => updated(Ready((partidas.head, event)))
    /*case GetDiaInhabil(id) =>
      effectOnly(Effect(AjaxClient[Api].getPartidaAnexo(id).call()
        .map(r => UpdatePartidaAnexo(r._1,r._2))))*/
    case InsertDiaInhabil(item) => effectOnly(Effect(AjaxClient[Api].insertDiaInhabil(item).call()
      .map(r => UpdateDiaInhabil(r._1,r._2))))

  }
}