package spatutorial.client.modules
//import com.sun.glass.ui.MenuItem

import diode.data.Pot
import diode.react.ModelProxy

import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.html_<^._

import spatutorial.client.components.Bootstrap.{Button, CheckBox, Combo, Text}
import spatutorial.client.components._
import spatutorial.shared._

//import autowire._
//import boopickle.Default._

//import scala.concurrent.Future
//import scala.util.{Failure, Success}
//import spatutorial.client.services.AjaxClient
//import spatutorial.client.services.SPACircuit

//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import org.scalajs.dom.raw._
import spatutorial.client.actionhandlers.{GetArticulo, SaveArticulo, SetNewArticulo}

object frmArticulos {
	//@inline private def bss = GlobalStyles.bootstrapStyles

	var partidas: Seq[String] = Seq[String]()
	var presentaciones: Seq[String] = Seq[String]()

	case class Props(proxy: ModelProxy[Pot[Articulo]]) {
		def dispatch = proxy.theDispatch
	}

	case class State( stateArt: Articulo )

  //val txtCveArt = Ref[HTMLInputElement]("txtCveArt")
  //val cboPartida = Ref[HTMLInputElement]("cboPartida")

	val notFound: String = "No encontrado!"

	class Backend($ : BackendScope[Props, State]) {

		def	render(p: Props, s: State) = {

				val newArticulo = Articulo()

				val (articulo, msg) = p.proxy() match {
					case x if x.isUnavailable => (newArticulo, notFound)
					case x if x.isEmpty => (newArticulo, "Nuevo")
					case _ => (p.proxy().get, "")
				}

				<.div( ^.padding := "30", ^.border := "solid", ^.marginTop := "50", ^.borderColor:="#20B2AA",
					<.header(<.h1( "Articulos")),

					frmfieldsArticulos(articulo, p.proxy, msg,
						() => p.proxy.dispatchCB( SetNewArticulo ),
						item => p.proxy.dispatchCB( SaveArticulo(item) ),
						(id: String) => p.proxy.dispatchCB ( GetArticulo(id) )
						/*,
						(id: PedidoId, reason: String) => p.proxy.dispatch( CancelPedido(id, reason) )
						*/
					)
				)
		}

		/*def onMount = {
			Callback.alert("Montando")
		}*/

	}

	val component = ScalaComponent.builder[Props]("Articulos")
		.initialState( State( Articulo())
		)
		.renderBackend[Backend]
		/*.componentWillMount{ scope =>
				scope.backend.onMount
		}*/
		.build

	def apply(proxy: ModelProxy[Pot[Articulo]]) = component(Props(proxy))
}


object frmfieldsArticulos {
	// shorthand for styles
	@inline private def bss = GlobalStyles.bootstrapStyles

	case class Props(item: Option[Articulo],
									 proxy: ModelProxy[Pot[Articulo]], msg: String,
									 nuevo: () => Callback,
									 saveArticulo: Articulo => Callback,
									 buscarArticulo: String => Callback
									)

	case class State(item: Articulo, editArticulo: Boolean = false)

	//val txtCveArt = Ref[HTMLInputElement]("txtCveArt")
	//val cboPartida = Ref[HTMLInputElement]("cboPartida")

	class Backend($: BackendScope[Props, State]) {

		def Save: Callback = $.state >>= { s =>
			/*Callback future {
				val art = Articulo(
					cve_articulo = s.item.cve_articulo,
					descripcion = s.item.descripcion,
					unidad = s.item.unidad,
					presentacion = s.item.presentacion,
					unid_med_pres = s.item.unid_med_pres,
					partida = s.item.partida,
					clave_cabms = s.item.clave_cabms,
					cb = s.item.cb,
					iva = if( s.item.iva == 0) 0.0 else 0.16,
					baja = s.item.baja
				)

				AjaxClient[Api].saveArtic(art) call() map { result =>
					$.setState( State( Articulo(
						cve_articulo = "", descripcion = "", unidad = "Seleccionar", presentacion = 0,
						unid_med_pres = "Seleccionar", partida = "Seleccionar", clave_cabms = "",
						cb = true, iva = true, baja = false ))
					)
				}
			}*/

			Callback.alert("Guardar")
		}

		def DelPedidos: Callback = { Callback.alert ( "Para borrar pedidos" ) }

		def CboChangedPartida(e: ReactEventFromInput): Callback = Callback.alert("Cambia parida") /*Callback.future {
			val seleccionado = e.currentTarget.value
			if (seleccionado != "Seleccionar"){
				AjaxClient[Api].createCve(seleccionado) call() map { result =>
					$.setState( State(cve_articulo = result,descripcion = "", unidad = "", presentacion = 0,
						unid_med_pres = "", partida = seleccionado, clave_cabms = "", cb = true, iva = true, baja = false))
				}
			} else {
				Future{ $.setState(State(cve_articulo = "",descripcion = "", unidad = "", presentacion = 0,
					unid_med_pres = "", partida = "", clave_cabms = "", cb = true, iva = true, baja = false)) }
			}
		}*/

		def CboChangedUnidad(e: ReactEventFromInput): Callback = {
			val text = e.target.value.toUpperCase
			$.modState( s => s.copy( item = s.item.copy(unidad = text)))
		}

		def onClickIVA: Callback = $.state >>= { s =>

			val valor = if( s.item.iva.getOrElse(0.0) == 0.0) 0.0 else 0.16
			$.modState( s => s.copy(item = s.item.copy(iva = Some(valor))))
		}


		def CboChangedunid_med_pres(e: ReactEventFromInput): Callback = {
			val text = e.target.value.toUpperCase
			$.modState( s => s.copy(item = s.item.copy(unid_med_pres = Some(text))))
		}

		def onChangeCveArtic(e: ReactEventFromInput): Callback = {
			val text = e.target.value
			$.modState( s => s.copy(item = s.item.copy(cve_articulo = text)))
		}

		def onChangeDescripcion(e: ReactEventFromInput): Callback = {
			val text = e.target.value
			$.modState( s => s.copy(item = s.item.copy(descripcion = text)))
		}

		def onChangePresentacion(e: ReactEventFromInput): Callback = {
			val text = e.target.value
			$.modState( s => s.copy(item = s.item.copy(presentacion = Some(text.toInt))))
		}

		var partidas: Seq[String] = Seq[String]()
		var presentaciones: Seq[String] = Seq[String]()

		/*def LoadProps: Callback = Callback future {

			val res = for {
				r1 <- AjaxClient[Api].getPartidas().call()
				r2 <- AjaxClient[Api].getPresentaciones().call()
			} yield {
				partidas = r1.map(_.cve_partida)
				presentaciones = r2.map(_.unidad)
				$.modState( s => s.copy(item = s.item.copy(cve_articulo = "")))
			}

			res

		}*/

		def onCancel = Callback.alert("cancelar");

		def onBuscar = $.state >>= { s =>
			$.props >>= { p => p.buscarArticulo( s.item.cve_articulo ) }
		}

		def onGuardar = Callback.alert("Buscar")

		def renderItem(cve: String) = <.option(^.value := cve, cve)

		def render(p: Props, s: State) = {
			<.div(^.padding := "10", ^.border:="1px solid", ^.marginTop := "3", ^.borderColor:="#20B2AA",
				^.height := "auto", ^.overflow := "hidden",
				<.header( <.h4("Articulo") ),
				<.div(^.height := "auto", ^.overflow := "hidden",

					Combo(Combo.Props( idx="cboPartida", label="partida",  onChange=CboChangedPartida,
						//values = (Seq("Seleccionar") ++ partidas), value=s.partida )
						values = Seq("Seleccionar"), value=s.item.partida )
					),

					Text( Text.Props( idx = "txtCveArticulo",  label = "Cve. Art.", value=s.item.cve_articulo,
						onChange=onChangeCveArtic, addStyles = Seq( bss.marginRight ),
						otrosStyles = Seq(^.width := "150", ^.marginRight := "5") ) ),

					//<.input( ^.ref := txtCveArt, ^.value := s.cve_articulo, ^.onChange ==> onChangeCveArtic ),

					<.div(^.clear:="both",
						Text( Text.Props( idx = "txtDescripcion",  label = "Descripción", value=s.item.descripcion,
							onChange=onChangeDescripcion, typeInput="area", addStyles = Seq( bss.marginRight ),
							otrosStyles = Seq(^.width := "300", ^.marginRight := "5") ) )
					),
					<.div(^.clear:="both", ^.marginTop := "50",
						Combo(Combo.Props( idx="cboColectivo", label="Env. colectivo",  onChange=CboChangedUnidad,
							//values=(Seq("Seleccionar")++presentaciones), value=s.item.unidad)
							values = Seq("Seleccionar"), value=s.item.unidad)
						),

						Text( Text.Props( idx = "txtPresentacion",  label = "Presentacion", value = s.item.presentacion.toString,
							onChange = onChangeCveArtic, addStyles = Seq( bss.marginRight ),
							otrosStyles = Seq(^.width := "150", ^.marginRight := "5"))),

						Combo(Combo.Props( idx="cboUnidad", label="Unidad Med.",
							onChange=CboChangedunid_med_pres,
							//values=(Seq("Seleccionar")++presentaciones), value=s.item.unid_med_pres)
							values = Seq("Seleccionar"), value=s.item.unid_med_pres.getOrElse(""))
						)
					),
					<.div(^.clear:="both", ^.width := "400", ^.marginTop := "150",
						CheckBox(CheckBox.Props( idx="Cuadro Basico", label="Cuadro Basico", onClickIVA, addStyles = Seq(bss.buttonXS))),
						CheckBox(CheckBox.Props(idx="I.V.A.",label="I.V.A.",onClickIVA, addStyles = Seq(bss.buttonXS))),
						CheckBox(CheckBox.Props(idx="Baja",label="Baja",onClickIVA, addStyles = Seq(bss.buttonXS)))
					)

				),
				<.div(^.padding := "10", ^.border:="1px solid", ^.marginTop := "30", ^.borderColor:="#20B2AA",
					^.height := "auto", ^.overflow := "hidden",
					Button(Button.Props(onCancel, addStyles = Seq( bss.buttonXS)), "Cancelar"),
					Button(Button.Props(onBuscar, addStyles = Seq( bss.buttonXS)), "Buscar" ),
					Button(Button.Props(onGuardar, addStyles = Seq( bss.buttonXS)), "Guardar" )
				)
			)
		}
	}

	val component = ScalaComponent.builder[Props]("fieldsArticulo")
		//.initialState_P(p => State(p.item.getOrElse(Articulo())))
	  .initialStateFromProps(p => State(p.item.getOrElse(Articulo())))
		.renderBackend[Backend]
		.componentWillReceiveProps { rp =>

			val newProps: Articulo = rp.nextProps.item.getOrElse(Articulo())

			val editar = if (newProps.cve_articulo != "") true else false

			rp.setState(State(newProps, editArticulo = editar))
		}
		.build

	def apply(item: Articulo, proxy: ModelProxy[Pot[Articulo]], msg: String,
						nuevo: () => Callback,
						saveArticulo: Articulo => Callback,
						buscarArticulo: String => Callback
						//,
						//desabilitarArticulo: String => Callback
					 ) = {
		  component(Props(Some(item), proxy, msg, nuevo, saveArticulo, buscarArticulo/*, desabilitarArticulo*/))
	}
}

