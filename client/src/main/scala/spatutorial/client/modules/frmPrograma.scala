package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
//import diode._
import japgolly.scalajs.react.vdom.{VdomArray /*, VdomElement*/ }

import spatutorial.client.services.SPACircuit
import spatutorial.client.components.Bootstrap.{Button, CheckBox, /*CommonStyle,*/ Text, TextArea}
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
import japgolly.scalajs.react.vdom.html_<^._

import spatutorial.client.actionhandlers.{GetPrograma, CleanPrograma, InsertPrograma, SavePrograma}

//import org.scalajs.dom.window

object frmPrograma {
	@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(proxy: ModelProxy[Pot[Programa]])

	case class State( item: Programa = Programa(), showSearchCatalogo: Boolean = false,
                    editar: Boolean = false, saved: Boolean = false, msg: String = "")

	class Backend($: BackendScope[Props, State]) {

		def onChangeID(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			if (text.length >= 9)	$.modState(s=>s.copy(item = s.item.copy(programa = text)), getPrograma(text))
			else	$.modState(s=>s.copy(item = s.item.copy(programa = text)))
		}

		def getPrograma(id: String) = $.props >>= (_.proxy.dispatchCB(GetPrograma(id)))

		//def searchPrograma(id: String) = $.props >>= (_.proxy.dispatchCB(GetArticulo(id)))

		def onChangeDescripcion(e: ReactEventFromInput) =  {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(descripcion = text)))
		}

		def onChangeDestino(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(destino = text)))
		}

		def onChangeDepto(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(depto = text)))
		}

		def onChangeShow = $.state >>= { s => $.modState(s=>s.copy(item = s.item.copy(mostrar = !s.item.mostrar)))}

		def onChangeRFC_Dependencia(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(rfc_dependencia = text)))
		}

		def onChangeNivel(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(nivel = text)))
		}

		def onChangeEncargado(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(encargado = Some(text))))
		}

		def onChangeActivo = $.state >>= { s => $.modState(s=>s.copy(item = s.item.copy(mostrar = !s.item.activo))) }

		def onChangeFuenteFinanciamiento(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(fuente_financiamiento = text)))
		}

		def onSave = $.state.zip($.props) >>= { case (s, p) =>
			p.proxy.dispatchCB(SavePrograma(s.item))
		}

		def onNew = $.props >>= (_.proxy.dispatchCB(CleanPrograma))

		def onAdd = $.state.zip($.props) >>= { case (s, p) =>
			p.proxy.dispatchCB(InsertPrograma(s.item))
		}

		def onSearch = Callback.alert("Buscar")

		def onDelete = Callback.alert("Borrar")

		def	render(p: Props, s: State) = {

				<.div( ^.padding := "30",  ^.marginLeft:="100",  ^.border := "1px solid", ^.marginTop := "3", ^.borderColor:="#20B2AA",
					^.height := "auto", ^.overflow := "hidden",

				<.header( <.h1( "Programa presupuestal") ),

					if(s.msg == "") VdomArray.empty() else <.span(s.msg),

					<.div( ^.padding := "5", ^.border := "solid", ^.marginTop := "5", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",
						<.section(^.width := "800", ^.padding := "5",

							<.div(^.height := "auto", ^.overflow := "hidden",
								Text(Text.Props(label="ID",  onChange = onChangeID, value = s.item.programa,
									otrosStyles = Seq(^.width := "120", ^.marginRight := "5"))),
								TextArea(TextArea.Props(label="Descripción", onChange = onChangeDescripcion,
									value = s.item.descripcion, otrosStyles = Seq(^.width := "160", ^.marginRight := "5"))),
								TextArea(TextArea.Props(label="Destino", onChange = onChangeDestino,
									value = s.item.destino, otrosStyles = Seq(^.width := "160", ^.marginRight := "5")))
							),

							<.div(^.height := "auto", ^.overflow := "hidden",
								Text(Text.Props(label="Departamento", onChange = onChangeDepto,
									value = s.item.depto, otrosStyles = Seq(^.width := "70", ^.marginRight := "5"))),

								CheckBox(CheckBox.Props(label="Mostrar", onClick = onChangeShow,
									value = s.item.mostrar, otrosStyles = Seq(^.width := "60"))),

								Text(Text.Props(label="Nivel", onChange = onChangeNivel,
									value = s.item.nivel, otrosStyles = Seq(^.width := "100", ^.marginRight := "5"))),

								Text(Text.Props(label="Encargado", onChange = onChangeEncargado,
									value = s.item.encargado.getOrElse(""), otrosStyles = Seq(^.width := "200", ^.marginRight := "5"))),

								CheckBox(CheckBox.Props(label="Activo", onClick = onChangeActivo,
									value = s.item.activo, otrosStyles = Seq(^.width := "60")))
							),
							Text(Text.Props(label="Fuente Financiamiento", onChange = onChangeFuenteFinanciamiento,
								value = s.item.fuente_financiamiento, otrosStyles = Seq(^.width := "250", ^.marginRight := "5")))

						)
					),
					<.div(^.padding := "30",  ^.marginLeft:="100",  ^.border := "1px solid", ^.marginTop := "3", ^.borderColor:="#20B2AA",
						^.height := "auto", ^.overflow := "hidden",
						Button(Button.Props(onSave, addStyles = Seq( bss.buttonXS)), "Guardar"),
						Button(Button.Props(onAdd, addStyles = Seq( bss.buttonXS)), "Agregar"),
						Button(Button.Props(onNew, addStyles = Seq( bss.buttonXS)), "Nuevo"),
						Button(Button.Props(onSearch, addStyles = Seq( bss.buttonXS)), "Buscar"),
						Button(Button.Props(onDelete, addStyles = Seq( bss.buttonXS)), "Borrar")
					)
				)
		}
	}

	val component = ScalaComponent.builder[Props]("frmPrograma")
		.initialState(State(item = Programa(), editar = false))
		.renderBackend[Backend]
    .componentWillReceiveProps{ rp =>

      val (programa: Programa, editar: Boolean, msg: String) = rp.nextProps.proxy() match {
        case x if x.isUnavailable => (Programa(programa = rp.state.item.programa), false, "No se encontro")
        case x if x.isEmpty => (Programa(), false, "Nuevo")
        case x if x.isFailed => (Programa(), "Error")
        case _ => {
					(rp.nextProps.proxy().get, true, "Guardado")
				}
      }
      rp.setState(State(item = programa, editar = editar, msg = msg))
    }
		.build


  def apply() = {
      val ProgramaWraper = SPACircuit.connect(_.programa)
			ProgramaWraper(prox => component(Props(proxy = prox)))
  }
}
