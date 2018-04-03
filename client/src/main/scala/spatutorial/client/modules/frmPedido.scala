package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
import diode._
//import spatutorial.client.pdfMake._

import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.html_<^._

import spatutorial.client.components.Bootstrap.{Button, /*Combo, CommonStyle,*/ Text}
import spatutorial.client.components._

//import spatutorial.client.logger._
import spatutorial.client.services._
import spatutorial.shared._
//import scala.concurrent.Future
//import scala.util.{Success, Failure, Try}

//import scala.concurrent.duration._
//import scala.concurrent.Await
//import scalacss.ScalaCssReact._
//import chandu0101.scalajs.react.components.Implicits._

//import autowire._
//import boopickle.Default._
//import spatutorial.client.services.AjaxClient
//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

//import scalajs.js
//import js.Dynamic.{ global => g }
//import scala.scalajs.js.{|, Date => jsDate}

//import java.time._
/*import java.util._
import java.text.SimpleDateFormat*/

import spatutorial.client.actionhandlers.{GetQryPedidoDatGrales, CleanQryPedidos, GetPartidaAnexo, SendEnviarAlmacen}

//import spatutorial.client.actionhandlers.{GetTotalDias, GetDescripcionCompra}

//https://github.com/jducoeur/bootstrap-datepicker-scalajs
//import org.querki.facades.bootstrap.datepicker._ // this adds '.datepicker' implicit

//import java.time.LocalDate
//import java.time.format.DateTimeFormatter

//import org.scalajs.dom.window

//import scalacss.internal.mutable.GlobalRegistry
//import scalacss.Defaults._

//import chandu0101.scalajs.react.components.Implicits._
//import chandu0101.scalajs.react.components.ReactTable

object frmPedido {

  //GlobalRegistry.register(ReactTable.DefaultStyle)

  //GlobalRegistry.addToDocumentOnRegistration()

  val columns: List[String] = List("numero", "tipo")

  type Model = Vector[Map[String, Any]]


  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(proxy: ModelProxy[Pot[(Seq[QryPedidoDatosGrales],Seq[DetallePedido], String)]] )

  case class State( item: QryPedidoDatosGrales, itemDetails: Seq[DetallePedido],
                    editar: Boolean = false, saved: Boolean = false,
                    msg: String = "", printDisabled: Boolean = false)

  val tipo_dcto_compra: Seq[String] = Seq("PEDI", "PROP")

	class Backend($: BackendScope[Props, State]) {

    def digits00(valor: Int) = if (valor > 1 && valor < 10) "0" + valor else valor

    def Hoy = {
      import java.time.{LocalDate}
      val hoy = LocalDate.now()
      digits00(hoy.getDayOfMonth) + "/" + digits00(hoy.getMonthValue) + "/" + hoy.getYear
    }

    //def onCancel = Callback { val reason = window.prompt("!Introduce el motivo de la cancelación!") }

    def onChangeNoPedido(e: ReactEventFromInput) = $.state.zip($.props) >>= { case (s, p) =>
      val text = e.target.value

      if(text.length >= 4)
        $.modState(s => s.copy(item = s.item.copy(no_pedido = text)))
      else {
        $.modState(s => s.copy(item = s.item.copy(no_pedido = text), printDisabled = true)).runNow() //>>
        p.proxy.dispatchCB(CleanQryPedidos)
      }
    }

		def onChangedTipoCompra(e: ReactEventFromInput): Callback = {
			val text = e.target.value.toUpperCase
      $.modState(s => s.copy(item = s.item.copy(tipo_compra  = text)))
		}

    def onChangeDestino(e: ReactEventFromInput) = {
      val text = e.target.value.toUpperCase
      $.modState( s => s.copy( item = s.item.copy(destino = text)))
    }

    def onChangePrograma(e: ReactEventFromInput) = {
      val text = e.target.value.toUpperCase
      $.modState(s=> s.copy( item = s.item.copy(programa = text)))
    }

    def onChangePresupuesto(e: ReactEventFromInput): Callback = {
      val num = e.target.value.toInt
      $.modState(s=>s.copy(item = s.item.copy(ejercicio_presupuesto = num)))
    }

		def onChangePlazo(e: ReactEventFromInput): Callback = {
      val text = e.target.value.toUpperCase
      $.modState(s => s.copy(item = s.item.copy(fecha_entrega = text)))
    }

		def onChangeEjercicio(e: ReactEventFromInput): Callback = {
			val num = e.target.value.toInt
			$.modState(s => s.copy(item = s.item.copy(ejercicio = num)))
		}

		def onChangeFecha(e: ReactEventFromInput): Callback = {
			val text = e.target.value
      $.modState(s => s.copy(item = s.item.copy(fecha_pedido = Fechas(text))))
		}

    def onDelete = Callback.alert("fmt.format(new Date())")

    def fechaFmt(dia: Int, mes: Int, año: Int): String = dia.toString  + "/" + mes.toString + "/" + año.toString

		def cboChangedCompra(e: ReactEventFromInput): Callback = {
			val text = e.target.value.toUpperCase
			$.modState(s => s.copy(item = s.item.copy(compra = text)))
		}

		def onChangeTipoCompra(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s => s.copy(item = s.item.copy(tipo_compra = text)))
		}

    def onChangeProveedor(e: ReactEventFromInput) = {
      val text = e.target.value.toUpperCase
      $.modState(s => s.copy(item = s.item.copy(proveedor = text)))
    }

    def onChangeElaboro(e: ReactEventFromInput) = {
      val text = e.target.value.toUpperCase
      $.modState(s => s.copy(item = s.item.copy(elaboro = text)))
    }

    def onChangeIdComparativo(e: ReactEventFromInput) = {
      val text = e.target.value.toUpperCase()
      $.modState(s => s.copy(item = s.item.copy(id_comparativo = text)))
    }

		def onBuscarTipoCompra = Callback.alert("Para Buscar")

    def onSearch = $.state.zip($.props) >>= { case (s,p) =>
      val buscar = PedidoId(no_pedido = s.item.no_pedido, compra = s.item.compra, ejercicio = s.item.ejercicio)
      p.proxy.dispatchCB(GetQryPedidoDatGrales(buscar))
    }

    def onSave = $.state.zip($.props) >>= { case (s,p) => Callback.alert("Pa guardar") }

    def addComa(str: String): String = {
      val partEntera = str.substring(0, str.length - 3)
      val partDecimal = str.substring(str.length-3, str.length)

      val miles: String = if (partEntera.length > 6) {
        val len = partEntera.length
        partEntera.substring(0,len-6) + "," + partEntera.substring(len-6,(len-3)) + "," + partEntera.substring(len-3,len)
      } else if (partEntera.length > 3) {
        partEntera.substring(0, partEntera.length-3) + "," + partEntera.substring(partEntera.length-3, partEntera.length)
      } else partEntera

      miles + partDecimal
    }

    def fmtMiles(valor: BigDecimal, dec: Int = 2): String = {
      import scala.scalajs.js.JSNumberOps._
      val newValor = if (dec>0) valor.toDouble.toFixed(dec) else valor.toDouble.toFixed(0)
      addComa(newValor)
    }

    def NumToMonth(n: Int): String =
      if(n == 1) "Enero"
      else if(n == 2) "Febrero"
      else if(n == 3) "Marzo"
      else if(n == 4) "Abril"
      else if(n == 5) "Mayo"
      else if(n == 6) "Junio"
      else if(n == 7) "Julio"
      else if(n == 8) "Agosto"
      else if(n == 9) "Septiembre"
      else if(n == 10) "Octubre"
      else if(n == 11) "Noviembre"
      else "Diciembre"

    /*def onAlmacen = $.state.zip($.props) >>= { case (s, p) =>
      Callback {
        val test = EnviarAlmacen(no_pedido = s.item.no_pedido, compra = "PEDI", ejercicio = s.item.ejercicio)
        val dispatch: Dispatcher = SPACircuit
        dispatch(SendEnviarAlmacen(test))
      }
    }*/

    def onImprimir = $.state.zip($.props) >>= { case (s, p) =>
      Callback {

        //val readerUser = SPACircuit.zoom(_.usuarios).value.get

        /*val user = readerUser.value match {
          case x if x.isUnavailable => Usuarios()
          case x if x.isEmpty => Usuarios()
          case x if x.isFailed => Usuarios()
          case _ => users.value.get
        }*/

        if(s.itemDetails.head.partida.take(2) != "32") {
          val test = EnviarAlmacen(no_pedido = s.item.no_pedido, compra = "PEDI", ejercicio = s.item.ejercicio)
          val dispatch: Dispatcher = SPACircuit
          dispatch(SendEnviarAlmacen(test))
        }

        rptMasterDetails.imprimir(s.item, s.itemDetails)
      }
    }

    def	render(s: State) = {

      <.div(^.padding := "10",  ^.marginLeft:="100",  ^.border := "1px solid", ^.marginTop := "3", ^.borderColor:="#20B2AA",
              ^.height := "auto", ^.overflow := "hidden",
        <.header(<.h4("Pedidos")),
        <.span(s.msg),
        <.div(^.height := "auto", ^.overflow := "hidden",
          Text(Text.Props(idx = "txtEjercicio", label="Ejercicio",  onChange = onChangeEjercicio,
            value = s.item.ejercicio.toString, otrosStyles = Seq(^.width := "60"))),

          Text(Text.Props(idx = "txtPresupuesto", label = "Presupuesto",
            onChange = onChangePresupuesto, value = s.item.ejercicio_presupuesto.toString, otrosStyles = Seq(^.width := "60"))),

          //cambiar por Text
          /*Combo(Combo.Props(idx="Compra", label="Compra",
            onChange = cboChangedCompra, values = tipo_dcto_compra, value = s.item.compra)),*/

          Text(Text.Props(idx="txtNoPedido", label="No. Pedido",
            onChange = onChangeNoPedido, value=s.item.no_pedido, otrosStyles = Seq(^.width := "60"))),

          Text(Text.Props(idx = "txtIdComparativo", label="IdComparativo",
            onChange = onChangeIdComparativo, value = s.item.id_comparativo,
            otrosStyles = Seq(^.width := "150"))),

          Text(Text.Props(idx = "txtPlazo", label="Plazo Entrega",
            onChange = onChangePlazo, value=s.item.fecha_entrega, otrosStyles = Seq(^.width := "140"))),

          Text(Text.Props(idx="txtFecha", label="Fecha", onChange=onChangeFecha,
            value = s.item.fecha_pedido.fecha, otrosStyles = Seq(^.width := "100"))),

          Text(Text.Props(idx="txtTipoCompra", label="Tipo compra",
            onChange = onChangeTipoCompra, value=s.item.tipo_compra, otrosStyles = Seq(^.width := "120"))),
          <.div(^.clear:="both", ^.height := "auto", ^.overflow := "hidden",
            Text(Text.Props(idx="txtDestino", label="Destino",
              onChange = onChangeDestino, value=s.item.destino, otrosStyles = Seq(^.width := "250"))),
            Text(Text.Props(idx="txtPrograma", label="Programa",
              onChange = onChangePrograma, value=s.item.programa, otrosStyles = Seq(^.width := "120"))),
            Text(Text.Props(idx="txtRFC", label="R.F.C.",
              onChange = onChangeProveedor, value=s.item.proveedor, otrosStyles = Seq(^.width := "140"))),
            Text(Text.Props(idx="txtElaboro", label="Elaboro",
              onChange = onChangeElaboro, value=s.item.elaboro, otrosStyles = Seq(^.width := "140")))
          )
        ),
        <.div( ^.padding := "10", ^.border:="1px solid", ^.marginTop := "30", ^.borderColor:="#20B2AA",
              ^.height := "auto", ^.overflow := "hidden",
          Button(Button.Props(onSearch, addStyles = Seq(bss.button)), "Buscar"),
          Button(Button.Props(onImprimir, addStyles = Seq(bss.button), otrosStyles = Seq(^.disabled:=s.printDisabled)), "Imprimir")/*,
          Button(Button.Props(onAlmacen, addStyles = Seq(bss.button)), "Almacèn")*/
        )
      )
		}
	}


	val component = ScalaComponent.builder[Props]( "frmPedido" )
		.initialState(State(item = QryPedidoDatosGrales( /*no_pedido = "0305", */fecha_pedido = Fechas(fecha = "")),
                       itemDetails = Seq(DetallePedido())))
		.renderBackend[Backend]
    .componentWillReceiveProps { rp =>

        val (pedido: QryPedidoDatosGrales, itemdetails: Seq[DetallePedido], msg: String, printAct) = rp.nextProps.proxy() match {
          case x if x.isUnavailable => {
            (QryPedidoDatosGrales, Seq(DetallePedido()), "No se encontro", true)
          }
          case x if x.isEmpty => (QryPedidoDatosGrales( no_pedido = rp.state.item.no_pedido), Seq(DetallePedido()), "Nuevo", true)
          case x if x.isFailed => {
            (QryPedidoDatosGrales(), Seq(DetallePedido()), "Error", true)
          }
          case _ => {
            val (ped, details, event) = rp.nextProps.proxy().get
            if (ped.isEmpty && event == "not found")
                (QryPedidoDatosGrales(no_pedido = rp.state.item.no_pedido), details, "No encontrado", true)
            else {
              if(event == "found") {
                val pedido = ped.head
                val dispatch: Dispatcher = SPACircuit
                dispatch(GetPartidaAnexo(pedido.id_comparativo))
                (pedido, details, "Encontrado", false)
              }
              else (ped.head, details, "Guardado", false)
            }
          }
        }

        rp.setState(State(item = pedido, itemDetails = itemdetails, printDisabled = printAct, msg = msg))
    }
    .componentDidMount(scope => scope.backend.onSearch)
    .componentWillMount{ scope =>
      scope.modState(s => s.copy(item = s.item.copy(compra = "PEDI", ejercicio=2018), printDisabled = true))
    }
		.build

  def apply() = {
    val pedidoWraper = SPACircuit.connect(_.qrypedidodatosgrales)
    pedidoWraper(prox => component(Props(proxy = prox)))
  }

}
