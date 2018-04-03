package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
import diode._
//import diode.react.ReactPot._
import japgolly.scalajs.react.vdom.VdomArray
//import org.scalajs.dom.ext.KeyValue
import spatutorial.client.services.SPACircuit


import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import spatutorial.client.components.Bootstrap._

import diode.{ /*ModelR, RootModelR,*/ Dispatcher}

//import japgolly.scalajs.react.extra.router.RouterCtl
//import spatutorial.client.SPAMain.{Loc, SearchArticulosLoc}
//import scala.math.BigDecimal.double2bigDecimal
import spatutorial.client.components._
//import spatutorial.client.logger._
import spatutorial.client.services._
import spatutorial.shared._

import scalacss.ScalaCssReact._

import org.scalajs.dom.raw._
import org.scalajs.dom.window

import spatutorial.client.actionhandlers.{InsertSolicitudPago, GetSolicitudPago, GetSolicitudPagoByIdComparativo, CleanSolicitudPago}
import spatutorial.client.actionhandlers.{InsertDetSolicitudPago, DeleteDetSolicitudPago, SaveDetSolicitudPago/*, GetDetSolicitudPago*/ }
import spatutorial.client.actionhandlers.{DeleteSolicitudPago, PrintSolicitudPagoByFolio}

import scalajs.js
//import js.Dynamic.{global => g}

object frmSolicitudPago {

  case class Props(/*router: RouterCtl[Loc], */proxy: ModelProxy[Pot[(SolicitudPago, String)]])

  case class State( solicitudPago: SolicitudPago = SolicitudPago(), details: Seq[DetSolicitudPago] = Seq.empty,
                    selectedItem: Option[DetSolicitudPago] = None, newItem: Boolean = false, msg: String = "",
                    showFrm: Boolean = false, usuario: String = "")

  class Backend($: BackendScope[Props, State]) {


    private var unsubscribe = Seq(Option.empty[() => Unit])

    def willMount = Callback {
      unsubscribe = Seq(Some(SPACircuit.subscribe(SPACircuit.zoom(_.printSolicitudPago))(rpt => getrptSolici(rpt).runNow())),
                    Some(SPACircuit.subscribe(SPACircuit.zoom(_.detSolicitudPago))(bills => getBills(bills))))
    }

    def willUnmount = Callback {
      unsubscribe.foreach{ f => f.foreach( f => f()) }
      unsubscribe = Seq.empty
    }

    def getrptSolici(rpts: ModelRO[Pot[(RptSolicitudPago, String)]]) = $.state >>= { s =>

      val rpt = rpts.value match {
        case x if x.isUnavailable => RptSolicitudPago()
        case x if x.isEmpty => RptSolicitudPago()
        case x if x.isFailed => RptSolicitudPago()
        case _ => rpts.value.get._1
      }

      if (rpt.folio == "") Callback.alert("No hay datos")
      else Callback {
        val rpt = new rptSolicitudPago()
        rpt.imprimir
      }

    }

    def onChangeIdComparativo(e: ReactEventFromInput) = $.state >>= { s =>
      val text = e.target.value
      if (text.takeRight(4) == "ADQ2" && !s.newItem)
        $.modState(s => s.copy(solicitudPago = s.solicitudPago.copy(id_comparativo = text.toUpperCase)), onBuscarByIdCompara(text))
      else
        $.modState(s => s.copy(solicitudPago = s.solicitudPago.copy(id_comparativo = text.toUpperCase)))
    }

    def onBuscarByIdCompara(id: String) = $.props >>= { p => p.proxy.dispatchCB(GetSolicitudPagoByIdComparativo(id)) }

    def onBuscarByFolio(folio: String, ejercicio: Int) = $.props >>= {
      p => p.proxy.dispatchCB(GetSolicitudPago(IdSolicitudPago(folio = folio, ejercicio = ejercicio)))
    }

    def onDeleteSolicitudPago = $.state.zip($.props) >>= { case (s, p) =>
       p.proxy.dispatchCB(DeleteSolicitudPago(IdSolicitudPago(folio = s.solicitudPago.folio, ejercicio = s.solicitudPago.ejercicio)))
    }

    //def editItem(item: Option[RenglonListBienes]) = Callback.empty

    def onEditFactura(detSolicitudPago: DetSolicitudPago) =
      $.modState(s => s.copy(selectedItem = Some(detSolicitudPago), showFrm = true/*, newFactura = false*/))

    def onAddFactura = $.modState( s => s.copy(showFrm = true, selectedItem = None))

    def onDeleteFactura(detSolicitudPago: DetSolicitudPago) = $.state.zip($.props) >>= { case (s, p) =>
        p.proxy.dispatchCB(DeleteDetSolicitudPago(detSolicitudPago))
      //$.modState(s => s.copy(selectedItem = Some(detSolicitudPago), showFrm = true))
    }

    /*def SelectFactura(detSolicitudPago: DetSolicitudPago) =
      $.modState(s => s.copy(selectedItem = Some(detSolicitudPago), showFrm = true))*/

    /*def itemEdited(item: RenglonListBienes, cancelled: Boolean) = {
      val cb = if (cancelled) Callback.log("Todo editing cancelled")
      else Callback.log(s"Todo edited: $item")
      cb >> $.modState(s => s.copy(showTodoForm = false))
    }*/

    //def onAdd = $.state.zip($.props) >>= { case (s, p) =>
      //p.router.set(SearchArticulosLoc)//>> p.proxy.dispatchCB(SetActiveID( s.proceso.id_comparativo))
    //}

    def onChangeFolio(e: ReactEventFromInput) = $.state >>= { s =>
      /*val text = e.target.value
      $.modState(s => s.copy(solicitudPago = s.solicitudPago.copy( folio = text.toInt)))*/
      val text = e.target.value
      if (text.length == 4  && !s.newItem)
        $.modState(s => s.copy(solicitudPago = s.solicitudPago.copy(folio = text)), onBuscarByFolio(text, s.solicitudPago.ejercicio))
      else
        $.modState(s => s.copy(solicitudPago = s.solicitudPago.copy(folio = text)))
    }

    def onChangeEjercicio(e: ReactEventFromInput) = $.state >>= { s =>
      val text = e.target.value
      $.modState(s => s.copy(solicitudPago = s.solicitudPago.copy( ejercicio = text.toInt)))
    }

    def onChangeFecha(e: ReactEventFromInput) = $.state >>= { s =>
      val text = e.target.value
      $.modState(s => s.copy(solicitudPago = s.solicitudPago.copy(fecha = Fechas(fecha = text))))
    }

    def onChangeFolioRecibidoRM(e: ReactEventFromInput) = $.state >>= { s =>
      val text = e.target.value
      $.modState(s => s.copy(solicitudPago = s.solicitudPago.copy(folio_recibido_recurs_mat = text)))
    }

    def onChangeFechaRecibidoRF(e: ReactEventFromInput) = $.state >>= { s =>
      val text = e.target.value
      $.modState(s => s.copy(solicitudPago = s.solicitudPago.copy(fecha_recibido_rec_finan = Fechas(fecha = text))))
    }

    def onInsert = $.state.zip($.props) >>= { case (s, p) =>
        p.proxy.dispatchCB(InsertSolicitudPago(s.solicitudPago))
    }

    def EditFactura(detSolicitudPago: DetSolicitudPago) = Callback.empty

    def itemEdited(item: DetSolicitudPago, cancelled: Boolean) = $.modState(s => s.copy(showFrm = false))

    def onCancel = $.state.zip($.props) >>= { case (s, p) =>
      p.proxy.dispatchCB(CleanSolicitudPago) >> $.modState(s => s.copy(newItem = false, msg = ""))
    }

    def onNew = $.state.zip($.props) >>= { case (s, p) =>
      p.proxy.dispatchCB(CleanSolicitudPago) >> $.modState(s => s.copy(solicitudPago = SolicitudPago(), newItem = true, msg = "Nuevo"))
    }

    def onPrint = $.state >>= { s =>
      Callback {
        val dispatch: Dispatcher = SPACircuit
        dispatch.dispatch(PrintSolicitudPagoByFolio(s.solicitudPago.folio, s.solicitudPago.ejercicio))
      }
    }

    def getBills(bills: ModelRO[Pot[(Seq[DetSolicitudPago], String)]]) = {
      val (bill: Seq[DetSolicitudPago], _) = bills.value match {
        case x if x.isUnavailable => (Seq(DetSolicitudPago()), "No se encontro")
        case x if x.isEmpty => (Seq(DetSolicitudPago()), "Nuevo")
        case x if x.isFailed => (Seq(DetSolicitudPago()), "Error")
        case _ => bills.value.get
      }
      //In this context is right to use ".runNow()"
      $.modState(s => s.copy(details = bill) ).runNow()
    }

    def render(p: Props, s: State) = {

      val colorMsg = if (s.newItem) " #f9927b" else " #9ff594 "

      Panel(Panel.Props("Solicitud de pago"),

        if(s.msg == "")  VdomArray.empty()
        else <.span(s.msg, ^.backgroundColor := colorMsg, ^.fontSize := "20", ^.fontWeight := "true"),

        <.div(/*^.clear:="both",*/ ^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
          ^.borderColor := "#20B2AA", ^.height := "auto", ^.overflow := "hidden",

          Text(Text.Props(idx="txtIdComparativo", label="Id Comparativo",
            onChange = onChangeIdComparativo, value = s.solicitudPago.id_comparativo, otrosStyles = Seq(^.width := "150"))),

          Text(Text.Props(idx="txtFolio", label="Folio", onChange = onChangeFolio,
            value = s.solicitudPago.folio, otrosStyles = Seq(^.width := "100"))),

          Text(Text.Props(idx="txtEjercicio", label = "Ejercicio", onChange = onChangeEjercicio,
            value = if (s.solicitudPago.ejercicio == 0) "" else s.solicitudPago.ejercicio.toString, otrosStyles = Seq(^.width := "100"))),

          Fecha(Fecha.Props(idx="txtFecha", label="Fecha",
            onChange = onChangeFecha, value = s.solicitudPago.fecha.fecha, otrosStyles = Seq(^.width := "60"))),

          Fecha(Fecha.Props(idx="FolioRM", label="Folio R.M.",
            onChange = onChangeFolioRecibidoRM, value = s.solicitudPago.folio_recibido_recurs_mat, otrosStyles = Seq(^.width := "60"))),

          Fecha(Fecha.Props(idx="txtRecibidoRF", label="Recibido R.F.",
            onChange = onChangeFechaRecibidoRF, value = s.solicitudPago.fecha_recibido_rec_finan.fecha, otrosStyles = Seq(^.width := "60"))),

          <.div(^.clear:="both", ^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
              ^.borderColor := "#20B2AA", ^.height := "auto", ^.overflow := "hidden",
            <.span("Menu datos generales de la solicitud"),
            Button(Button.Props(onPrint), Icon.binoculars, "Imprimir"),
            Button(Button.Props(onDeleteSolicitudPago), Icon.binoculars, "Eliminar"),
            Button(Button.Props(onInsert), Icon.binoculars, "Agregar"),
            if (s.newItem)
              Button(Button.Props(onCancel), Icon.binoculars, "Cancelar")
            else
              Button(Button.Props(onNew), Icon.binoculars, "Nuevo")

        )
      ),
        <.div(^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
          ^.borderColor := "#20B2AA", ^.height := "auto", ^.overflow := "hidden",

          if (s.msg == "encontrado" || s.msg == "Guardado" || s.msg == "Agregado"){
              <.div(^.clear:="both", ^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
                ^.borderColor := "#20B2AA", ^.height := "auto", ^.overflow := "hidden",
                <.span("Acciones con las facturas."),
                Button(Button.Props(onAddFactura), Icon.binoculars, "Agregar Factura")
              )
          } else { VdomArray.empty },

          //p.proxy().renderFailed(ex => "Error loading"),
          //p.proxy().renderPending(_ > 500, _ => <.p("Loading...") ),
          //p.proxy().renderEmpty( "No hay facturas :) !"),

          //p.proxy().render(items =>
            <.div(^.padding := "10", ^.border := "1px solid", ^.marginTop := "3", ^.borderColor := "#20B2AA",
              ^.height := "auto", ^.overflow := "hidden",
              cmpLstFacturas(s.details, onEditFactura, onDeleteFactura)
            )
          //)
        ),
        if (s.showFrm) {
          val newItem = true
          if( s.selectedItem == None ) {
            val fact: DetSolicitudPago = DetSolicitudPago(folio = s.solicitudPago.folio, ejercicio = s.solicitudPago.ejercicio)
            frmFactura(Some(fact), itemEdited, newItem, s.details.length + 1)
          } else {
            frmFactura(s.selectedItem, itemEdited, !newItem, 0)
          }
        } else VdomArray.empty
      )
    }
  }

  val component = ScalaComponent.builder[Props]("frmSolicitudPago")
    .initialState( State(solicitudPago = SolicitudPago()))
    .renderBackend[Backend]
    .componentWillReceiveProps { rp =>
      val idCompara = rp.state.solicitudPago.id_comparativo
      val prevFolio = rp.state.solicitudPago.folio
      val prevEjercicio = rp.state.solicitudPago.ejercicio

      val (solicPago: SolicitudPago, msg: String) = rp.nextProps.proxy() match {
        case x if x.isUnavailable =>
            if (rp.state.newItem) (SolicitudPago(), "Nuevo")
            else (SolicitudPago(id_comparativo = idCompara, folio = prevFolio, ejercicio = prevEjercicio),"No se encontro")
        case x if x.isEmpty => (SolicitudPago(), "Nuevo")
        case x if x.isFailed => (SolicitudPago(), "Error")
        case _ =>  rp.nextProps.proxy().get
      }

      val newItem = if(msg == "Nuevo") true else false


      val user = SPACircuit.zoom(_.usuarios).value.getOrElse(Usuarios())
      //scope.modState(s => s.copy(usuario = user.usuario))

      rp.setState(State( solicitudPago = solicPago, msg = msg, newItem = newItem, usuario = user.usuario))
    }
    .componentWillMount(_.backend.willMount)
    .componentWillUnmount(_.backend.willUnmount)
    .build

  /** Returns a function compatible with router location system while using our own props */
  def apply() = {
    val solicitudPagoWraper = SPACircuit.connect(_.solicitudPago)
    solicitudPagoWraper(prox => component(Props(proxy = prox)))
  }
}


object frmFactura {

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  //case class Props(item: Option[TodoItem], submitHandler: (TodoItem, Boolean) => Callback)
  case class Props(item: Option[DetSolicitudPago], submitHandler: (DetSolicitudPago, Boolean) => Callback, newItem: Boolean, lastRenglon: Int)

  //case class State(item: TodoItem, cancelled: Boolean = true)
  case class State(item: DetSolicitudPago, cancelled: Boolean = true)

  //val txtCveArticulo = Ref[HTMLInputElement]("txtCveArticulo")

  class Backend(t: BackendScope[Props, State]) {


    def submitForm() = t.state.zip(t.props) >>= { case (s, p) =>
      Callback {
        val dispatch: Dispatcher = SPACircuit

        if (!p.newItem && window.confirm("Modificar?")) dispatch(SaveDetSolicitudPago(s.item))
        else dispatch(InsertDetSolicitudPago(s.item))
      } >> t.modState(s => s.copy(cancelled = false))
    }

    def formClosed(state: State, props: Props) = props.submitHandler(state.item, state.cancelled)

    def onChangeAlta(e: ReactEventFromInput) = {
      val txt = e.target.value
      t.modState(s => s.copy(item = s.item.copy(alta_almacen = txt)))
    }

    def onChangeEjercicioAlta(e: ReactEventFromInput) = {
      val txt = e.target.value
      t.modState(s => s.copy(item = s.item.copy(ejercicio_alta = txt.toInt)))
    }

    def onChangeFechaAlta(e: ReactEventFromInput) = {
      val txt = e.target.value
      t.modState(s => s.copy(item = s.item.copy(fecha_alta = Fechas(fecha = txt))))
    }

    def onChangeFactura(e: ReactEventFromInput) = {
      val txt = e.target.value
      t.modState(s => s.copy(item = s.item.copy(factura = txt)))
    }

    def onChangeFolioControl(e: ReactEventFromInput) = {
      val txt = e.target.value
      t.modState(s => s.copy(item = s.item.copy(folio_control = Some(txt))))
    }

    def onChangeFecha(e: ReactEventFromInput) = {
      val txt = e.target.value
      t.modState(s => s.copy(item = s.item.copy(fecha = Fechas(fecha = txt))))
    }

    def fmtMiles(valor: BigDecimal, dec: Int = 2): String =
      valor.toDouble.asInstanceOf[js.Dynamic]
        .toLocaleString("en-US",js.Dynamic.literal("minimumFractionDigits" -> dec),js.Dynamic.literal("maximumFractionDigits" -> dec),
          js.Dynamic.literal("style" -> "currency"),js.Dynamic.literal("currency" -> "US")).asInstanceOf[String]

    def onChangeImporte(e: ReactEventFromInput) = t.state >>= { s =>
      val txt = e.target.value
      val dblValor = if (txt.length > 0) txt.toDouble else 0
      t.modState(s => s.copy(item = s.item.copy(importe = dblValor)))
      //Callback.empty
    }





    def render(p: Props, s: State) = {
      val headerText = "" //if (s.item.id == "") "Add new todo" else "Edit todo"
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.h4(headerText)),
        // footer has the OK button that submits the form before hiding it
        footer = hide => <.span(Button(Button.Props(submitForm() >> hide), "OK")),
        // this is called after the modal has been hidden (animation is completed)
        closed = formClosed(s, p)),

        <.div(bss.formGroup, ^.clear := "both", ^.height := "auto", ^.overflow := "hidden", ^.width := "800",

          <.div(^.clear := "both", ^.height := "auto", ^.overflow := "hidden", ^.width := "600",

            Text(Text.Props(idx = "txtFactura", label = "Factura",
              onChange = onChangeFactura, value = s.item.factura, otrosStyles = Seq(^.width := "120"))),
            Text(Text.Props(idx = "txtFecha", label = "Fecha",
              onChange = onChangeFecha, value = s.item.fecha.fecha, otrosStyles = Seq(^.width := "120"))),

            Number(Number.Props(idx = "txtImporte", label = "Importe",
              onChange = onChangeImporte, value = s.item.importe.toString, otrosStyles = Seq(^.width := "120"))),

            Text(Text.Props(idx = "txtFolioControl", label = "Folio Control",
              onChange = onChangeFolioControl, value = s.item.folio_control.getOrElse(""), otrosStyles = Seq(^.width := "120")))


          ),
          <.div(^.clear := "both", ^.height := "auto", ^.overflow := "hidden", ^.width := "600",

            Text(Text.Props(idx = "txtAlta", label = "Alta",
              onChange = onChangeAlta, value = s.item.alta_almacen, otrosStyles = Seq(^.width := "120"))),

            Text(Text.Props(idx="txtEjercicioAlta", label = "Ejercicio alta", onChange = onChangeEjercicioAlta,
              value = if (s.item.ejercicio_alta == 0) "" else s.item.ejercicio_alta.toString, otrosStyles = Seq(^.width := "200"))),

            Text(Text.Props(idx = "txtFecha", label = "Fecha",
              onChange = onChangeFecha, value = s.item.fecha_alta.fecha, otrosStyles = Seq(^.width := "120")))

          )
        )
      )
    }
  }

  val component = ScalaComponent.builder[Props]("frmItem")
    //.initialState_P(p => State(p.item.getOrElse(RenglonListBienes())))
    .initialStateFromProps(p => State(p.item.getOrElse(DetSolicitudPago())))
    .renderBackend[Backend]
    .componentWillMount{ scope =>
      val fact = scope.props.item.getOrElse(DetSolicitudPago())
      if(scope.props.newItem) scope.modState(s => s.copy(item = fact.copy(renglon = scope.props.lastRenglon)))
      else scope.modState(s => s.copy(item = fact.copy() ))
    }
    .build

  //def apply(props: Props) = component(props)
  def apply(  item: Option[DetSolicitudPago], submitHandler: (DetSolicitudPago, Boolean) => Callback, newItem: Boolean, lastRenglon: Int) =
    component(Props(item , submitHandler, newItem, lastRenglon))
}