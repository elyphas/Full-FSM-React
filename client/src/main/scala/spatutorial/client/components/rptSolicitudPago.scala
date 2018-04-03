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
//import spatutorial.shared._

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

//import scala.scalajs.js.JSConverters._
//import scala.scalajs.js.annotation.JSName
//import scala.scalajs.js

//https://github.com/jducoeur/bootstrap-datepicker-scalajs
//import org.querki.facades.bootstrap.datepicker._ // this adds '.datepicker' implicit

//import java.time.LocalDate
//import java.time.format.DateTimeFormatter

class rptSolicitudPago extends reports2 {

  def bodyDocument = Seq(
    paragraph(text = Seq(texto(text = "\n\n\nOFICIO NÚMERO: SS/SAF/SRM/" + printSolicitPago.folio + "/2017\n\n\n", alignment = "right"))),
    paragraph(text = Seq(
      texto(text = "\n\n\nASUNTO: SE ENVÍA FACTURACIÓN PARA TRÁMITE DE PAGO.\n\n\n", bold = true, alignment = "right")
    )),
    paragraph(text = Seq(
      texto(text = "\n\nChilpancingo, Gro. a " + fechaLarga(printSolicitPago.fecha.fecha) + "\n\n", alignment = "right")
    )),
    paragraph(text = Seq(
      texto(text = "\n\nAnexo al presente, me permito remitir la siguiente documentación original:\n\n")
    )),
    paragraph(table = anexo),
    paragraph(text = Seq(
      texto(text = "\n\nLo anterior con la finalidad de que bajo previa validación de su área y en caso que cumpla con la normatividad "),
      texto("y el marco legal aplicable, procesa al pago.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "\n\nAprovecho la ocasión para enviarle un cordial saludo.\n\n")
    )),
    firmas,
    paragraph(text = Seq(
      texto(text = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nc.c.p. Lic. Juan Manuel Santamaría Ramírez.- Subsecretario de Administración y Finanzas.- Para su conocimiento.\n\n", fontSize = 6)
    ))
  )

  def anexo = {

    //val numToLetter = new numbertoletter

    val fact = lstFacts.head
    val folioControl = fact.folio_control.getOrElse("")

    //val bdImporte: BigDecimal = fact.importe
    val decima: Int = ((fact.importe % 1) * 100).toInt
    val parteInt: Int = (fact.importe - (decima*.01)).toInt

    val numToLet = new numbertoletter
    val letras = numToLet.numberToLetter(parteInt) + " PESOS " + decima.toString + "/100 M.N."


    val filas: Seq[Seq[String|Double|Int|imagen|celda|tabla|columnas]] =
      Seq(
        Seq(
          celda(text = Seq(texto(text = "No. DE CONTRATO:", bold = true))),
          celda(text = Seq(texto(text = printSolicitPago.no_pedido, bold = true)), colSpan = 3),
          celda(text = Seq(texto(text = ""))),
          celda(text = Seq(texto(text = "")))
        ),
        Seq(
          celda(text = Seq(texto(text = "EMPRESA:", bold = true)), fillColor =  "#fdcced"),
          celda(text = Seq(texto(text = printSolicitPago.cve_provedor + ": "  + printSolicitPago.razon_social, bold = true)), colSpan = 3, fillColor =  "#fdcced"),
          celda(text = Seq(texto(text = "")), fillColor =  "#fdcced"), celda(text = Seq(texto(text = "")), fillColor =  "#fdcced")
        ),
        Seq(
          celda(text = Seq(texto(text = "PROGRAMA:", alignment = "center", bold = true))),
          celda(text = Seq(texto(text = printSolicitPago.programa + ": " + printSolicitPago.programa, bold = true)), colSpan = 3),
          celda(text = Seq(texto(text = ""))), celda(text = Seq(texto(text = "")))
        ),
        Seq(
          celda(text = Seq(texto(text = "FACTURA:", alignment = "center", bold = true)), fillColor =  "#fdcced"),
          celda(text = Seq(texto(text = fact.factura, alignment = "center", bold = true)), fillColor =  "#fdcced"),
          celda(text = Seq(texto(text = "FOLIO CONTROL", alignment = "center", bold = true)), fillColor =  "#fdcced"),
          celda(text = Seq(texto(text = folioControl, alignment = "center", bold = true)), fillColor =  "#fdcced")
        ),
        Seq(
          celda(text = Seq(texto(text = "No. DE ALTA:", bold = true))),
          celda(text = Seq(texto(text = "466", bold = true)), colSpan = 3),
          celda(text = Seq(texto(text = "", bold = true))),
          celda(text = Seq(texto(text = "", bold = true)))
        ),
        Seq(
          celda(text = Seq(texto(text = "MONTO TOTAL:", bold = true)), fillColor =  "#fdcced"),
          celda(text = Seq(texto(text = "$ " + fmtMiles(fact.importe))), colSpan = 3, fillColor =  "#fdcced"),
          celda(text = Seq(texto(text = "")), fillColor =  "#fdcced"),
          celda(text = Seq(texto(text = "")), fillColor =  "#fdcced")
        ),
        Seq(
          celda(text = Seq(texto(text = "IMPORTE C/LETRA:", bold = true))),
          celda(text = Seq(texto(text = letras, bold = true)), colSpan = 3),
          celda(text = Seq(texto(text = ""))),
          celda(text = Seq(texto(text = "")))
        )
      ): Seq[Seq[String|Double|Int|imagen|celda|tabla|columnas]]
    val widths = Seq(120, 120, 120, 120)
    bodyTable(body = filas, widths = widths)
  }

  def firmas = {
    val filas: Seq[Seq[String|Double|Int|imagen|celda|tabla|columnas]] =
      Seq(
        Seq(
          celda(text = Seq(texto(text = "Vo. Bo.", alignment = "center", bold = true))),
          celda(text = Seq(texto(text = "", alignment = "center", bold = true)))
        ),
        Seq(
          celda(text = Seq(texto(text = "SUBDIRECCIÓN DE RECURSOS MATERIALES", alignment = "center", bold = true))),
          celda(text = Seq(texto(text = "DEPARTAMENTO DE ADQUISICIONES", alignment = "center", bold = true)))
        ),
        Seq(
          celda(text = Seq(texto(text = "_______________________________________", bold = true, alignment = "center"))),
          celda(text = Seq(texto(text = "_______________________________________", bold = true, alignment = "center")))
        ),
        Seq(
          celda(text = Seq(texto(text = "C.P. CHRISTIAN GARIBAY GALEANA", alignment = "center", bold = true))),
          celda(text = Seq(texto(text = "C.P. BERNARDO MONDRAGON LUVIANO", alignment = "center", bold = true)))
        )

      ): Seq[Seq[String|Double|Int|imagen|celda|tabla|columnas]]
    val widths = Seq(240, 240)
    //bodyTable(body = filas, widths = widths)

    tabla(table = bodyTable(body = filas, widths = widths), layout = "noBorders")

  }


  override def imprimir() = {
    try {

      val pdf = pdfMake.createPdf(
        docDefinition(
          header = (currentPage: Int, pageCount: Int) => {
            tabla(table = bodyTable(body = membrete(currentPage, pageCount), widths = Seq(10, 50, 400, 50)), layout = "noBorders")
        },
        footer = (currentPage: Int, pageCount: Int) => footer(currentPage, pageCount),
        pageMargins = marginsPage,
        pageOrientation = "portrait",
        content = bodyDocument
      ))
      pdf.open
    } catch {
      case e: Exception =>
        Callback(println("jcza, my print; exception caught: " + e))
    }
  }



}
