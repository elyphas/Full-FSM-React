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
import spatutorial.client.services._
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
import scala.scalajs.js.{|/*, Date => jsDate*/}

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

class rptBases  extends reports {

  def getAnexoUno() = {
    val listado = SPACircuit.zoom(_.lstbienes).value

    val (lista: Seq[RenglonListBienes]) = listado match {
      case x if x.isUnavailable => Seq.empty
      case x if x.isEmpty => Seq.empty
      case x if x.isFailed => Seq.empty
      case _ => listado.get
    }
    lista
  }

  def tableAnexoUnos() = {
    val lista = getAnexoUno

      val celdas = Seq(
        celda(text = Seq(texto(text = "clave")), bold = true, fontSize = 9, fillColor = "#e7e3e4", alignment = "center"),
        celda(text = Seq(texto(text = "descripcion")), bold = true, fontSize = 9, fillColor = "#f2edee", alignment = "center"),
        celda(text = Seq(texto(text = "cantidad")), bold = true, fontSize = 10, fillColor = "#f2edee", alignment = "center")
      ) +: lista.map{ rcd =>
          Seq(
            celda(text = Seq(texto(text = rcd.cve_articulo)), fontSize = 9 ),
            celda(text = Seq(texto(text = rcd.descripcion)), fontSize = 9, alignment = "justify"),
            celda(text = Seq(texto(text = rcd.cantidad.toString, bold = true, fontSize = 10, alignment = "right")))
          )
      }.toSeq: Seq[Seq[String|Double|Int|imagen|celda|tabla|columnas]]
    val widths = Seq(70, 370, 50)
    tabla(table = bodyTable(body = celdas, widths = widths))
  }

  def AnexoDos() = {
    Seq(
      paragraph(text = Seq(
        texto(text = "FORMATO PARA ASENTAR DATOS LEGALES DEL PROVEEDOR.", alignment = "center", fontSize = 15)
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "(FORMATO)", alignment = "center", bold = true, fontSize = 15)
      )),
      paragraph(text = Seq(
        texto(text = "________________________, en mi carácter de ___________________, manifiesto bajo protesta de decir verdad, que "),
        texto(text = "los datos aquí asentados, son ciertos y han sido debidamente verificados, así como que cuento con facultades "),
        texto(text = "suficientes para suscribir la propuesta en el presente procedimiento, a nombre y representación de: _____________________")
      )),
      paragraph(text = Seq(
        texto(text = "No. de procedimiento: "))),
      paragraph(text = Seq(
        texto(text = "No. de Padrón de Proveedores: (dicho requisito será exigible para proveedor adjudicado) ")
      )),
      tabla(table = bodyTable(body = Seq(Seq(
        celda(text = Seq(
          texto(text = "Registro Federal de Contribuyentes:\n\n", bold = true, fontSize = 10, margin = Seq(50, 50, 50, 50)),
          texto(text = "Domicilio:\n\n", bold = true, fontSize = 10),
          texto(text = "Calle y número:\n\n", bold = true, fontSize = 10),
          texto(text = "Colonia:                    Delegación o Municipio:\n\n", bold = true, fontSize = 10),
          texto(text = "Codigo Postal:              Entidad Federativa:\n\n", bold = true, fontSize = 10),
          texto(text = "Teléfonos:                  Fax:\n\n", bold = true, fontSize = 10),
          texto(text = "Correo electrónico:\n\n", bold = true, fontSize = 10),
          texto(text = "No. de la escritura pública en la que consta su acta constitutiva:      Fecha:\n\n", bold = true, fontSize = 10),
          texto(text = "Nombre, número y lugar del Notario Público ante el cual se otorgó:\n\n", bold = true, fontSize = 10),
          texto(text = ":Relación de accionistas.-\n\n", bold = true, fontSize = 10),
          texto(text = "Apellido Paterno:           Apellido Materno:           Nombre:\n\n", bold = true, fontSize = 10),
          texto(text = "Descripción del objeto social:\n\n", bold = true, fontSize = 10),
          texto(text = "Reformas del acta constitutiva:\n\n", bold = true, fontSize = 10)
        ))
      )), widths = Seq(500))),
      tabla(table = bodyTable(body = Seq(Seq(
        celda(text = Seq(
          texto(text = "Nombre del apoderado:\n\n", bold = true, fontSize = 10, margin = Seq(50, 50, 50, 50)),
          texto(text = "Datos del documento mediante el cual acredita su personalidad y facultades:\n\n", bold = true, fontSize = 10),
          texto(text = "Escritura pública número:\n\n", bold = true, fontSize = 10),
          texto(text = "Nombre, número y lugar del Notario Público ante el cual se otorgó:\n\n", bold = true, fontSize = 10)
        ))
      )), widths = Seq(500))),
      paragraph(text = Seq(
        texto(text = "(Lugar y fecha)\n", alignment = "center"),
        texto(text = "Protesto lo necesario\n", alignment = "center"),
        texto(text = "(Firma)\n", alignment = "center")
      ))
    ).toJSArray
  }

  def AnexoTres() = Seq(
    paragraph(text = Seq(texto(text = "FORMATO DE FIANZA", alignment = "center")), margin = Seq(0,2,0,2)),
    paragraph(text = Seq(texto(text = "Características que deberá contener la Fianza para los Servicios Estatales de Salud, ",
        alignment = "center"))),
    paragraph(text = Seq(texto(text = "para cumplimiento de contrato", alignment = "center"))),
    paragraph(text = Seq(texto(text = "(Proveedor, Contratista, Prestador de Servicios) garantizará que el periodo de dicha garantía por concepto de (los artículos, bienes, productos, trabajos y/o servicios)  se extenderá por un periodo de __ año (s) a partir de la recepción de los bienes y/o productos, trabajos y/o servicios motivo de contrato y/o pedido, la cual deberá ser del 10 % sin I.V.A. incluido del monto total adjudicado, como se indica en el contrato y/o pedidoylacual deberá contener las siguientes características: "))),
    paragraph(text = Seq(texto(text = "LA INSTITUCIÓN AFIANZADORA. (nombre de la Afianzadora), EN EJERCICIO DE LA AUTORIZACIÓN QUE LE OTORGO EL GOBIERNO FEDERAL POR CONDUCTO DE LA SECRETARÍA DE HACIENDA Y CRÉDITO PÚBLICO, SE CONSTITUYE COMO FIADORA DE (NOMBRE DEL CONTRATISTA, PRESTADOR DE SERVICIOS, EMPRESA O SIMILAR), POR LO QUE EXPIDE ESTA FIANZA A FAVOR DE LOSSERVICIOS ESTATALES DE SALUD,  A FIN DE GARANTIZAR POR UN MONTO MÁXIMO DE $.......................(cantidad según lo establecido en el contrato y/o pedido), EL DEBIDO CUMPLIMIENTO DE LAS OBLIGACIONES A CARGO DEL FIADO Y QUE APARECEN CONSIGNADAS EN EL CONTRATO (O PEDIDO) NUMERO  CORRESPONDIENTE A LA (nombre del proceso Licitación Pública Nacional y/o Internacional y/o Invitación Fundada en Antecedentes y Méritos y/o Invitación a Cuando Menos Tres Personas y/o Adjudicación Directa); ASÍ COMO LA BUENA CALIDAD DEL OBJETO PACTADO EN EL CONTRATO Y QUE FUE CELEBRADO EL DÍA ………( fecha en que fue firmado)  ENTRE LOS SERVICIOS ESTATALES DE SALUD, A TRAVÉS DE LA SUBSECRETARÍA DE ADMINISTRACIÓN Y FINANZAS REPRESENTADA POR EL SUBSECRETARIO DE FINANZAS Y ADMINISTRACIÓNLIC. Lic. JUAN MANUEL SANTAMARÍA RAMÍREZ Y POR (nombre del fiado (por su propio derecho si es persona física y si es persona moral denominación de la empresa) REPRESENTADA POR  (nombre y cargo de quien representa a la persona moral en su caso), RESPECTIVAMENTE, CONTRATO QUE TIENE POR OBJETO: (Descripción del bien o servicio), POR LO QUE LA INSTITUCIÓN AFIANZADORA PAGARA EN CASO DE INCUMPLIMIENTO DE CUALQUIERA DE LAS OBLIGACIONES PREVISTAS EN DICHO CONTRATO (O PEDIDO) Y DE LOS ANEXOS QUE LO INTEGRAN ASÍ COMO SU BUENA CALIDAD Y HASTA POR EL MONTO DEL IMPORTE DE ESTA FIANZA."))),
    paragraph(text = Seq(texto(text = "LA INSTITUCIÓN AFIANZADORA SE OBLIGA A NO CANCELAR LA PRESENTE FIANZA, HASTA EN TANTO NO CUENTE CON EL FINIQUITO A LOS SERVICIOS ESTATALES DE SALUD Ó LA CULMINACIÓN DELA VIGENCIA QUE ABARQUE EL CONTRATO (O PEDIDO) QUE GARANTIZA."))),
    paragraph(text = Seq(texto(text = "LA INSTITUCIÓN AFIANZADORA ACEPTA SOMETERSE EXPRESAMENTE AL PROCEDIMIENTO DISPUESTO  POR EL ARTÍCULO 95 DE LA LEY FEDERAL DE LAS INSTITUCIONES DE FIANZAS EN VIGOR Y SU REGLAMENTO Y RENUNCIA EXPRESAMENTE A LOS BENEFICIOS QUE LE OTORGA EL ARTÍCULO 119 DE LA MISMA LEY POR LO QUE ACEPTA EXPRESAMENTE LAS MODIFICACIONES, LA OBLIGACIÓN AFIANZADA, LAS PRORROGAS O ESPERAS QUE LOS SERVICIOS ESTATALES DE SALUD, CONCEDA A SU FIADO PARA EL CUMPLIMIENTO DEL CONTRATO (O PEDIDO), ASÍ COMO LOS CONVENIOS  MODIFICATORIOS QUE EL FIADO Y LA SECRETARÍA DE FINANZAS Y ADMINISTRACIÓN CELEBREN, AUN CUANDO CUALQUIERA DE ELLOS OMITIERA DARLE AVISO Y LA INSTITUCIÓN DE FIANZAS NO HUBIESE OTORGADO EXPRESAMENTE SU CONSENTIMIENTO PARA LAS PRORROGAS O LAS MODIFICACIONES AL CONTRATO O CONVENIO."))),
    paragraph(text = Seq(texto(text = "LA PRESENTE FIANZA PERMANECERÁ EN VIGOR DESDE LA FECHA EN QUE SE EXPIDE, DURANTE LA VIGENCIA DEL CONTRATO  O PEDIDO QUE LAS PARTES CELEBREN, Y EN SU CASO, DURANTE O LAS PRORROGAS QUE LAS PARTES EN EL CONTRATO PACTEN Y DURANTE EL TIEMPO QUE LLEVE LA SUBSTANCIACIÓN DE TODOS LOS RECURSOS O JUICIOS QUE SE INTERPONGAN HASTA QUE SE DICTE RESOLUCIÓN DEFINITIVA POR AUTORIDAD COMPETENTE.                               FIN.")))
  ).toJSArray

  def disposicionesEspecificas( //datosGrales: QryPedidoDatosGrales,
                                datosGrales: Proceso,
                                descripCompra: DescripcionCompra,
                                partDescripcion: PartidaAnexo,
                                calendario: Calendario,
                                programa: Programa
                              ) = {

    val columnas: Seq[Seq[String|Double|Int|imagen|celda|tabla|columnas]] = Seq(
      Seq(
        celda(text = Seq(texto(text = "\n\n 1")),fontSize = 9, fillColor = "#e7e3e4"),
        celda(text = Seq(texto(text = "\n\n Descripción General de los bienes")),
                  fontSize = 9, fillColor = "#f2edee"),
        celda(text = Seq(
                texto(text="de la INVITACIÓN A CUANDO MENOS TRES PERSONAS, ", bold = true, fontSize = 10),
                texto(text = "para la adquisción de: "),
                texto(text = descripCompra.descripcion),
                texto(text = datosGrales.destino),
                texto(text = " \n\n"),
                texto(text = "LAS ESPECIFIACIONES TÉCNICAS DE LA CONTRATACIÓN SE ENCUENTRAN DETALLADAS EN EL ANEXO UNO "),
                texto(text = "DE LAS PRESENTES BASES. ")
            )
        )
      ),
      Seq(
        celda(text = Seq(texto(text = "2")),fontSize = 9, fillColor = "#e7e3e4"),
        celda(text = Seq(texto(text = "Origen de los recursos")),fontSize = 9, fillColor = "#f2edee"),
        celda(text = Seq(
                  texto(text = datosGrales.programa + ": " + programa.descripcion),
                  texto(text = ", con cargo a la(s) partida(s): "),
                  texto(text = partDescripcion.partida + " " + partDescripcion.descripcion)
                )
          )
      ),
      Seq(
        celda(text = Seq(texto(text = "\n3")),fontSize = 9, fillColor = "#e7e3e4"),
        celda(text = Seq(texto(text = "\nLugar y plazo de entrega")),fontSize = 9, fillColor = "#f2edee"),
        celda(text = Seq(

            texto(text = "La entrega se hará en el almacén central de la secretaría de salud y/o servicios estatales de salud, "),
            texto(text = "en calle prosperidad sin número, colonia universal, código postal 39060 en Chilpancingo, Guerrero, "),
            texto(text = "tel. 01 (747) 47 2 33 77, en el horario de 09:00 a 14:00 horas de lunes a viernes. "),
            texto(text = "\n\n", bold = true),
            texto(text = "ENTREGA DE LOS BIENES: ", bold = true),
            texto(text = datosGrales.plazo, bold = true)

          )
        )
      ),
      Seq(
        celda(text = Seq(texto(text = "\n4")),fontSize = 9, fillColor = "#e7e3e4"),
        celda(text = Seq(texto(text = "\nCondiciones de pago")),fontSize = 9, fillColor = "#f2edee"),
        celda(text = Seq(
          texto(text = "Condiciones de pago: el pago se hará en un plazo de 20 días hábiles posteriores a la presentación de la factura "),
          texto(text = "correspondiente, previa entrega de los bienes o servicios a entera satisfacción de la secretaría de salud y/o "),
          texto(text = "servicios estatales de salud. "),
          texto(text = "\n\n "),
          texto(text = "Anticipos: No se otorgará ningún anticipo.", bold = true)
        )
        )
      ),
      Seq(
        celda(text = Seq(texto(text = "\n5")),fontSize = 9, fillColor = "#e7e3e4"),
        celda(text = Seq(texto(text = "\nJunta de aclaraciones")),fontSize = 9, fillColor = "#f2edee"),
        celda(text = Seq(

            texto(text = "Se llevará a cabo el día: "),
            texto(text = calendario.fecha_inicio_disponible_base.fecha.substring(0,2), bold = true),
            texto(text = " de ", bold = true),
            texto(text = NumToMonth(calendario.fecha_inicio_disponible_base.fecha.substring(3,5).toInt), bold = true),
            texto(text = " del ", bold = true),
            texto(text = calendario.fecha_inicio_disponible_base.fecha.substring(6,11), bold = true),
            texto(text = " a las ", bold = true),
            texto(text = calendario.hora_junta_aclaraciones.hora, bold = true)

          )
        )
      ),
      Seq(
        celda(text = Seq(texto(text = "\n6.1")),fontSize = 9, fillColor = "#e7e3e4"),
        celda(text = Seq(texto(text = "\nActo de presentación y apertura de propuestas técnicas")),
                    fontSize = 9, fillColor = "#f2edee"),
        celda(text = Seq(
          texto(text = "Las propuestas técnicas se harán en un sobre cerrado que contendrá la oferta técnica en las carpetas "),
          texto(text = "debidamente separadas y cerradas en las oficinas mencionadas anteriormente, a más tardar a las: "),
          texto(text = " horas. ", bold = true),
          texto(text = calendario.hora_apertura_tecnica.hora, bold = true),
          texto(text = " del día: ", bold = true),
          texto(text = calendario.fecha_apertura_tecnica.fecha.substring(0,2), bold = true),
          texto(text = " de ", bold = true),
          texto(text = NumToMonth(calendario.fecha_apertura_tecnica.fecha.substring(3,5).toInt), bold = true),
          texto(text = " del ", bold = true),
          texto(text = calendario.fecha_apertura_tecnica.fecha.substring(6,11), bold = true),
          texto(text = "\n\n"),
          texto(text = "En la Sala de Juntas Dr. Cesar Piña Cámara, ubicada en Avenidad Ruffo Figueroa # 6, Colonia Burócratas, "),
          texto(text = "Códig Postal 39090, Chilpancingo de los Bravo, Estado de Guerreor ")
        ))
      ),
      Seq(
        celda(text = Seq(texto(text = "\n6.2")),fontSize = 9, fillColor = "#e7e3e4"),
        celda(text = Seq(texto(text = "\nActo de presentación y apertura de propuestas económicas")),
          fontSize = 9, fillColor = "#f2edee"),
        celda(text = Seq(
          texto(text = "Las propuestas económicas se harán en un sobre cerrado que contendrá la oferta técnica en las carpetas "),
          texto(text = "debidamente separadas y cerradas en las oficinas mencionadas anteriormente, a más tardar a las: "),
          texto(text = " horas. ", bold = true),
          texto(text = calendario.hora_apertura_tecnica.hora, bold = true),
          texto(text = " del día: ", bold = true),
          texto(text = calendario.fecha_apertura_tecnica.fecha.substring(0,2), bold = true),
          texto(text = " de ", bold = true),
          texto(text = NumToMonth(calendario.fecha_apertura_tecnica.fecha.substring(3,5).toInt), bold = true),
          texto(text = " del ", bold = true),
          texto(text = calendario.fecha_apertura_tecnica.fecha.substring(6,11), bold = true),
          texto(text = "\n\n"),
          texto(text = "En la Sala de Juntas Dr. Cesar Piña Cámara, ubicada en Avenidad Ruffo Figueroa # 6, Colonia Burócratas, "),
          texto(text = "Códig Postal 39090, Chilpancingo de los Bravo, Estado de Guerreor ")
        ))
      ),
      Seq(
        celda(text = Seq(texto(text = "\n7")),fontSize = 9, fillColor = "#e7e3e4"),
        celda(text = Seq(texto(text = "\nFallo")), fontSize = 9, fillColor = "#f2edee"),
        celda(text = Seq(
          texto(text = "El fallo del presente procedimiento administrativo será dado a conocer por esta Secretaría de Salud y/o "),
          texto(text = "Servicios Estatales de Salud, "),
          texto(text = " del día: ", bold = true),
          texto(text = calendario.fecha_fallo.fecha.substring(0,2), bold = true),
          texto(text = " de ", bold = true),
          texto(text = NumToMonth(calendario.fecha_fallo.fecha.substring(3,5).toInt), bold = true),
          texto(text = " del ", bold = true),
          texto(text = calendario.fecha_fallo.fecha.substring(6,11), bold = true),
          texto(text = " a las ", bold = true),
          texto(text = calendario.hora_apertura_tecnica.hora, bold = true),
          texto(text = " horas. ", bold = true),
          texto(text = "\n\n"),
          texto(text = "En la Sala de Juntas de la Secretaría Salud en el domicilio ya citado.")
        ))
      ),
      Seq(
        celda(text = Seq(texto(text = "\n8")),fontSize = 9, fillColor = "#e7e3e4"),
        celda(text = Seq(texto(text = "\nFirma de pedido y/o contrato")),
          fontSize = 9, fillColor = "#f2edee"),
        celda(text = Seq(
          texto(text = "El representante legal del licitante ganador, deberá presentarse a la firma del contrato "),
          texto(text = " del día: ", bold = true),
          texto(text = calendario.fecha_firma_contrato.fecha.substring(0,2), bold = true),
          texto(text = " de ", bold = true),
          texto(text = NumToMonth(calendario.fecha_firma_contrato.fecha.substring(3,5).toInt), bold = true),
          texto(text = " del ", bold = true),
          texto(text = calendario.fecha_firma_contrato.fecha.substring(6,11), bold = true),
          texto(text = " a las ", bold = true),
          texto(text = calendario.hora_firma_contrato.hora, bold = true),
          texto(text = " horas. ", bold = true),
          texto(text = ", en la sala de juntas que ocupa la unidad convocante; para tal efecto, deberá otorgar la garantía "),
          texto(text = "de cumplimiento del contrato por el "),
          texto(text = "10% del importe total contratado, sin considerar el impuesto al valor agregado (IVA).", bold = true),
          texto(text = "Esta garantía deberá ser entregada a la firma del contrato respectivo.")
        ))
      ),
      Seq(
        celda(text = Seq(texto(text = "9")),fontSize = 9, fillColor = "#e7e3e4"),
        celda(text = Seq(texto(text = "Entrega de catálogos")), fontSize = 9, fillColor = "#f2edee"),
        celda(text = Seq(texto(text = "No aplica.")))
      ),
      Seq(
        celda(text = Seq(texto(text = "\n10")),fontSize = 9, fillColor = "#e7e3e4"),
        celda(text = Seq(texto(text = "\nFacturación")), fontSize = 9, fillColor = "#f2edee"),
        celda(text = Seq(
          texto(text = "La facturación será a nombre de los "),
          texto(text = "Servicios Estatales de Salud; RFC SES-870401TX8", bold = true),
          texto(text = "y tiene establecido su domicilio fiscal en Avenida Ruffo Figueroa No. 6, Colonia Burocratas, "),
          texto(text = "Código Postal 39090, Chilpancingo de los Bravo, Estado de Guerrero. Así mismo contendrá el número "),
          texto(text = "de la licitación, el número de contrato y/o pedido, programa al que corresponde, partida, clave del bien que oferta.")
        )
        )
      ),
      Seq(
        celda(text = Seq(texto(text = "11")),fontSize = 9, fillColor = "#e7e3e4"),
        celda(text = Seq(texto(text = "Vigencia del contrato")), fontSize = 9, fillColor = "#f2edee"),
        celda(text = Seq(texto(text = "12 meses a partir de la firma del contrato")))
      )
    )
    val widths = Seq(20, 100, 348)
    tabla(table = bodyTable(body = columnas, widths = widths))
  }

  def disposicionesGrales(  descrCompra: DescripcionCompra,
                            //datosGrales: QryPedidoDatosGrales,
                            datosGrales: Proceso, partDescripcion: PartidaAnexo,
                            calendario: Calendario, programa: Programa ) = {

    Seq(Seq(
      paragraph(text = Seq(
        texto(text = "Apartado B", bold = true, fontSize = 15, alignment = "center")
      ), margin = Seq(10,10,10,10)),
      paragraph(text = Seq(
        texto(text = "Disposiciones generales", bold = true, fontSize = 15, alignment = "center")
      ), margin = Seq(10,10,10,2)),
      paragraph(text = Seq(
        texto(text = "1.- De la supletoriedad de las bases del procedimiento de Invitación a Cuando Menos Tres Personas",
          bold = true, fontSize = 15, alignment = "center")
      ), margin = Seq(10,10,10,2)),
      paragraph(text = Seq(

        texto(text = "En todo lo no previsto por las presentes bases se atenderá lo establecido por la Ley Número 230 de Adquisiciones, "),
        texto(text = "Enajenaciones, Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado "),
        texto(text = "de Guerrero, Código Civil Federal, Código de Procedimientos Contensiosos Administrativos del Estado de Guerrero No. 215 "),
        texto(text = "y demás leyes vigentes que resulten aplicables.")

      ), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(
        texto(text = "2.- Sobre las propuestas técnicas y económicas", bold = true, fontSize = 15, alignment = "center")
      ), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(

        texto(text = "Las propuestas técnicas y económicas deberán ser incluidas en un sobre membretado, cerrado con la denominación "),
        texto(text = "y razón social de la empresa y contendrá la oferta técnica y económica, debiendo identificar claramente el  "),
        texto(text = "procedimiento en que participa, en papel membretado de la empresa, mecanografiadas en original, redactadas en "),
        texto(text = "idioma español, firmada exclusivamente cada una de las hojas por el proveedor o por la persona legalmente "),
        texto(text = "autorizada para contraer obligaciones a su nombre en la última hoja al calce y en cada una de ellas rubricada al margen "),
        texto(text = ", sin tachaduras o enmendaduras, indicando que serán entregadas en un solo sobre cerrado con la siguiente leyenda: ")
      ), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(
        texto(text = "INVITACIÓN A CUANDO MENOS TRES PERSONAS para la adquisición de la(s) partida(s): "),
        texto(text = descrCompra.concepto + ": " + descrCompra.descripcion),
        texto(text = " para "),
        texto(text = datosGrales.destino),
        texto(text = " con recursos provenientes del programa: "),
        texto(text = datosGrales.programa + ": " + programa.descripcion),
        texto(text = " con cargo a las partidas: "),
        texto(text = partDescripcion.partida + ": " + partDescripcion.descripcion),
        texto(text = "en el sobre de las ofertas técnicas y económicas seguidas de la frase: "),
        texto(text = "''No abrir antes del día: ''", bold = true),
        texto(text = calendario.fecha_inicio_disponible_base.fecha.substring(0, 2), bold = true),
        texto(text = " de ", bold = true),
        texto(text = NumToMonth(calendario.fecha_inicio_disponible_base.fecha.substring(3, 5).toInt), bold = true),
        texto(text = " del ", bold = true),
        texto(text = calendario.fecha_inicio_disponible_base.fecha.substring(6, 11), bold = true),
        texto(text = ".", bold = true)
      ), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Las propuestas de referencia, así como todos y cada uno de los documentos que las integren, "),
        texto(text = "deberán entregarse físicamente a las oficinas de la unidad convocante, con residencia en la Sala "),
        texto(text = "de Juntas Dr. Cesar Piña Cámara, ubicada en Avenidad Ruffo Figueroa # 6, Colonia Burócratas, "),
        texto(text = "Código Postal 39090, Chilpancingo de los Bravo, Estado de Guerrero.")


      ), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(texto(text = "3.- Documentos que debe contener el sobre técnico-económico.", bold = true, alignment = "center"))),
      paragraph(text = Seq(

        texto(text = "La documentación legal y administrativa de los participantes será presentada para su revisión por el personal de la "),
        texto(text = "Secretaría de Salud y/o Servicios Estatales de Salud, misma que será devuelta en la junta de presentación y apertura "),
        texto(text ="de propuestas técnicas y económicas. Recibiendo únicamente en original la propuesta técnica-económica, para su análisis "),
        texto(text = "cuantitativo y cualitativo, los proveedores entregarán por escrito su propuesta técnica-económica, dentro del sobre "),
        texto(text = "deberán presentar copia simple y dentro o fuera del sobre a elección del participante, original para su cotejo, excepto "),
        texto(text = "(los documentos identificados con los incisos 1,7,8,9,10,11,12,13,14,15,16,17,18,19,20 y 21, mismos que deberán estar "),
        texto(text = "incluidos en original dentro del sobre técnico), los documentos originales que pueden venir fuera son los que se marcan "),
        texto(text = "con los números 2,3,4,5 y 6 que a continuación se mencionan los documentos citados en este párrafo se recibirán para "),
        texto(text = "su cotejo mismos que serán devueltos en la junta de apertura técnica-económica.")), margin = Seq(10,2,0,2)

      ),
      paragraph(text = Seq(
        texto(text = "1.- La propuesta técnica que contenga la descripción general de los bienes (dentro del sobre y firmada dicha descripción "),
        texto(text = "general de los bienes (dentro del sobre y firmada dicha descripción), para la propuesta técnica la elaboración y  "),
        texto(text = "presentación será en una columna con la descripción solicitada en las bases y una segunda columna con la descripción  "),
        texto(text = "ofertada  por el licitante. El proveedor deberá apegarse y cumplir con las especificaciones técnicas requeridas "),
        texto(text = "indicadas en el anexo uno, y las Normas Oficiales Mexicanas vigentes.\n")), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(
        texto(text = "2.- Acta constitutiva y sus modificaciones, si las hubiere, en la que se deberá señalar que el objeto social "),
        texto(text = "empresa es relacionada con los servicios objeto del presente procedimiento de Invitación a Cuando Menos "),
        texto(text = "Tres Personas, para el caso de personas morales y acta de nacimiento en el caso de personas físicas, acreditando "),
        texto(text = "su objeto social con su RFC.\n")), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(
        texto(text = "3.- Poder notarial deun la persona que firme las ofertas cuyos términos acrediten la legal representación del proveedor.")
      ), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(
        texto(text = "4.- Identificación oficial legible con fotografía de la persona cuya firma aparece suscribiendo la oferta y también de "),
        texto(text = "la persona que asista al evento en representación de esta, para lo cual únicamente se aceptará credencial para votar, "),
        texto(text = "Cedula Profesional y/o pasaporte vigente.")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "5.- Cédula del Registro Federal de Contribuyentes de la empresa.")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "6.- Carta poder simple de la persona quien presente las propuestas, cuando se trate del que concurra en representación "),
        texto(text = "del apoderado legal o del propietario.")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "7.- Escrito bajo protesta de decir verdad en el que manifieste que se encuentre al corriente en sus pagos fiscales.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "8.- Carta donde manifieste bajo protesta de decir verdad no encontrarse dentro de los siguientes supuestos:\n")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "a) No tener ninguna relación de parentesco, personal, familiar o de negocios con los servidores públicos "),
        texto(text = "responsables del proceso de compra o con alguna persona que integre el área convocante. ")
      ), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(
        texto(text = "b) No desempeñar, cargo, empleo o comisión dentro de la Administración Pública Federal, Estatal y Municipal.")
      ), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(
        texto(text = "c) No se les haya rescindido el contrato por alguna dependencia u organismo requirente del Gobierno "),
        texto(text = "Federal, Estatal y Municipal por causas imputables a él. ")), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(
        texto(text = "d) No encontrarse inhabilitadas por resolución de las contralorías de las entidades federativas o de la Secretaría "),
        texto(text = "de la Función Pública. ")), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(
        texto(text = "e) No haber incurrido anteriormente en atraso en las entregas de los bienes o servicios, que le hayan sido adjudicados "),
        texto(text = "por cualquier unidad licitadora de los tres niveles de Gobierno. ")), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(
        texto(text = "f) No estar en estado de quiebra, suspensión de pagos o sujetas a juicio de concurso de acreedores. ")
      ), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(
        texto(text = "g) No celebrar contratos sobre las materias reguladas por esta ley, sin estar facultadas para hacer uso de derechos de propiedad intelectual. ")
      ), margin = Seq(10,2,0,2)),
      paragraph(text = Seq(texto(text = " ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "9.- Curriculum Vitae. Con el cuaal acredite la evolución del giro comercial con que participa en el procedimiento de "),
        texto(text = "Invitación a Cuando Menos Tres Personas y en el cual señale un mínimo de 3 nombres, domicilios y teléfonos de las "),
        texto(text = "personas jurídicas morales de derecho privado y público a las que ha prestado los servicios como los que se "),
        texto(text = "licitan en el presente procedimiento administrativo. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "10.- Escrito en el que manifieste bajo protesta de decir verdad que cuenta con una experiencia mínima de dos años en  "),
        texto(text = "el manejo y prestación de los servicios concursados, como los que se detalla en el anexo uno de las presentes bses. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "11.- Capital contable, debiendo acreditarlo mediante la última declaración de pago de impuestos anual ante la  "),
        texto(text = "Secretaría de Hacienda y Crédito Público, así como sus dos últimos pagos parciales, para el caso de los proveedores "),
        texto(text = "que efectúan el pago de imppuestos vía electrónica, carta bajo protesta de decir verdad que la impresión de los "),
        texto(text = "comprobantes de pago se han efectuado atendiendo las reglas expedidas por la Secretaría de Hacienda y Crédito "),
        texto(text = "Público para el pago e impresión de documentos a traves de esta modalidad. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "12.- Carta donde manifieste bajo protesta de decir verdad que el proveedor cuenta con los elementos humanos, "),
        texto(text = "financieros técnicos y materiales para la prestación de los servicios solicitados en el anexo. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "13.- Escrito en hoja membretada de la empresa y firmada bajo protesta de decir verdad, en la que garantiza que "),
        texto(text = "los servicios serán prestados de acuerdo a las especificaciones técnicas descritas en las bases concursales. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "14.- Escrito bajo protesta de decir verdad que el proveedor conoce acepta en su totalidad el contenido de la Ley "),
        texto(text = "Número 230 de Adquisiciones, Enajenaciones, Arrendamientos, Prestación de Servicios y Administración de Bienes "),
        texto(text = "Muebles e Inmuebles del Estado de Guerrero. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "15.- Carta elaborada en papel membretado de la empresa que participa, en la que presente una declaración de integridad "),
        texto(text = "y manifieste que por sí mismo o a través de interpósita persona, se abstendrá de adoptar conductas que induzcan o "),
        texto(text = "alteren las evaluaciones de las propuestas, el resultado del procedimiento u otros aspectos que otorguen condiciones "),
        texto(text = "más ventajosas con relación a los demás participantes, debidamente firmada autógrafamente por el representante legal. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "16.- Carta del proveedor elaborada en papel membretado, bajo protesta de decir verdad, dirigida a la Secretaría de Salud "),
        texto(text = "y/o Servicios Estatales de Salud, debidamente firmada autógrafamente por el representante legal, indicando que en caso "),
        texto(text = "de resultar adjudicado se compromete a reponer sin costo adicional para la Secretaría de Salud, aquellos servicios que "),
        texto(text = "presenten vicios ocultos, en un plazo no mayor a cinco días habiles. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "17.- Anexo dos. (Formato de Personalidad debiendo indicar número de padrón de proveedores asignado por la "),
        texto(text = "Secretaría de Salud y/o Servicios Estatales de Salud. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "18.- En su caso, escrito bajo protesta de decir verdad que la empresa cuenta con personal con discapacidad en una "),
        texto(text = "proporción del 5% cuando menos de la totalidad de su planta de empleados, cuya antiguedad no sea inferior a seis meses; "),
        texto(text = "antiguedad que se comprobará con el aviso del alta al régimen obligatorio del Instituto Mexicano del Seguro Social, "),
        texto(text = "documento que servirá de antecedente en caso de empate entre dos o más proposiciones, no constituyendo su omisión "),
        texto(text = "una causal de descalificación. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "19.- El proveedor deberá presentar un escrito bajo protesta de decir verdad que en caso de resultar adjudicado, las facturas "),
        texto(text = "correspondientes, contendrán el número de la presente Invitación a Cuando Menos Tres Personas, el número de contrato, "),
        texto(text = "programa al que corresponde, partida y clave del servicio prestado. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "20.- Estados Financieros auditados al 31 de diciembre del 2016 y copia de la Cedula Profesional del contador "),
        texto(text = "certificado que los audito. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "21.- Carta de aceptación mediante el cual el proveedor manifieste su conformidad para que el personal de la Secretaría "),
        texto(text = "de Salud y/o Servicios Estatales de Salud, realice las visitas de inspección a sus instalaciones y las demás que "),
        texto(text = "considere necesario en cualquier tiempo. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(texto(text = "3.1 Documentos y datos que debe contener el sobre económico.")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Los licitantes entregarán por escrito su propuesta económica dentro del sobre deberán presentar en original, los "),
        texto(text = "documentos que a continuación se indican: ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "1).- Oferta económica ", bold = true),
        texto(text = "(descripción general e importe total ofertado de los bienes):")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Deberá incluir precio unitario, se deberá incluir además el subtotal, IVA desglosado y el importe total ofertado "),
        texto(text = "de los bienes o servicios (excepto cuando se trate de bienes que no sea gravados con ese impuesto, como lo son alimentos y medicinas), cotizados en peso mexicano, que pretende suministrar/prestar a los Servicios Estatales de Salud (el o los documentos de la  "),
        texto(text = "descripción deberán presentarse debidamente firmados por el apoderado o representante legal de la empresa). También,  "),
        texto(text = "deberá de presentar su cotización en CD ROOM, lo cual permitirá efectuar la redacción del acta con mayor rapidez, "),
        texto(text = "no constituyendo su omisión una causal de descalificación. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Así mismo deberá contener los siguientes rubros: ", bold = true)), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "a). Condiciones de pago y plazo de entreta. ", bold = true)), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "El pago total por los bienes adquiridos se realizará hasta que las autoridades de la dependencia requirente "),
        texto(text = "firmen acuse de recibido a entera satisfacción. Una vez entregada la factura y las autoridades firmen a entera "),
        texto(text = "satisfacción el plazo para el pago "),
        texto(text = "no excederá de 20 días habiles. ", bold = true)), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "b). Lugar de entrega, instalación y capacitación:", bold = true)), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "La entrega de los bienes, deberá ser libre a bordo dentro de los "),
        texto(text = "45 días habiles ", bold = true),
        texto(text = "posteriores a la firma del pedido y/o contrato, con destino en el Almacén Central de la Secretaría de Salud y/o Servicios "),
        texto(text = "Estatales de Salud, ubicado en calle prosperidad s/n, Colonia Universal, Código Postal 39060, Chilpancingo de los Bravo, "),
        texto(text = "Estado de Guerrero, Tel.(01-747) 472 33 77, o en su caso en el destino de la compra el cual vendrá especificado en el "),
        texto(text = "pedido y/o contrato, en horario de 9:00 a 14:00 de lunes a viernes.")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "2).- Vigencia de la cotización: 30 días habiles ", bold = true),
        texto(text = "despues de la lectura de fallo del presente procedimiento de Invitación a Cuando Menos Tres Personas.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "3).- Garantía de seriedad de las propuestas: ", bold = true),
        texto(text = "El licitante deberá garantizar la seriedad de su oferta mediante cheque certificado o fianza equivalente al 10% "),
        texto(text = " del total de su propuesta económica, sin incluir el impuesto al valor agregado (I.V.A.), a favor de los Servicios "),
        texto(text = "Estatales de Salud, mediante póliza de fianza, cheque certificado o cheque de caja, deberá ser expedido por la misma "),
        texto(text = "empresa participante. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "4.- Aclaración de bases", bold = true, alignment = "center")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "La junta de aclaraciones tendrá verificativo en la fecha, horario y lugar establecido en el punto 5 del apartado A, "),
        texto(text = "disposiciones específicas, de esta manera los proveedores que no asistan o que no envíen a sus representantes debidamente "),
        texto(text = "autorizados, no será motivo de descalificación, pero se sujetarán a los acuerdos tomados en la misma, los cuales "),
        texto(text = "constatarán en el acta debidamente firmada por los proveedores, por la Secretaría de Salud y/o Servicios Estatales de Salud, "),
        texto(text = "dependencia u organismo requirente y no podrán solicitar aclaraciones posteriores o argumentar incompresibilidad del "),
        texto(text = "contenido de las bases. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "En dicha junta, los representantes de la "),
        texto(text = "Secretaría de Salud y/o Servicios Estatales de Salud, ", bold = true),
        texto(text = "leerán y darán respuesta a las preguntas que le hubieren sido formuladas por escrito (en papel membretado de la empresa), "),
        texto(text = "iniciado el acto no se recibirá ningún cuestionario, otorgando las respuestas correspondientes, si alguno de los proveedores "),
        texto(text = "que habiendo presentando sus dudas, en los términos señalados en el párrafo anterior, requiere aclaraciones adicionales "),
        texto(text = "relacionadas con las dudas planteadas por escrito, las podrán solicitar en el mismo acto de la junta de aclaraciones; "),
        texto(text = "posterior a este, no se aceptará ninguna petición de aclaración. ")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Los licitantes deberán enviar sus preguntas sobre el contenido de las presentes bases y sus anexos, 24 horas previo al acto citado, remitiéndolas al Departamento de Adquisiciones de la Secretaría de Salud y/o Servicios Estatales de Salud, o al correo electrónico: ses_adqui@hotmail.com, en formato Microsoft Word, para lo cual deberá solicitar acuse de recibo, la convocante no se hará responsable de aquellas dudas o aclaraciones que no cuenten con acuse de recibo por  parte de la misma,  cabe señalar que la asistencia de los proveedores a la junta de aclaraciones de la Invitación es optativa, pero cualquier aclaración a las bases de la licitación derivada de la junta de aclaraciones, será considerada como parte integrante de éstas.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "El acto de presentación y apertura de proposiciones, en el que sólo participarán los proveedores que hayan sido invitados para este proceso y el cual se llevará a cabo en dos etapas conforme a lo siguiente:")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(texto(text = "5.-  Apertura  del sobre con las ofertas  técnicas y económicas.")), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Se recibirá el sobre con las proposiciones técnicas y económicas, debiendo ser presentadas por los proveedores en un sobre cerrado de manera inviolable que contendrá la oferta técnica y económica con las carpetas debidamente separadas. Se procederá a su apertura, haciéndose constar la documentación presentada sin que implique la evaluación de su contenido. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "En esta etapa, solamente se procederá a la apertura del sobre que contenga las propuestas técnicas y económicas, desechándose las que hubieren omitido alguno de los requisitos de estas bases.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Únicamente podrá estar presente en el acto de apertura de proposiciones técnicas y económicas, un representante de cada proveedor, el cual tendrá derecho a voz, siempre y cuando presente carta poder simple que lo faculte, caso contrario podrá ingresar al acto en calidad de observador.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Se levantará un acta del acto de apertura de ofertas técnicas y económicas, la cual será firmada por los proveedores, por los integrantes del Departamento de Adquisiciones de la Secretaria de Salud y/o Servicios Estatales de Salud, los representantes del área requirente y empresas participantes, cuya copia se entregará a los participantes, señalando las propuestas técnicas-económicas aceptadas cuantitativamente para su evaluación a detalle, así como las propuestas técnicas que hubieran sido descalificadas y las causas que motivaron su descalificación. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Se asentará en dicha acta la fecha, lugar y hora en que se llevará a cabo la lectura del fallo. La omisión de firma por parte de alguno de los proveedores no invalidará el contenido del acta. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Las propuestas que se desechen serán devueltas transcurridos sesenta días naturales  contados a partir de la fecha en que se de a conocer el acta del fallo. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "6.- Criterios para la evaluación documental y técnica.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Se verificará que las proposiciones presentadas incluyan la información, documentos y requisitos solicitados en la primera etapa de la apertura de proposiciones, elaborándose el dictamen que justifique la aceptación de las propuestas técnicas y económicas y/o desechamiento de aquellas que no hayan cumplido conforme a éstas bases. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "En el caso de que el fallo no se realice en la misma fecha, los licitantes y los servidores públicos de la Secretaría de Salud y/o Servicios Estatales de Salud presentes, firmarán las proposiciones técnicas-económicas aceptadas y se fijará la fecha, hora y lugar para dar la lectura del fallo del presente procedimiento de Invitación a Cuando Menos Tres Personas.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "La Secretaría de Salud y/o Servicios Estatales de Salud, (Departamento de Adquisiciones), elaborará el acta correspondiente en la que se harán constar las propuestas aceptadas, sus importes, así como las que hubiesen sido desechadas y las causas que lo motivaron; será firmada por los proveedores, dependencia requirente e integrantes de la Secretaria de Salud y/o Servicios Estatales de Salud y se les hará entrega de una copia de la misma, las ofertas que cumplan cuantitativamente con los requisitos solicitados en las bases serán aceptadas para ser analizadas cualitativamente por lo que se refiere a aspectos  técnicos, legales y económicos, de no cumplir cualitativamente con alguno de los requisitos, serán desechadas dichas ofertas y cuya fundamentación y motivación será dada a conocer momentos previos a la lectura del fallo del presente procedimiento de Invitación Fundada en Antecedentes y Méritos.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "7.- Criterios para la evaluación técnica, económica, dictamen y \nAdjudicación de los pedidos y/o contratos.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Para facilitar el criterio de la adjudicación del presente procedimiento, la propuesta técnica-económica deberá ser presentada con un precio unitario por la(s) partida(s) ofertada(s) y se calificará de la manera siguiente:")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "7.1.  Evaluación de la mejor oferta total.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "7.2. Evaluación del mejor precio, financiamiento, calidad  y entrega de todos los bienes o servicios adjudicados para la Secretaría de Salud y/o Servicios Estatales de Salud objeto del presente procedimiento administrativo.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Para los efectos de continuar con este procedimiento, procederá a realizar el cuadro comparativo de cumplimiento de los requisitos, con el propósito de determinar la mejor oferta que, consecuentemente, sustentará el fallo que emita esta Secretaría de Salud y/o Servicios Estatales de Salud.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "El pedido y/o contrato se adjudicará al proveedor que reúna y mejore las condiciones legales, técnicas y económicas requeridas por la convocante y garanticen satisfactoriamente el cumplimiento de las obligaciones respectivas; además al que ofrezca mejores condiciones en cuanto a precio, calidad, financiamiento y demás oportunidades para la Secretaría de Salud y/o Servicios Estatales de Salud. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "8.- Causas de descalificación de los proveedores.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Se descalificará a los proveedores por los motivos siguientes:")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "1) Si no cumplen con los requisitos legales, técnicos y económicos que se establecen en las bases del presente procedimiento de Invitación a Cuando Menos Tres Personas. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "2) Si se comprueba que tienen acuerdos con otros participantes para elevar  los precios de los artículos/bienes/servicios o arrendamiento objeto del presente procedimiento administrativo o si se comprueba que tienen acuerdos con funcionarios de la dependencia u organismo requirente.\u0000 ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "3) Si del análisis de la información proporcionada por el proveedor, en el mismo acto de apertura de las ofertas o posterior a estos eventos, se comprueba que la misma no es verídica.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Así como por cualquier violación a la Ley Número 230 de Adquisiciones, Enajenaciones, Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Si se comprueba que el participante se encuentra dentro de cualquiera de los supuestos comprendidos en el numeral 3, del punto 9 del apartado B, disposiciones generales. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Si el valor de la garantía presentada sea inferior al 5% del valor total de la      propuesta, el proveedor será descalificado de este procedimiento administrativo, lo cual será dado a conocer durante el acto de aperturas de proposiciones técnicas- económicas. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "El proveedor también podrá ser descalificado en la partida o partidas que oferte en el presente procedimiento administrativo, cuando del análisis cualitativo realizado a la misma, se determinen  que  no cumple con lo solicitado en las bases del procedimiento de referencia.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Si el licitante presentará dos opciones, quedará descalificado en la partida  si hiciera duplicidad de opciones. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "La falta de firma autógrafa por lo menos en la última hoja de cada documento.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Si el licitante a la fecha de apertura de las propuestas técnicas y económicas, se encuentra en situación de demora.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "9.- Causas que determinarán declarar  desierto el procedimiento de Invitación a Cuando Menos Tres Personas.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = " La Secretaría de Salud y/o Servicios Estatales de Salud, se reserva el derecho de declarar desierto el presente procedimiento de Invitación a Cuando Menos Tres Personas en los siguientes casos:")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Cuando no se presente ningún licitante en el acto de presentación de ofertas   y apertura de ofertas técnicas-económicas.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Cuando ningún proveedor reúna los requisitos establecidos en éstas bases.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Cuando previa investigación efectuada se determine que los precios ofrecidos no sean aceptables o rebasen el techo presupuestal asignado a este rubro.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Cuando ninguna de las proposiciones ofrezca mejores condiciones disponibles en cuanto a precio, calidad, financiamiento, oportunidad y demás circunstancias pertinentes.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Cuando concurran circunstancias de interés general y pongan en peligro los intereses colectivos.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "En caso fortuito o de fuerza mayor, esto es, que existan causas que provoquen   la extinción de la necesidad de adquirir los artículos/bienes/servicios o arrendamiento que de continuarse con la contratación de los mismos, se pudiera ocasionara un daño o perjuicio a la dependencia u organismo requirente.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Cuando no se cuente con un mínimo de tres propuestas susceptibles de analizarse técnicamente.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "También, podrá(n) declararse desiertas (s) una o mas partidas de la que describen en el anexo uno de las presentes bases, cuando estas no reúnan las características técnicas solicitadas en las bases, rebasen el techo presupuestal autorizado, el precio que ofrezca sea excesivamente bajo respecto a los indicados en el precio de referencia a los señalados en la investigación de mercado o bien, cuando no sea (n) ofertada (s) por ningún participante.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Cuando el presente procedimiento administrativo se declare desierto o no se reciban proposiciones aceptables según la evaluación efectuada por la Secretaría de Salud y/o Servicios Estatales de Salud. Ésta podrá convocar a un segundo evento, conforme a lo dispuesto por la normatividad aplicable, atendiendo la prioridad de cobertura al programa referido.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "10.- Cancelación del procedimiento de Invitación a cuando Menos Tres Personas ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "El Departamento de Adquisiciones, podrá cancelar el procedimiento de Invitación a Cuando Menos Tres Personas, partidas o conceptos incluidos en estas, por caso fortuito o fuerza mayor. También podrá cancelar cuando existan circunstancias debidamente justificadas que provoquen la extinción de la necesidad para adquirir los bienes y que de continuarse con el procedimiento de contratación se pudiera ocasionar un daño o perjuicio a la Secretaria de Salud y/o Servicios Estatales de Salud.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "11.- Prohibición para negociar las bases y las proposiciones.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Una vez iniciado el acto de presentación y apertura de proposiciones, los términos y condiciones señalados tanto en éstas bases como en las ofertas presentadas no podrán ser modificadas ni negociadas.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "12.- Criterios que se aplicarán para adjudicar los pedidos y/o contratos.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Con base en el resultado de la tabla comparativa de las propuestas económicas y otras que se elaboren en su caso, se elegirá en igualdad de circunstancias, aquella que ofrezca el precio más conveniente a la Secretaria de Salud y/o Servicios Estatales de Salud.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Si en este procedimiento administrativo se presentara igualdad de condiciones entre las propuestas de dos o más proveedores, se dará preferencia a personas con discapacidad o a la empresa que cuente con personal con discapacidad en una proporción del cinco por ciento cuando menos de la totalidad de su planta de empleados, cuya antigüedad no sea inferior a seis meses; antigüedad que se comprobará con el aviso de alta al régimen obligatorio del Instituto Mexicano del Seguro Social.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "En caso de que cumplidos los requisitos de las bases se tengan precios iguales, y no acrediten los proveedores tener personal con discapacidad en un proporción del 5 %  la adjudicación se efectuará a favor del proveedor que resulte ganador del sorteo manual por insaculación que se desarrollará en el propio acto de fallo, el cual consistirá en la participación de un boleto por cada propuesta que resulte empatada, los que serán depositados en una urna, de la que se extraerá el boleto del licitante ganador.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "La adjudicación del contrato se realizará en función del presupuesto que para tal efecto se tenga autorizado, de ser insuficiente el presupuesto podrá reducirse la cantidad total de los artículos, bienes, servicios o arrendamiento hasta en un 50% (Cincuenta por ciento), al momento de emitir el fallo, no existiendo la posibilidad de sustituir dichos artículos, bienes, servicios o arrendamiento, ni \nvariar sus características.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "13.- Fallo del procedimiento")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "El fallo que dicte la Secretaría de Salud y/o Servicios Estatales de Salud, será inapelable por haber sido sustentado por el personal asignado a este caso. Sin embargo, los proveedores tendrán la facultad de interponer el medio de impugnación previsto en  las presentes bases contra actos de administración que a consideración del  proveedor  lesionen su esfera jurídica.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "El fallo podrá diferirse y el nuevo plazo no deberá exceder de 20 días naturales, contados a partir de la fecha fijada originalmente, el diferimiento será comunicado mediante oficio y, de igual manera,  se  les  convocará  al  fallo.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "14.- Contrato de prestación de servicio")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "La Secretaría de Salud y/o Servicios Estatales de Salud, elaborará el contrato del proveedor que haya sido favorecido con el fallo del presente procedimiento para tal efecto, deberá otorgar la garantía de cumplimiento del contrato que se menciona en las presentes bases, misma que deberá ser presentada en el momento de su suscripción.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Dicho contrato se adjudicará al proveedor que reúna las condiciones legales, técnicas y económicas requeridas por la convocante y garantice satisfactoriamente el cumplimiento de las obligaciones respectivas.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "15.- De la garantía de cumplimiento de pedido ó contrato ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "El proveedor ganador de éste procedimiento a fin de garantizar el cumplimiento de las obligaciones derivadas del pedido ó contrato que suscriba, deberá otorgar en el momento mismo de su firma, cheque certificado, de caja o fianza a nombre de los Servicios Estatales de Salud, por un importe equivalente al 10% (Diez por ciento) del importe total contratado, sin incluir el I.V.A (siempre y cuando no se trate de artículos, bienes o servicios no gravables con ese impuesto).")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "La anterior garantía será devuelta al proveedor ganador una vez que haya cumplido con las cláusulas contenidas en el contrato ó pedido de compraventa de los artículos, servicios o arrendamiento adjudicados.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "16.-  Rescisión del contrato")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Los Servicios Estatales de Salud y/o Secretaria de Salud, rescindirá administrativamente el pedido ó contrato en caso de incumplimiento de las obligaciones a cargo del proveedor/prestador de servicios/arrendador, en cuyo caso el procedimiento deberá iniciarse dentro de los diez días hábiles siguientes a aquél en que se hubiere agotado el monto límite de aplicación de las penas convencionales.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Si previamente a la determinación de dar por rescindido el pedido ó contrato, se hiciere entrega de los bienes, concluidos los servicios o el arrendamiento de bienes muebles e inmuebles que le fueron adjudicados, el procedimiento iniciado quedará sin efectos.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "El procedimiento de rescisión de pedido ó contrato se llevará a cabo conforme a lo siguiente:")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Se iniciará a partir de que al proveedor le sea comunicado por escrito el incumplimiento en que haya incurrido, para que en un término de diez días hábiles exponga lo que a su derecho convenga y aporte, en su caso, las pruebas que estime pertinentes; ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Transcurrido el término a que se refiere la fracción anterior, se resolverá considerando los argumentos y pruebas que hubiere  hecho valer, y ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "La determinación de dar o no por rescindido el contrato deberá ser debidamente fundada, motivada y comunicada al proveedor dentro de los diez días hábiles siguientes a lo señalado en la fracción I de este Capítulo.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Finalmente, la Secretaría de Salud y/o Servicios Estatales de Salud, (Departamento de Adquisiciones), podrá dar por terminado anticipadamente el pedido ó contrato de compraventa cuando concurran razones de interés general, o bien, cuando por causas justificadas se extinga la necesidad de requerir los artículos, bienes, servicios o arrendamiento originalmente contratados y se demuestre que de continuar con el cumplimiento de las obligaciones pactadas, se ocasionaría un daño o perjuicio al Estado.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "17.- De las infracciones y sanciones")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Los proveedores que infrinjan las disposiciones contenidas en la Ley Número 230 de Adquisiciones, Enajenaciones, Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero o las normas que con base en ella se dicten, serán sancionadas por la Secretaría de Contraloría y Transparencia Gubernamental, con domicilio en Boulevard René Juárez Cisneros Numero 62, Ciudad de los Servicios, Código Postal 39075, Chilpancingo de los Bravo, Estado de Guerrero, Teléfonos: (747) 4719740/4719741, con multas equivalentes a la cantidad de diez hasta mil días el salario mínimo general vigente en la Capital del Estado de Guerrero, al momento de hacerse efectiva la multa, misma que deberá enterarse en el Departamento de Adquisiciones. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Las sanciones administrativas se impondrán sin perjuicio de las penas que correspondan en caso de delito.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Las infracciones y sanciones administrativas en que incurran las personas físicas y morales que participen en el presente procedimiento administrativo derivadas de la infracción y omisión al cumplimiento a la Ley Número 230 de Adquisiciones, Enajenaciones, Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero, se aplicarán en términos de lo dispuesto por los artículos 131 y 133 párrafo primero de la Ley antes citada, por la instancia administrativa competente. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "18.- Penas convencionales")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Cuando el proveedor/prestador de servicios/arrendador, incurra en atraso en la entrega de los artículos, prestación de servicios o arrendamiento de bienes muebles e inmuebles que se le hayan adjudicado, la Secretaría de Salud y/o Servicios Estatales de Salud, le impondrá una pena convencional del 1% por cada día natural de atraso, sin rebasar el término de 10 días naturales o sin exceder el porcentaje del  10% para el cumplimiento del pedido ó contrato. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "19.-  Causales  para hacer efectivas las garantías ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "19.1 Se podrán hacer efectivas las garantías relativas al sostenimiento de las proposiciones en los siguientes casos: ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Cuando los proveedores retiren sus propuestas después del acto de presentación y apertura de proposiciones.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Cuando el proveedor ganador no firme el contrato en el plazo establecido o no entregue la garantía de cumplimiento de contrato correspondiente en la fecha convenida. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "19.2  Se podrán hacer efectivas las garantías relativas al cumplimiento del pedido ó contrato: ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Cuando hubiese transcurrido el tiempo convenido para la entrega de los artículos, bienes o servicios y estos no hayan sido recibido por la dependencia u organismo requirente.")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Por no cumplir los artículos, bienes o servicios con las condiciones técnicas establecidas en el anexo uno de las presentes bases y junta de aclaraciones. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Cuando hubiese transcurrido el plazo que se conceda a los proveedores para efectuar las reposiciones. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "20.- Verificación de la calidad. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Una vez asignado el pedido ó contrato, las unidades administrativas o Dependencias requirentes deberán verificar que la adquisición de bienes y/o contratación de servicios se lleve a cabo considerando los requerimientos técnicos solicitados, los tiempos de entrega solicitados, debiendo informar el Departamento Adquisiciones, quien podrá corroborar su respectivo avance del cumplimiento de las especificaciones técnicas cuando así lo considere necesario. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Dichos resultados proporcionarán los elementos para: ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Comprobar que el proveedor mantiene o mejora la calidad de los artículos, bienes, servicios o arrendamiento que se pretende contratar. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Recomendar la suspensión del pedido ó contrato y sustitución inmediata de los artículos, bienes, servicios o arrendamiento contratados por la Secretaría de Salud y/o Servicios Estatales de Salud, (Departamento de Adquisiciones), según la gravedad del incumplimiento, en cuyo caso se notificará por escrito al proveedor para que manifieste en el término de cinco días hábiles lo que a su derecho convenga. En el entendido de que hacer caso omiso o no satisfacer fehacientemente la irregularidad, dará motivo a la suspensión y en su caso a la rescisión del contrato en el término previsto en el título relativo a la rescisión del pedido ó contrato. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "21.-  Patentes, marcas y derechos de autor. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "El  proveedor al que se le adjudique el pedido ó contrato asumirá la responsabilidad total en caso de infringir marcas, derechos de patente, o viole registros de derechos de autor. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "22.- Medios de impugnación. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Las resoluciones que las autoridades dicten con fundamento en la Ley Número 230 de Adquisiciones, Enajenaciones, Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero y las disposiciones que de ella deriven podrán ser recurridas administrativamente por los afectados dentro del plazo de quince días hábiles siguientes a su notificación ante el superior jerárquico de quien las haya emitido. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Al interponer el recurso se deberá acreditar plenamente la personalidad del promovente, se podrá ofrecer toda clase de pruebas, con excepción de la confesional, siempre que tengan relación con los hechos que constituyan la motivación de la resolución recurrida y deberán acompañarse las documentales. Para el desahogo de las pruebas se señalará un plazo no menor de ocho días ni mayor de treinta días hábiles. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Quedará a cargo del recurrente la presentación de testigos, dictámenes y documentos, de no presentarlos dentro del término concedido se resolverá con los elementos de juicio que obren en el expediente. ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Anexos")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Los anexos elaborados y enviados por el área técnica que se agregan a estas bases y a los cuales los proveedores se deben sujetar, son los siguientes: ")
      ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = "Anexo uno. Especificaciones técnicas para la INVITACIÓN A CUANDO MENOS TRES PERSONAS PARA LA ADQUISICIÓN: "),
        texto(text = descrCompra.concepto + ": " + descrCompra.descripcion),
        texto(text = ", para: " + datosGrales.destino),
        texto(text = ", con recursos provenientes del programa: "),
        texto(text = datosGrales.programa + ": " + programa.descripcion ),
        texto(text = ", con cargo a las partidas: " + partDescripcion.partida + "; " + partDescripcion.descripcion),
        texto(text = ", ejercicio presupuestal: " + datosGrales.ejercicioPresupuestal)
    ), margin = Seq(0,2,0,2)),
      paragraph(text = Seq(
        texto(text = " ")
      ), margin = Seq(0,2,0,2))

    ).toJSArray
    ).toJSArray
  }


  //def bodyDocument(datosGrales: QryPedidoDatosGrales) = {
  def bodyDocument(datosGrales: Proceso) = {

      val partDescripcion = getPartidas
      val descripCompra = getDescripcionCompra
      val calendario = getCalendario
      val programa = getPrograma

      Seq(
      paragraph(text = Seq(texto(text = "BASES", alignment = "center", bold = true, fontSize = 15))),
      paragraph(text = Seq(texto(text = "Invitación a Cuando Menos Tres Personas", alignment = "center", bold = true, fontSize = 15)),
        margin = Seq(0,2,0,2)),

      paragraph(text = Seq(
          texto(text = "Los "),
          texto(text = "SERVICIOS ESTATALES DE SALUD, ", bold = true),
          //*****
          texto(text = "La Secretaria de Salud y/o Servicios Estatales de Salud, a través del Departamento de Adquisiciones, con fundamento en los artículos 134 "),
          texto(text = "de la Constitución Política de los Estados Unidos Mexicanos, 22 fracciones XXXIII y XXXVI de la Ley Orgánica de la Administración "),
          texto(text = "Pública del Estado de Guerrero número 433,  1, 7 y 32 fracción II de la Ley Número 230 de Adquisiciones, Enajenaciones, Arrendamientos, "),
          texto(text = "Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero; comunica a las empresas invitadas a "),
          texto(text = "participar en el presente procedimiento administrativo, que las bases y anexos técnicos relativos al procedimiento de: "),
          texto(text = " "),
          texto(text = " "),
          texto(text = " "),
          /*texto(text = "de la constitución Política de los Estados Unidos Mexicanos, en el artículo 4° de la Ley General de Salud; "),
          texto(text = "11, 18 apartado A, fracción IX y 28 de la Ley Orgánica de la Administración Pública del Estado de Guerrero "),
          texto(text = "Número 08; los artículos 1, 7 y 32 fracción II de la Ley Número 230 de Adquisiciones, Enajenaciones, "),
          texto(text = "Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero; "),
          texto(text = "comunica a las empresas invitadas a participar en el presente procedimiento administrativo, que las bases y "),
          texto(text = "anexos técnicos relativos al procedimiento de: "),*/
          texto(text = datosGrales.tipoCompra, bold = true),
          texto(text = "para la adquisición de: "),
          texto(text = descripCompra.concepto + ": " + descripCompra.descripcion.toUpperCase, bold = true),
          texto(text = ", destinados para: "),
          texto(text = datosGrales.destino, bold = true),
          texto(text = " con recurso proveniente de: "),
          texto(text = datosGrales.programa + ": " + programa.descripcion, bold = true),
          texto(text = ", de la(s) partida(s): "),
          texto(text = partDescripcion.partida + "; " + partDescripcion.descripcion.toUpperCase, bold = true),
          texto(text = ", del ejercicio presupuestal: "),
          texto(text = datosGrales.ejercicio.toString, bold = true),
          texto(text = "no tendrán ningún costo, por lo que se proporcionarán gratuitamente anexas al oficio de invitación respectivo o en su defecto estarán "),
          texto(text = "disponibles en el Departamento de adquisiciones, con domicilio en  Avenida Ruffo Figueroa # 6, Colonia Burócratas, Código Postal 39090, "),
          /*texto(text = " no tendrán ningún costo, por lo que se proporcionarán gratuitamente, las cuales deberán estar anexas al oficio de "),
          texto(text = "invitación respectivo o en su defecto estarán disponibles en el Subsecretaría de Administración y Finanzas, "),
          texto(text = "con domicilio en "),
          texto(text = "Av. Ruffo Figueroa No. 6, Col. Burocratas, Chilpancingo, Guerrero, C.P. 39090, Teléfono 747 472 7312 ext. 1325, ",
            bold = true),*/
          texto(text = "del "),
          texto(text = calendario.fecha_inicio_disponible_base.fecha.substring(0,2), bold = true),
          texto(text = " de ", bold = true),
          texto(text = NumToMonth(calendario.fecha_inicio_disponible_base.fecha.substring(3,5).toInt), bold = true),
          texto(text = " del ", bold = true),
          texto(text = calendario.fecha_inicio_disponible_base.fecha.substring(6,11), bold = true),
          texto(text = " al ", bold = true),
          texto(text = calendario.fecha_final_disponible_base.fecha.substring(0,2), bold = true),
          texto(text = " del ", bold = true),
          texto(text = NumToMonth(calendario.fecha_final_disponible_base.fecha.substring(3,5).toInt), bold = true),
          texto(text = " del "),
          texto(text = calendario.fecha_final_disponible_base.fecha.substring(6,11), bold = true),
          texto(text = " con un horario de las "),
          texto(text = " 09:00 a las 15:00 horas ", bold = true)
        )
      ),

      paragraph(text = Seq(texto(text = " "))),
      paragraph(text = Seq(
        texto(text = "Los participantes deberán presentar sus propuestas técnicas–económicas en un sobre cerrado dirigido a la Secretaría de Salud y/o "),
        texto(text = "Servicios Estatales de Salud del Estado de Guerrero y deberá cumplir con los requisitos y lineamientos que se describen en las "),
        texto(text = "siguientes: "),
        texto(text = " "))
      ),

      paragraph(text = Seq(texto(text = " "))),
      paragraph(text = Seq(texto(text = " BASES ", alignment = "center", bold = true))),
      paragraph(text = Seq(texto(text = " Apartado A ", alignment = "center", bold = true))),
      paragraph(text = Seq(texto(text = " Disposiciones específicas \n", alignment = "center", bold = true))),

      //****************************************

      disposicionesEspecificas(datosGrales, descripCompra, partDescripcion, calendario, programa),
      disposicionesGrales(descripCompra, datosGrales, partDescripcion, calendario, programa),

      //****************************************

      paragraph(text = Seq(
        texto(text = "Anexo Uno ", alignment = "center", bold = true, pageBreak = "before", fontSize = 20)
      ), pageBreak = "before", margin = Seq(0,2,0,2)),
      tableAnexoUnos,
      paragraph(text = Seq(
        texto(text = "Anexo Dos ", alignment = "center", bold = true, pageBreak = "before", fontSize = 20)
      ), pageBreak = "before", margin = Seq(0,2,0,2)),
      AnexoDos,
        paragraph(text = Seq(
          texto(text = "Anexo Tres ", alignment = "center", bold = true, pageBreak = "before", fontSize = 20)
        ), pageBreak = "before", margin = Seq(0,2,0,2)),
      AnexoTres
    )
  }

  override def imprimir(lst: Seq[Licitante]) = {
  //def imprimir(datosGrales: Proceso) = {

      val datosGrales = getProceso
      //val partDescripcion = getPartidas
      //val descripCompra = getDescripcionCompra
      //val calendario = getCalendario
      //val programa = getPrograma

    try {
          pdfMake.createPdf(
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
            content = bodyDocument(datosGrales)
            //content = Seq(paragraph(text = Seq(texto(text = "probar"))))

          )).open
        } catch {
          case e: Exception =>
            Callback(println("jcza, my print; exception caught: " + e))
        }
    }
}
