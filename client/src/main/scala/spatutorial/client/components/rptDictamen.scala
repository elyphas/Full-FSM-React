package spatutorial.client.components

//import com.sun.tracing.Probe
//import diode.data.Pot
//import diode.react.ModelProxy
//import diode._
import spatutorial.client.pdfMake._
import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.html_<^._
//import spatutorial.client.components.Bootstrap.{Button, Combo, CommonStyle, Text}
//import spatutorial.client.components._
//import spatutorial.client.logger._
//import spatutorial.client.services._
import spatutorial.shared._

//import scala.concurrent.Future
//import scala.util.{Failure, Success, Try}
//import scala.concurrent.duration._
//import scala.concurrent.Await
//import scalacss.ScalaCssReact._
//import chandu0101.scalajs.react.components.Implicits._

//import autowire._
//import boopickle.Default._
//import spatutorial.client.services.AjaxClient
//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

//import scalajs.js
//import js.Dynamic.{ global => g }
import scala.scalajs.js.{| /*, Date => jsDate*/ }

//import java.time._
//import java.util._
//import java.text.SimpleDateFormat

//import spatutorial.client.actionhandlers.{GetQryPedidoDatGrales, GetPartidaAnexo}

import scala.scalajs.js.JSConverters._
//import scala.scalajs.js.annotation.JSName
//import scala.scalajs.js

//https://github.com/jducoeur/bootstrap-datepicker-scalajs
//import org.querki.facades.bootstrap.datepicker._ // this adds '.datepicker' implicit

//import java.time.LocalDate
//import java.time.format.DateTimeFormatter

class rptDictamen extends reports {

  def bodyDocument(lst: Seq[Licitante]) = {

    /*val partidaAnexo = getPartidas
    val descripcionCompra = getDescripcionCompra
    val proceso = getProceso
    val programa = getPrograma
    val calendario = getCalendario*/

    Seq (
      paragraph(text = Seq(texto(text = "\n\n"))),
      paragraph(text = Seq(texto(text = "Chilpancingo, Gro. a " + fechaLarga(proceso.fecha.fecha) + "\n\n", bold = true, alignment = "right"))),
      paragraph(text = Seq(
          texto(text = "En relación con la "),
          texto(text = proceso.tipoCompra, bold = true),
          texto(text = ", para la adquisición de: "),
          texto(text = descripcionCompra.concepto + "; " + descripcionCompra.descripcion, bold = true),
          texto(text = ", para: "),
          texto(text = proceso.destino, bold = true),
          texto(text = ", con recursos provenientes del programa: "),
          texto(text = programa.programa + "; " + programa.descripcion, bold = true),
          texto(text = ", con cargo a las " ),
          texto(text = "partidas, ejercicio presupuestal "),
          texto(text = proceso.ejercicioPresupuestal.toString, bold = true),
          texto(text = ", derivado de la revisión efectuada a las especificaciones técnicas ofertadas "),
          texto(text = "por los participantes, se emite la siguiente: ")
      )),
      paragraph(text = Seq(texto(text = "\n\nDICTAMEN TÉCNICO\n\n", bold = true, alignment = "center"))),
      paragraph(text = Seq(
          texto(text = "1.- Mediante invitación realizada por el Departamento de Adquisiciones de la Secretaría de Salud y/o Servicios "),
          texto(text = "Estatales de Salud del Estado de Guerrero, se invitó a las empresas interesadas a participar en la presentación "),
          texto(text = "de proposiciones técnicas y económicas del proceso de: "),
          texto(text = proceso.tipoCompra, bold = true),
          texto(text = ", para la adquisición: "),
          texto(text = descripcionCompra.concepto + "; " + descripcionCompra.descripcion, bold = true),
          texto(text = ", para "),
          texto(text = proceso.destino, bold = true),
          texto(text = ", con recurso proveniente del programa: "),
          texto(text = programa.programa + "; " + programa.descripcion, bold = true),
          texto(text = ", con cargo a las partidas "),
          texto(text = partidaAnexo.partida + "; " + partidaAnexo.descripcion, bold = true),
          texto(text = ", del ejercicio "),
          texto(text = proceso.ejercicioPresupuestal.toString + ".\n\n", bold = true)
      )),
      paragraph(text = Seq(
          texto(text = "2.- Con fecha "),
          texto(text = fechaLarga( calendario.fecha_junta_aclaraciones.fecha), bold = true),
          texto(text = ", a las "),
          texto(text = calendario.hora_junta_aclaraciones.hora, bold = true),
          texto(text = ", de conformidad con lo establecido en el Apartado A, Disposiciones específicas, numeral 5 "),
          texto(text = "de las bases concúrsales, se llevo a cabo la Junta de Aclaraciones de la Invitación referida, "),
          texto(text = "a la que asistieronlas empresas.")
      )),
      paragraph(text = Seq(texto(text = "\n\n"))),
      lstParticipantes(lst),
      paragraph(text = Seq(texto(text = "\n\n"))),
      paragraph(text = Seq(
        texto(text = "Se precisa que no se recibieron en esta junta los cuestionarios de preguntas sobre algunas dudas o aclaraciones "),
        texto(text = "que pudieran darse respecto de cualquier requisito solicitado en las bases concúrsales, de las empresas "),
        texto(text = "participantes y que consta en el acta debidamente firmada por los funcionarios participantes.\n\n")
      )),
      paragraph(text = Seq(
        texto(text = "3.- El "),
        texto(text = fechaLarga(calendario.fecha_apertura_tecnica.fecha), bold = true),
        texto(text = " a las "),
        texto(text = calendario.hora_apertura_tecnica.hora, bold = true),
        texto(text = " y de acuerdo con lo establecido en las bases concúrsales, se "),
        texto(text = "realizó en el lugar indicado para este efecto, la recepción de sobres que contienen las proposiciones "),
        texto(text = "técnicas y económicas y se efectúo las aperturas de los sobres que contienen dichas proposiciones "),
        texto(text = "técnicas y económicas respectivamente de las siguientes empresas:\n\n")
      )),
      lstParticipantes(lst),
      paragraph(text = Seq(texto(text = "\n\n"))),
      paragraph(text = Seq(
        texto(text = "De las cuales se revisó y analizó por el personal responsable dependiente del Departamento de Adquisiciones "),
        texto(text = "de la Secretaría de Salud y/o Servicios Estatales de Salud, la documentación respectiva, determinando que "),
        texto(text = "cumplieron con la presentación de los requisitos solicitados en las bases concúrsales, por lo que "),
        texto(text = "fueron aceptadas para realizar con posterioridad el análisis de las "),
        texto(text = "carácteristicas técnicas y económicas de los bienes.\n\n")
      )),
      paragraph(text = Seq(
        texto(text = "4.- Posteriormente se procedió a la revisión de las especificaciones técnicas de los bienes "),
        texto(text = "ofertados por los concursantes que cumplieron con los requisitos solicitados, de las cuales se "),
        texto(text = "verificó que cumple con las requeridas por la convocante en las bases de la presente Invitación.")
      )),
      paragraph(text = Seq(texto(text = "\n\nPARTIDAS\n\n", bold = true))),
      lstParticipantes(lst),
      paragraph(text = Seq(texto(text = "\n\n"))),
      evaluacion,
      paragraph(text = Seq(texto(text = "\n\nEVALUACIÓN\n\n", bold = true, alignment = "center"))),
      paragraph(text = Seq(
        texto(text = "Se informa que de la evaluación económica realizada a la oferta presentada, considerando el resultado "),
        texto(text = "técnico en cita. Se determina la adjudicación a la empresa que se ajusta sustancialmente a lo solicitado "),
        texto(text = "por la convocante en cuanto a condiciones legales, técnicas y económicas, siendo esta la siguiente: "),
        texto(text = " ")
      )),
      paragraph(text = Seq(texto(text = "\n\n"))),
      paragraph(table = adjudicados),
      paragraph(text = Seq(texto(text = "\n\nTOTAL ADJUDICADO\n\n"))),
      totalAdjudicado,
      paragraph(text = Seq(texto(text = "\n\n"))),
      paragraph(text = Seq(
        texto(text = "Finalmente se informa que la(s) partida(s) se adjudican de acuerdo al techo presupuestal autorizado y de "),
        texto(text = "conformidad a lo establecido en el numeral 7, de las bases que rigen el presente procedimiento de "),
        texto(text = proceso.tipoCompra, bold = true),
        texto(text = ", para la adquisición de: "),
        texto(text = descripcionCompra.concepto + "; " + descripcionCompra.descripcion, bold = true),
        texto(text = ", para la "),
        texto(text = proceso.destino, bold = true),
        texto(text = ", con recursos provenientes del programa: "),
        texto(text = programa.programa + "; " + programa.descripcion, bold = true),
        texto(text = ", con cargo a las partidas: "),
        texto(text = partidaAnexo.partida + "; " + partidaAnexo.descripcion, bold = true),
        texto(text = ", ejercicio presupuestal: ", bold = true),
        texto(text = proceso.ejercicioPresupuestal.toString, bold = true),
        texto(text = "")
      )),
      paragraph(text = Seq(texto(text = "\n\n\n")))
    )
  }

  def lstParticipantes(lst: Seq[Licitante]) = {
    val lstProv = lst.zipWithIndex.map { case (p, i) =>
        val prov = p.razon_social.getOrElse("")
        val indice = (i+1).toString
        paragraph(text = Seq(texto(text = indice + ".-" + prov, bold = true)))
    }.toJSArray
    lstProv
  }

  def descripcionGanador = " del análisis realizado se desprende que cumple con todos los requisitos técnicos y económicos solicitados en la presente invitación."

  def descripcionPerdedor = ", sus precios resultan no aceptable para la convocante. " +
                "Por lo que de acuerdo en lo establecido en el numeral 8, puntos 8.1 de la convocatoria de la " +
                "del presente procedimiento de invitación. Motivo por el que se desecha y como consecuencia se descalifica " +
                "a la empresa por cuanto a esta partida."

  def descripcionAsignacion = "Por lo que de acuerdo a lo establecido en el numeral 7, inciso 7.1 y 7.2 de las bases de la " +
                "Invitación a Cuando Tres Tres Personas, se adjudica la partida que a continuación se detalla, de acuerdo a " +
                "la disponibilidad presupuestal a la empresa: "

  def evaluacion = {
    val dictamen = getDictamen

    val fmtRenglones = dictamen.groupBy(_.renglon).map { r =>
        val partida =
          Seq((r._1, paragraph(text = Seq(texto(text = "PARTIDA: " + r._1.toString + "\n\n", bold = true))))) +: r._2.map { d =>
            val prov = d.razon_social
            Seq((r._1.toInt,
              paragraph(text = Seq(
                if ( d.precio == d.minimo ) texto(text = "Cabe señalar que la oferta presentada por la empresa: ")
                else texto(text = ""),
                texto(text = prov, bold = true),
                if ( d.precio == d.minimo ) texto(text = descripcionGanador)
                else texto(text = descripcionPerdedor)
                , texto(text = " \n\n")
              ))
            ))
        } :+ Seq((r._1, paragraph(text = Seq(
                          texto(text = "\n"),
                          texto(text = descripcionAsignacion),
                          texto(text = "\n\n")
                      )
                  )
              ))

      val evalCompleted = partida :+ Seq(
          (r._1, paragraph(table = descripcionRenglon(r._2.head))),
          (r._1, paragraph(text = Seq(texto(text = "\n\n") ) ))
      )

      evalCompleted.flatten

      }.flatten.toList.sortBy( _._1 ).map( _._2 )
    //fmtRenglones.toList.sortBy( _._1 ).map( _._2 ).toJSArray
    fmtRenglones.toJSArray
  }


  def descripcionRenglon(item: RenglonDictamen) = {
    val columnas: Seq[Seq[String|Double|Int|imagen|celda|tabla|columnas]] = Seq(
        Titulo(Seq("Clave", "Descripción", "Unidad", "Cantidad", "Precio", "Importe")),
        Fila(Seq(item.cve_articulo, item.descripcion, item.unidad, item.cantidad, item.precio, item.importe))
    )
    val widths = Seq(50, 180, 60, 50, 50, 70)
    bodyTable(body = columnas, widths = widths)
  }

  def adjudicados = {
    val dictamen = getDictamen

    //type rows = Seq[String|Double|Int|imagen|celda|tabla|columnas]

    val ganadores = Titulo(Seq("Nombre de la empresa", "Total de \npartidas", "Importe \n Adjudicado")) +: dictamen
        .filter( f => f.precio == f.minimo).groupBy(_.razon_social).map { case (i, g) =>
            val partidas = g.map(_.renglon).foldLeft("")(_+","+_).reverse.replaceFirst(",", " y ").reverse.replaceFirst(",", "")
            val monto = g.map(_.importe).foldLeft(0.0)(_+_)
            Fila(Seq( i, partidas, monto))
    }.toSeq

    val widths = Seq(180, 70, 70, 130)
    bodyTable(body = ganadores, widths = widths)
  }

  def totalAdjudicado = {
      val dictamen = getDictamen
      val totalAdjudicado = dictamen.map(_.importe).foldLeft(0.0)(_+_)

      val decima = totalAdjudicado % 1
      val parteInt: Int = (totalAdjudicado - decima).toInt

      val numToLet = new numbertoletter
      val letras = numToLet.numberToLetter(parteInt)

      paragraph(text = Seq(
        texto(text = "TOTAL ADJUDICADO POR UN MONTO DE $ ", bold = true),
        texto(text = fmtMiles(totalAdjudicado, 2), bold = true),
        texto(text = " ( ", bold = true),
        texto(text = letras, bold = true),
        texto(text = " PESOS ", bold = true),
        texto(text = fmtMiles(decima*100,2), bold = true),
        texto(text = "/100 MONEDA NACIONAL)", bold = true)
      ))
  }

  def lstParticipantes(notaRecepcion: Boolean = true) = {
    val lst = getLicitantes

    val nota = if(notaRecepcion) "SE RECIBE SUS PROPOSICIONES TÉNICAS Y ECONÓMICAS PARA EL ANÁLISIS CUALITATIVO DE LA MISMA.\n\n" else ""

    val lstProv = lst.zipWithIndex.map { case (p, i) =>
      val prov = p.razon_social.getOrElse("")
      val indice = (i+1).toString
      paragraph(text = Seq(
        texto(text = indice + ".-" + prov, bold = true),
        texto(text = nota)
      ))
    }.toJSArray
    lstProv
  }


  override def imprimir(lst: Seq[Licitante]) = {
    try {
      val pdf = pdfMake.createPdf(
        docDefinition(
          header = (currentPage: Int, pageCount: Int) => {
            tabla(table = bodyTable(body = membrete(currentPage, pageCount), widths = Seq(10, 50, 400, 50) ), layout = "noBorders" )
        },
        footer = (currentPage: Int, pageCount: Int) => footer(currentPage, pageCount),
        pageMargins = Seq(50, 80/*incluir lo del header*/, 50, 50),//left,top,right,bottom
        pageOrientation = "portrait",
        content = bodyDocument(lst)
      ))
      pdf.open
    } catch {
      case e: Exception =>
        Callback(println("jcza, my print; exception caught: " + e))
    }
  }
}
