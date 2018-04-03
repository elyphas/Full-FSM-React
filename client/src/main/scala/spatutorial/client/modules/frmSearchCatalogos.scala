package spatutorial.client.modules

import diode.react.ReactPot._
import diode.react._
import diode.data.Pot
//import diode.{ModelR, RootModelR}

import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.html_<^._

import spatutorial.client.components.Bootstrap._
import japgolly.scalajs.react.extra.router.RouterCtl
import spatutorial.client.SPAMain.{Loc, LstBienesLoc}
//import scala.math.BigDecimal.double2bigDecimal
import spatutorial.client.components.Bootstrap.{Button, /*Combo, CommonStyle,*/ Text}
import spatutorial.client.components._
//import spatutorial.client.logger._
import spatutorial.client.services._
import spatutorial.shared._

//import scalacss.ScalaCssReact._

import org.scalajs.dom.raw._
//import org.scalajs.dom.window

import spatutorial.client.actionhandlers.{SearchArticulos, GetArticulo, SetSelectedArticulo, SetShowFormRenglonesBienes}

//import scalajs.js
//import js.Dynamic.{ global => g }

object frmSearchCatalogos{
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(router: RouterCtl[Loc], proxy: ModelProxy[Pot[Seq[Articulo]]])

  case class State( items: Seq[Articulo]=Seq.empty,
                    selectedItem: Option[Articulo] = None,
                    showTodoForm: Boolean = false,
                    search: String = "",
                    id_comparativo: String = ""

                  )

  class Backend($: BackendScope[Props, State]) {

    def onChangeSearch(e: ReactEventFromInput) = $.state.zip($.props) >>= { case (s, p) =>
      val text = e.target.value.toUpperCase
      $.modState(s => s.copy(search = text))
    }

    def onSearch = $.state.zip($.props) >>= { case (s,p) =>
      p.proxy.dispatchCB(SearchArticulos(s.search))
    }

    def onGetActiveID = $.state.zip($.props) >>= { case (s,p) =>
      /*val reader: ModelR[RootModel, RequisicionID] = SPACircuit.zoom(_.activeID)
      val id = reader.value
      Callback.alert( id.cve_oficina+"-"+id.folio.toString+"-"+id.ejercicio.toString )*/
      Callback.alert("ID")
    }

    def editTodo(item: Option[Articulo]) =
      $.modState(s => s.copy(selectedItem = item, showTodoForm = true))

    def todoEdited(item: RenglonListBienes, cancelled: Boolean) =
        $.modState(s => s.copy(showTodoForm = false))

    def render(p: Props, s: State) = {
      Panel(Panel.Props("Buscar artículos"),

        Text(Text.Props(idx="txtSearchCatalogo", label="Buscar",
          onChange = onChangeSearch, value = s.search, otrosStyles = Seq(^.width := "200"))),

        Button(Button.Props(onSearch, addStyles = Seq( bss.button)), "Buscar"),
          <.div(/*^.clear := "both", */^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
            ^.borderColor := "#20B2AA", ^.height := "auto", ^.overflow := "hidden",

          p.proxy().renderFailed(ex => "Error loading"),
          p.proxy().renderPending(_ > 500, _ => <.p("Loading...")),
          p.proxy().renderEmpty( if(s.search == "") "" else "No hay productos!")
          ,
          p.proxy().render( items =>
            <.div(^.padding := "10", ^.border := "1px solid", ^.marginTop := "3", ^.borderColor := "#20B2AA",
              ^.height := "auto", ^.overflow := "hidden",
              cmpItemsFound( items,
                  item => p.proxy.dispatchCB(SetShowFormRenglonesBienes(true)) >> p.router.set(LstBienesLoc) >>
                          p.proxy.dispatchCB(SetSelectedArticulo(item)),
                  item => editTodo(Some(item)),
                  item => p.proxy.dispatchCB(GetArticulo(item.cve_articulo))
              )
            )
          )
        )

      )
    }
  }

  val component = ScalaComponent.builder[Props]("frmSearchCatalogos")
    .initialState(State())
    .renderBackend[Backend]
    .componentWillReceiveProps { rp =>
        val (lstarticulos:Seq[Articulo], _ /*, msg:String*/) = rp.nextProps.proxy() match {
          case x if x.isUnavailable => (Seq(Articulo), "No se encontro")
          case x if x.isEmpty => (Seq(Articulo), "Nuevo")
          case x if x.isFailed => (Seq(Articulo), "Error")
          case _ => (rp.nextProps.proxy().get, "Guardado")
        }
        rp.setState(State(items = lstarticulos, search = rp.state.search, id_comparativo = rp.state.id_comparativo))
    }
    .build

  /** Returns a function compatible with router location system while using our own props */
  def apply(router: RouterCtl[Loc]) = {
    val lstarticulosWraper = SPACircuit.connect(_.catArticulos)
    lstarticulosWraper(proxlstArticulos => component(Props(router = router, proxy = proxlstArticulos)))
  }
}