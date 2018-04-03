package services

//import java.util.{Date, UUID}

import scala.language.postfixOps

import spatutorial.shared._

import scala.concurrent.Future
//import scala.util.{Failure, Success}

//import scala.concurrent.ExecutionContext.Implicits.global
//import java.util.{Date, UUID}

import postg.{CAprobar/*, CDetPedido*/}

import postg.{CArticulo, COficina, CProcesos}
import postg.{CPartida, CPedido, CPresentacion}
import postg.{CPrograma, CQryPedidoDatosGrales, CQryDetallePedido, CDetPedido, CListBienes}
import postg.{CUsuarios, CProveedores, CRenglon, CCalendario, CPartidaAnexo, CDiaInhabil}
import postg.{CDescripcionCompra, CLicitantes, CProvLicitante, CRenglonDictamen}
import postg.{CTotalCotizacion, COficioPresupuesto, CSeguimiento, CSolicitudPago, CDetSolicitudPago, CRptSolicitudPago}
import postg.{CPrecioReferencia, CAprobar}

import javax.inject.Inject

//import akka.stream.javadsl.Sink
//import play.api.inject.Injector

class ApiService @Inject()(
                            protected val procesos: CProcesos,
                            protected val oficina: COficina,
                            protected val partida: CPartida,
                            protected val presentacion: CPresentacion,
                            protected val articulo: CArticulo,
                            protected val pedido: CPedido,
                            protected val qrypedidodatosgrales: CQryPedidoDatosGrales,
                            protected val qrydetpedido: CQryDetallePedido,
                            protected val detpedido: CDetPedido,
                            protected val programa: CPrograma,
                            protected val lstbienes: CListBienes,
                            protected val usuario: CUsuarios,
                            protected val proveedor: CProveedores,
                            protected val renglon: CRenglon,
                            protected val aprobar: CAprobar,
                            protected val calendario: CCalendario,
                            protected val partidaAnexo: CPartidaAnexo,
                            protected val diaInhabil: CDiaInhabil,
                            protected val descripcionCompra: CDescripcionCompra,
                            protected val licitantes: CLicitantes,
                            protected val provLicitante: CProvLicitante,
                            protected val renglonDictamen: CRenglonDictamen,
                            protected val totalCotizacion: CTotalCotizacion,
                            protected val oficioPresupuesto: COficioPresupuesto,
                            protected val seguimiento: CSeguimiento,
                            protected val solicitudPago: CSolicitudPago,
                            protected val detSolicitudPago: CDetSolicitudPago,
                            protected val rptSolicitudPago: CRptSolicitudPago,
                            protected val precioReferencia: CPrecioReferencia
                          ) extends Api {

  ///*************************  Enviar al almacén    ****************************************************
  //override def enviarAlmacen(id: EnviarAlmacen(): Future[Boolean] = aprobar.aprobar(pedido)
  ///****************************************************************************************************

  //*********************  Enviar Almacen       *********************
  override def enviarAlmacen(idPedido: EnviarAlmacen): Future[Boolean] = aprobar.aprobar(idPedido)
  //******************************************

  ///*************************  Oficinas    ***************************
  override def Oficinas(): Future[Seq[Oficina]] = oficina.ListAll
  override def getOficina(id: String): Future[(Oficina, String)] = oficina.ById(id)
  override def Descripcion(clave: String): Future[Seq[String]] = oficina.getDescripcion(clave)

  override def getAllOficinas(): Future[Seq[Oficina]] = oficina.ListAll
  ///******************************************************************

  ///*************************  Oficio Presupuesto    *****************
  override def getOficioPresupuesto(id: String): Future[(OficioPresupuesto, String)] = oficioPresupuesto.ById(id)
  override def insertOficioPresupuesto(item: OficioPresupuesto): Future[(OficioPresupuesto, String)] = oficioPresupuesto.insert(item)
  ///******************************************************************

  override def getPresentaciones(): Future[Seq[Presentacion]] = presentacion.ListAll

  //********************QryPedidos
  override def getQryPedidoDatosGrales(no_pedido: String, compra: String, ejercicio: Int):
        Future[(Seq[QryPedidoDatosGrales], Seq[DetallePedido], String)] =
            qrypedidodatosgrales
                .getPedido(PedidoId(no_pedido = no_pedido, compra = compra, ejercicio = ejercicio))
  //***************************Fin QryPedidos


  //************************* Usuarios   **********************************************
  override def getUsuario(user: String): Future[Seq[Usuarios]] = usuario.ById(user)
  override def logear(item: Usuarios): Future[(Usuarios, String)] = usuario.logear(item)
  //***********************************************************************************


  //************************* Proveedores   **********************************************
  override def getProveedor(rfc: String): Future[Seq[Proveedor]] = proveedor.ById(rfc)
  override def updateProveedor(item: Proveedor): Future[Seq[Proveedor]] = proveedor.updateProveedor(item)
  override def addNewProveedor(item: Proveedor): Future[Seq[Proveedor]] = proveedor.addNew(item)
  override def deleteProveedor(Id: String) : Future[Int] = proveedor.deleteProveedor(Id)
  override def searchProveedores(str: String) : Future[Seq[Proveedor]] = proveedor.searchByDescripcion(str)
  ///************************ Fin de Proveedores  ****************************************


  ///***********************************  Pedidos     ************************************
  override def getPedido(no_pedido: String, compra: String, ejercicio: Int):
    Future[(Pedido, String)] = pedido.getPedido(PedidoId(no_pedido = no_pedido, compra = compra, ejercicio = ejercicio))

  override def getPedidoByID(id: String): Future[(Pedido, String)] = pedido.ById(id)

  override def getQryDetPedido(idPedido: PedidoId): Future[Seq[DetallePedido]] = qrydetpedido.ById(idPedido)

  override def getDetPedido(idPedido: PedidoId): Future[(Seq[DetPedido], String)] = detpedido.ById(idPedido)

  override def insertPedido(ped: Pedido): Future[Seq[Pedido]] = pedido.insertPed(ped)

  override def savePedido(ped: Pedido): Future[(Pedido, /*Seq[DetallePedido],*/ String)] = pedido.updatePedido(ped)

  override def cancelarPedido(idPedido: PedidoId, reason: String): Future[Option[Pedido]] =
    pedido.CancelarPedido(idPedido, reason)

  //**************************  Articulos   ****************//
  override def getArticulo(cve: String): Future[Seq[Articulo]] = articulo.ById(cve)
  override def getAllArticulos(): Future[Seq[Articulo]] = articulo.ListAll
  override def saveArtic(art: Articulo): Future[Seq[Articulo]] = articulo.save(art)
  override def insertArtic(art: Articulo): Future[Seq[Articulo]] = articulo.insert(art)
  override def createCve(partida: String): String = articulo.createCve(partida)
  override def searchArticulo(str: String): Future[Seq[Articulo]] = articulo.searchDescripcion(str)
  ///********************************************************

  ///*********    Programas     ******************************
  override def getPrograma(progr: String): Future[(Programa, String)] = programa.ById(progr)
  override def getAllPrograma(): Future[Seq[Programa]] = programa.ListAll
  override def savePrograma(progr: Programa): Future[Programa] = programa.save(progr)
  override def editPrograma(progr: Programa): Future[Seq[Programa]] = programa.edit(progr)
  override def insertPrograma(item: Programa): Future[Programa] = programa.insert(item)
  //***********************************************************


  ///*********    Procesos     ********************************
  override def getLastId(ejercicio: Int): Future[Seq[LastID]] = procesos.getLastId(ejercicio)
  //override  def SearchProceso(id: String): Future[Seq[Proceso]] = procesos.ById(id)
  override def getProceso(id: String): Future[(Seq[Proceso], String)] = procesos.ById(id)
  override def insertProceso(item: Proceso): Future[(Seq[Proceso], String)] = procesos.insert(item)
  override def saveProceso(item: Proceso): Future[(Seq[Proceso], String)] = procesos.save(item)
  override def deleteProceso(id: String): Future[(Int, String)] = procesos.delete(id)
  override def deletePedidos(id: String): Future[Int] = procesos.DelPedidos(id)
  //***********************************************************


  ///*********    ListBienes     ******************************
  override def getListBienes(id: String): Future[Seq[RenglonListBienes]] = lstbienes.ById(id)
  override def deleteItemLstBienes(rengl: Renglon): Future[Seq[RenglonListBienes]] = renglon.deleteRenglon(rengl)
  override def addItemLstBienes(item: Renglon): Future[Seq[RenglonListBienes]] = renglon.addItem(item)
  override def editItemLstBienes(item: Renglon): Future[Seq[RenglonListBienes]] = renglon.editItem(item)
  //***********************************************************

  ///*********    Partida     ********************************
  //override  def SearchPartida(id: String): Future[Seq[Partida]] = Partidas.ById(id)
  override def getPartidas(): Future[Seq[Partida]] = partida ListAll
  override def getPartida(id: String): Future[(Seq[Partida], String)] = partida ById(id)
  override def insertPartida(item: Partida): Future[(Seq[Partida], String)] = partida insert(item)
  override def savePartida(item: Partida): Future[(Seq[Partida], String)] = partida save(item)

  /*override def savePartida(item: Partida): Future[(Seq[Partida], String)] = partida savePartida(item)
  override def deletePartida(id: String): Future[(Int, String)] = partida deletePartida(id)*/
  //***********************************************************


  ///*********    Calendario   ********************************
  override def getCalendario(id: String): Future[(Seq[Calendario], String)] = calendario ById(id)
  override def insertCalendario(item: Calendario): Future[(Seq[Calendario], String)] = calendario insert(item)
  override def saveCalendario(item: Calendario): Future[(Seq[Calendario], String)] = calendario save(item)
  override def calcularDias_aPartirDe(fecha: String): Future[Int] = calendario.calcularDias_aPartirDe(fecha)
  //***********************************************************

  ///*********    Partidas del Anexo     **************************
  def getPartidaAnexo(id: String): Future[(Seq[PartidaAnexo], String)] = partidaAnexo.ById(id)
  def getDescripcionCompra(id: String): Future[(Seq[DescripcionCompra], String)] = descripcionCompra ById(id)
    //***********************************************************

  ///*********    Dias Inhabiles     **************************
  def insertDiaInhabil(item: Dia_Inhabil): Future[(Seq[Dia_Inhabil], String)] = diaInhabil.insert(item)
  //***********************************************************

  //************************** Licitantes     ****************************************
  override def getLicitantes(id: String): Future[(Seq[Licitante], String)] = licitantes.ById(id)
  override def insertLicitante(item: ProvLicitante): Future[(Seq[Licitante], String)] = provLicitante.insert(item)
  override def deleteLicitante(item: ProvLicitante): Future[(Seq[Licitante], String)] = provLicitante.delete(item)
  //**********************************************************************************

  //************************** Dictamen    *******************************************
  override def getDictamen(id: String): Future[(Seq[RenglonDictamen], String)] = renglonDictamen.ById(id)
  //**********************************************************************************

  //************************** TotalCotizacion    *******************************************
  override def getTotalCotizacion(id: String): Future[(Seq[TotalCotizacion], String)] = totalCotizacion.ById(id)
  //**********************************************************************************


  //************************** Seguimiento *******************************************
  override def getAllSeguimiento(): Future[(Seq[Seguimiento], String)] = seguimiento.getAll

  override def getBetweenDatesSeguimiento(start: String, end: String): Future[(Seq[Seguimiento], String)] = seguimiento.getBetweenDate(start, end)
  //**********************************************************************************

  //************************** Solicitud Pago *******************************************
  override def insertSolicitudPago(item: SolicitudPago): Future[(SolicitudPago, String)] = solicitudPago.insert(item)
  override def deleteSolicitudPago(id: IdSolicitudPago): Future[Int] = solicitudPago.delete(id)
  override def getSolicitudPago(id: IdSolicitudPago): Future[(SolicitudPago, String)] = solicitudPago.ById(id)
  override def getSolicitudPagoByIdComparativo(id: String): Future[(SolicitudPago, String)] = solicitudPago.ByIdComparativo(id)
  //**********************************************************************************


  //************************** Detalles Solicitud Pago *******************************************
  override def insertDetSolicitudPago(item: DetSolicitudPago): Future[(Seq[DetSolicitudPago], String)] = detSolicitudPago.insert(item)
  override def saveDetSolicitudPago(item: DetSolicitudPago): Future[(Seq[DetSolicitudPago], String)] = detSolicitudPago.update(item)
  override def deleteDetSolicitudPago(item: DetSolicitudPago): Future[(Seq[DetSolicitudPago], String)] = detSolicitudPago.delete(item)
  override def getDetSolicitudPago(id: IdSolicitudPago): Future[(Seq[DetSolicitudPago], String)] = detSolicitudPago.ById(id)
  //override def getDetSolicitudPagoByIdComparativo(id: String): Future[(Seq[DetSolicitudPago], String)] = detSolicitudPago
  //**********************************************************************************

  //************************** Reportes Solicitud Pagos *******************************************
  //def getAllRptSolicitudPago(): Future[(Seq[Seguimiento], String)]
  override def getBetweenDatesRptSolicitudPago(start: String, end: String): Future[(Seq[RptSolicitudPago], String)] =
            rptSolicitudPago.getBetweenDate(start, end)
  override def printSolicitudPagoByFolio(folio: String, ejercicio: Int): Future[(RptSolicitudPago, String)] = rptSolicitudPago.ById(folio, ejercicio)
  //**********************************************************************************

  //************************** Precio Referencia *******************************************
  override def getPrecioReferencia(key: String): Future[(Seq[PrecioReferencia], String)] = precioReferencia.ById(key)
  //****************************************************************************************


}
