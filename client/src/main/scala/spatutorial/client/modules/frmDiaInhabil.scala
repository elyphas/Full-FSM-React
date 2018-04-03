package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
//import diode._
import japgolly.scalajs.react.vdom.{VdomArray /*, VdomElement*/ }

import spatutorial.client.services.SPACircuit
import spatutorial.client.components.Bootstrap.{Button, Text /*, CheckBox, CommonStyle, TextArea*/ }
//import spatutorial.client.components.Bootstrap._
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

import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.html_<^._

import spatutorial.client.actionhandlers.{InsertDiaInhabil}

//import org.scalajs.dom.window

object frmDiaHabil {
	@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(proxy: ModelProxy[Pot[(Dia_Inhabil, String)]])

	case class State( item: Dia_Inhabil = Dia_Inhabil(),
                    editar: Boolean = false, saved: Boolean = false, msg: String = "")

	class Backend($: BackendScope[Props, State]) {

		def digits00(valor: Int) = if (valor > 1 && valor < 10) "0" + valor else valor

		def Hoy = {
			/*import java.time.{LocalDate}
			val hoy = LocalDate.now()
			digits00(hoy.getDayOfMonth) + "/" + digits00(hoy.getMonthValue) + "/" + hoy.getYear*/
			val hoy: scalajs.js.Date = new scalajs.js.Date()
			digits00(hoy.getDay) + "/" + digits00(hoy.getMonth ) + "/" + hoy.getFullYear
		}

		def onChangeFecha(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase

			/*if (text.takeRight(4) == "ADQ2")
				$.modState(s=>s.copy(item = s.item.copy(id_comparativo = text)), search(text))
			else*/
				$.modState(s=>s.copy(item = s.item.copy(fecha = Fechas(fecha = text))))
		}

		//def search(id: String) = $.props >>= (_.proxy.dispatchCB(GetCalendario(id)))

		def onChangeObservacion(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(observacion = text)))
		}

		def onAdd = $.state.zip($.props) >>= { case (s, p) => p.proxy.dispatchCB(InsertDiaInhabil(s.item)) }

		def onSave = Callback.alert("Guardar") //$.state.zip($.props) >>= { case (s, p) =>	p.proxy.dispatchCB(SaveCalendario(s.item))	}

		def onNew = Callback.alert("Nuevo") //$.props >>= ( _.proxy.dispatchCB(CleanCalendario))

		def onSearch = Callback.alert("Buscar")
		def onDelete = Callback.alert("Borrar")

		def	render(p: Props, s: State) = {

				<.div( ^.padding := "10", ^.border := "solid", ^.marginTop := "10", ^.borderColor:="#20B2AA",
			<.header( <.h1( "Día Inhabil") ),
					if(s.msg == "") VdomArray.empty() else <.span(s.msg),
					<.div(^.padding := "5", ^.border := "solid", ^.marginTop := "10", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",
						<.section(^.width := "800", ^.padding := "10",

							<.div(^.height := "auto", ^.overflow := "hidden",
								Text(Text.Props(idx = "txtFecha", label="Fecha",  onChange = onChangeFecha, value = s.item.fecha.fecha,
									otrosStyles = Seq(^.width := "100", ^.marginRight := "5"))),
								Text(Text.Props(idx = "txtObservacion", label="Observacion",  onChange = onChangeObservacion, value = s.item.observacion,
									otrosStyles = Seq(^.width := "200", ^.marginRight := "5")))

							)
						)
					),
					<.div(^.padding := "10", ^.border:="solid", /*^.marginTop:=50,*/ ^.borderColor:="#20B2AA",
						Button(Button.Props( onSave, addStyles = Seq( bss.buttonXS)), "Guardar" ),
						Button(Button.Props( onAdd, addStyles = Seq( bss.buttonXS)), "Agregar" ),
						Button(Button.Props(onNew, addStyles = Seq( bss.buttonXS)), "Nuevo" ),
						Button(Button.Props(onSearch, addStyles = Seq( bss.buttonXS)), "Buscar" ),
						Button(Button.Props( onDelete, addStyles = Seq( bss.buttonXS)), "Borrar" )
					)
				)
		}
	}

	val component = ScalaComponent.builder[Props]("frmDiaHabil")
		.initialState(State(item = Dia_Inhabil(), editar = false))
		.renderBackend[Backend]
    .componentWillReceiveProps{ rp =>

			val (dia_habil: Dia_Inhabil, msg: String) = rp.nextProps.proxy() match {
				case x if x.isUnavailable =>
					(Dia_Inhabil(), "No se encontro")
				case x if x.isEmpty =>
					(Dia_Inhabil(), "Nuevo")
				case x if x.isFailed =>
					(Dia_Inhabil(), "Error")
				case _ => {
					val (dia_inhabil, event) = rp.nextProps.proxy().get

					if (dia_inhabil.fecha == "" && event == "not found")
						(Dia_Inhabil(), "No encontrado")
					else {
						if(event == "found") (dia_inhabil, "Encontrado")
						else (dia_inhabil, "Guardado")
					}
				}
			}
      rp.setState(State(item = dia_habil, msg = msg))
    }
		.componentWillMount { scope =>
				scope.modState(s=>s.copy(item = s.item.copy(fecha = Fechas(scope.backend.Hoy))))
		}
		.build


  def apply() = {
      val DiaInhabilWraper = SPACircuit.connect(_.dia_Inhabil)
			DiaInhabilWraper(prox => component(Props(proxy = prox)))
  }
}
