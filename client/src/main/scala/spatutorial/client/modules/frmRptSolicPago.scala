package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
//import diode._
import japgolly.scalajs.react.vdom.{VdomArray/*, VdomElement*/}


import spatutorial.client.services.SPACircuit
import spatutorial.client.components.Bootstrap.{Button, /*TextArea, RadioButton, CheckBox, CommonStyle,*/ Text}
import spatutorial.client.components._

//import spatutorial.client.logger._
import spatutorial.client.services._
import spatutorial.shared._
//import scala.concurrent.Future
//import scala.util.{Success, Failure, Try}

//import scala.concurrent.duration._

//import scala.concurrent.Await
//import scalacss.ScalaCssReact._

//import autowire._
//import boopickle.Default._
//import spatutorial.client.services.AjaxClient
//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.html_<^._

import spatutorial.client.actionhandlers.{/*GetAllRptSolicitudPago,*/ GetBetweenDatesRptSolicitudPago}

//import org.scalajs.dom.window

import scala.scalajs.js.{| /*, Date => jsDate*/ }
import scalajs.js.Dynamic.{ global => g }

import spatutorial.client.excelPlus._

object frmRptSolicPago {
//class frmDocumentos {
	@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(proxy: ModelProxy[Pot[(Seq[RptSolicitudPago], String)]])

	case class State(lst: Seq[RptSolicitudPago] = Seq.empty, dateStart: String = "", dateEnd: String = "", msg: String = "")

	class Backend($: BackendScope[Props, State]) {

		def digits00(valor: Int) = if (valor > 1 && valor < 10) "0" + valor else valor

		def Hoy = {
			/*import java.time.{LocalDate}
			val hoy = LocalDate.now()
			digits00(hoy.getDayOfMonth) + "/" + digits00(hoy.getMonthValue) + "/" + hoy.getYear*/

			val hoy: scalajs.js.Date = new scalajs.js.Date()
			digits00(hoy.getDay) + "/" + digits00(hoy.getMonth ) + "/" + hoy.getFullYear

		}

		def onFiltrar = $.state.zip($.props) >>= { case (s, p) =>
			p.proxy.dispatchCB(GetBetweenDatesRptSolicitudPago(s.dateStart, s.dateEnd))
		}

		def onExcel: Callback = $.state >>= { s =>
			Callback {
				if ( s.lst.isEmpty ) {
					g.alert("No hay elementos que guardar, por favor defina fechas y de click en el boton ''Actualizar''")
				} else {
						val Xls = new ExcelPlus
						Xls.createFile("reporte")

						type RowExcel = Seq[String | Double | Int]

						val printLst = s.lst.map { item =>
							Seq(
								item.id_comparativo, item.ejercicio, item.folio, item.no_pedido, item.fecha.fecha,

								item.alta_almacen,
								item.fecha_alta.fecha,
								item.fecha_pedido.fecha,
								item.ejercicio_pedido, item.ejercicio_presupuesto, item.tipo_compra, item.requerimiento,
								item.destino, item.cve_provedor, item.razon_social,
								item.programa, item.programa,
								item.partida, item.partida,
								item.subtotal, item.iva, item.total): RowExcel
						}.toSeq

						/*val totales: List[Double] = s.lista.map { Item =>
							val total: BigDecimal = Item.total
							total.toDouble
						}.toList*/
						val columns: RowExcel = List(
							"id comparativo", "ejercicio", "folio", "contrato", "fecha",
							"alta almacen", "fecha alta", "fecha pedido",
							"ejercicio pedido", "ejercicio presupuesto", "tipo compra", "requerimiento", 	"destino", "rfc", "razon social",
							"programa", "partida", "subtotal", "iva", "total")

						//val granTotal: Double = totales.foldLeft(0.0)(_ + _)
						//val footerTotales = Seq(Seq("", "", "", "", "", "", "", "", "Grand Total", 75.00))
						Xls.write(Options(content = columns.asInstanceOf[RowExcel] +: printLst /*:+ footerTotales.asInstanceOf[RowExcel]*/))
						Xls.saveAs("Reporte.xlsx")
					}
				}
		}

		def OnChangeDateStart(e: ReactEventFromInput) = {
				val text = e.target.value.toUpperCase
				$.modState(s => s.copy(dateStart = text))
		}

		def OnChangeDateEnd(e: ReactEventFromInput) = {
				val text = e.target.value.toUpperCase
				$.modState(s => s.copy(dateEnd = text))
		}


		def	render(s: State) = {
			<.div(^.padding := "30",  ^.marginLeft:="100",  ^.border := "1px solid", ^.marginTop := "3", ^.borderColor:="#20B2AA",
				^.height := "auto", ^.overflow := "hidden",
			<.header( <.h1( "Reportes en Excel")),

				<.div(^.padding := "30",  ^.marginLeft:="100",  ^.border := "1px solid", ^.marginTop := "3", ^.borderColor:="#20B2AA",
					^.height := "auto", ^.overflow := "hidden",

					Text(Text.Props(idx = "txtCveOficina", label = "Cve. Oficina", value = s.dateStart,
						onChange = OnChangeDateStart, addStyles = Seq( bss.marginRight ), otrosStyles = Seq(^.width := "120"))),

					Text(Text.Props(idx = "txtCveOficina", label = "Cve. Oficina", value = s.dateEnd,
						onChange = OnChangeDateEnd, addStyles = Seq( bss.marginRight ), otrosStyles = Seq(^.width := "120")))
				),
				<.div(^.padding := "10", ^.border:="solid", /*^.marginTop:=50,*/ ^.borderColor:="#20B2AA",
					//Button(Button.Props(onExcel, addStyles = Seq( bss.buttonXS), otrosStyles = Seq(^.disabled := s.buttonDisabled)), "Excel")
					Button(Button.Props(onFiltrar, addStyles = Seq(bss.button)), "Filtrar"),
					if (s.msg == "Encontrado") Button(Button.Props(onExcel, addStyles = Seq(bss.button)), "Excel") else VdomArray.empty
				)
			)
		}
	}

	val component = ScalaComponent.builder[Props]("frmRptSolicPago")
		.initialState(State())
		.renderBackend[Backend]
		.componentWillReceiveProps{ rp =>
			val (lst: Seq[RptSolicitudPago], event: String) = rp.nextProps.proxy() match {
				case x if x.isUnavailable => Seq.empty
				case x if x.isEmpty => Seq.empty
				case x if x.isFailed => Seq.empty
				case _ => {
					val (seg: Seq[RptSolicitudPago], evento: String) = rp.nextProps.proxy().get
					val message = if(evento == "inserted") "Guardado"
					else if (evento == "saved")	"Modificado"
					else if (evento == "found") "Encontrado"
					else "No encontrado"
					(seg, message)
				}
			}
			rp.setState(State(lst = lst, dateStart = rp.state.dateStart, dateEnd = rp.state.dateEnd, msg = event))
		}
		.componentWillMount{ scope =>
			scope.modState( s => s.copy(dateStart = "01/01/2017", dateEnd = scope.backend.Hoy))
			//scope.modState( s => s.copy(dateStart = scope.backend.Hoy, dateEnd = scope.backend.Hoy))
		}
		.build

  def apply() = {
		val RptSolicitudPagoWraper = SPACircuit.connect(_.rptSolicitudPago)
		RptSolicitudPagoWraper(prox => component(Props(proxy = prox)))
	}

}
