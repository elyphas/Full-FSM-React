package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
//import diode._
//import japgolly.scalajs.react.vdom.{VdomArray, VdomElement }


import spatutorial.client.services.SPACircuit
import spatutorial.client.components.Bootstrap.{Button, Text /*, RadioButton, CheckBox, CommonStyle,  TextArea*/ }
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

import spatutorial.client.actionhandlers.{/*GetAllSeguimiento,*/ GetBetweenDatesSeguimiento}

//import spatutorial.client.components.{ rptInvCotizar }

//import spatutorial.client.actionhandlers.{GetPartida, CleanPartida/*, InsertPrograma, SavePrograma*/}

//import org.scalajs.dom.window

import scala.scalajs.js.{| /*, Date => jsDate*/ }
import scalajs.js.Dynamic.{ global => g }

import spatutorial.client.excelPlus._

object frmReportsExcel {
//class frmDocumentos {
	@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(proxy: ModelProxy[Pot[(Seq[Seguimiento], String)]])

	case class State(lst: Seq[Seguimiento] = Seq.empty, dateStart: String = "", dateEnd: String = ""/*,buttonDisabled: Boolean = true*/)

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
			p.proxy.dispatchCB(GetBetweenDatesSeguimiento(s.dateStart, s.dateEnd))
		}

		def onExcel: Callback = $.state >>= { s =>
			Callback {
				if ( s.lst.isEmpty ) {
					g.alert("No hay elementos que guardar, por favor defina fechas y de click en el boton ''Actualizar''")
				} else {

						val Xls = new ExcelPlus
						Xls.createFile("reporte")

						type RowExcel = Seq[String | Double | Int]

						val printLst = s.lst.map { Item =>
							val noPedido: String = Item.pedido.getOrElse("")
							val cotiz: Int = Item.cotizaciones.getOrElse(0)
							val solCompra: String = Item.no_sol_compra.getOrElse("")

							val tipo_compra = Item.tipo_compra.getOrElse("")
							val compra = Item.compra.getOrElse("")
							val cve_presup = Item.cve_presup.getOrElse("")

							Seq( Item.id_comparativo, Item.elaboro, noPedido, Item.fecha.fecha, tipo_compra, compra, solCompra, cve_presup, Item.dias, cotiz ): RowExcel
						}.toSeq

						/*val totales: List[Double] = s.lista.map { Item =>
							val total: BigDecimal = Item.total
							total.toDouble
						}.toList*/

						val columns: RowExcel = List("IdComparativo", "Elaboro","Pedido", "Fecha", "Dias", "Cotizaciones")
						//val granTotal: Double = totales.foldLeft(0.0)(_ + _)
						//val footerTotales = Seq(Seq("", "", "", "", "", "", "", "", "Grand Total", 75.00))

						//Xls.write(Options(content = columns.asInstanceOf[Seq[String | Double | Int]] +: printList :+ footerTotales.asInstanceOf[Seq[String | Double | Int]]))
						//Xls.write(Options(content = footerTotales.asInstanceOf[Seq[Seq[String|Double|Int]]]))
						Xls.write(Options(content = columns.asInstanceOf[RowExcel] +: printLst /*:+ footerTotales.asInstanceOf[RowExcel]*/))
						//Callback(
						Xls.saveAs("Reporte.xlsx")
						//)
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
					Button(Button.Props(onExcel, addStyles = Seq(bss.button)), "Excel")
				)
			)
		}
	}

	val component = ScalaComponent.builder[Props]("frmReportsExcel")
		.initialState(State())
		.renderBackend[Backend]
		.componentWillReceiveProps{ rp =>

			val (lst: Seq[Seguimiento], _ /*, event: String*/) = rp.nextProps.proxy() match {
				case x if x.isUnavailable => Seq.empty
				case x if x.isEmpty => Seq.empty
				case x if x.isFailed => Seq.empty
				case _ => {
					val (seg: Seq[Seguimiento], evento: String) = rp.nextProps.proxy().get //rp.nextProps.proxy().get
					val message = if(evento == "inserted") "Guardado"
					else if (evento == "saved")	"Modificado"
					else if (evento == "found") "Encontrado"
					else "No encontrado"

					(seg, message)
				}
			}
			rp.setState(State(lst = lst, dateStart = rp.state.dateStart, dateEnd = rp.state.dateEnd))
		}
		.componentWillMount { scope =>
			scope.modState( s => s.copy(dateStart = scope.backend.Hoy, dateEnd = scope.backend.Hoy))
		}
		.build

  def apply() = {
		val SeguimientoWraper = SPACircuit.connect(_.seguimiento)
		SeguimientoWraper(prox => component(Props(proxy = prox)))
	}

}
