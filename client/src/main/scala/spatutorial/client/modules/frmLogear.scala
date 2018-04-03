package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
//import diode._
//import japgolly.scalajs.react.vdom.VdomArray
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

import spatutorial.client.actionhandlers.{Loggear}

//import org.scalajs.dom.window

object frmLogear {
	@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(proxy: ModelProxy[Pot[Usuarios]])

	case class State( item: Usuarios = Usuarios())

	class Backend($: BackendScope[Props, State]) {

		def onChangeUsuario(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s=>s.copy(item = s.item.copy(usuario = text)))
		}

		def onChangeContraseña(e: ReactEventFromInput) = {
			val text = e.target.value
			$.modState(s=>s.copy(item = s.item.copy(contraseña = text)))
		}

		def onLogear = $.state.zip($.props) >>= { case (s, p) => p.proxy.dispatchCB(Loggear(s.item)) }

		def	render(p: Props, s: State) = {
				<.div( ^.padding:="5", ^.border:="1px solid", ^.marginTop:="5", ^.borderColor:="#20B2AA", ^.display:= "inline-block",
			<.header( <.h1( "Logear") ),
					//if(s.msg == "") VdomArray.empty() /*Seq.empty[ReactElement]*/ else <.span(s.msg),
					<.div(^.float:="left", ^.padding := "5", ^.border:="1px solid", ^.marginTop := "5", ^.borderColor:="#20B2AA", ^.height := "auto", ^.display:= "inline-block",
						Text(Text.Props(idx="txtUsuario", label="Usuario",  onChange = onChangeUsuario, value = s.item.usuario,
							otrosStyles = Seq(^.width:="140", ^.marginRight:="5"))),

						Text(Text.Props(idx="txtContraseña", label="Contraseña",  onChange = onChangeContraseña,
							typeInput = "password", value=s.item.contraseña, otrosStyles = Seq(^.width:="310", ^.marginRight:="5")))
					),
					<.div( ^.clear:="both", ^.float:="left", ^.marginTop:="10", ^.padding := "5", ^.border:="1px solid", ^.borderColor:="#20B2AA", ^.display:= "inline-block",
						Button(Button.Props(onLogear, addStyles = Seq( bss.buttonXS)), "Logear")
					)
				)
		}
	}

	val component = ScalaComponent.builder[Props]( "frmLogear" )
		.initialState(State(item = Usuarios()))
		.renderBackend[ Backend ]
    .componentWillReceiveProps { rp =>

      val usuario: Usuarios = rp.nextProps.proxy() match {
        case x if x.isUnavailable => Usuarios()
        case x if x.isEmpty => Usuarios()
        case x if x.isFailed => Usuarios()
        case _ => rp.nextProps.proxy().get
      }
      rp.setState(State(item = usuario))
    }
		.build

  def apply() = {
    val UsuariosWraper = SPACircuit.connect(_.usuarios)
		UsuariosWraper(prox => component(Props(proxy = prox)))
  }
}
