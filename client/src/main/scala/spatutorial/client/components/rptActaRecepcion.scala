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
//import scala.scalajs.js.{|, Date => jsDate}

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

class rptActaRecepcion extends reports {

  def bodyDocument(lst: Seq[Licitante]) = Seq(
    paragraph(text = Seq(texto(text = "\n\n"))),
    paragraph(text = Seq(
      texto(text = "Acta de Recepción y Apertura de Proposiciones Técnicas y Económicas.\n\n", bold = true, alignment = "center", fontSize = 15)
    )),
    paragraph(text = Seq(
      texto(text = "En la Ciudad de Chilpancingo de los Bravo, Estado de Guerrero, Capital del Estado de Guerrero, siendo las 10:00 horas del "),
      texto(text = "día 26 de Mayo de 2017, con fundamento en lo dispuesto por los artículos 134 de la Constitución Política de los Estados "),
      texto(text = "Unidos Mexicanos, 22 fracciones XXXIII y XXXVI de la Ley Orgánica de la Administración Pública del Estado de Guerrero "),
      texto(text = "número 433, 1, 7, 32 fracción II, 33 párrafo segundo y 55 fracción II de la Ley Número 230 de Adquisiciones, Enajenaciones, "),
      texto(text = "Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero, así como "),
      texto(text = "de las bases que norman el presente procedimiento de Invitación a Cuando Menos Tres Personas, se dio inicio al acto de "),
      texto(text = "recepción de documentación administrativa y legal, proposiciones técnicas y económicas y apertura de proposiciones "),
      texto(text = "técnicas y económicas para la INVITACIÓN A CUANDO MENOS TRES PERSONAS PARA LA ADQUISICIÓN DE REFACCIONES Y ACCESORIOS "),
      texto(text = "MENORES DE MAQUINARIA Y OTROS EQUIPOS  DESTINADOS A SERVICIOS PÚBLICOS Y LA OPERACIÓN DE PROGRAMAS PÚBLICOS  PARA "),
      texto(text = "LA DIRECCIÓN DE EPIDEMIOLOGIA Y MEDICINA PREVENTIVA (DENGUE), CON RECURSOS PROVENIENTES DEL PROGRAMA DENGUE "),
      texto(text = "RAMO 12 AFASPE  CON CARGO  A LAS PARTIDAS 29801. EJERCICIO PRESUPUESTAL 2017, de acuerdo con las especificaciones "),
      texto(text = "señaladas en el anexo uno de las bases de concurso, levantándose para el efecto la presente acta.\n\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "De conformidad con el artículo 45 fracción IV de la Ley Número 230 de Adquisiciones, Enajenaciones, Arrendamientos, "),
      texto(text = "Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero, el Lic. Juan Manuel "),
      texto(text = "Santamaría Ramírez, Subsecretario de Administración y Finanzas de la Secretaria de Salud en Guerrero, quien preside "),
      texto(text = "el acto de recepción de documentación administrativa y legal, proposiciones técnicas económicas y apertura de "),
      texto(text = "proposiciones técnicas y económicas de los licitantes participantes, en uso de la palabra manifestó que en esta "),
      texto(text = "Invitación a Cuando Menos Tres Personas, será en dos actos públicos de acuerdo a lo siguiente, el primer acto se "),
      texto(text = "recibirán de cada uno de los participantes el sobre cerrado que contiene su proposiciones técnica y económica y su "),
      texto(text = "documentación legal y administrativa, se recibirá cuantitativamente la documentación legal y administrativa y "),
      texto(text = "proposiciones técnicas, desechando las propuestas técnicas de aquellos participantes que no hayan "),
      texto(text = "cumplido con los requisitos exigidos en las bases, y se dará lectura al importe total de cada una de las "),
      texto(text = "proposiciones económicas. En el segundo y último acto público, se dará a conocer el fallo de las mismas.\n\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Primer acto:\n\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Se procedió a recibir de los licitantes el sobre que contiene su propuesta técnica-económica y su documentación "),
      texto(text = "legal y administrativa, acto seguido los representantes de la Secretaria de Salud, abrieron los sobres que contienen "),
      texto(text = "las propuestas técnicas-económicas y la documentación legal, administrativa de los licitantes y se revisó la "),
      texto(text = "documentación legal administrativamente y las propuestas técnicas cuantitativamente.\n\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Una vez cotejado los documentos, se devuelven los originales de la documentación legal y administrativa a "),
      texto(text = "cada uno de los licitantes. ")
    )),
    paragraph(text = Seq(
      texto(text = "Después de haber hecho la revisión de la documentación legal y administrativa y análisis generales de las propuestas "),
      texto(text = "técnicas de conformidad con lo que establece el artículo 45 fracción IV de la Ley Número 230 de Adquisiciones, "),
      texto(text = "Enajenaciones, Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de "),
      texto(text = "Guerrero, el pleno de La Secretaria de Salud y/o Servicios Estatales de Salud, determina que: ")
    )),
    lstParticipantes(true),
    paragraph(text = Seq(
      texto(text = "Los participantes en este proceso, así como los representantes de La Secretaria de Salud y/o Servicios Estatales "),
      texto(text = "de Salud, firmaron la parte de las propuestas técnicas, que contienen la descripción de los bienes a adquirir. ")
    )),
    paragraph(text = Seq(
      texto(text = "Importe de las propuestas económicas ")
    )),
    paragraph(text = Seq(
      texto(text = "Acto seguido el Lic. Juan Manuel Santamaría Ramírez, Subsecretario de Administración, dio lectura al importe "),
      texto(text = "sin incluir el impuesto al valor agregado (IVA) de cada una de las propuestas técnicas económicas presentadas. ")
    )),
    totalCotizaciones.zipWithIndex.flatMap { case (cotiz, i) =>
      Seq(
        paragraph(text = Seq(texto(text = (i+1).toString + ".- " + cotiz.razon_social, bold = true))),
        tableTotalCotizacion(cotiz.monto),
        paragraph(text = Seq(texto(text = "\n\n")))
      )
    }.toJSArray,
    paragraph(text = Seq(
      texto(text = "En uso de la palabra el Lic. Juan Manuel Santamaría Ramírez, Subsecretario de Administración y Finanzas, "),
      texto(text = "pregunto a los participantes y a los integrantes de esta Secretaria de Salud y/o Servicios Estatales de "),
      texto(text = "Salud, si tenían alguna observación o sugerencia que agregar a lo que respondieron no hay comentario alguno. ")
    )),
    paragraph(text = Seq(
      texto(text = "De esta manera, el fallo de la presente Invitación A Cuando Menos Tres Personas, será dado a conocer en "),
      texto(text = "esta misma sala, el día 02  de Junio de 2017, a las 09:00 horas, por lo que en este evento quedan citados "),
      texto(text = "a los integrantes de la convocante, así como de las empresas participantes. ")
    )),
    paragraph(text = Seq(
      texto(text = "Es conveniente precisar que en caso que los participantes no asistan a la lectura del fallo en la fecha "),
      texto(text = "y hora señaladas en bases de la invitación, dicha acta estará disponible en los estrados del Departamento "),
      texto(text = "de Adquisiciones ")
    )),
    paragraph(text = Seq(
      texto(text = "No habiendo otro asunto que tratar, ni observación que asentar, leída que fue la presente acta, se "),
      texto(text = "firman de conformidad por los que en ella intervinieron al calce y al margen siendo las 11:25 horas "),
      texto(text = "del día de su inicio. ")
    )),
    paragraph(text = Seq(
      texto(text = " "),
      texto(text = " "),
      texto(text = " "),
      texto(text = " "),
      texto(text = " "),
      texto(text = " "),
      texto(text = " "),
      texto(text = " ")
    ))
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
            tabla(table = bodyTable(body = membrete(currentPage, pageCount), widths = Seq(10,50,400,50)), layout = "noBorders")
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
