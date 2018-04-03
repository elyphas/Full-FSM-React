package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
import diode._
import japgolly.scalajs.react.vdom.{VdomArray /*, VdomElement*/ }

import spatutorial.client.services.SPACircuit
import spatutorial.client.components.Bootstrap.{Button, Text /*, CheckBox, CommonStyle,  TextArea*/ }
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

import spatutorial.client.actionhandlers.{GetCalendario, InsertCalendario, SaveCalendario, CleanCalendario /*, DeleteCalendario*/ }
import spatutorial.client.actionhandlers.{GetTotalDias, GetDescripcionCompra}

//import java.text.SimpleDateFormat

//import org.scalajs.dom.window

object frmCalendario {
	@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(proxy: ModelProxy[Pot[(Calendario, String)]])

	case class State( item: Calendario = Calendario(), showSearchCatalogo: Boolean = false,
                    editar: Boolean = false, saved: Boolean = false, msg: String = "", dias: Int = 0)

	class Backend($: BackendScope[Props, State]) {

		def digits00(valor: Int) = if (valor > 1 && valor < 10) "0" + valor else valor

		def Hoy = {
			import java.time.{LocalDate}
			val hoy = LocalDate.now()
			digits00(hoy.getDayOfMonth) + "/" + digits00(hoy.getMonthValue) + "/" + hoy.getYear
		}

		def getCalcularTotalDias = $.state.zip($.props) >>= { case (s, p) =>
			val dispatch: Dispatcher = SPACircuit
			Callback{
				dispatch(GetTotalDias("25/07/2017"))
				dispatch(GetDescripcionCompra(s.item.id_comparativo))
			}
		}

		/*def MostrarTotalDias(dias: ModelRO[Pot[Int]]) = {
			val total = dias.value match {
				case x if x.isUnavailable => 0
				case x if x.isEmpty => 0
				case x if x.isFailed => 0
				case _ => dias.value.get
			}

			$.modState(s=>s.copy(dias = total)).runNow()
		}*/

		def onChangeIDComparativo(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase

			if (text.takeRight(4) == "ADQ2")
				$.modState(s=>s.copy(item = s.item.copy(id_comparativo = text)), search(text))
			else
				$.modState(s=>s.copy(item = s.item.copy(id_comparativo = text)))
		}

		def search(id: String) = $.props >>= (_.proxy.dispatchCB(GetCalendario(id)))


		def onChangeFechaDisposicBaseInicio(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(fecha_inicio_disponible_base = Fechas(fecha = text))))
		}

		def onChangeFechaDisposicBaseFinal(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(fecha_final_disponible_base = Fechas(fecha = text))))
		}

		def onChangeFechaJuntaAclara(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(fecha_junta_aclaraciones = Fechas(fecha = text))))
		}

		def onChangeHoraJuntaAclara(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(hora_junta_aclaraciones = Hora(hora = text))))
		}

		def onChangeObservJuntaAclara(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(observacion_junta_aclaraciones = text)))
		}

		def onChangeFechaApertTecnic(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(fecha_apertura_tecnica = Fechas(fecha = text))))
		}

		def onChangeHoraApertTecnic(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(hora_apertura_tecnica = Hora(hora = text))))
		}

		def onChangeObservApertTecnic(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(observacion_aper_tecnica = text)))
		}

		def onChangeFechaApertEconoc(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(fecha_apertura_economica = Fechas(fecha = text))))
		}

		def onChangeHoraApertEconoc(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(hora_apertura_economica = Hora(hora = text))))
		}

		def onChangeObservApertEconoc(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(observacion_aper_econ = text)))
		}

		def onChangeFechaFallo(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(fecha_fallo = Fechas(fecha = text))))
		}

		def onChangeHoraFallo(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(hora_fallo = Hora(hora = text))))
		}

		def onChangeObservFallo(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(observacion_fallo = text)))
		}

		def onChangeFechaFirmaContrato(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(fecha_firma_contrato = Fechas(fecha = text))))
		}

		def onChangeHoraFirmaContrato(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(hora_firma_contrato = Hora(hora = text))))
		}

		def onChangeObservFirmaContrato(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(observacion_firma_contrato = text)))
		}

		def onAdd = $.state.zip($.props) >>= { case (s, p) => p.proxy.dispatchCB(InsertCalendario(s.item)) }

		def onSave = $.state.zip($.props) >>= { case (s, p) =>	p.proxy.dispatchCB(SaveCalendario(s.item))	}

		def onNew = $.props >>= ( _.proxy.dispatchCB(CleanCalendario))

		def onSearch = Callback.alert("Buscar")
		def onDelete = Callback.alert("Borrar")

		def	render(p: Props, s: State) = {

				<.div( ^.padding := "10", ^.border := "solid", ^.marginTop := "10", ^.borderColor:="#20B2AA",
			<.header(<.h1("Calendario")),

					<.span(s.dias.toString + "******************"),
					if(s.msg == "") VdomArray.empty() else <.span(s.msg),
					<.div(^.padding := "5", ^.border := "solid", ^.marginTop := "10", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",
						<.section(^.width := "800", ^.padding := "10",

							<.div(^.height := "auto", ^.overflow := "hidden",
								Text(Text.Props(idx = "txtID", label="ID Comparativo",  onChange = onChangeIDComparativo, value = s.item.id_comparativo,
									otrosStyles = Seq(^.width := "200", ^.marginRight := "5")))
							),
							<.div(^.padding := "5", ^.border := "solid", ^.marginTop := "10", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",

								<.h4("Fecha de la Disposición de Bases"),

								Text(Text.Props(idx = "txtFechaDisposicInicio", label = "Inicio",  onChange = onChangeFechaDisposicBaseInicio, value = s.item.fecha_inicio_disponible_base.fecha,
									otrosStyles = Seq(^.width := "100", ^.marginRight := "5"))),

								Text(Text.Props(idx = "txtFechaDisposicFinal", label = "Final",  onChange = onChangeFechaDisposicBaseFinal, value = s.item.fecha_final_disponible_base.fecha,
									otrosStyles = Seq(^.width := "100", ^.marginRight := "5"))),

								Text(Text.Props(idx = "txtObservDisposicionBases", label="Observación a las disposición de bases",
										onChange = onChangeObservJuntaAclara, value = s.item.observacion_junta_aclaraciones,
										otrosStyles = Seq(^.width := "240", ^.marginRight := "5")))
							),
							<.div(^.padding := "5", ^.border := "solid", ^.marginTop := "10", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",

								<.h4("Junta Aclaraciones"),

								Text(Text.Props(idx = "txtFechaJuntaAclar", label = "Fecha",  onChange = onChangeFechaJuntaAclara, value = s.item.fecha_junta_aclaraciones.fecha,
									otrosStyles = Seq(^.width := "100", ^.marginRight := "5"))),

								Text(Text.Props(idx = "txtHoraJuntaAclar", label="Hora",  onChange = onChangeHoraJuntaAclara, value = s.item.hora_junta_aclaraciones.hora,
									otrosStyles = Seq(^.width := "100", ^.marginRight := "5"))),

								Text(Text.Props(idx = "txtObservJuntaAclar", label="Observación",  onChange = onChangeObservJuntaAclara, value = s.item.observacion_junta_aclaraciones,
									otrosStyles = Seq(^.width := "240", ^.marginRight := "5")))
							),
							<.div(^.padding := "5", ^.border := "solid", ^.marginTop := "10", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",

								<.h4("Apertura Técnica"),

								Text(Text.Props(idx = "txtFechaApertTecnic", label="Fecha",  onChange = onChangeFechaApertTecnic, value = s.item.fecha_apertura_tecnica.fecha,
									otrosStyles = Seq(^.width := "100", ^.marginRight := "5"))),

								Text(Text.Props(idx = "txtHoraApertTecnic", label="Hora",  onChange = onChangeHoraApertTecnic, value = s.item.hora_apertura_tecnica.hora,
									otrosStyles = Seq(^.width := "100", ^.marginRight := "5"))),

								Text(Text.Props(idx = "txtObservApertTecnic", label="Observaciones",  onChange = onChangeObservApertTecnic, value = s.item.observacion_aper_tecnica,
									otrosStyles = Seq(^.width := "240", ^.marginRight := "5")))
							),
							<.div(^.padding := "5", ^.border := "solid", ^.marginTop := "10", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",

								<.h4("Apertura Económica"),

									Text(Text.Props(idx = "txtFechaApertEconoc", label="Fecha",  onChange = onChangeFechaApertEconoc, value = s.item.fecha_apertura_economica.fecha,
									otrosStyles = Seq(^.width := "100", ^.marginRight := "5"))),

								Text(Text.Props(idx = "txtHoraApertEconoc", label="Hora",  onChange = onChangeHoraApertEconoc, value = s.item.hora_apertura_economica.hora,
									otrosStyles = Seq(^.width := "100", ^.marginRight := "5"))),

								Text(Text.Props(idx = "txtObservApertEconom", label="Observación",  onChange = onChangeObservApertEconoc, value = s.item.observacion_aper_econ,
									otrosStyles = Seq(^.width := "240", ^.marginRight := "5")))

							),
							<.div(^.padding := "5", ^.border := "solid", ^.marginTop := "10", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",

								<.h4("Fallo"),

								Text(Text.Props(idx = "txtFechaFallo", label="Fecha",  onChange = onChangeFechaFallo, value = s.item.fecha_fallo.fecha,
									otrosStyles = Seq(^.width := "140", ^.marginRight := "5"))),

								Text(Text.Props(idx = "txtHoraFallo", label="Hora",  onChange = onChangeHoraFallo, value = s.item.hora_fallo.hora,
									otrosStyles = Seq(^.width := "140", ^.marginRight := "5"))),

								Text(Text.Props(idx = "txtObservFallo", label="Observación",  onChange = onChangeObservFallo, value = s.item.observacion_fallo,
									otrosStyles = Seq(^.width := "140", ^.marginRight := "5")))
							),
							<.div(^.padding := "5", ^.border := "solid", ^.marginTop := "10", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",

								<.h4("Firma de Contrato"),

								Text(Text.Props(idx = "txtFechaFirmaContrato", label="Fecha",  onChange = onChangeFechaFirmaContrato, value = s.item.fecha_firma_contrato.fecha,
									otrosStyles = Seq(^.width := "140", ^.marginRight := "5"))),

								Text(Text.Props(idx = "txtHoraFirmaContrato", label="Hora",  onChange = onChangeHoraFirmaContrato, value = s.item.hora_firma_contrato.hora,
									otrosStyles = Seq(^.width := "140", ^.marginRight := "5"))),

								Text(Text.Props(idx = "txtObservFirmaContrato", label="Observación",  onChange = onChangeObservFirmaContrato, value = s.item.observacion_firma_contrato,
									otrosStyles = Seq(^.width := "140", ^.marginRight := "5")))
							)
						)
					),
					<.div(^.padding := "10", ^.border:="solid", /*^.marginTop:=50,*/ ^.borderColor:="#20B2AA",
						Button(Button.Props( onSave, addStyles = Seq( bss.buttonXS)), "Guardar" ),
						Button(Button.Props( onAdd, addStyles = Seq( bss.buttonXS)), "Agregar" ),
						Button(Button.Props(onNew, addStyles = Seq( bss.buttonXS)), "Nuevo" ),
						Button(Button.Props(onSearch, addStyles = Seq( bss.buttonXS)), "Buscar" ),
						Button(Button.Props(onDelete, addStyles = Seq( bss.buttonXS)), "Borrar" ),
						Button(Button.Props(getCalcularTotalDias, addStyles = Seq( bss.buttonXS)), "Calcular los dias" )
					)
				)
		}
	}

	val component = ScalaComponent.builder[Props]("frmCalendario")
		.initialState(State(item = Calendario(), editar = false))
		.renderBackend[Backend]
    .componentWillReceiveProps{ rp =>

      /*val (calendario: Calendario, editar: Boolean, msg: String) = rp.nextProps.proxy() match {
        case x if x.isUnavailable =>
					(Calendario(id_comparativo = rp.state.item.id_comparativo), false, "No se encontro")
        case x if x.isEmpty => (Calendario(), false, "Nuevo")
        case x if x.isFailed => (Calendario(), "Error")
        case _ => {
					(rp.nextProps.proxy().get, true, "Guardado")
				}
      }*/

			val (calendario: Calendario, msg: String) = rp.nextProps.proxy() match {
				case x if x.isUnavailable =>
					(Calendario(), "No se encontro")
				case x if x.isEmpty =>
					(Calendario(), "Nuevo")
				case x if x.isFailed =>
					(Calendario(), "Error")
				case _ => {
					val (calend, event) = rp.nextProps.proxy().get

					if (calend.id_comparativo == "" && event == "not found")
						(Calendario(), "No encontrado")
					else {
						if(event == "found") (calend, "Encontrado")
						else (calend, "Guardado")
					}
				}
			}
      rp.setState(State(item = calendario, msg = msg))
    }
		/*.componentWillMount { scope =>
			val unsubscribe = SPACircuit.subscribe(SPACircuit.zoom(_.totalDias))(dias => scope.backend.MostrarTotalDias(dias))
			scope.modState(s=>s.copy(item = s.item.copy(fecha_junta_aclaraciones = Fechas(scope.backend.Hoy))))
		}*/
		.build

  def apply() = {
      val CalendarioWraper = SPACircuit.connect(_.calendario)
			CalendarioWraper(prox => component(Props(proxy = prox)))
  }
}