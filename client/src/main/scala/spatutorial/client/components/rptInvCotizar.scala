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
//import scala.scalajs.js.{| , Date => jsDate}

//import java.time._
//import java.util._
//import java.text.SimpleDateFormat

//import spatutorial.client.actionhandlers.{GetQryPedidoDatGrales, GetPartidaAnexo}

//import scala.scalajs.js.JSConverters._
//import scala.scalajs.js.annotation.JSName
//import scala.scalajs.js

//https://github.com/jducoeur/bootstrap-datepicker-scalajs
//import org.querki.facades.bootstrap.datepicker._ // this adds '.datepicker' implicit

//import java.time.LocalDate
//import java.time.format.DateTimeFormatter

class rptInvCotizar  extends reports {



  def bodyDocument(lst: Seq[Licitante]) = {

    /*val partidaAnexo = getPartidas
    val descripcionCompra = getDescripcionCompra
    val proceso = getProceso
    val programa = getPrograma*/

    val fecha = proceso.fecha

    val count = lst.length

    val oficios = lst.view.zipWithIndex.flatMap { case (currentProv, index) =>

      val breakPage = if(index != (count-1)) "after" else ""

      var folio = currentProv.folio.getOrElse("")
      val razon_social = currentProv.razon_social.getOrElse("")
      val propietario = currentProv.propietario.getOrElse("")
      val ciudad = currentProv.ciudad.getOrElse("")
      val col = currentProv.colonia.getOrElse("")
      val calle = currentProv.calle.getOrElse("")
      val cp = currentProv.cp.getOrElse("")

      Seq(
        paragraph(text = Seq(texto(text = " ", bold = true))),
        paragraph(text = Seq(texto(text = " ", bold = true))),
        paragraph(text = Seq(
          texto(text = "Oficio Número: SS/SAF/SR/DA/", alignment = "right", bold = true, fontSize = 10),
          texto(text = folio, bold = true),
          texto(text = "/2017", bold = true)
        )),
        paragraph(text = Seq(
          texto(text = "Asunto: ", alignment = "right", bold = true, fontSize = 10),
          texto(text = proceso.tipoCompra, alignment = "right", bold = true, fontSize = 10)
        )),
        paragraph(text = Seq(
          texto(text = "Chilpancingo, Gro. a ", bold = true, alignment = "right"),
          texto(text = fecha.fecha.substring(0,2), bold = true),
          texto(text = " de ", bold = true),
          texto(text = NumToMonth(fecha.fecha.substring(3,5).toInt), bold = true),
          texto(text = " del ", bold = true),
          texto(text = fecha.fecha.substring(6,11), bold = true)
        )),
        paragraph(text = Seq(texto(text = " ", bold = true))),
        paragraph(text = Seq(texto(text = " ", bold = true))),
        paragraph(text = Seq(texto(text = razon_social, bold = true))),
        paragraph(text = Seq(texto(text = "PROPIETARIO DE LA EMPRESA", bold = true))),
        paragraph(text = Seq(texto(text = propietario, bold = true))),
        paragraph(text = Seq(texto(text = ciudad, bold = true))),
        paragraph(text = Seq(texto(text = col, bold = true))),
        paragraph(text = Seq(texto(text = calle, bold = true))),
        paragraph(text = Seq(texto(text = cp, bold = true))),
        paragraph(text = Seq(texto(text = "P R E S E N T E", bold = true))),
        paragraph(text = Seq(texto(text = " ", bold = true))),
        paragraph(text = Seq(texto(text = " ", bold = true))),
        paragraph(text = Seq(

          texto(text = "Con fundamento en lo dispuesto por los artículos 134 de la Constitución Política de los Estados Unidos Mexicanos, "),
          texto(text = "22 fracciones XXXIII y XXXVI de la Ley Orgánica de la Administración Pública del Estado de Guerrero Número 433, "),
          texto(text = "1, 7, 32 fracción II, 33 párrafo segundo y 55 fracción II de la Ley Número 230 de Adquisiciones, Enajenaciones, "),
          texto(text = "Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero, "),
          texto(text = "y el Manual de Políticas, Bases y Lineamientos en Materia de Adquisiciones, Arrendamientos de Bienes Muebles y "),
          texto(text = "Contratación de Servicios. Me permito invitar cordialmente a usted, a participar mediante la presentación de "),
          texto(text = "sus propuestas técnicas y económicas para el procedimiento de "),
          texto(text = proceso.tipoCompra, bold = true ),
          texto(text = " para la adquisición "),
          texto(text = "de: "),
          texto(text = descripcionCompra.descripcion, bold = true),
          texto(text = " , para: "),
          texto(text = proceso.destino, bold = true),
          texto(text = " con recursos provenientes del programa: "),
          texto(text = programa.programa + "; " + programa.descripcion, bold = true),
          texto(text = ", con cargo a las partidas: "),
          texto(text = partidaAnexo.partida + "; " + partidaAnexo.descripcion.toUpperCase, bold = true),
          texto(text = " ejercicio presupuestal "),
          texto(text = proceso.ejercicioPresupuestal.toString),
          texto(text = ", con domicilio en Avenidad Ruffo Figueroa # 6, Colonia Burócratas, Código Postal 39090, "),
          texto(text = "Chilpancingo, Gro."))),
        paragraph(text = Seq(texto(text = " "))),
        paragraph(text = Seq(texto(text = " "))),
        paragraph(text = Seq(texto(text = "Se anexan bases que normarán el desarrollo del presente procedimiento incluyendo "))),
        paragraph(text = Seq(texto(text = "ficha técnica de los bienes solicitados."))),
        paragraph(text = Seq(texto(text = " "))),
        paragraph(text = Seq(texto(text = "Sin más por el momento, le envío un cordial saludo."))),
        paragraph(text = Seq(texto(text = " "))),
        paragraph(text = Seq(texto(text = " "))),
        paragraph(text = Seq(texto(text = "A T E N T A M E N T E", bold = true))),
        paragraph(text = Seq(texto(text = " "))),
        paragraph(text = Seq(texto(text = " "))),
        paragraph(text = Seq(texto(text = "C.P. BERNARDO MONDRAGÓN LUVIANO", bold = true))),
        paragraph(text = Seq(texto(text = "JEFE DEL DEPARTAMENTO DE ADQUISICIONES", bold = true)), pageBreak = breakPage)
      )//Este parentesis termina la secuencia principal
    }

    oficios
  }

  override def imprimir(lst: Seq[Licitante]) = {

      try {
          val pdf = pdfMake.createPdf(
            docDefinition(
              header = (currentPage: Int, pageCount: Int) => {
                tabla(table = bodyTable(body = membrete(currentPage, pageCount), widths = Seq(10, 50, 400, 50) ), layout = "noBorders" )
            },
            footer = (currentPage: Int, pageCount: Int) => {
              tabla(table = bodyTable(body =
                Seq(
                  Seq(
                    imagen(image = logoGuerrero, width = 70),
                    celda(text = Seq(texto(text = "Pagina " + currentPage.toString + " de " + pageCount.toString)))
                  )
                ),
                widths = Seq(500, 80)),layout = "noBorders")
            },
            pageMargins = Seq(50, 80/*incluir lo del header*/, 50, 50),//left,top,right,bottom
            pageOrientation = "portrait",
            content = bodyDocument(lst)
          ))

          pdf.open
          /*if(preview) pdf.open
          else pdf.print*/

        } catch {
          case e: Exception =>
            Callback(println("jcza, my print; exception caught: " + e))
        }
    }
}
