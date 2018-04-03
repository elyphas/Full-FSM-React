package spatutorial.shared

import scala.concurrent.Future

trait Api {


  ///*************************  Enviar al almacén    ***************************
  //def enviarAlmacen(id: EnviarAlmacen): Future[Boolean]
  ///***************************************************************************

  //*********************  Enviar Almacen       *********************
  def enviarAlmacen(idPedido: EnviarAlmacen): Future[Boolean]
  //******************************************

  ///*************************  Oficinas    ***************************
  def Oficinas(): Future[Seq[Oficina]]
  def getOficina(id: String): Future[(Oficina, String)]
  def getAllOficinas(): Future[Seq[Oficina]]
  ///******************************************************************


  ///*************************  Oficio Presupuesto    *****************
  def getOficioPresupuesto(id: String): Future[(OficioPresupuesto, String)]
  def insertOficioPresupuesto(item: OficioPresupuesto): Future[(OficioPresupuesto, String)]
  ///******************************************************************

  def Descripcion(clave: String): Future[Seq[String]]

  ///*************************  Partidas    ***************************
  def insertPartida(item: Partida): Future[(Seq[Partida], String)]
  def getPartidas(): Future[Seq[Partida]]
  ///******************************************************************

  def getPresentaciones(): Future[Seq[Presentacion]]

  //*************************   Pedidos       *******************************************************
  def getQryPedidoDatosGrales(no_pedido: String, compra: String, ejercicio: Int):
          Future[(Seq[QryPedidoDatosGrales], Seq[DetallePedido], String)]

  def getPedido(no: String, compra: String, ejercicio: Int): Future[(Pedido, String)]

  def getPedidoByID(id: String): Future[(Pedido, String)]

  def getQryDetPedido(idPedido: PedidoId): Future[Seq[DetallePedido]]

  def getDetPedido(idPedido: PedidoId): Future[(Seq[DetPedido], String)]

  def savePedido( ped: Pedido ): Future[(Pedido, /*Seq[DetallePedido],*/ String)]

  def insertPedido(ped: Pedido): Future[Seq[Pedido]]
  def deletePedidos(id: String): Future[Int]
  //************************************************************************************************

  //*************************   Usuarios       ******************************
  def getUsuario(user: String): Future[Seq[Usuarios]]
  def logear(item: Usuarios): Future[(Usuarios, String)]
  //****************************************************************************

  //*************************   Proveedores       ******************************
  def getProveedor(rfc: String): Future[Seq[Proveedor]]
  def updateProveedor(item: Proveedor): Future[Seq[Proveedor]]
  def addNewProveedor(item: Proveedor): Future[Seq[Proveedor]]
  def deleteProveedor(Id: String) : Future[Int]
  def searchProveedores(str: String) : Future[Seq[Proveedor]]
  ///***************************************************************************

  /// ********************** Articulos **********************
  def getArticulo(cve: String): Future[Seq[Articulo]]
  def getAllArticulos(): Future[Seq[Articulo]]
  def saveArtic(art: Articulo): Future[Seq[Articulo]]
  def insertArtic(art: Articulo): Future[Seq[Articulo]]
  def createCve(part: String): String
  def cancelarPedido(idPedido: PedidoId, reason: String): Future[Option[Pedido]]
  def searchArticulo(str: String): Future[Seq[Articulo]]
  ///*********************************************************

  ///*********    Programas     ******************************

  def getPrograma(programa: String): Future[(Programa, String)]
  def getAllPrograma(): Future[Seq[Programa]]
  def savePrograma(programa: Programa): Future[Programa]
  def editPrograma(programa: Programa): Future[Seq[Programa]]
  def insertPrograma(item: Programa): Future[Programa]

  ///*********    Procesos     ******************************
  def getLastId(ejercicio: Int): Future[Seq[LastID]]

  //def SearchProceso(id: String): Future[Seq[Proceso]]
  def getProceso(id: String): Future[(Seq[Proceso], String)]
  def deleteProceso(id: String): Future[(Int, String)]

  def insertProceso(item: Proceso): Future[(Seq[Proceso], String)]
  def saveProceso(item: Proceso): Future[(Seq[Proceso], String)]
  //**********************************************************


  ///*********    ListBienes     ******************************
  def getListBienes(id: String): Future[Seq[RenglonListBienes]]
  def deleteItemLstBienes(rengl: Renglon): Future[Seq[RenglonListBienes]]
  def addItemLstBienes(item: Renglon): Future[Seq[RenglonListBienes]]
  def editItemLstBienes(item: Renglon): Future[Seq[RenglonListBienes]]
  //***********************************************************


  ///*********    Partida     ********************************
  def getPartida(id: String): Future[(Seq[Partida], String)]
  def savePartida(item: Partida): Future[(Seq[Partida], String)]
  //***********************************************************

  ///*********    Calendario     ********************************
  def getCalendario(id: String): Future[(Seq[Calendario], String)]
  def insertCalendario(item: Calendario): Future[(Seq[Calendario], String)]
  def saveCalendario(item: Calendario): Future[(Seq[Calendario], String)]
  def calcularDias_aPartirDe(fecha: String): Future[Int]
  //***********************************************************

  ///*********    Partidas del Anexo     *********************************************
  def getPartidaAnexo(id: String): Future[(Seq[PartidaAnexo], String)]
  def getDescripcionCompra(id: String): Future[(Seq[DescripcionCompra], String)]
  //**********************************************************************************

  ///*********    Dias Inhabiles     *************************************************
  def insertDiaInhabil(fecha: Dia_Inhabil): Future[(Seq[Dia_Inhabil], String)]
  //**********************************************************************************

  //************************** Licitantes     ****************************************
  def getLicitantes(id: String): Future[(Seq[Licitante], String)]
  def insertLicitante(item: ProvLicitante): Future[(Seq[Licitante], String)]
  def deleteLicitante(item: ProvLicitante): Future[(Seq[Licitante], String)]
  //**********************************************************************************

  //************************** Dictamen    *******************************************
  def getDictamen(id: String): Future[(Seq[RenglonDictamen], String)]
  //**********************************************************************************

  //************************** TotalCotizacion    ************************************
  def getTotalCotizacion(id: String): Future[(Seq[TotalCotizacion], String)]
  //**********************************************************************************


  //************************** Seguimiento *******************************************
  def getAllSeguimiento(): Future[(Seq[Seguimiento], String)]
  def getBetweenDatesSeguimiento(start: String, end: String): Future[(Seq[Seguimiento], String)]
  //**********************************************************************************


  //************************** Solicitud Pago *******************************************
  def insertSolicitudPago(item: SolicitudPago): Future[(SolicitudPago, String)]
  def deleteSolicitudPago(id: IdSolicitudPago): Future[Int]
  def getSolicitudPago(id: IdSolicitudPago): Future[(SolicitudPago, String)]
  def getSolicitudPagoByIdComparativo(id: String): Future[(SolicitudPago, String)]
  //**********************************************************************************

  //************************** Detalles Solicitud Pago *******************************************
  def insertDetSolicitudPago(item: DetSolicitudPago): Future[(Seq[DetSolicitudPago], String)]
  def saveDetSolicitudPago(item: DetSolicitudPago): Future[(Seq[DetSolicitudPago], String)]
  def deleteDetSolicitudPago(item: DetSolicitudPago): Future[(Seq[DetSolicitudPago], String)]
  def getDetSolicitudPago(id: IdSolicitudPago): Future[(Seq[DetSolicitudPago], String)]
  //def getDetSolicitudPagoByIdComparativo(id: String): Future[(Seq[DetSolicitudPago], String)]
  //**********************************************************************************


  //************************** Reportes Solicitud Pagos *******************************************
  //def getAllRptSolicitudPago(): Future[(Seq[Seguimiento], String)]
  def getBetweenDatesRptSolicitudPago(start: String, end: String): Future[(Seq[RptSolicitudPago], String)]
  def printSolicitudPagoByFolio(folio: String, ejercicio: Int): Future[(RptSolicitudPago, String)]
  //**********************************************************************************

  //************************** Precios de referencia *******************************************
  def getPrecioReferencia(key: String): Future[(Seq[PrecioReferencia], String)]
  //**********************************************************************************

}