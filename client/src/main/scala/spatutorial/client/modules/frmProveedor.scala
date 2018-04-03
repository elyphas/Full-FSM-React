package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
//import diode._
//import japgolly.scalajs.react.vdom.VdomArray
import spatutorial.client.services.SPACircuit
import spatutorial.client.components.Bootstrap.{Button, CheckBox, Text /*CommonStyle,*/ }
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

import spatutorial.client.actionhandlers.{GetProveedor, UpdateProveedor2, AddNewProveedor, CleanProveedor, DeleteProveedor}
import spatutorial.client.actionhandlers.CleanProveedor

import org.scalajs.dom.window

object frmProveedor {
	@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(proxy: ModelProxy[Pot[Proveedor]])

	case class State( item: Proveedor = Proveedor(),  showSearchCatalogo: Boolean = false,
                    editar: Boolean = false, saved: Boolean = false, msg: String = "")

	class Backend($: BackendScope[Props, State]) {

		def onChangeCveProveedor(e: ReactEventFromInput) = {
			val text = e.target.value
			if (text.length >= 12) $.modState(s=>s.copy(item = s.item.copy(cve_provedor = text)), onSearch)
			else $.modState(s=>s.copy(item = s.item.copy(cve_provedor = text)))
		}

		def onChangeRazonSocial(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(razon_social = text)))
		}

		def onChangeRepresentante(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(propietario = Some(text))))
		}

		def onChangeCalle(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(calle = Some(text))))
		}

		def onChangeColonia(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(colonia = Some(text))))
		}

		def onChangeDelegacion(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(delegacion = Some(text))))
		}

		def onChangeCP(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(cp = Some(text))))
		}

		def onChangeCiudad(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(ciudad = Some(text))))
		}

		def onChangeTelefonos(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(telefonos = Some(text))))
		}

		def onChangeFax(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(fax = Some(text))))
		}

		def onChangeObservaciones(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(observaciones = Some(text))))
		}

		def onChangeActivo = $.state >>= { currentState =>
			$.modState(s=>s.copy(item = s.item.copy(activo = !currentState.item.activo)))
		}

		def onSave = $.state.zip($.props) >>= { case (s, p) =>
			if (s.editar)
				if (window.confirm("¿Modificar?")) p.proxy.dispatchCB(UpdateProveedor2(s.item))
				else Callback.empty
			else
				p.proxy.dispatchCB(AddNewProveedor(s.item))
		}

		def onDelete = $.state.zip($.props) >>= { case (s, p) =>
			if( window.confirm("¿Eliminar?"))
				p.proxy.dispatchCB(DeleteProveedor(s.item.cve_provedor))
			else
				Callback.empty
		}

		def onNew = $.props >>= { p =>
			$.modState(s=>s.copy(item = Proveedor(), editar = false)) >> p.proxy.dispatchCB(CleanProveedor)
		}

		def onSearch = $.state.zip($.props) >>= { case (s, p) =>
			p.proxy.dispatchCB(GetProveedor(s.item.cve_provedor))
		}

		def	render(p: Props, s: State) = {
				<.div( ^.padding:="5", ^.border:="solid", ^.marginTop:="5", ^.borderColor:="#20B2AA",
			<.header( <.h1( "Datos Generales") ),
					if(s.msg == "") VdomArray.empty() /*Seq.empty[ReactElement]*/ else <.span(s.msg),
					<.div(^.padding := "5", ^.border:="solid", ^.marginTop := "5", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",
						<.section(^.width := "800", ^.padding := "10",
							Text(Text.Props(idx="txtRFC", label="R.F.C.",  onChange = onChangeCveProveedor, value = s.item.cve_provedor,
								otrosStyles = Seq(^.width:="140", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtRazonSocial", label="Razon Social",  onChange = onChangeRazonSocial,
                value=s.item.razon_social, otrosStyles = Seq(^.width:="310", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtPropietario", label="Representante",  onChange = onChangeRepresentante,
								value = s.item.propietario.getOrElse(""), otrosStyles = Seq(^.width:="210", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtCalle", label="Calle",  onChange = onChangeCalle,
								value = s.item.calle.getOrElse(""),	otrosStyles = Seq(^.width:="210", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtColonia", label="Colonia",  onChange = onChangeColonia,
								value = s.item.colonia.getOrElse(""), otrosStyles = Seq(^.width:="210", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtDelegacion", label="Delegación", onChange = onChangeDelegacion,
								value = s.item.delegacion.getOrElse(""), otrosStyles = Seq(^.width:="210", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtCP", label="C.P.", onChange = onChangeCP,
								value = s.item.cp.getOrElse(""), otrosStyles = Seq(^.width:="90", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtCiudad", label="Ciudad", onChange = onChangeCiudad,
								value = s.item.ciudad.getOrElse(""), otrosStyles = Seq(^.width:="210", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtTelefono", label="Telefonos", onChange = onChangeTelefonos,
								value = s.item.telefonos.getOrElse(""), otrosStyles = Seq(^.width:="210", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtFax", label="Fax", onChange = onChangeFax,
								value = s.item.fax.getOrElse(""), otrosStyles = Seq(^.width:="210", ^.marginRight:="5"))),

							Text(Text.Props(idx="txtObservaciones", label = "Observaciones", onChange = onChangeObservaciones,
								value = s.item.observaciones.getOrElse(""), otrosStyles = Seq(^.width:="210", ^.marginRight:="5"))),

							CheckBox(CheckBox.Props(idx="txtActivo", label="Activo", onClick = onChangeActivo, value = s.item.activo))
						)
					),
					<.div(^.padding := "5", ^.border:="solid", ^.borderColor:="#20B2AA",
						Button(Button.Props( onSave, addStyles = Seq( bss.buttonXS)), "Guardar" ),
						Button(Button.Props(onNew, addStyles = Seq( bss.buttonXS)), "Nuevo" ),
						Button(Button.Props(onSearch, addStyles = Seq( bss.buttonXS)), "Buscar" ),
						Button(Button.Props( onDelete, addStyles = Seq( bss.buttonXS)), "Borrar" )
					)
				)
		}
	}

	val component = ScalaComponent.builder[Props]( "frmProveedor" )
		.initialState(State(item = Proveedor(), editar = false))
		.renderBackend[ Backend ]
    .componentWillReceiveProps { rp =>

      val (proveedor: Proveedor, editar: Boolean, msg: String) = rp.nextProps.proxy() match {
        case x if x.isUnavailable => {
					println("Cuando no esta disponible!!")
					(Proveedor(cve_provedor = rp.state.item.cve_provedor), false, "No se encontro")
				}
        case x if x.isEmpty => {
					println("Cuando es Empty")
					(Proveedor(), false, "Nuevo")
				}
        case x if x.isFailed => (Proveedor(), "Error")
        case _ => {
					(rp.nextProps.proxy().get, true, "Guardado")
				}
      }

      rp.setState(State(item = proveedor, editar = editar, msg = msg))
    }
		.build


  def apply() = {
      val ProveedorWraper = SPACircuit.connect(_.proveedor)
			ProveedorWraper(prox => component(Props(proxy = prox)))
  }
}
