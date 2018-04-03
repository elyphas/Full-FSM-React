package spatutorial.client.modules

import diode.data.Pot
//import diode.react.ModelProxy
import diode._
//import japgolly.scalajs.react.vdom.{VdomArray, VdomElement}

import spatutorial.client.services.SPACircuit
import spatutorial.client.components.Bootstrap.{Button, RadioButton, Text /*, CheckBox, CommonStyle, TextArea*/ }
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

import spatutorial.client.actionhandlers.{GetProceso/*GetLicitantes*/}

//import spatutorial.client.components.{ rptInvCotizar }

//import spatutorial.client.actionhandlers.{GetPartida, CleanPartida/*, InsertPrograma, SavePrograma*/}

//import org.scalajs.dom.window

object frmDocumentos {
	@inline private def bss = GlobalStyles.bootstrapStyles

  //case class Props(proxy: ModelProxy[Pot[(Partida, String)]])

	case class State(id_comparativo: String = "", document: String = "", buttonDisabled: Boolean = true, lst: Seq[Licitante] = Seq.empty)

	class Backend($: BackendScope[Unit, State]) {

		private var unsubscribe = Option.empty[() => Unit]

    def willMount = Callback {
        unsubscribe = Some(SPACircuit.subscribe(SPACircuit.zoom(_.licitantes))(lst => getLicitantes(lst).runNow()))
    }

		def willUnmount = Callback {
			unsubscribe.foreach(f => f())
			unsubscribe = None
		}

		def onChangeIdProceso(e: ReactEventFromInput) = $.state >>= { s =>
			val text = e.target.value.toUpperCase
			if (text.takeRight(4) == "ADQ2") $.modState(s => s.copy(id_comparativo = text), buscaProceso(text))
			else	$.modState(s => s.copy(id_comparativo = text, buttonDisabled = true))
		}

		def onClickBases = $.state >>= { s => $.modState( s => s.copy( document = "Bases")) }

		def onInvCotizar = $.state >>= { s => $.modState( s => s.copy( document = "InvCotizar")) }

		def onDictamen = $.state >>= { s => $.modState( s => s.copy( document = "Dictamen")) }

		def onFallo = $.state >>= { s => $.modState( s => s.copy( document = "Fallo"))}

		def onContrato = $.state >>= { s => $.modState( s => s.copy( document = "Contrato"))}

		def onActaRecepcion = $.state >>= { s => $.modState( s => s.copy( document = "ActaRecepcion"))}

		def onPrint = Callback.alert("Imprimir")

		def buscaProceso(id: String ) = Callback {
			val dispatch: Dispatcher = SPACircuit
			dispatch(GetProceso(id))
		}

		def onPreview = $.state >>= { s =>

			val doc: reports = s.document match {
  					case "InvCotizar" => new rptInvCotizar
  					case "Dictamen" => new rptDictamen
  					case "Bases" => new rptBases
						case "Fallo" => new rptFallo
						case "Contrato" => new rptContrato
						case "ActaRecepcion" => new rptActaRecepcion
			}
			Callback(doc.imprimir(s.lst))
		}


  	def getLicitantes(lst: ModelRO[Pot[(Seq[Licitante], String)]]) = $.state >>= { s =>
      val licitantes = lst.value match {
          case x if x.isUnavailable => Seq(Licitante())
          case x if x.isEmpty => Seq(Licitante())
          case x if x.isFailed => Seq(Licitante())
          case _ => lst.value.get._1
        }
			if ( licitantes.isEmpty) Callback.alert("No hay licitantes")
			else	$.modState( s => s.copy(buttonDisabled = false, lst = licitantes))
    }


		def	render(s: State) = {

			<.div( ^.padding := "30",  ^.marginLeft:="100",  ^.border := "1px solid", ^.marginTop := "3", ^.borderColor:="#20B2AA",
				^.height := "auto", ^.overflow := "hidden",
			<.header( <.h1( "Imprimir documentos") ),

					<.div( ^.padding := "5", ^.border := "solid", ^.marginTop := "5", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",
						Text(Text.Props(idx = "txtIdProceso", label = "Id Proceso",
							onChange = onChangeIdProceso, value = s.id_comparativo, otrosStyles = Seq(^.width := "500")))
					),
					<.div( ^.padding := "5", ^.border := "solid", ^.marginTop := "5", ^.borderColor:="#20B2AA", ^.height := "auto", ^.overflow := "hidden",
						RadioButton(RadioButton.Props( "rdBases", "Bases", onClickBases, addStyles = Seq( bss.buttonXS),
							value = if(s.document == "Bases") true else false)),
						RadioButton(RadioButton.Props( "rdInvCotizar", "Inv. a cotizar", onInvCotizar, addStyles = Seq( bss.buttonXS),
							value = if(s.document == "InvCotizar") true else false)),
						RadioButton(RadioButton.Props( "rdDictamen", "Dictamen", onDictamen, addStyles = Seq( bss.buttonXS),
							value = if(s.document == "Dictamen") true else false)),
						RadioButton(RadioButton.Props( "rdFallo", "Fallo", onFallo, addStyles = Seq( bss.buttonXS),
							value = if(s.document == "Fallo") true else false)),
						RadioButton(RadioButton.Props( "rdContrato", "Contrato", onContrato, addStyles = Seq( bss.buttonXS),
							value = if(s.document == "Contrato") true else false)),
						RadioButton(RadioButton.Props( "rdActaRecepcion", "Acta Recepción", onActaRecepcion, addStyles = Seq( bss.buttonXS),
							value = if(s.document == "ActaRecepcion") true else false))
					),
					<.div(^.padding := "10", ^.border:="solid", /*^.marginTop:=50,*/ ^.borderColor:="#20B2AA",
						Button(Button.Props( onPrint, addStyles = Seq( bss.buttonXS), otrosStyles = Seq(^.disabled := s.buttonDisabled)),  "Imprimir"),
						Button(Button.Props( onPreview, addStyles = Seq( bss.buttonXS), otrosStyles = Seq(^.disabled := false/*s.buttonDisabled*/)), "Prever")
					)
				)
		}
	}

	val component = ScalaComponent.builder[Unit]("frmDocumentos")
		.initialState(State(id_comparativo = "", document = "ActaRecepcion", buttonDisabled = true))
		.renderBackend[Backend]
    .componentWillMount(_.backend.willMount)
    .componentWillUnmount(_.backend.willUnmount)
	  .componentDidMount(_.backend.buscaProceso("315-2017-ADQ2"))
		.build

  def apply() = component()

}
