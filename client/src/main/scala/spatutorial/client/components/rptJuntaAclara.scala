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

//import scala.scalajs.js.JSConverters._
//import scala.scalajs.js.annotation.JSName
//import scala.scalajs.js

//https://github.com/jducoeur/bootstrap-datepicker-scalajs
//import org.querki.facades.bootstrap.datepicker._ // this adds '.datepicker' implicit

//import java.time.LocalDate
//import java.time.format.DateTimeFormatter

class rptJuntaAclara extends reports {

  def bodyDocument(lst: Seq[Licitante]) = Seq(
    paragraph(text = Seq(texto(text = "\n\n"))),
    paragraph(text = Seq(
      texto(text = "\n\n", bold = true, alignment = "center", fontSize = 15)
    )),
    paragraph(text = Seq(texto(text = "ACTA DE JUNTA DE ACLARACIONES. "))),
    paragraph(text = Seq(texto(text = "INVITACIÓN A CUANDO MENOS TRES PERSONAS "))),
    paragraph(text = Seq(
      texto(text = "En la ciudad de Chilpancingo de los Bravo, capital del Estado de Guerrero, siendo las 09:30 horas del día 18 de "),
      texto(text = "mayo del 2017, previa invitación mediante diversos oficios de fecha 08 de mayo  del año en curso, se reunieron en la "),
      texto(text = "sala de juntas de la Secretaría de Salud y/o Servicios Estatales de Salud, ubicada en Avenida Ruffo Figueroa Número. "),
      texto(text = "6, Colonia Burócratas, Código Postal 39090, Chilpancingo de los Bravo, Guerrero, representantes de la convocante y "),
      texto(text = "representantes del área requirente, representados por los funcionarios cuyos nombres y firmas se anotan al final "),
      texto(text = "de esta acta, con el objeto de dar respuesta a las preguntas recibidas en relación al proceso de INVITACIÓN A CUANDO "),
      texto(text = "MENOS TRES PERSONAS PARA LA ADQUISICIÓN DE REFACCIONES Y ACCESORIOS MENORES DE MAQUINARIA Y OTROS EQUIPOS "),
      texto(text = "DESTINADOS A SERVICIOS PÚBLICOS Y LA OPERACIÓN DE PROGRAMAS PÚBLICOS  PARA  LA DIRECCIÓN DE EPIDEMIOLOGIA "),
      texto(text = "Y MEDICINA PREVENTIVA (DENGUE), CON RECURSOS PROVENIENTES DEL PROGRAMA DENGUE RAMO 12 AFASPE  CON CARGO  A "),
      texto(text = "LAS PARTIDAS 29801. EJERCICIO PRESUPUESTAL 2017. Esto de conformidad con los artículos 134 de la Constitución "),
      texto(text = "Política de los Estados Unidos Mexicanos y 45 fracción IV de la Ley de Adquisiciones, Enajenaciones, Arrendamientos, "),
      texto(text = "Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero, así como en el "),
      texto(text = "apartado B, numeral 4 de las bases del presente proceso administrativo. ")
    )),
    paragraph(text = Seq(texto(text = "Acto seguido, se hizo relación del siguiente: "))),
    paragraph(text = Seq(texto(text = "Orden del día. "))),
    paragraph(text = Seq(texto(text = "I.- Lista de asistencia "))),
    paragraph(text = Seq(texto(text = "II.- Consideración y en su caso, aprobación del orden del día "))),
    paragraph(text = Seq(texto(text = "III.- Presentación de las preguntas recibidas oportunamente "))),
    paragraph(text = Seq(texto(text = "IV.- Formulación de respuestas a las preguntas recibidas oportunamente "))),
    paragraph(text = Seq(texto(text = "V.- Firma del acta "))),
    paragraph(text = Seq(texto(text = "I.- Lista de asistencia "))),
    paragraph(text = Seq(
      texto(text = "A continuación, el Lic. Juan Manuel Santamaría Ramírez, Subsecretario de Administración y Finanzas, pasa lista de "),
      texto(text = "asistencia, encontrándose el pleno los representantes de la Secretaría de Salud y/o Servicios Estatales de Salud "),
      texto(text = "con lo que se tiene quórum legal. ")
    )),
    paragraph(text = Seq(
      texto(text = "II.- Consideración y en su caso, aprobación del orden del día ")
    )),
    paragraph(text = Seq(
      texto(text = "Los asistentes en esta reunión tuvieron a la vista el orden del día, aprobándolo por unanimidad, por lo que se "),
      texto(text = "procedió al desahogo de sus puntos III, IV, y V. ")
    )),
    paragraph(text = Seq(
      texto(text = "III.- Presentación de las preguntas recibidas oportunamente ")
    )),
    paragraph(text = Seq(
      texto(text = "En uso de la palabra, el Lic. Juan Manuel Santamaría Ramírez, Subsecretario de Administración y Finanzas, manifestó "),
      texto(text = "que de acuerdo a la mecánica establecida en el apartado B, numeral 4, de las bases del presente procedimiento "),
      texto(text = "administrativo, cualquier participante que haya sido invitado podrá asistir a la junta de aclaraciones, debiendo "),
      texto(text = "presentar preguntas dentro del tiempo previsto en este proceso administrativo. ")
    )),
    paragraph(text = Seq(
      texto(text = "Por otra parte, en el caso de los que no concurrieron a la reunión señalada, podrán acudir al domicilio del "),
      texto(text = "Departamento de Adquisiciones de la Secretaria de Salud y/o Servicios Estatales de Salud, para que les sea "),
      texto(text = "otorgada una copia del acta de esta junta de aclaraciones. ")
    )),
    paragraph(text = Seq(
      texto(text = "IV.- Formulación de respuestas a las preguntas recibidas oportunamente ")
    )),
    paragraph(text = Seq(texto(text = "No hubo preguntas ")   )),
    paragraph(text = Seq(texto(text = "V.- Firma del acta "))),
    paragraph(text = Seq(
      texto(text = "No habiendo otro asunto que tratar, ni observación que asentar, leída que fue la presente acta, se "),
      texto(text = "firma de conformidad por lo que en ella intervinieron siendo las 10:50 horas del día de su inicio, "),
      texto(text = "se hace constar que se entrega copia de esta acta a todos los participantes de este acto. ")
    )),
    paragraph(text = Seq(
      texto(text = " "),
      texto(text = " ")
    ))
  )

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
