package spatutorial.client.actionhandlers

import autowire._
import spatutorial.shared.{Api, Proceso}
import spatutorial.client.services.AjaxClient

import diode._
import diode.data._
//import diode.util._
//import diode.react.ReactConnector

import boopickle.Default._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateProceso(item: Seq[Proceso], event: String) extends Action

case class UpdateProcesoOnly(item: Seq[Proceso], event: String) extends Action

case class UpdateProceso2(item: Seq[Proceso], event: String) extends Action

case class GetProceso(id: String) extends Action

case class GetProcesoOnly(id: String) extends Action

case class InsertProceso(proc: Proceso) extends Action

case class SaveProceso(proc: Proceso) extends Action

case class FinalizarProceso(id: String, reason: String) extends Action

//case class BuscarPedido( no: String, compra: String, ejercicio: Int) extends Action
//case class BuscarPedido(idPedido: PedidoId) extends Action

case class DeleteProceso(id: String) extends Action

case class DeletePedidos(id: String) extends Action

case object SetNewProceso extends Action

/** Handles actions related to todos
  * @param modelRW Reader/Writer to access the model  */
class DatosGralesHandler[M](modelRW: ModelRW[M, Pot[(Proceso, String)]]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateProceso(proceso, event) =>
      if (proceso.isEmpty)
        updated(Unavailable)
      else {

        val proces = proceso.head

        updated(Ready((proces, event)),
          Effect.action(GetDescripcionCompra(proces.id_comparativo)) +
            Effect.action(GetCalendario(proces.id_comparativo)) +
            Effect.action(GetListBienes(proces.id_comparativo)) +
            Effect.action(GetPartidaAnexo(proces.id_comparativo)) +
            Effect.action(GetPrograma(proces.programa)) +
            Effect.action(GetLicitantes(proces.id_comparativo)) + //Deberia esta aqui la llamadas a todos los datos.
            Effect.action(GetPrograma(proces.programa)) +
            Effect.action(GetDictamen(proces.id_comparativo)) +
            Effect.action(GetTotalCotizacion(proces.id_comparativo)) +
            Effect.action(GetPedidoByID(proces.id_comparativo)) +
            Effect.action(GetOficina(proces.cveOficina)) +
            Effect.action(GetOficioPresupuesto(proces.id_comparativo))
        )
    }
    case UpdateProcesoOnly(proceso, event) =>
      if (proceso.isEmpty) updated(Unavailable)
      else updated(Ready((proceso.head, event)))
    case GetProceso(id) =>
      effectOnly(Effect(AjaxClient[Api].getProceso(id).call().map(r => UpdateProceso(r._1, r._2))))
    case GetProcesoOnly(id) =>
      effectOnly(Effect(AjaxClient[Api].getProceso(id).call().map(r => UpdateProcesoOnly(r._1, r._2))))
    case DeletePedidos(id) =>
      effectOnly(Effect(AjaxClient[Api].deletePedidos(id).call().map(r => NoAction)))
    case InsertProceso(proceso) =>
      effectOnly(Effect(AjaxClient[Api].insertProceso(proceso).call().map(r => UpdateProceso(r._1, r._2))))
    case SaveProceso(proceso) =>
      effectOnly(Effect(AjaxClient[Api].saveProceso(proceso).call().map( r => UpdateProceso(r._1, r._2) )))
    case DeleteProceso(id) =>
      effectOnly(Effect(AjaxClient[Api].deleteProceso(id).call().map{r => if(r._1 > 0) SetNewProceso else NoAction}))
    case SetNewProceso => updated(Empty)
  }
}