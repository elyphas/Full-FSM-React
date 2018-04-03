package spatutorial.client.modules

import diode.data.Pot
//import diode.react.ModelProxy

import diode._
//import diode.react.ReactPot._
import spatutorial.client.services.SPACircuit

//import diode.react._

import japgolly.scalajs.react.vdom.VdomArray
import org.scalajs.dom.ext.KeyValue
//import spatutorial.client.SPAMain.LstBienesLoc
import spatutorial.client.actionhandlers._
//import spatutorial.client.modules.frmSearchCatalogos.bss


import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.html_<^._

import spatutorial.client.components.Bootstrap._

import diode.{ /*ModelR, RootModelR,*/ Dispatcher}

import japgolly.scalajs.react.extra.router.RouterCtl
import spatutorial.client.SPAMain.{Loc, SearchArticulosLoc}

import scala.math.BigDecimal.double2bigDecimal
//import spatutorial.client.components.Bootstrap.{Button, Combo, CommonStyle, Text}
import spatutorial.client.components._
//import spatutorial.client.logger._
import spatutorial.client.services._
import spatutorial.shared._

import scalacss.ScalaCssReact._

import org.scalajs.dom.raw._
import org.scalajs.dom.window

import spatutorial.client.actionhandlers.{GetListBienes, AddItemLstBienes, EditItemLstBienes /*, SetLastRenglon, SetActiveID,  GetProceso, GetArticulo*/ }
import spatutorial.client.actionhandlers.{DeleteItemLstBienes, CleanLstProgramas}
import spatutorial.client.actionhandlers.{GetPrograma}

import scalajs.js
//import js.Dynamic.{global => g}

object frmListBienes {

  case class Props(router: RouterCtl[Loc])

  case class State(
                   items: Seq[RenglonListBienes] = Seq.empty,
                   selectedItem: Option[RenglonListBienes] = None,
                   proceso: Proceso = Proceso(),
                   showTodoForm: Boolean = false,
                   searchArticulo: String = "",
                   itemsFound: Seq[Articulo] = Seq.empty,
                   cvePrograma: String = "",
                   descripcionPrograma: String = "",
                   programas: Seq[Programa] = Seq.empty,
                   programasFiltered: Seq[Programa] = Seq.empty,

                   showLstProgramas: Boolean = false,
                   showLstArticulos: Boolean = false,
                   editItem: Boolean = false

                  )

  class Backend($: BackendScope[Props, State]) {

    def onChangeIdComparativo(e: ReactEventFromInput) = $.state >>= { s =>
      val txt = e.target.value
      if (txt.takeRight(4) == "ADQ2")
        $.modState(s => s.copy(proceso = s.proceso.copy(id_comparativo = txt.toUpperCase)), onBuscar2(txt))
      else
        $.modState(s => s.copy(proceso = s.proceso.copy(id_comparativo = txt.toUpperCase)))
    }

    def onChangePrograma(e: ReactEventFromInput) = $.state >>= { s =>
      val txt = e.target.value

      if (txt.length == 0)
        $.modState(s => s.copy( cvePrograma = txt.toUpperCase, showLstProgramas = false))
      else if (txt.length > 0) {
          $.modState(s => s.copy( cvePrograma = txt.toUpperCase, showLstProgramas = true), onBuscarCvePrograma(txt))
      } else
        $.modState(s => s.copy(cvePrograma = txt.toUpperCase, showLstProgramas = true))
    }

    def onKeyDownCvePrograma(e: ReactKeyboardEvent) = if(e.key == KeyValue.Escape) $.modState(s => s.copy(showLstProgramas = false)) else Callback.empty

    def onChangeDescripcionPrograma(e: ReactEventFromInput) = $.state >>= { s =>
      val txt = e.target.value

      if (txt.length == 0)
        $.modState(s => s.copy(descripcionPrograma = txt.toUpperCase, showLstProgramas = false))
      else if (txt.length > 2)
        $.modState(s => s.copy(descripcionPrograma = txt.toUpperCase, showLstProgramas = true), onBuscarDescripcionPrograma(txt))
      else
        $.modState(s => s.copy(descripcionPrograma = txt.toUpperCase, showLstProgramas = true))
    }

    def onProgramasFound(itemsFound: ModelRO[Pot[Seq[Programa]]]) =  {
      val lstProgrs = itemsFound.value match {
        case x if x.isUnavailable => Seq(Programa())
        case x if x.isEmpty => Seq(Programa())
        case x if x.isFailed => Seq(Programa())
        case _ => itemsFound.value.get
      }
      $.modState(s => s.copy(programas = lstProgrs)).runNow()
    }

    def onBuscarCvePrograma(str: String) = $.state >>= { s =>
        val filtered = s.programas.filter(p => p.programa contains(str))
        $.modState(s=>s.copy(programasFiltered = filtered))
    }

    def onBuscarDescripcionPrograma(str: String) = $.state >>= { s =>
      val filtered = s.programas.filter(p => p.descripcion contains(str))
      $.modState(s=>s.copy(programasFiltered = filtered))
    }

    def onBuscar2(id: String) = $.state.zip($.props) >>= { case (s, p) => Callback.alert("se descontinuo pa mejorar") }

    def onBuscar = $.state.zip($.props) >>= { case (s, p) =>
      Callback {
        SPACircuit.dispatch(GetProcesoOnly(s.proceso.id_comparativo))
        SPACircuit.dispatch(GetListBienes( s.proceso.id_comparativo))
      }
    }

    def editItem(item: Option[RenglonListBienes]) = $.modState(s => s.copy(selectedItem = item, showTodoForm = true))

    def itemEdited(item: RenglonListBienes, cancelled: Boolean) = {
      val cb = if (cancelled) Callback.log("Todo editing cancelled")
      else Callback.log(s"Todo edited: $item")
      cb >> $.modState(s => s.copy(showTodoForm = false))
    }

    def onAdd = $.state.zip($.props) >>= { case (s, p) =>
        p.router.set(SearchArticulosLoc)
    }

    def onAddByCve = $.state.zip($.props) >>= { case (s, p) =>
      $.modState(s => s.copy( selectedItem = None, showTodoForm = true))
    }

    def ActiveProceso(proceso: ModelRO[Pot[(Proceso, String)]]) = {
      val (proc: Proceso, _ /*msg: String*/) = proceso.value match {
          case x if x.isUnavailable => (Proceso(), "No se encontro")
          case x if x.isEmpty => (Proceso(), "Nuevo")
          case x if x.isFailed => (Proceso(), "Error")
          case _ => proceso.value.get
      }

      SPACircuit.dispatch(GetPrograma(proc.programa))

      $.modState(s => s.copy(proceso = proc, cvePrograma = proc.programa)).runNow //runNow es apropiado en este contexto
    }

    def onChangeSearchArticulo(e: ReactEventFromInput) = $.state.zip($.props) >>= { case (s, p) =>
      val txt = e.target.value

      if ( txt.length > 4)
        $.modState(s => s.copy(searchArticulo = txt.toUpperCase), onSearchArticulos(txt))
      else
        $.modState(s => s.copy(searchArticulo = txt.toUpperCase))
    }

    def onSearchArticulos(txt: String) = $.state.zip($.props) >>= { case (s, p) =>
        Callback(SPACircuit.dispatch(SearchArticulos(txt)))
    }

    def onItemsFound(itemsFound: ModelRO[Pot[Seq[Articulo]]]) = {
      val lstArtic = itemsFound.value match {
          case x if x.isUnavailable => Seq(Articulo())
          case x if x.isEmpty => Seq(Articulo())
          case x if x.isFailed => Seq(Articulo())
          case _ => itemsFound.value.get
      }
      $.modState(s => s.copy(itemsFound = lstArtic, searchArticulo = s.searchArticulo)).runNow()
    }


    def onUpdatePrograma(programa: ModelRO[Pot[Programa]]) = {
      val progr: Programa = programa.value match {
        case x if x.isUnavailable => Programa()
        case x if x.isEmpty => Programa()
        case x if x.isFailed => Programa()
        case _ => programa.value.get
      }
      $.modState(s => s.copy(descripcionPrograma = progr.descripcion)).runNow()
    }

    def onLstBienes(itemsFound: ModelRO[Pot[Seq[RenglonListBienes]]]) = {
      val lstBienes = itemsFound.value match {
        case x if x.isUnavailable => Seq(RenglonListBienes())
        case x if x.isEmpty => Seq(RenglonListBienes())
        case x if x.isFailed => Seq(RenglonListBienes())
        case _ => itemsFound.value.get
      }
      $.modState(s => s.copy(items = lstBienes)).runNow()
    }

    def onSelectPrograma(item: Programa) = {
      val dispatch: Dispatcher = SPACircuit
      dispatch(CleanLstProgramas)
      $.modState(s=>s.copy(showLstProgramas = false, cvePrograma = item.programa, descripcionPrograma = item.descripcion))
    }

    def onFocusPrograma = $.modState( s => s.copy(showLstProgramas = true))

    def onLostFocusPrograma = Callback.empty //$.modState(s => s.copy(showLstProgramas = false))

    def onLostFocusSearchArticulo = Callback.empty  //$.modState(s => s.copy(showLstArticulos = false))

    def onFocusSearchPrograma = $.modState( s => s.copy( showLstArticulos = true ) )

    def domLstBienes(p:Props, s:State) =
      <.div(^.float:="left", ^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
        ^.borderColor := "#20B2AA", ^.height := "auto", ^.width:="700", //^.zIndex:="0", //^.position:="relative", //^.overflow := "hidden",
        cmpLstBienes(s.items,
          item => Callback(SPACircuit.dispatch(GetListBienes(s.proceso.id_comparativo))), //p.proxy.dispatchCB(GetListBienes(s.proceso.id_comparativo)),  //Add
          item => editItem(Some(item)),                                                   //Edit
          item => Callback(SPACircuit.dispatch(DeleteItemLstBienes(item)))                //p.proxy.dispatchCB(DeleteItemLstBienes(item))                 //Delete
        )
    )

    def onSelectArticFound(item: Articulo) = $.state.zip($.props) >>= { case (s, p) =>

      val exist = s.items.filter(i => i.cve_articulo == item.cve_articulo)

      val lastRenglon = s.items.length + 1


      val selectedArtic = RenglonListBienes(id_comparativo = s.proceso.id_comparativo, renglon = lastRenglon, programa = s.proceso.programa,
        cve_articulo = item.cve_articulo, descripcion = item.descripcion, unidad = item.unidad,
        cantidad = 0, precio_referencia = 0.0, importe = 0.0, iva = 0.0, total = 0.0)

      if (!exist.isEmpty)
        if (window.confirm("Modificar?")) {

          val result = exist.map { i => selectedArtic.copy(renglon = i.renglon) }.head

          $.modState( s => s.copy(selectedItem = Some(result), showTodoForm = true, editItem = true))

        }
        else Callback.empty
      else
        $.modState( s => s.copy(selectedItem = Some(selectedArtic), showTodoForm = true, editItem = false))
    }

    def domArtsFound(p:Props, s:State) =
      <.div(^.clear:="both", ^.padding := "10", ^.marginTop := "20", ^.height := "auto",  ^.overflow := "hidden", //^.position:="absolute", ^.zIndex:="15",
        ^.width:="500",
        cmpItemsFound( s.itemsFound,
          item => onSelectArticFound(item),
          item => Callback.empty,
          item => Callback.empty
        )
      )

    def domSearchArticulos(p:Props,   s:State) =
      <.div(^.float :="left", ^.padding := "10", ^.border := "1px solid", ^.marginTop := "3", ^.width:="500",
        ^.borderColor := "#20B2AA", ^.height := "auto", ^.overflow := "visible", ^.zIndex:="3", ^.position:="relative",

        Text(Text.Props(idx = "txtSearchArticulo", label = "Buscar Artículo",
          onChange = onChangeSearchArticulo, onFocus = onFocusSearchPrograma, onBlur = onLostFocusSearchArticulo,
          value = s.searchArticulo, otrosStyles = Seq(^.width := "200"))),

        Button(Button.Props(onBuscar), Icon.binoculars, "Buscar Artículo"),

        if (s.showLstArticulos) domArtsFound(p, s) else VdomArray.empty()
    )

    def domMainData(p:Props, s:State) = <.div(^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
        ^.height := "130", ^.overflow := "visible", ^.width := "800", ^.position := "relative", ^.zIndex := "10",

        Text(Text.Props(idx="txtIdComparativo", label="Id Comparativo",
          onChange = onChangeIdComparativo, value = s.proceso.id_comparativo, otrosStyles = Seq(^.width := "150"))),

        Button(Button.Props(onBuscar), Icon.binoculars, "Buscar ID"),

        Text(Text.Props(idx="txtCvePrograma", label="Cve. Programa",
            onChange = onChangePrograma, onFocus = onFocusPrograma, onBlur = onLostFocusPrograma,
            value = s.cvePrograma, otrosStyles = Seq(^.width := "130", ^.onKeyDown ==> onKeyDownCvePrograma))),

        Text(Text.Props(idx="txtDescripcionPrograma", label="Descripcion Programa",
            onChange = onChangeDescripcionPrograma, onFocus = onFocusPrograma, onBlur = onLostFocusPrograma,
            value = s.descripcionPrograma, otrosStyles = Seq(^.width := "200", ^.onKeyDown ==> onKeyDownCvePrograma))),

        Text(Text.Props(idx="txtFecha", label="Fecha",
          onChange = onChangeDescripcionPrograma, onFocus = onFocusPrograma, onBlur = onLostFocusPrograma,
          value = s.proceso.fecha.fecha, otrosStyles = Seq(^.width := "200", ^.onKeyDown ==> onKeyDownCvePrograma))),

        if(s.showLstProgramas) domLstProgramas(p, s) else VdomArray.empty()
    )

    def domLstProgramas(p:Props, s:State) =
    <.div(^.padding := "10", ^.marginTop := "50", ^.marginLeft := "60",
      ^.width:="600", ^.height := "auto",  ^.overflow := "hidden", ^.position:="absolute", ^.zIndex:="15",
        cmpLstProgramas(s.programasFiltered,
          item => onSelectPrograma(item),  //Add
          item => Callback.empty,         //Edit
          item => Callback.empty          //Delete
        )
    )

    def render(p: Props, s: State) = {

      Panel(Panel.Props("Listado de bienes"),

        <.div(/*^.clear:="both",*/ ^.float:="rigth", ^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
          ^.borderColor := "#20B2AA", ^.height := "800", ^.overflow := "hidden", ^.position:="relative",
          domMainData(p, s),
          domLstBienes(p, s),
          domSearchArticulos(p, s)
        ),
        if (s.showTodoForm) frmItem(s.selectedItem, itemEdited, s.editItem) else VdomArray.empty()
      )
    }

  }

  val component = ScalaComponent.builder[Props]("frmRenglonListBienes")
    .initialState( State(proceso = Proceso(id_comparativo = "315-2017-ADQ2")) )
    .renderBackend[Backend]
    /*.componentWillReceiveProps { rp =>
      val readerArticulo = SPACircuit.zoom(_.selectedArticulo)
      val idPrev = rp.state.proceso.id_comparativo

      val selectedArti = if (readerArticulo.value.cve_articulo != "")
              Some(RenglonListBienes(
                  id_comparativo = idPrev, renglon = 1, programa = "", cve_articulo = readerArticulo.value.cve_articulo,
                  descripcion = readerArticulo.value.descripcion, unidad = readerArticulo.value.unidad,
                  cantidad = 0))
          else None

      rp.setState(State(selectedItem = selectedArti, proceso = Proceso(id_comparativo = idPrev)))
    }*/
    .componentWillMount{ scope =>

      //val unsubscribe =
        SPACircuit.subscribe(SPACircuit.zoom(_.proceso))(proceso => scope.backend.ActiveProceso(proceso))

      //val unsubscribeArtics =
        SPACircuit.subscribe(SPACircuit.zoom((_.catArticulos)))(arts => scope.backend.onItemsFound(arts))

      //val unsubscribeProgrs =
        SPACircuit.subscribe(SPACircuit.zoom((_.catProgramas)))(progrs => scope.backend.onProgramasFound(progrs))

      //val unsubscribeLstBienes =
        SPACircuit.subscribe(SPACircuit.zoom((_.lstbienes)))(lstBienes => scope.backend.onLstBienes(lstBienes))

      //val unsubscribePrograma =
        //SPACircuit.subscribe(SPACircuit.zoom((_.programa)))(programa => scope.backend.onUpdatePrograma(programa))

      val showForm = SPACircuit.zoom(_.showFormRenglonBienes).value

      val readerProceso = SPACircuit.zoom(_.proceso).value

      val (proces, evento) = readerProceso match {
        case x if x.isUnavailable => (Proceso(), "No se encontro")
        case x if x.isEmpty => (Proceso(id_comparativo = "315-2017-ADQ2"), "Nuevo")
        case x if x.isFailed => (Proceso(), "Error")
        case _ => readerProceso.get
      }

      scope.modState(s => s.copy(proceso = proces, showTodoForm = showForm))

    }
    .componentDidMount{ scope =>
      val dispatch: Dispatcher = SPACircuit
      Callback{
        dispatch(GetAllPrograma)
      }
    }
    .build

  /** Returns a function compatible with router location system while using our own props */
  def apply(router: RouterCtl[Loc]) = component(Props(router = router))

}



object frmItem {  //Form to edit the item.

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(item: Option[RenglonListBienes], submitHandler: (RenglonListBienes, Boolean) => Callback, editItem: Boolean)

  case class State(item: RenglonListBienes, cancelled: Boolean = true)

  //val txtCveArticulo = Ref[HTMLInputElement]("txtCveArticulo")

  class Backend(t: BackendScope[Props, State]) {

    def submitForm() = t.state.zip(t.props) >>= { case (s, p) =>
      Callback {

        //val (proceso, evento) = SPACircuit.zoom(_.proceso).value.get

        val renglon = Renglon(
          id_comparativo =  s.item.id_comparativo, programa = s.item.programa, renglon = s.item.renglon, cve_articulo = s.item.cve_articulo,
          memo = "s.item.memo", entrega = 1, cantidad = s.item.cantidad,
          anexo = "proceso.memo", marca = "", modelo = "", cancelado = false, tipo = 1)

        val dispatch: Dispatcher = SPACircuit

        if(p.editItem) dispatch(EditItemLstBienes(renglon))
        else dispatch(AddItemLstBienes(renglon))

      } >> t.modState(s => s.copy(cancelled = false))
    }

    def formClosed(state: State, props: Props) =
      props.submitHandler(state.item, state.cancelled)

    //def clearAndFocusInput = t.modState(s=>s.copy(cancelled = false),txtCveArticulo(t).tryFocus)

    def onChangeDescripcion(e: ReactEventFromInput) = {
      val valor = e.target.value
      t.modState(s => s.copy(item = s.item.copy(descripcion = valor)))
    }

    def onChangeUnidad(e: ReactEventFromInput) = {
      val text = e.target.value.toUpperCase
      t.modState(s => s.copy(item = s.item.copy(unidad = text)))
    }

    def onChangeCveArticulo(e: ReactEventFromInput) = Callback.alert("cambiando clave articulo")

    def fmtMiles(valor: BigDecimal, dec: Int = 2): String =
      valor.toDouble.asInstanceOf[js.Dynamic]
        .toLocaleString("en-US",js.Dynamic.literal("minimumFractionDigits" -> dec),js.Dynamic.literal("maximumFractionDigits" -> dec),
          js.Dynamic.literal("style" -> "currency"),js.Dynamic.literal("currency" -> "US")).asInstanceOf[String]

    def onChangeCantidad(e: ReactEventFromInput) = t.state >>= { s =>
      val text = e.target.value
      val valorEntero = if ( text.length > 0 ) text.toInt else 0
      val subtotal = s.item.precio_referencia * valorEntero
      t.modState(s => s.copy(item = s.item.copy(cantidad = valorEntero, importe = subtotal )))
    }

    def onChangePrecio(e: ReactEventFromInput) = t.state >>= { s =>
      val valor = e.target.value
      val valorDouble = if ( valor.length > 0 ) valor.toDouble else 0
      val subtotal = s.item.cantidad * valorDouble
      t.modState(s => s.copy(item = s.item.copy(precio_referencia = valorDouble, importe = subtotal)))
    }

    def getPrecioRef(precioRef: ModelRO[Pot[(PrecioReferencia,String)]]) = {
      val precRef: PrecioReferencia = precioRef.value match {
        case x if x.isUnavailable => PrecioReferencia()
        case x if x.isEmpty => PrecioReferencia()
        case x if x.isFailed => PrecioReferencia()
        case _ =>
          val (precioReferen: PrecioReferencia, _ /*event: String*/ ) = precioRef.value.get
          precioReferen
      }
      t.modState(s => s.copy(item = s.item.copy(precio_referencia = precRef.precio_referencia))).runNow()
    }

    def onChangeImporte(e: ReactEventFromInput) = Callback.empty

    def onChangeRenglon(e: ReactEventFromInput) = Callback.empty

    def render(p: Props, s: State) = {

      val headerText = "" //if (s.item.id == "") "Add new todo" else "Edit todo"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.h4(headerText)),
        // footer has the OK button that submits the form before hiding it
        footer = hide => <.span(Button(Button.Props(submitForm() >> hide), "OK")),
        // this is called after the modal has been hidden (animation is completed)
        closed = formClosed(s, p)),
        <.div(bss.formGroup, ^.clear := "both", ^.height := "auto", ^.overflow := "hidden",
          <.div(^.clear := "both", ^.height := "auto", ^.overflow := "hidden",

            Text(Text.Props(idx = "txtRenglon", label = "Renglon",
              onChange = onChangeRenglon, value = s.item.renglon.toString, otrosStyles = Seq(^.width := "120", ^.readOnly:=true))),

            Text(Text.Props(idx = "txtCveArticulo", label = "Cve Articulo",
              onChange = onChangeCveArticulo, value = s.item.cve_articulo, otrosStyles = Seq(^.width := "120"))),

            TextArea(TextArea.Props(idx = "txtDescripcion", label = "Descripcion", onChange = onChangeDescripcion, value = s.item.descripcion)),

            Text(Text.Props(idx = "txtUnidad", label = "Unidad", onChange = onChangeUnidad, value = s.item.unidad))
          ),
          Text(Text.Props(idx = "txtCantidad", label = "Cantidad", onChange = onChangeCantidad, typeInput ="numero",
            value = if(s.item.cantidad > 0) s.item.cantidad.toString else "", otrosStyles = Seq(^.width := "120"))),

          Text(Text.Props(idx = "txtPrecio", label = "Precio",
            onChange = onChangePrecio, typeInput ="numero",
            value = if(s.item.precio_referencia > 0) s.item.precio_referencia.toString else "", otrosStyles = Seq(^.width := "120"))),

          Text(Text.Props(idx = "txtSubtotal", label = "Subtotal",
            onChange = onChangeImporte, typeInput = "numero", value = fmtMiles(s.item.importe), otrosStyles = Seq(^.width := "120")))

        )
      )
    }
  }

  val component = ScalaComponent.builder[Props]("frmItem")
    .initialStateFromProps(p => State(p.item.getOrElse(RenglonListBienes())))
    .renderBackend[Backend]
    .componentWillMount{ scope =>
      Callback {
        //val unsubscribe =
          //SPACircuit.subscribe(SPACircuit.zoom(_.precioReferencia))(precioRef => scope.backend.getPrecioRef(precioRef))
        val dispatch: Dispatcher = SPACircuit
        dispatch(GetPrecioReferencia(scope.state.item.cve_articulo))
      }
    }
    .build

  def apply(  item: Option[RenglonListBienes],
              submitHandler: (RenglonListBienes, Boolean) => Callback,
              editItem: Boolean
           ) =
      component(Props(item, submitHandler, editItem)  )
}