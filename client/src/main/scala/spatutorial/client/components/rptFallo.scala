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
//import scala.scalajs.js.{| , Date => jsDate }

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

class rptFallo extends reports {

  def bodyDocument(lst: Seq[Licitante]) = Seq (
    paragraph(text = Seq(texto(text = "\n\n"))),
    paragraph(text = Seq(texto(text = "Acta de Fallo\n", bold = true, alignment = "center", fontSize = 15))),
    paragraph(text = Seq(texto(text = "Invitación a Cuando Menos Tres Personas\n\n", bold = true, alignment = "center", fontSize = 15))),
    paragraph(text = Seq(
      texto(text = "En la ciudad de Chilpancingo de los Bravo, Estado de Guerrero, siendo las: "),
      texto(text = calendario.hora_fallo.hora, bold = true),
      texto(text = " del día "),
      texto(text = fechaLarga(calendario.fecha_fallo.fecha), bold = true),
      texto(text = ", se reunieron en la Sala de Juntas \"Dr. Cesar A. Piña Cámara\" de la Secretaría de Salud y/o Servicios Estatales de Salud, "),
      texto(text = "ubicado en Avenidad Ruffo Figueroa número. 6, Código Postal 39090, Colonia Burocratas, en esta Ciudad Capital, los "),
      texto(text = "funcionarios cuyos nombres y firmas se anotan al final de la presente acta, así como los representantes de las empresas "),
      texto(text = "participantes, para dar inicio al acto de fallo del procedimiento:"),
      texto(text = proceso.tipoCompra, bold = true),
      texto(text = descripcionCompra.concepto + "; " + descripcionCompra.descripcion, bold = true),
      texto(text = proceso.destino, bold = true),
      texto(text = programa.programa + "; " + programa.descripcion),
      texto(text = ", con cargo a las partidas "),
      texto(text = partidaAnexo.partida + "; " + partidaAnexo.descripcion),
      texto(text = ", ejercicio prespuestal: "),
      texto(text = proceso.ejercicioPresupuestal.toString, bold = true),
      texto(text = " de acuerdo con las especificaciones señaladas en el anexo técnico, de las bases concúrsales levantándose para tal efecto "),
      texto(text = "la presente acta.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "De conformidad con los artículos 134 de la Constitución Política de los Estados Unidos Mexicanos, así como "),
      texto(text = "1,7, 32 fracción II, 33 párrafo segundo y 55 fracción II de la ley Número 230 de Adquisiciones,  Enajenación, "),
      texto(text = "Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero, "),
      texto(text = "y las bases concursales, se desarrolló el presente procedimiento de Invitación a Cuando Menos Tres Personas, bajo el "),
      texto(text = "siguiente orden:\n\n "))),
    paragraph(text = Seq(
      texto(text = "Acto de presentación de los sobres que contienen las proposiciones técnicas-económicas, de la documentación legal y "),
      texto(text = "administrativa del procedimiento: "),
      texto(text = proceso.tipoCompra, bold = true),
      texto(text = ", para la adquisición de: "),
      texto(text = descripcionCompra.concepto + "; " + descripcionCompra.descripcion, bold = true),
      texto(text = ", destinados a: "),
      texto(text = proceso.destino, bold = true),
      texto(text = ", con recursos provenientes del programa: "),
      texto(text = programa.programa + "; " + programa.destino, bold = true),
      texto(text = ", con cargo a las partidas: "),
      texto(text = partidaAnexo.partida + "; " + partidaAnexo.descripcion, bold = true),
      texto(text = ", ejercicio presupuestal: "),
      texto(text = proceso.ejercicioPresupuestal.toString, bold = true),
      texto(text = "\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "El uso de la palabra el "),
      texto(text = "Lic. juan Manuel Santamaría Ramírez", bold = true),
      texto(text = ", Subsecretario de Administración y Finanzas de la Secretaría de Salud y/o Servicios Estatales de Salud, manifestó que "),
      texto(text = "el presente procedimiento de Invitación a Cuando Menos Tres Personas, se llevó a cabo en dos actos públicos de acuerdo a "),
      texto(text = "lo siguiente: En el primer acto se recibieron de cada uno de los participantes el sobre cerrado que contiene sus proposiciones "),
      texto(text = "técnica-económica, así como su documentación legal y administrativa, se revisó cuantitativamente la documentación legal, "),
      texto(text = "administrativa y proposiciones técnica-económica y se dio lectura al importe total de cada una de las proposiciones económicas, "),
      texto(text = "en el segundo y último acto público del presente procedimiento administrativo, se dará a conocer el fallo de la misma.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Se procedio a recibir de los concursantes, el sobre que contiene su propuesta técnica-económica y su documentación legal y "),
      texto(text = "administrativa, acto seguido, los representantes de la Secretaría de Salud y/o Servicios Estatales de Salud, abrieron los "),
      texto(text = "sobres que contienen las proposiciones técnica y económica y la documentación legal, administrativa de los concursantes y se "),
      texto(text = "efectúo la revisión cuantitativa de la misma.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Una vez cotejado los documentos, se devuelven los originales de la documentación legal y administrativa a cada unos de los "),
      texto(text = "licitantes.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Después de haber hecho la revisión de la documentación legal y administrativa y análisis generales de las proposiciones "),
      texto(text = "técnicas de conformidad con lo que establece el artículo 51 de la Ley Número 230 de Adquisiciones, Enajenaciones, "),
      texto(text = "Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero, el pleno de la "),
      texto(text = "Secretaría de Salud y/o Servicios Estatales de Salud, determina que:\n\n")
    )),
    lstParticipantes(),
    paragraph(text = Seq(texto(text = "Los participantes en este proceso, así como los representantes de la Secretaría de Salud y/o Servicios "))),
    paragraph(text = Seq(texto(text = "Estatales de Salud, firmaron la parte de las propuestas técnicas, que contienen la descripción de los "))),
    paragraph(text = Seq(texto(text = "bienes a adquirir.\n\n"))),
    paragraph(text = Seq(texto(text = "Importe de las propuestas económicas\n\n", bold = true))),
    paragraph(text = Seq(
      texto(text = "Acto seguido el "),
      texto(text = "Lic. Juan Manuel Santamaría Ramírez", bold = true),
      texto(text = ", Subsecretario de Administración y Finanzas, dio lectura al importe, sin incluir el impuesto al valor agregado (IVA) de cada "),
      texto(text = "una de las proposiciones técnicas económicas presentadas: \n\n")
    )),
    paragraph(text = Seq(texto(text = " "))),
    totalCotizaciones.zipWithIndex.flatMap { case (cotiz, i) =>
      Seq(
        paragraph(text = Seq(texto(text = (i+1).toString + ".- " + cotiz.razon_social, bold = true))),
        tableTotalCotizacion(cotiz.monto),
        paragraph(text = Seq(texto(text = "\n\n")))
      )
    }.toJSArray,
    paragraph(text = Seq(
      texto(text = "Con fundamento en los artículos 1, 7, 32 fracción II, 33 párrafo segundo y 55 fracción II de la Ley Número 230 de Adquisiciones "),
      texto(text = ", Enajenaciones, Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de  "),
      texto(text = "Guerrero y una vez que se llevó a cabo el análisis y evaluación de las proposiciones técnicas y que se verificó que los "),
      texto(text = "participantes cumplieran con la documentación legal, administrativa y los requisitos de las bases, la convocante determina. \n\n\n")
    )),
    paragraph(text = Seq(texto(text = "Análisis y Resultado de las propuestas técnicas y económicas.\n\n\n", bold = true))),
    paragraph(text = Seq(
      texto(text = "Derivado de la revisión y evaluación cuantitativa y cualitativa de las propuestas técnicas económicas presentadas por las "),
      texto(text = "empresas participantes en este procedimiento administrativo, así como las pruebas físicas cuya revisión fue realizada por esta "),
      texto(text = "Secretaría de Salud emitió lo siguiente:\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "En relación con la ***tipoCompra***, para la adquisición de: ***descripcionCompra***, "),
      texto(text = "para: ***destino***, con recursos provenientes del programa: "),
      texto(text = "***programa***, con cargo a las partidas: ***partidasAnexo***, ejercicio presupuestal: ***ejercicioPresupuestal***.\n\n")
    )),
    paragraph(text = Seq(texto(text = "Evaluación Técnica", bold = true))),
    paragraph(text = Seq(
      texto(text = "1.- Mediante bases, se invito a participar en la presentación de propuestas técnicas y económicas de la ***tipoCompra***, "),
      texto(text = "para la adquisición de: ***descripciónCompra***, para: ***destino***, con recursos provenientes: ***programa****, "),
      texto(text = "con cargo a las partidas: ***partidasAnexo***, ejercicio presupuestal: ***ejercicioPresupuestal. \n\n")
    )),
    paragraph(text = Seq(
      texto(text = "2.- El día ***fechaRecepciónSobres***, a las ***horas***, de acuerdo con lo establecido en las bases de la ***tipoCompra***, "),
      texto(text = "se realizo en el lugar indicado para este efecto, la recepción de sobres que contienen las proposiciones técnicas y económicas "),
      texto(text = "de las empresas participantes, procediéndose enseguida a la apertura de los mismos en el siguiente orden:\n\n")
    )),
    lstParticipantes(notaRecepcion = false),
    paragraph(text = Seq(
      texto(text = "Y de las cuales se revisó y analizó por el personal responsable, la documentación respectiva, determinando que cumplieron con "),
      texto(text = "la presentación de los requisitos solicitados en las bases de concurso, por lo que fueron aceptadas para realizar con posterioridad "),
      texto(text = "el análisis de las características técnicas y económicas de la contratación de los servicios. \n\n")
    )),
    paragraph(text = Seq(
      texto(text = "3.- Posteriormente se procedio a la revisión de las especificaciones técnicas de los servicios presentados por los licitantes "),
      texto(text = "que cumplieron con los requisitos solicitados, de las cuales se verificó que cumplen con las requeridas por la convocante "),
      texto(text = "en la presente invitación.\n\n")
    )),
    //paragraph(text = Seq(texto(text = "PARTIDAS", bold = true, alignment = "center"))),
    paragraph(text = Seq(
      texto(text = " ")
    )),
    paragraph(text = Seq(
      texto(text = " "),
      texto(text = " "),
      texto(text = " "),
      texto(text = " "),
      texto(text = " "),
      texto(text = " ")
    )),
    paragraph(text = Seq(texto(text = " "))),
    paragraph(text = Seq(texto(text = " ")))
  )

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

  def tableTotalCotizacion(monto: Double) = {
    val columns = Seq(
      Titulo(Seq("descripción", "Total")),
      Fila(Seq(descripcionCompra.descripcion, monto))
    )
    val widths = Seq(300, 100)
    tabla(table = bodyTable(body = columns, widths = widths))
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
