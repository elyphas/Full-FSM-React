package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
//import diode._
import diode.Dispatcher
import diode.ModelRO

import spatutorial.client.actionhandlers.GetAllPrograma
import spatutorial.client.services.SPACircuit
import spatutorial.client.components.Bootstrap.{Button, Text}
//import spatutorial.client.components.Bootstrap._
import spatutorial.client.components._

import org.scalajs.dom.ext.KeyValue

//import spatutorial.client.logger._
import spatutorial.client.services._
import spatutorial.shared._

//import scala.concurrent.duration._
//import scala.concurrent.{Future}
//import scala.util.{Success, Failure, Try}
//import scalacss.ScalaCssReact._

//import autowire._
//import boopickle.Default._
//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import spatutorial.client.actionhandlers.{ /*GetProceso,*/ GetLastIDProceso, SetNewProceso, DeleteProceso, DeletePedidos /*InsertProceso, SaveProceso,*/ }
import spatutorial.client.actionhandlers.{GetLicitantes, GetProcesoOnly, GetOficina}
import spatutorial.client.actionhandlers.{GetAllOficinas, CleanLstProgramas, GetPrograma}

//import org.scalajs.dom.raw._
//import org.scalajs.dom.window

/*import scalaz._
import syntax.validation._
import syntax.semigroup._
import Validation._
import std.AllInstances._
import std.option._
import syntax.std.all._
import std.string._*/

import scala.scalajs.js.{/*|,*/ Date => jsDate}

//import scala.concurrent.ExecutionContext.Implicits.global


object frmDatosGrales {

	@inline private def bss = GlobalStyles.bootstrapStyles

  case class Props( proxy: ModelProxy[Pot[(Proceso, String)]] )

  case class State( programa: Seq[String] = Seq(), descripOficina: String = "", descripPrograma: String = "", item: Proceso = Proceso(),
										showSearchCatalogo: Boolean = false, newItem: Boolean = false, msg: String = "",
										lstOficinas: Seq[Oficina] = Seq.empty, oficinasFiltered: Seq[Oficina] = Seq.empty,
										lstProgramas: Seq[Programa] = Seq.empty, programasFiltered: Seq[Programa] = Seq.empty,
										showLstOficinas: Boolean = false,
										showLstProgramas: Boolean = false
									)

	class Backend($: BackendScope[Props, State]) {

		def onChangePrograma(e: ReactEventFromInput) = {
			val txt = e.target.value.toUpperCase
			$.modState(s => s.copy(item = s.item.copy(programa = txt)))
		}

		/*def CboChangedTipoCompra(e: ReactEventFromInput) =	Callback.alert(e.target.value)

		def CboChangedPrograma(e: ReactEventFromInput) =	Callback.alert(e.target.value)*/

		def digits00(valor: Int) = if (valor > 1 && valor < 10) "0" + valor else valor

		def Hoy = {
				//import java.time.{LocalDate}
				//val hoy = LocalDate.now()
				//digits00(hoy.getDayOfMonth) + "/" + digits00(hoy.getMonthValue) + "/" + hoy.getYear
				val hoy: jsDate = new jsDate()
				digits00(hoy.getDay) + "/" + digits00(hoy.getMonth ) + "/" + hoy.getFullYear

		}

		def onSave = $.state.zip($.props) >>= { case (s, p) =>
			Callback.alert("Se modifico por que no se va ha usar scalaz!!")
			/*import scalaz.Scalaz._
			import scalaz.std.string._

			s.item.validate match {
				case Failure(errores) =>
					Callback.alert(errores.toList.mkString("\n"))
				case Success(ver) => {
					if (s.newItem)
							p.proxy.dispatchCB(InsertProceso(s.item))
					else if (!s.newItem && window.confirm("Modificar?"))
							p.proxy.dispatchCB(SaveProceso(s.item))
					else
							Callback.alert("no se guardo!")
				}
			}*/
		}

		def Delete() = $.state.zip($.props) >>= { case (s, p) => p.proxy.dispatchCB(DeleteProceso(s.item.id_comparativo)) }

		def DelPedidos = $.state.zip($.props) >>= { case (s, p) => p.proxy.dispatchCB(DeletePedidos(s.item.id_comparativo)) }

 		def onChageElaboro(e: ReactEventFromInput) = Callback.alert(e.target.value)

		def onChangeDoctoSolicitud(e: ReactEventFromInput) = {
			val txt = e.target.value.toUpperCase
			$.modState(s => s.copy(item = s.item.copy(memo = txt)))
		}

		def onChangeDestino(e: ReactEventFromInput) = {
			val txt = e.target.value.toUpperCase
			$.modState(s => s.copy(item = s.item.copy(destino = txt)))
		}

		def onBuscarOfic = $.modState(_.copy(showSearchCatalogo = true))

		def changeEjercicio(e: ReactEventFromInput) =	{
      val txt = e.target.value.toUpperCase
      $.modState(s => s.copy(item = s.item.copy(ejercicio = txt.toInt)))
    }

		def onChangeFecha(e: ReactEventFromInput) = {
			val txt = e.target.value.toUpperCase
			$.modState(s => s.copy(item = s.item.copy(fecha = Fechas(txt))))
		}

		def onChangeCveOficina(e: ReactEventFromInput) = {

      val txt = e.target.value.toUpperCase

      if (txt.length == 0)
        $.modState(s => s.copy(item = s.item.copy(cveOficina = txt.toUpperCase), showLstOficinas = false))
      else if (txt.length > 1)
        $.modState(s => s.copy(item = s.item.copy(cveOficina = txt.toUpperCase), showLstOficinas = true), onBuscarCveOficinas(txt))
      else
        $.modState(s => s.copy(item = s.item.copy(cveOficina = txt.toUpperCase), showLstOficinas = true))

		}

    def onBuscarCveOficinas(str: String) = $.state >>= { s =>
      val filtered = s.lstOficinas.filter(of => of.clave.take(str.length) == str).sortBy(_.clave)
      $.modState(s => s.copy(oficinasFiltered = filtered))
    }

		def onChangeDescripcionOficina(e: ReactEventFromInput) = {
			val txt = e.target.value.toUpperCase
			if (txt.length == 0)
				$.modState(s => s.copy( descripOficina = txt.toUpperCase, showLstOficinas = false))
			else if (txt.length > 4)
				$.modState(s => s.copy( descripOficina = txt.toUpperCase, showLstOficinas = true), onBuscarOficinas(txt))
			else
				$.modState(s => s.copy( descripOficina = txt.toUpperCase, showLstOficinas = true))
		}

		def onBuscarOficinas(str: String) = $.state >>= { s =>
			val filtered = s.lstOficinas.filter(of => of.descripcion contains(str))
			$.modState(s=>s.copy(oficinasFiltered = filtered))
		}

    def onChangeCvePrograma(e: ReactEventFromInput) = {
      val txt = e.target.value.toUpperCase
      if (txt.length == 0)
        $.modState(s => s.copy(item = s.item.copy(programa = txt.toUpperCase), showLstProgramas = false))
      else if (txt.length > 1)
        $.modState(s => s.copy(item = s.item.copy(programa = txt.toUpperCase), showLstProgramas = true), onBuscarCveProgramas(txt))
      else
        $.modState(s => s.copy(item = s.item.copy(programa = txt.toUpperCase), showLstProgramas = true))
    }

    def onBuscarCveProgramas(str: String) = $.state >>= { s =>
      val filtered = s.lstProgramas.filter(p => p.programa.take(str.length) == str).sortBy(_.programa)
      $.modState(s => s.copy(programasFiltered = filtered))
    }

		def onChangeDescripcionPrograma(e: ReactEventFromInput) = {
			val txt = e.target.value.toUpperCase
			if (txt.length == 0)
				$.modState(s => s.copy( descripPrograma = txt.toUpperCase, showLstProgramas = false))
			else if (txt.length > 4) {
				$.modState(s => s.copy( descripPrograma = txt.toUpperCase, showLstProgramas = true), onBuscarProgramas(txt))
			} else
				$.modState(s => s.copy( descripPrograma = txt.toUpperCase, showLstProgramas = true))
		}

		def onBuscarProgramas(str: String) = $.state >>= { s =>
			val filtered = s.lstProgramas.filter(prog => prog.descripcion contains(str))
			$.modState(s=>s.copy(programasFiltered = filtered))
		}

		def updateLstProgramas(programas: ModelRO[Pot[Seq[Programa]]]) =  {
			val progrs = programas.value match {
				case x if x.isUnavailable => Seq(Programa())
				case x if x.isEmpty => Seq(Programa())
				case x if x.isFailed => Seq(Programa())
				case _ => programas.value.get
			}
			$.modState(s => s.copy(lstProgramas = progrs)).runNow()
		}

    def updateDescripcionPrograma(programa: ModelRO[Pot[Programa]]) =  {
      val progr: Programa = programa.value match {
        case x if x.isUnavailable => Programa()
        case x if x.isEmpty => Programa()
        case x if x.isFailed => Programa()
        case _ => programa.value.get
      }
      $.modState(s => s.copy( descripPrograma = progr.descripcion)).runNow()
    }

    def updateDescripcionOficina(oficina: ModelRO[Pot[(Oficina, String)]]) =  {
      val ofic: Oficina = oficina.value match {
        case x if x.isUnavailable => Oficina()
        case x if x.isEmpty => Oficina()
        case x if x.isFailed => Oficina()
        case _ => {
          val (ofici: Oficina, _ ) = oficina.value.get
          ofici
        }
      }
      $.modState(s => s.copy( descripOficina = ofic.descripcion)).runNow()
    }

		def onChangePlazo(e: ReactEventFromInput) = {
			val text = e.target.value.toUpperCase
			$.modState(s => s.copy(item = s.item.copy(plazo = text)))
		}


		def onChageIdProceso(e: ReactEventFromInput) = $.state >>= { s =>

			val text = e.target.value

			if (text.takeRight(4) == "ADQ2")
				$.modState(s => s.copy(item = s.item.copy(id_comparativo = text)), buscarProceso(text))
			else
				$.modState(s => s.copy(item = s.item.copy(id_comparativo = text)))

		}

		def onSearch = Callback.alert("buscar")

		def onKeyDownCveOficina(e: ReactKeyboardEvent) = if(e.key == KeyValue.Escape) $.modState(s => s.copy(showLstOficinas = false)) else Callback.empty

		def onKeyDownDescripcionOficina(e: ReactKeyboardEvent) = if(e.key == KeyValue.Escape) $.modState(s => s.copy(showLstOficinas = false)) else Callback.empty

		def onKeyDownDescripcionPrograma(e: ReactKeyboardEvent) = if(e.key == KeyValue.Escape) $.modState(s => s.copy(showLstProgramas = false)) else Callback.empty

		def onFocusCveOficina = $.modState( s => s.copy(showLstOficinas = true))

		def onLostFocusCveOficina = Callback.empty

		def buscarProceso(id: String) = $.props >>= (_.proxy.dispatchCB(GetProcesoOnly(id)))

    def onNew = $.state.zip($.props) >>= { case (s, p) =>
			val dispatch: Dispatcher = SPACircuit
			dispatch(GetLastIDProceso(s.item.ejercicio))
			$.modState(s => s.copy( newItem = true)) >> p.proxy.dispatchCB(SetNewProceso)
		}

		def getLicitantes = $.state >>= { s =>
			Callback {
				val dispatch: Dispatcher = SPACircuit
				dispatch(GetLicitantes(s.item.id_comparativo))
			}
		}

    def updateID(lastID: ModelRO[Pot[LastID]]) = {
      val id = lastID.value match {
        case x if x.isUnavailable => LastID()
        case x if x.isEmpty => LastID()
        case x if x.isFailed => LastID()
        case _ => lastID.value.get
      }
			val folio = id.lastID + 1
			val idComparativo = folio + "-" + id.ejercicio.toString + "-ADQ2"

			//In this context is right to use ".runNow()"
      $.modState(s => s.copy(item = s.item.copy(id_comparativo = idComparativo, folio = folio ))).runNow()
    }

		def updateLstOficinas(lstOfi: ModelRO[Pot[Seq[Oficina]]]) = {
			val lstOfic: Seq[Oficina] = lstOfi.value match {
					case x if x.isUnavailable => Seq(Oficina())
					case x if x.isEmpty => Seq(Oficina())
					case x if x.isFailed => Seq(Oficina())
					case _ => lstOfi.value.get
			}
			$.modState(s => s.copy(lstOficinas = lstOfic)).runNow()	//In this context is right to use ".runNow()"
		}

		def onSelectOficina(of: Oficina) = $.modState(s => s.copy(item = s.item.copy(cveOficina = of.clave),	showLstOficinas = false, descripOficina = of.descripcion))

		def domLstOficinas(p:Props, s:State) =
			<.div(/*^.float:="left", ^.padding := "10", */ ^.border := "1px solid", ^.marginTop := "70", ^.marginLeft := "400",
				^.borderColor := "#20B2AA", ^.height := "auto", ^.width := "400", ^.position := "absolute",
				^.height := "200",  ^.overflow := "scroll",
				//^.width:="600", ^.height := "auto",  ^.overflow := "hidden", ^.position:="absolute", ^.zIndex:="15",
				cmpLstOficinas( s.oficinasFiltered,
					item => onSelectOficina(item),												//Add
					item => Callback.empty,                                       //Edit
					item => Callback.empty            														//Delete
				)
			)

		def onSelectPrograma(it: Programa) = {
			val dispatch: Dispatcher = SPACircuit
			dispatch(CleanLstProgramas)
			$.modState(s => s.copy(item = s.item.copy(programa = it.programa), showLstProgramas = false, descripPrograma = it.descripcion))
		}

		def domLstProgramas(p:Props, s:State) =
			<.div(^.border := "1px solid", ^.marginTop := "70", ^.marginLeft := "300", ^.marginBottom:="200", ^.borderColor := "#20B2AA",
				^.width := "420", ^.position := "absolute", ^.height := "200",  ^.overflow := "scroll",
				cmpLstProgramas( s.programasFiltered,
					item => onSelectPrograma(item),						//Add
					item => Callback.empty,                   //Edit
					item => Callback.empty            				//Delete
				)
			)

		def	render(p: Props, s: State) = {

				val colorMsg = if (s.newItem) " #f9927b" else " #9ff594 "

				<.div(^.float:="left", ^.padding := "30", ^.marginLeft:="100", ^.border := "1px solid", ^.marginTop := "3",
					^.borderColor:="#20B2AA", ^.height := "auto", ^.position:="relative",

				<.header(<.h1("Datos Generales")),

					if(s.msg == "") VdomArray.empty()

					else <.span(s.msg, ^.backgroundColor := colorMsg, ^.fontSize := "20", ^.fontWeight := "true"),
					<.div(
						<.div(^.width := "800", ^.padding := "10",

							Text(Text.Props(idx="txtElabora", label="Elabora",  onChange = onChageElaboro, value = s.item.elaboro,
								otrosStyles = Seq(^.width := "110", ^.marginRight := "5"))),

							Text(Text.Props( idx = "txtEjercicio",  label = "Ejercicio", value = s.item.ejercicio.toString,
								onChange = changeEjercicio, addStyles = Seq(bss.marginRight), otrosStyles = Seq(^.width := "57", ^.marginRight := "5"))),

							Text(Text.Props(idx = "txtPresupuesto", label = "Presupuesto", value = s.item.ejercicioPresupuestal.toString,
								onChange=changeEjercicio, addStyles = Seq( bss.marginRight ), otrosStyles = Seq(^.width := "57", ^.marginRight := "5"))),

							Text(Text.Props(idx="txtIdProceso", label="IdProceso",  onChange = onChageIdProceso,value=s.item.id_comparativo)),

							Text(Text.Props(idx="txtFecha", label="Fecha",onChange = onChangeFecha, value = s.item.fecha.fecha,
								otrosStyles = Seq(^.width := "100", ^.marginRight := "5", ^.readOnly:=true)))

						)
					),
					<.div(
						<.div(^.clear:="both",
							Text(Text.Props(idx="docSolicitud", label="Doc. Solicitud", value = s.item.memo, onChange=onChangeDoctoSolicitud)),
							Text(Text.Props(idx="txtDestino", label="Destino", value = s.item.destino, onChange = onChangeDestino))
						),
						Text(Text.Props(idx = "txtCveOficina", label = "Cve. Oficina",
							value = s.item.cveOficina, onChange = onChangeCveOficina, onFocus = onFocusCveOficina, onBlur = onLostFocusCveOficina,
							addStyles = Seq( bss.marginRight ), otrosStyles = Seq(^.width := "57", ^.onKeyDown ==> onKeyDownCveOficina)
						)),
						Text(Text.Props(idx = "txtNombreOficina", label = "Nombre Oficina", value = s.descripOficina, onChange = onChangeDescripcionOficina,
							addStyles = Seq( bss.marginRight ), otrosStyles = Seq(^.width:="200", ^.onKeyDown ==> onKeyDownDescripcionOficina)
						)),
						if(s.showLstOficinas) domLstOficinas(p, s) else VdomArray.empty()
					),
					<.div( ^.clear := "both",
						Text(Text.Props(idx = "txtPlazo", label="Plazo", value = s.item.plazo, onChange = onChangePlazo,
							addStyles = Seq(bss.marginRight), otrosStyles = Seq(^.width := "150", ^.marginRight := "5")
						)),

						Text(Text.Props(idx="txtCvePrograma", label="Cve. Programa", value = s.item.programa, onChange=onChangeCvePrograma,
							addStyles = Seq(bss.marginRight), otrosStyles = Seq(^.width := "100", ^.marginRight := "5")
						)),

						Text(Text.Props(idx = "txtDescripcionPrograma", label = "Descripcion Programa", value = s.descripPrograma, onChange = onChangeDescripcionPrograma,
							addStyles = Seq( bss.marginRight ), otrosStyles = Seq(^.width:="200", ^.onKeyDown ==> onKeyDownDescripcionPrograma)
						)),
						if(s.showLstProgramas) domLstProgramas(p, s) else VdomArray.empty()
					),
					<.div(^.clear:="both", ^.float:="left", ^.border:="1px solid", ^.marginTop := "10", ^.borderColor:="#20B2AA", ^.display:= "inline-block", ^.padding:="5",
						Button(Button.Props(onSave, addStyles = Seq( bss.buttonXS)), "Guardar"),
						Button(Button.Props(Delete , addStyles = Seq( bss.buttonXS)), "Borrar"),
						Button(Button.Props(onNew, addStyles = Seq( bss.buttonXS)), "Nuevo"),
						Button(Button.Props(onSearch, addStyles = Seq( bss.buttonXS)), "Buscar"),
						Button(Button.Props(DelPedidos, addStyles = Seq( bss.buttonXS)), "Borra Pedidos")
					)
				)
		}
	}

	val component = ScalaComponent.builder[Props]("frmDatosGrales")
		.initialState(State( item = Proceso()))
		.renderBackend[Backend]
    .componentWillReceiveProps { rp =>

      val idPrev = rp.state.item.id_comparativo

      val (proceso: Proceso, msg: String) = rp.nextProps.proxy() match {
        case x if x.isUnavailable => {
					if (rp.state.newItem) (Proceso(id_comparativo = idPrev), "Nuevo")
					else	(Proceso(elaboro = "JCESAR"),"No se encontro")
				}
        case x if x.isEmpty => (Proceso(elaboro = "JCESAR", fecha = Fechas(rp.backend.Hoy)), "Nuevo")
        case x if x.isFailed => (Proceso(elaboro = "JCESAR"), "Error")
        case _ => {
					val (proc: Proceso, evento: String) = rp.nextProps.proxy().get

					val mensage = if(evento == "inserted") "Guardado"
												else if (evento == "saved")	"Modificado"
												else if (evento == "found") "Encontrado"
												else "No encontrado"
					(proc, mensage)
				}
      }

      SPACircuit.subscribe(SPACircuit.zoom(_.programa))(progr => rp.backend.updateDescripcionPrograma(progr))
      SPACircuit.dispatch(GetPrograma(proceso.programa))

      SPACircuit.subscribe(SPACircuit.zoom(_.oficina))(ofic => rp.backend.updateDescripcionOficina(ofic))
      SPACircuit.dispatch(GetOficina(proceso.cveOficina))

      //val editar = if (proceso.id_comparativo != "") true else false
			val newItem = if(msg == "Nuevo" || msg == "No se encontro") true else false

      rp.setState(State(item = proceso, newItem = newItem, msg = msg))

    }
   .componentWillMount { scope =>
      //val unsubscribe =
			SPACircuit.subscribe(SPACircuit.zoom(_.lastID))(lastID => scope.backend.updateID(lastID))
      //SPACircuit.subscribe(SPACircuit.zoom(_.catOficinas))(oficinas => scope.backend.updateOficinas(oficinas))
		 	//val unsubscribeLstOficinas =
			SPACircuit.subscribe(SPACircuit.zoom(_.catOficinas))(lstOficinas => scope.backend.updateLstOficinas(lstOficinas))
		 	SPACircuit.dispatch(GetAllOficinas)
		  SPACircuit.subscribe(SPACircuit.zoom(_.catProgramas))(lstProgramas => scope.backend.updateLstProgramas(lstProgramas))
		  SPACircuit.dispatch(GetAllPrograma)
      val date = new jsDate()
		 	scope.modState(s=>s.copy(item = s.item.copy(fecha = Fechas(scope.backend.Hoy), ejercicio = date.getFullYear, ejercicioPresupuestal = date.getFullYear)))
  	}
		.build

  def apply() = {
    val ProcesoWraper = SPACircuit.connect(_.proceso)
    ProcesoWraper(prox => component(Props(proxy = prox)))
  }
}
