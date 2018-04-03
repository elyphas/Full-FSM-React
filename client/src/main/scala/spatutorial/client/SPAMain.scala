package spatutorial.client

import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import spatutorial.client.components.GlobalStyles
import spatutorial.client.logger._
import spatutorial.client.modules._
//import spatutorial.client.services.SPACircuit

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import CssSettings._
import scalacss.ScalaCssReact._


//parece que esta es la solución para cuando se quiere crear los objetos al llamarlos con el menu.
// ejemplo:  | staticRoute("#solicitudpago", SolicitudPagoLoc) ~> renderR(ctl => new frmSolicitudPago())
//import japgolly.scalajs.react.vdom.Implicits._

@JSExportTopLevel("SPAMain")
object SPAMain extends js.JSApp {

  // Define the locations (pages) used in this application
  sealed trait Loc

  case object PedidoLoc extends Loc

  case object ArticuloLoc extends Loc

  case object DatosGralesLoc extends Loc

  case object DetPedidoLoc extends Loc

  case object LstBienesLoc extends Loc

  case object SearchArticulosLoc extends Loc

  case object DatosGralesRequisicionLoc extends Loc

  case object ProveedoresLoc extends Loc

  case object ProgramaLoc extends Loc

  case object PartidaLoc extends Loc

  case object CalendarioLoc extends Loc

  case object DiaHabilLoc extends Loc

  case object DocumentosLoc extends Loc

  case object LicitantesLoc extends Loc

  case object OficioPresupuestoLoc extends Loc

  case object ReportsExcelLoc extends Loc

  case object LogearLoc extends Loc

  case object SolicitudPagoLoc extends Loc

  case object RptSolicPagoLoc extends Loc


  // configure the router
  val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
    import dsl._
    // wrap/connect components to the circuit
      (staticRoute(root, PartidaLoc) ~> renderR( ctl => frmPartida())
      | staticRoute("#pedido", PedidoLoc) ~> renderR(ctl => frmPedido())
      | staticRoute("#logear", LogearLoc) ~> renderR(ctl => frmLogear())
      | staticRoute("#datosgrales", DatosGralesLoc) ~> renderR(ctl => frmDatosGrales())
      | staticRoute("#solicitudpago", SolicitudPagoLoc) ~> renderR(ctl => frmSolicitudPago())
      | staticRoute("#documentos", DocumentosLoc) ~> renderR(ctl => frmDocumentos())
      | staticRoute("#licitantes", LicitantesLoc) ~> renderR(ctl => frmLicitantes(ctl))
      | staticRoute("#calendario", CalendarioLoc) ~> renderR(ctl => frmCalendario())
      | staticRoute("#proveedores", ProveedoresLoc) ~> renderR(ctl => frmProveedor())
      | staticRoute("#reportesExcel", ReportsExcelLoc) ~> renderR(ctl => frmReportsExcel())
      | staticRoute("#rptsolicpago", RptSolicPagoLoc) ~> renderR(ctl => frmRptSolicPago())
      | staticRoute("#oficiopresupuesto", OficioPresupuestoLoc) ~> renderR(ctl => frmOficioPresupuesto())
      | staticRoute("#lstBienes", LstBienesLoc) ~> renderR(ctl => frmListBienes(ctl))
      | staticRoute("#searcharticulos", SearchArticulosLoc) ~> renderR(ctl => frmSearchCatalogos(ctl))
      | staticRoute("#articulos", ArticuloLoc) ~> renderR(ctl => frmArticulo())
      | staticRoute("#programa", ProgramaLoc) ~> renderR(ctl => frmPrograma())
      | staticRoute("#partida", PartidaLoc) ~> renderR(ctl => frmPartida())
      | staticRoute("#diahabil", DiaHabilLoc) ~> renderR(ctl => frmDiaHabil())

      ).notFound(redirectToPage(PedidoLoc)(Redirect.Replace))
  }.renderWith(layout)



  // base layout for all pages
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div(^.className:="wrapper",
      <.header(
        <.h5("Web Sicap")),
      // here we use plain Bootstrap class names as these are specific to the top level layout defined here
      <.aside( ^.float:="left", ^.width:="150", ^.height:="600", ^.backgroundColor := " #ebf4f5 ",
        ^.border := "1px solid",
        MainMenu(c, r.page) ),
      // currently active module is shown in this container
      <.main(r.render())
    )
  }

  @JSExport
  def main(): Unit = {
    log.warn("Application starting")
    // send log messages also to the server
    log.enableServerLogging("/logging")
    log.info("This message goes to server as well")

    // create stylesheet
    GlobalStyles.addToDocument()
    // create the router
    val router = Router(BaseUrl.until_#, routerConfig)
    // tell React to render the router in the document body
    router().renderIntoDOM(dom.document.getElementById("root"))
  }
}
