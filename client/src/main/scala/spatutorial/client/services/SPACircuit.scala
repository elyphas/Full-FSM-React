package spatutorial.client.services

//import autowire._
import diode._
import diode.data._
//import diode.util._
import diode.react.ReactConnector
import spatutorial.shared._
//import boopickle.Default._
import spatutorial.client.actionhandlers._

//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

// The base model of our application
case class RootModel(
                      articulo: Pot[Articulo],
                      partidas: Pot[Seq[Partida]],
                      programa: Pot[Programa],
                      catProgramas: Pot[Seq[Programa]],
                      catOficinas: Pot[Seq[Oficina]],
                      proceso: Pot[(Proceso, String)],
                      //detpedidos: Pot[Seq[DetPedido]],
                      qrydetpedidos: Pot[Seq[DetallePedido]],
                      //lstbienes: Pot[ListadoBienes],
                      lstbienes: Pot[Seq[RenglonListBienes]],
                      catArticulos: Pot[Seq[Articulo]],
                      //pedido: Pot[(Seq[Pedido], Seq[DetallePedido], String)],
                      pedido: Pot[(Pedido, String)],
                      detPedido: Pot[(Seq[DetallePedido], String)],
                      qrypedidodatosgrales: Pot[(Seq[QryPedidoDatosGrales], Seq[DetallePedido], String)],
                      usuarios: Pot[Usuarios],
                      proveedor: Pot[Proveedor],
                      activeID: String,
                      selectedArticulo: Articulo,
                      willAddArticulo: RenglonListBienes,
                      showFormRenglonBienes: Boolean,
                      datosGralesRequisicion: Pot[DatosGralesRequisicion],
                      lastID: Pot[LastID],
                      lastRenglon: Int,
                      partida: Pot[(Partida, String)],
                      calendario: Pot[(Calendario, String)],
                      partidaAnexo: Pot[(PartidaAnexo, String)],
                      dia_Inhabil: Pot[(Dia_Inhabil,String)],
                      totalDias: Pot[Int],
                      descripcionCompra: Pot[(DescripcionCompra, String)],
                      licitantes: Pot[(Seq[Licitante], String)],
                      proveedores: Pot[Seq[Proveedor]],
                      dictamen: Pot[(Seq[RenglonDictamen], String)],
                      totalCotizacion: Pot[(Seq[TotalCotizacion], String)],
                      oficina: Pot[(Oficina, String)],
                      oficioPresupuesto: Pot[(OficioPresupuesto, String)],
                      seguimiento: Pot[(Seq[Seguimiento], String)],
                      solicitudPago: Pot[(SolicitudPago, String)],
                      detSolicitudPago: Pot[(Seq[DetSolicitudPago], String)],
                      rptSolicitudPago: Pot[(Seq[RptSolicitudPago], String)],
                      printSolicitudPago: Pot[(RptSolicitudPago, String)],
                      precioReferencia: Pot[(PrecioReferencia, String)],
                      enviarAlmacen: Pot[EnviarAlmacen]
                    )

// Application circuit
object SPACircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  // initial application model
  override protected def initialModel = RootModel(  Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty,
                                                    Empty, Empty, Empty, Empty, Empty, "", Articulo(), RenglonListBienes(),
                                                    false, Empty,Empty,0, Empty, Empty, Empty, Empty, Empty, Empty,
                                                    Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty,
                                                    Empty, Empty
                                                )
  // combine all handlers into one
  override protected val actionHandler = composeHandlers(
    new CatPartidasHandler(zoomRW(_.partidas)((m, v) => m.copy(partidas = v))),
    new ProgramaHandler(zoomRW(_.programa)((m, v) => m.copy(programa = v))),
    new CatProgramaHandler(zoomRW(_.catProgramas)((m, v) => m.copy(catProgramas = v))),

    new CatOficinasHandler(zoomRW(_.catOficinas)((m, v) => m.copy(catOficinas = v))),

    new DatosGralesHandler(zoomRW(_.proceso)((m, v) => m.copy(proceso = v))),
    new CalendarioHandler(zoomRW(_.calendario)((m, v) => m.copy(calendario = v))),

    new QryDetPedidosHandler(zoomRW(_.qrydetpedidos)((m, v) => m.copy(qrydetpedidos = v))),

    new CatArticulosHandler(zoomRW(_.catArticulos)((m, v) => m.copy(catArticulos = v))),
    new PedidosHandler(zoomRW(_.pedido)((m, v) => m.copy(pedido = v))),

    new DetPedidosHandler(zoomRW(_.detPedido)((m, v) => m.copy(detPedido = v))),

    new QryPedidoDatosGralesHandler(zoomRW(_.qrypedidodatosgrales)((m, v) => m.copy(qrypedidodatosgrales = v))),

    new UsuariosHandler(zoomRW(_.usuarios)((m, v) => m.copy(usuarios = v))),
    new ProveedorHandler(zoomRW(_.proveedor)((m, v) => m.copy(proveedor = v))),

    new LstBienesHandler(zoomRW(_.lstbienes)((m, v) => m.copy(lstbienes = v))),
    new ArticulosHandler(zoomRW(_.articulo)((m, v) => m.copy(articulo = v))),
    new SelectedArticuloHandler(zoomRW(_.selectedArticulo)((m, v) => m.copy(selectedArticulo = v))),
    new WillAddArticuloHandler(zoomRW(_.willAddArticulo)((m, v) => m.copy(willAddArticulo = v))),
    new ActiveIDHandler(zoomRW(_.activeID)((m, v) => m.copy(activeID = v))),
    new ShowFormRenglonBienes(zoomRW(_.showFormRenglonBienes)((m, v) => m.copy(showFormRenglonBienes = v))),
    new ActiveIDHandler(zoomRW(_.activeID)((m, v) => m.copy(activeID = v))),
    new DatosGralesRequisicionHandler(zoomRW(_.datosGralesRequisicion)((m, v) => m.copy(datosGralesRequisicion = v))),
    new LastRenglonHandler(zoomRW(_.lastRenglon)((m, v) => m.copy(lastRenglon = v))),
    new IDProcesoHandler(zoomRW(_.lastID)((m, v) => m.copy(lastID = v))),
    new PartidaHandler(zoomRW(_.partida)((m, v) => m.copy(partida = v))),
    new PartidaAnexoHandler(zoomRW(_.partidaAnexo)((m, v) => m.copy(partidaAnexo = v))),
    new DiaInhabilHandler(zoomRW(_.dia_Inhabil)((m, v) => m.copy(dia_Inhabil = v))),
    new TotalDiasHandler(zoomRW(_.totalDias)((m, v) => m.copy(totalDias = v))),
    new DescripcionCompraHandler(zoomRW(_.descripcionCompra)((m, v) => m.copy(descripcionCompra = v))),
    new LicitantesHandler(zoomRW(_.licitantes)((m, v) => m.copy(licitantes = v))),
    new CatProveedoresHandler(zoomRW(_.proveedores)((m, v) => m.copy(proveedores = v))),
    new DictamenHandler(zoomRW(_.dictamen)((m, v) => m.copy(dictamen = v))),
    new TotalCotizacionHandler(zoomRW(_.totalCotizacion)((m, v) => m.copy(totalCotizacion = v))),
    new OficinaHandler(zoomRW(_.oficina)((m, v) => m.copy(oficina = v))),
    new OficioPresupuestoHandler(zoomRW(_.oficioPresupuesto)((m, v) => m.copy(oficioPresupuesto = v))),
    new SeguimientoHandler(zoomRW(_.seguimiento)((m, v) => m.copy(seguimiento = v))),
    new SolicitudPagoHandler(zoomRW(_.solicitudPago)((m, v) => m.copy(solicitudPago = v))),
    new DetSolicitudPagoHandler(zoomRW(_.detSolicitudPago)((m, v) => m.copy(detSolicitudPago = v))),
    new RptSolicPagoHandler(zoomRW(_.rptSolicitudPago)((m, v) => m.copy(rptSolicitudPago = v))),
    new PrintSolicitudPagoHandler(zoomRW(_.printSolicitudPago)((m, v) => m.copy(printSolicitudPago = v))),
    new PrecioReferenciaHandler(zoomRW(_.precioReferencia)((m, v) => m.copy(precioReferencia = v))),

    new EnviarAlmacenHandler(zoomRW(_.enviarAlmacen)((m, v) => m.copy(enviarAlmacen = v)))

  )
}