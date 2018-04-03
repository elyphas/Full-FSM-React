package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
//import diode._
import japgolly.scalajs.react.vdom.VdomArray
import spatutorial.client.services.SPACircuit
import spatutorial.client.components.Bootstrap.{Button, Text /*CheckBox, CommonStyle,*/ }
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

import spatutorial.client.actionhandlers.{GetOficioPresupuesto, InsertOficioPresupuesto}
//import spatutorial.client.actionhandlers.CleanProveedor

//import org.scalajs.dom.window

object frmOficioPresupuesto {
	@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(proxy: ModelProxy[Pot[(OficioPresupuesto, String)]])

	case class State( item: OficioPresupuesto = OficioPresupuesto(), showSearchCatalogo: Boolean = false,
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

		def onChangeIDComparativo(e: ReactEventFromInput) = {
			val txt = e.target.value
			if (txt.takeRight(4) == "ADQ2") $.modState(s=>s.copy(item = s.item.copy(id_comparativo = txt)), onSearch)
			else $.modState(s=>s.copy(item = s.item.copy(id_comparativo = txt)))
		}

		def onChangeNumero(e: ReactEventFromInput) = {
			val txt = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(numero = txt)))
		}

		def onChangeFecha(e: ReactEventFromInput) = {
			val txt = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(fecha = Fechas(txt))))
		}

		def onChangeFechaRecibido(e: ReactEventFromInput) = {
			val txt = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(fecha_recibido = Fechas(txt))))
		}

		def onChangeMonto(e: ReactEventFromInput) = {
			val txt = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(monto = txt.toDouble)))
		}

		def onChangeObservaciones(e: ReactEventFromInput) = {
			val txt = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(observaciones = txt)))
		}

		def onSave = $.state.zip($.props) >>= { case (s, p) =>
			/*if (s.editar)
				if (window.confirm("¿Modificar?")) p.proxy.dispatchCB(UpdateProveedor2(s.item))
				else Callback.empty
			else*/
				p.proxy.dispatchCB(InsertOficioPresupuesto(s.item))
		}

		/*def onSave = $.state.zip($.props) >>= { case (s, p) =>
			if (s.editar)
				if (window.confirm("¿Modificar?")) p.proxy.dispatchCB(UpdateProveedor2(s.item))
				else Callback.empty
			else
				p.proxy.dispatchCB(AddNewProveedor(s.item))
		}*/

		/*def onDelete = $.state.zip($.props) >>= { case (s, p) =>
			if( window.confirm("¿Eliminar?"))
				p.proxy.dispatchCB(DeleteProveedor(s.item.cve_provedor))
			else
				Callback.empty
		}*/

		/*def onNew = $.props >>= { p =>
			$.modState(s=>s.copy(item = Proveedor(), editar = false)) >> p.proxy.dispatchCB(CleanProveedor)
		}*/

		def onSearch = $.state.zip($.props) >>= { case (s, p) =>
			p.proxy.dispatchCB(GetOficioPresupuesto(s.item.id_comparativo))
		}

		def	render(p: Props, s: State) = {
				<.div( ^.padding:="5", ^.border:="solid", ^.marginTop:="5", ^.borderColor:="#20B2AA",
			<.header( <.h1( "Datos Generales") ),
					if(s.msg == "") VdomArray.empty() /*Seq.empty[ReactElement]*/ else <.span(s.msg),
					<.div(^.padding := "5", ^.border:="solid", ^.marginTop := "5", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",
						<.section(^.width := "800", ^.padding := "10",

							Text(Text.Props(idx="txtIdComparativo", label="ID-Comparativo",  onChange = onChangeIDComparativo, value = s.item.id_comparativo,
								otrosStyles = Seq(^.width:="200", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtNumero", label="Numero",  onChange = onChangeNumero,
                value=s.item.numero, otrosStyles = Seq(^.width:="100", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtFecha", label="Fecha Memo",  onChange = onChangeNumero,
								value = s.item.fecha.fecha, otrosStyles = Seq(^.width:="210", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtFechaRecibido", label="Fecha Recibido",  onChange = onChangeFechaRecibido,
								value = s.item.fecha_recibido.fecha,	otrosStyles = Seq(^.width:="210", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtMonto", label="Monto", onChange = onChangeMonto,
								value = if(s.item.monto > 0) s.item.monto.toString else "", otrosStyles = Seq(^.width:="410", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtObservaciones", label="Observaciones", onChange = onChangeObservaciones,
								value = s.item.observaciones, otrosStyles = Seq(^.width:="410", ^.marginRight:="5")))

						)
					),
					<.div(^.padding := "5", ^.border:="solid", ^.borderColor:="#20B2AA",
						Button(Button.Props(onSave, addStyles = Seq( bss.buttonXS)), "Guardar")/*,
						Button(Button.Props(onNew, addStyles = Seq(bss.buttonXS)), "Nuevo"),
						Button(Button.Props(onSearch, addStyles = Seq(bss.buttonXS)), "Buscar"),
						Button(Button.Props(onDelete, addStyles = Seq(bss.buttonXS)), "Borrar")*/
					)
				)
		}
	}

	val component = ScalaComponent.builder[Props]( "frmOficioPresupuesto" )
		.initialState(State(item = OficioPresupuesto(), editar = false))
		.renderBackend[ Backend ]
    .componentWillReceiveProps { rp =>

      val (oficioPresupuesto: OficioPresupuesto, editar: Boolean, msg: String) = rp.nextProps.proxy() match {
        case x if x.isUnavailable => {
					println("Cuando no esta disponible!!")
					( OficioPresupuesto(id_comparativo = rp.state.item.id_comparativo), false, "No se encontro")
				}
        case x if x.isEmpty => (OficioPresupuesto(), false, "Nuevo")
        case x if x.isFailed => (OficioPresupuesto(), "Error")
        case _ => {
					val (oficio, event) = rp.nextProps.proxy().get
					(oficio, true, "Guardado")
				}
      }

      rp.setState(State(item = oficioPresupuesto, editar = editar, msg = msg))
    }
		.componentWillMount { scope =>
			scope.modState(s=>s.copy(item = s.item.copy(fecha = Fechas(scope.backend.Hoy), fecha_recibido  = Fechas(scope.backend.Hoy))))
		}
		.build


  def apply() = {
    	val OficioPresupuestoWraper = SPACircuit.connect(_.oficioPresupuesto)
			OficioPresupuestoWraper(prox => component(Props(proxy = prox)))
  }
}
