package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
import diode._
import diode.react.ReactPot._
//import japgolly.scalajs.react.vdom.VdomArray
//import org.scalajs.dom.ext.KeyValue
import spatutorial.client.services.SPACircuit

/*
import diode.react.ReactPot._
import diode.react.ModelProxy
import diode.data.Pot
import spatutorial.client.services.SPACircuit
*/
//import diode.react._

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import spatutorial.client.components.Bootstrap._

//import diode.{ModelR, RootModelR , Dispatcher}

import japgolly.scalajs.react.extra.router.RouterCtl
import spatutorial.client.SPAMain.{Loc /*, SearchArticulosLoc*/ }

//import scala.math.BigDecimal.double2bigDecimal
//import spatutorial.client.components.Bootstrap.{Button, Combo, CommonStyle, Text}
import spatutorial.client.components._
//import spatutorial.client.logger._
import spatutorial.client.services._
import spatutorial.shared._

//import scalacss.ScalaCssReact._

import org.scalajs.dom.raw._
import org.scalajs.dom.window

//import scalajs.js
//import js.Dynamic.{global => g}

import spatutorial.client.actionhandlers.{GetLicitantes, SearchProveedores, InsertLicitante, DeleteLicitante}

object frmLicitantes {

  case class Props(router: RouterCtl[Loc], proxy: ModelProxy[Pot[(Seq[Licitante], String)]])

  case class State( itemsLicit: Seq[Licitante] = Seq.empty, itemsProvFounds: Seq[Proveedor] = Seq.empty,
                    buscaProv: String = "", id_comparativo: String = "")

  class Backend($: BackendScope[Props, State]) {

    def onChangeIdComparativo(e: ReactEventFromInput) = $.state >>= { s =>
      val text = e.target.value.toUpperCase
      if (text.takeRight(4) == "ADQ2") $.modState(s => s.copy(id_comparativo = text), onBuscar(text))
      else $.modState(s => s.copy(id_comparativo = text))
    }

    def onChangeBuscaProveedor(e: ReactEventFromInput) = $.state.zip($.props) >>= { case (s, p) =>
      val text = e.target.value.toUpperCase
      val count = text.length
      val buscar = if ( (count%5) == 0 ) true else false
      if (buscar && count > 3) $.modState(s => s.copy(buscaProv = text), searchProv(text))
      else $.modState(s => s.copy(buscaProv = text.toUpperCase))
    }

    def searchProv(str: String) = $.state.zip($.props) >>= { case (s, p) =>
      p.proxy.dispatchCB(SearchProveedores(s.buscaProv))
    }

    def onBuscar(id: String) = $.props >>= { p => p.proxy.dispatchCB( GetLicitantes(id)) }

    def onBuscar2 = $.state.zip($.props) >>= { case (s, p) =>
      p.proxy.dispatchCB( GetLicitantes(s.id_comparativo))
    }

    def ProveedoresFound(provsFound: ModelRO[Pot[Seq[Proveedor]]]) = {
      val lstProv = provsFound.value match {
        case x if x.isUnavailable => Seq(Proveedor())
        case x if x.isEmpty => Seq(Proveedor())
        case x if x.isFailed => Seq(Proveedor())
        case _ => provsFound.value.get
      }
      //In this context is right to use ".runNow()"
      $.modState(s => s.copy(itemsProvFounds = lstProv)).runNow()
    }

    def provParticipa(item: Proveedor) = $.state.zip($.props) >>= { case (s, p) =>
      val folio = window.prompt("!Introduce el folio de oficio!")
      if ( folio == "" || folio == null)
        Callback.empty
      else {
        val insertProv = ProvLicitante(id_comparativo = s.id_comparativo, rfc = item.cve_provedor, plazo = folio)
        p.proxy.dispatchCB(InsertLicitante(insertProv))
      }
    }

    def render(p: Props, s: State) = {
      Panel(Panel.Props("Licitantes"),

        <.div(/*^.clear:="both", */^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
          ^.borderColor := "#20B2AA", ^.height := "auto", ^.overflow := "hidden",

          Text(Text.Props(idx="txtIdComparativo", label="Id Comparativo",
            onChange = onChangeIdComparativo, value = s.id_comparativo, otrosStyles = Seq(^.width := "200",  ^.marginRight:="100"))),

          Text(Text.Props(idx="txtBuscaProveedor", label="Buscar Proveedor",
            onChange = onChangeBuscaProveedor, value = s.buscaProv, otrosStyles = Seq(^.width := "200", ^.float:="right"))),

          p.proxy().renderFailed(ex => "Error loading"),
          p.proxy().renderPending(_ > 500, _ => <.p("Loading...")),

          <.div(^.clear:="both", ^.float:="left", ^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
            ^.borderColor := "#20B2AA", ^.height := "auto", ^.overflow := "hidden", ^.width := "400",
            p.proxy().render(items =>
              cmpLstLicitantes( s.itemsLicit,
                item => p.proxy.dispatchCB(DeleteLicitante(ProvLicitante(id_comparativo = item.id_comparativo, rfc = item.cve_provedor.getOrElse("")))))
            )
          ),
          <.div(^.float:="right", ^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
            ^.borderColor := "#20B2AA", ^.height := "auto", ^.overflow := "hidden", ^.width := "400",
            p.proxy().render(items =>
              cmpProvFound( s.itemsProvFounds, provParticipa)
            )
          )
        )
      )
    }
  }

  val component = ScalaComponent.builder[Props]("frmLicitantes")
    .initialState( State() )
    .renderBackend[Backend]
    .componentWillReceiveProps { rp =>

      val prevID = rp.state.id_comparativo

      val (licitantes: Seq[Licitante], _ /*msg:String*/ ) = rp.nextProps.proxy() match {
        case x if x.isUnavailable => (Seq.empty, "No se encontro")
        case x if x.isEmpty => (Seq.empty, "Nuevo")
        case x if x.isFailed => (Seq.empty, "Error")
        case _ => {
          val (lici, msg) = rp.nextProps.proxy().get
          (lici, "Guardado")
        }
      }

      rp.setState(State(itemsLicit = licitantes, id_comparativo = prevID))
    }
    .componentWillMount { scope =>
      Callback{
        //val unsubscribe =
          SPACircuit.subscribe(SPACircuit.zoom(_.proveedores))(provs => scope.backend.ProveedoresFound(provs))
        //scope.modState(s=>s.copy(item = s.item.copy(fecha = Fechas(scope.backend.Hoy))))
      }
    }
    .build

  /** Returns a function compatible with router location system while using our own props */
  def apply(router: RouterCtl[Loc]) = {
    val licitantesWraper = SPACircuit.connect(_.licitantes)
    licitantesWraper(prox => component(Props(router = router, proxy = prox)))


  }
}