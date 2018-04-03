package spatutorial.client.modules


import diode.data.Pot
import diode._
//import diode.react.ReactPot._
//import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import spatutorial.client.SPAMain._
//import spatutorial.client.components.Bootstrap.CommonStyle
import spatutorial.client.components.Icon._
import spatutorial.client.components._
import spatutorial.client.services._
import spatutorial.shared.{/*Calendario,*/ Usuarios}

import scalacss.ScalaCssReact._

object MainMenu {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(router: RouterCtl[Loc], currentLoc: Loc)

  case class State(item: Usuarios = Usuarios())

  private case class MenuItem(idx: Int, label: (Props) => VdomNode, icon: Icon, location: Loc, submenu: String)

  /*private val menuItems = Seq(
    MenuItem( 1, _ => "Pedido", Icon.check, PedidoLoc, "Adquisición"),
    MenuItem( 2, _ => "Documentos", Icon.check, DocumentosLoc, "Documentos"),
    MenuItem( 3, _ => "Licitantes", Icon.check, LicitantesLoc, "Licitantes"),
    MenuItem( 4, _ => "DatosGrales", Icon.check, DatosGralesLoc, "Adquisición"),
    MenuItem( 5, _ => "LstBienes", Icon.check, LstBienesLoc, "Adquisición"),
    MenuItem( 6, _ => "Buscar Articulo", Icon.check, SearchArticulosLoc, "Busquedad"),
    MenuItem( 7, _ => "Proveedores", Icon.check, ProveedoresLoc, "Catalogos"),
    MenuItem( 8, _ => "Articulos", Icon.check, ArticuloLoc, "Catalogos"),
    MenuItem( 9, _ => "Programa", Icon.check, ProgramaLoc, "Catalogos"),
    MenuItem(10, _ => "Calendario", Icon.check, CalendarioLoc, "Calendario"),
    MenuItem(11, _ => "Partida", Icon.check, PartidaLoc, "Catalogos"),
    MenuItem(12, _ => "Dia Habil", Icon.check, DiaHabilLoc, "DiaHabile"),
    MenuItem(13, _ => "OficioPresupuesto", Icon.check, OficioPresupuestoLoc, "OficioPresupuesto"),
    MenuItem(14, _ => "ReportesExcel", Icon.check, ReportsExcelLoc, "Reportes Excel"),
    MenuItem(15, _ => "RptSolicPago", Icon.check, RptSolicPagoLoc , "Reportes Solicitudes Pago"),
    MenuItem(16, _ => "Logear", Icon.check, LogearLoc, "Logear")
  )*/

  private class Backend($: BackendScope[Props, State]) {

    def Logged(users: ModelRO[Pot[Usuarios]]) = {
      val user = users.value match {
        case x if x.isUnavailable => Usuarios()
        case x if x.isEmpty => Usuarios()
        case x if x.isFailed => Usuarios()
        case _ => users.value.get
      }
      $.modState(s => s.copy(item = user)).runNow() //In this context is right to use ".runNow()"
    }

    def menuTest(props: Props) = {
      <.li(^.key := 1, "Menu", ^.fontSize := "15",
        <.li(^.key := 1, "Adquis", ^.fontSize := "15",
          <.li(^.key := 1, (^.className := "active").when(props.currentLoc == PedidoLoc),
            props.router.link(PedidoLoc)(Icon.check, " ", "Pedido")),
          <.li(^.key := 2, (^.className := "active").when(props.currentLoc == LstBienesLoc),
            props.router.link(LstBienesLoc)(Icon.check, " ", "Lista de bienes")),
          <.li(^.key := 3, (^.className := "active").when(props.currentLoc == PedidoLoc),
            props.router.link(PedidoLoc)(Icon.check, " ", "Pedido")),
          <.li(^.key := 4, (^.className := "active").when(props.currentLoc == LicitantesLoc),
            props.router.link(LicitantesLoc)(Icon.check, " ", "Licitantes")),
          <.li(^.key := 5, (^.className := "active").when(props.currentLoc == DocumentosLoc),
            props.router.link(DocumentosLoc)(Icon.check, " ", "Documentos")),
          <.li(^.key := 6, (^.className := "active").when(props.currentLoc == CalendarioLoc),
            props.router.link(CalendarioLoc)(Icon.check, " ", "Calendario")),
          <.li(^.key := 7, (^.className := "active").when(props.currentLoc == DatosGralesLoc),
            props.router.link(DatosGralesLoc)(Icon.check, " ", "Datos Generales")),
          <.li(^.key := 8, (^.className := "active").when(props.currentLoc == OficioPresupuestoLoc),
            props.router.link(OficioPresupuestoLoc)(Icon.check, " ", "Oficio Presupuesto")),
          <.li(^.key := 9, (^.className := "active").when(props.currentLoc == SolicitudPagoLoc),
            props.router.link(SolicitudPagoLoc)(Icon.check, " ", "Solicitud Pago")),
          <.li(^.key := 10, (^.className := "active").when(props.currentLoc == RptSolicPagoLoc),
            props.router.link(RptSolicPagoLoc)(Icon.check, " ", "Reportes Solicitud de pago"))
        ),
        <.li(^.key := 2, "Catalogo", ^.fontSize := "15",
          <.li(<.a(^.key := 1, (^.className := "active").when(props.currentLoc == SearchArticulosLoc),
            props.router.link(SearchArticulosLoc)(Icon.check, " ", "Buscar Articulo"))),
          <.li(<.a(^.key := 3, (^.className := "active").when(props.currentLoc == ArticuloLoc),
            props.router.link(ArticuloLoc)(Icon.check, " ", "Articulos"))),
          <.li(<.a(^.key := 2, (^.className := "active").when(props.currentLoc == PartidaLoc),
            props.router.link(PartidaLoc)(Icon.check, " ", "Partidas"))),
          <.li(<.a(^.key := 3, (^.className := "active").when(props.currentLoc == ProgramaLoc),
            props.router.link(ProgramaLoc)(Icon.check, " ", "Programas"))),
          <.li(<.a(^.key := 4, (^.className := "active").when(props.currentLoc == DiaHabilLoc),
            props.router.link(DiaHabilLoc)(Icon.check, " ", "Dia Habil"))),
          <.li(<.a(^.key := 5, (^.className := "active").when(props.currentLoc == LstBienesLoc),
            props.router.link(LstBienesLoc)(Icon.check, " ", "Listado")))
        )
      )
    }

    def menu(props: Props, s: State) = {
      if (s.item.tipo == "normal1") {
        <.li(^.key := 1, "Adquis", ^.fontSize := "15",
          <.li(^.key := 1, (^.className := "active").when(props.currentLoc == PedidoLoc),
            props.router.link(PedidoLoc)(Icon.check, " ", "Pedido"))
        ) /*,
          <.li( ^.key := 3, (^.className := "active").when( props.currentLoc == DocumentosLoc),
            props.router.link(DocumentosLoc)(Icon.check, " ", "Documentos"))*/
      } else if (s.item.tipo == "administrador1") {
        <.li(^.key := 7, (^.className := "active").when(props.currentLoc == ReportsExcelLoc),
          props.router.link(ReportsExcelLoc)(Icon.check, " ", "Reportes Excel"))
      } else if (s.item.tipo == "administrador") {
        <.li(^.key := 1, "Adquis", ^.fontSize := "15",
          <.li(^.key := 1, (^.className := "active").when(props.currentLoc == PedidoLoc),
            props.router.link(PedidoLoc)(Icon.check, " ", "Pedido"))    //,
          /*<.li(^.key := 2, (^.className := "active").when(props.currentLoc == LicitantesLoc),
            props.router.link(LicitantesLoc)(Icon.check, " ", "Licitantes")),
          <.li(^.key := 3, (^.className := "active").when(props.currentLoc == DocumentosLoc),
            props.router.link(DocumentosLoc)(Icon.check, " ", "Documentos")),
          <.li(^.key := 4, (^.className := "active").when(props.currentLoc == CalendarioLoc),
            props.router.link(CalendarioLoc)(Icon.check, " ", "Calendario")),*/
          /*<.li(^.key := 6, (^.className := "active").when(props.currentLoc == OficioPresupuestoLoc),
            props.router.link(OficioPresupuestoLoc)(Icon.check, " ", "Oficio Presupuesto"),
            <.li(^.key := 7, (^.className := "active").when(props.currentLoc == RptSolicPagoLoc),
              props.router.link(RptSolicPagoLoc)(Icon.check, " ", "Reportes Solicitud de pago"))
          )*/
        )
      } else {
        <.li(^.key := 2, "Logear", ^.fontSize := "15",
          <.li(<.a(^.key := 1, (^.className := "active").when(props.currentLoc == LogearLoc),
            props.router.link(LogearLoc)(Icon.check, " ", "Logear"))))
      }
  }

    def render(props: Props, s: State) = {
      <.ul(bss.aside)(
        menu(props, s)
        //menuTest(props)
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("MainMenu")
    .initialState(State())
    .renderBackend[Backend]
    .componentWillMount { scope =>
      Callback{
        //val unsubscribe =
          SPACircuit.subscribe(SPACircuit.zoom(_.usuarios))(user => scope.backend.Logged(user))
      }
    }
    .build

  def apply(ctl: RouterCtl[Loc], currentLoc: Loc): VdomElement =
      component(Props(ctl, currentLoc))
}
