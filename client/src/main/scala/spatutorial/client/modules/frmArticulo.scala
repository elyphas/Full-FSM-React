package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
//import diode.ModelRO
//import diode._

import japgolly.scalajs.react.vdom.{VdomArray /*, VdomElement*/ }
//import spatutorial.client.actionhandlers.CleanProveedor
import spatutorial.client.services.SPACircuit
import spatutorial.client.components.Bootstrap.{Button, CheckBox, Text, TextArea /*CommonStyle,*/ }
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

//import spatutorial.client.actionhandlers.{GetProveedor, UpdateProveedor, AddNewProveedor, DeleteProveedor}
//import spatutorial.client.actionhandlers.{CleanProveedor}

import spatutorial.client.actionhandlers.{GetArticulo, SaveArticulo  /*InsertArticulo,*/ }

//import org.scalajs.dom.window

object frmArticulo {
	@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(proxy: ModelProxy[Pot[Articulo]])

	case class State( item: Articulo = Articulo(),
										showSearchCatalogo: Boolean = false,
                    editar: Boolean = false, saved: Boolean = false, msg: String = "")

	class Backend($: BackendScope[Props, State]) {

		def onChangeCveArticulo(e: ReactEventFromInput) = {
			val text = e.target.value

			if (text.length == 12)
				$.modState(s=>s.copy(item = s.item.copy(cve_articulo = text)), searchArticulo(text))
			else
				$.modState(s=>s.copy(item = s.item.copy(cve_articulo = text)))
		}

		def searchArticulo(id: String) = $.props >>= (_.proxy.dispatchCB(GetArticulo(id)))

		def onChangeDescripcion(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(descripcion = text.toUpperCase)))
		}

		def onChangeUnidad(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(unidad = text.toUpperCase)))
		}

		def onChangePresentacion(e: ReactEventFromInput) = {
			val text = e.target.value
			val valorEntero = if ( text.length > 0 ) text.toInt else 0
			$.modState(s=>s.copy(item = s.item.copy(presentacion = Some(valorEntero.toInt))))
		}

		def onChangeUnidMedPres(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(unid_med_pres = Some(text.toUpperCase))))
		}

		def onChangePartida(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(partida = text)))
		}

		def onChangeIVA(e: ReactEventFromInput) = {
			val text = e.target.value
			val valor: Double = if ( text.length > 0 ) text.toDouble else 0.0
			$.modState(s=>s.copy(item = s.item.copy(iva = Some(valor))))
		}

		def onChangeBaja = $.state >>= { s =>
			$.modState(s=>s.copy(item = s.item.copy(baja = Some(!s.item.baja.getOrElse(false)))))
		}

		/*def onChangeBaja = $.state >>= { s =>
			$.modState(s=>s.copy(item = s.item.copy(baja = Some(!s.item.baja.getOrElse(false)))))
		}*/

		def onSave = $.state.zip($.props) >>= { case (s, p) =>
			p.proxy.dispatchCB(SaveArticulo(s.item))
		}

		def onAdd = Callback.alert("Agregar")
		def onNew = Callback.alert("Nuevo")
		def onSearch = Callback.alert("Buscar")
		def onDelete = Callback.alert("Borrar")

		def renderInit(p: Props, s: State) =
			<.div( ^.padding := "10", ^.border := "solid", ^.marginTop := "10", ^.borderColor:="#20B2AA",
				<.header( <.h1( "Datos Generales") ),
				if(s.msg == "") VdomArray.empty() else <.span(s.msg),
				<.div(^.padding := "5", ^.border := "solid", ^.marginTop := "10", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",
					<.section(^.width := "800", ^.padding := "10",

						<.div(^.height := "auto", ^.overflow := "hidden",

							Text(Text.Props(idx = "txtCveArticulo", label="Cve. Articulo",  onChange = onChangeCveArticulo, value = s.item.cve_articulo,
								otrosStyles = Seq(^.width := "140", ^.marginRight := "5"))),

							TextArea(TextArea.Props(idx = "txtDescripcion", label="Descripción",
								onChange = onChangeDescripcion, value = s.item.descripcion,
								otrosStyles = Seq(^.width := "140", ^.marginRight := "5")))
						),

							Text(Text.Props(idx = "txtUnidad", label="Unidad",  onChange = onChangeUnidad, value = s.item.unidad,
								otrosStyles = Seq(^.width := "140", ^.marginRight := "5"))),

							Text(Text.Props(idx = "txtPresentacion", label="Presentación",
								onChange = onChangePresentacion, typeInput ="numero",
								value = if(s.item.presentacion != None) s.item.presentacion.getOrElse("").toString else "",
								otrosStyles = Seq(^.width := "140", ^.marginRight := "5"))),

							Text(Text.Props(idx = "txtUnidMedPres", label="Unidad Presentación",  onChange = onChangeUnidMedPres, value = s.item.unid_med_pres.getOrElse(""),
								otrosStyles = Seq(^.width := "140", ^.marginRight := "5"))),

							Text(Text.Props(idx = "txtPartida", label="Partida",  onChange = onChangePartida, value = s.item.partida,
								otrosStyles = Seq(^.width := "140", ^.marginRight := "5"))),

							//CheckBox(CheckBox.Props(idx="txtIVA", label="I.V.A.", onClick = onChangeIVA, value = s.item.iva)),

							Text(Text.Props(idx = "txtIVA", label="I.V.A.",  onChange = onChangeIVA, value = s.item.iva.getOrElse("").toString ,
								otrosStyles = Seq(^.width := "40", ^.marginRight := "5"))),

							CheckBox(CheckBox.Props(idx="txtBaja", label="Baja", onClick = onChangeBaja, value = s.item.baja.getOrElse(false)))

						)
				),
				<.div(^.padding := "10", ^.border:="solid", ^.borderColor:="#20B2AA",
					Button(Button.Props( onSave, addStyles = Seq( bss.buttonXS)), "Guardar" ),
					Button(Button.Props( onAdd, addStyles = Seq( bss.buttonXS)), "Agregar" ),
					Button(Button.Props(onNew, addStyles = Seq( bss.buttonXS)), "Nuevo" ),
					Button(Button.Props(onSearch, addStyles = Seq( bss.buttonXS)), "Buscar" ),
					Button(Button.Props( onDelete, addStyles = Seq( bss.buttonXS)), "Borrar" )
				)
			)


		def	render(p: Props, s: State) = {
				renderInit(p, s)
		}
	}

	val component = ScalaComponent.builder[Props]("frmArticulo")
		.initialState(State(item = Articulo(), editar = false))
		.renderBackend[Backend]
    .componentWillReceiveProps{ rp =>

      val (articulo: Articulo, editar: Boolean, msg: String) = rp.nextProps.proxy() match {
        case x if x.isUnavailable =>
					(Articulo(cve_articulo = rp.state.item.cve_articulo), false, "No se encontro")
        case x if x.isEmpty => (Articulo(), false, "Nuevo")
        case x if x.isFailed => (Articulo(), "Error")
        case _ => {
					(rp.nextProps.proxy().get, true, "Guardado")
				}
      }

      rp.setState( State(item = articulo, editar = editar, msg = msg) )

    }
		.build


  def apply() = {
      val ArticuloWraper = SPACircuit.connect(_.articulo)
			ArticuloWraper(prox => component(Props(proxy = prox)))
  }
}
