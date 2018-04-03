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

class rptContrato extends reports {

  val representante = proveedor.propietario.getOrElse("")
  val tipoPersona = proveedor.giro.getOrElse("")

  val calle = proveedor.calle.getOrElse("")
  val colonia = proveedor.colonia.getOrElse("")
  val cp = proveedor.cp.getOrElse("")
  val ciudad = proveedor.ciudad.getOrElse("")
  val telefonos =  proveedor.telefonos.getOrElse("")

  val firmaJefeOficina = oficina.firma.getOrElse("")

  val cargoOficina = oficina.cargo.getOrElse("")

  def bodyDocument(lst: Seq[Licitante]) = Seq(
    paragraph(text = Seq(texto(text = "\n\n"))),
    paragraph(text = Seq(
      texto(text = "Contrato Número SSA/SAF/SRM/DA/" + contrato.no_pedido + "/2017", bold = true),
      texto(text = ", para la Adquisición de: "),
      texto(text = descripcionCompra.concepto + "; " + descripcionCompra.descripcion, bold = true),
      texto(text = ", para "),
      texto(text = proceso.destino, bold = true),
      texto(text = ", que celebran por una parte La Secretaría de Salud del Estado de Guerrero yo/ Servicios Estatales de Salud, a quien se le denominará "),
      texto(text = "\"LA SECRETARÍA\" ", bold = true),
      texto(text = "representado en este caso por el LICENCIADO JUAN MANUEL SANTAMARÍA RAMÍREZ, Subsecretario de Administración y Finanzas, y por "),
      texto(text = "otra la empresa "),
      texto(text = proveedor.razon_social, bold = true),
      texto(text = ", a quien en lo sucesivo y para efectos de este contrato se denominará "),
      texto(text = "\"EL PROVEEDOR\", "),
      texto(text = "a traves de su propietario "),
      texto(text = representante, bold = true),
      texto(text = "mismas partes que aceptan de manera libre y voluntaria sujetarse al fiel y exacto cumplimiento de sus obligaciones "),
      texto(text = "contractuales al tenor de las declaraciones y clausulas siguientes:\n\n")
    )),
    paragraph(text = Seq(texto(text = "Declaraciones\n\n", bold = true, alignment = "center"))),
    paragraph(text = Seq(texto(text = "I.- Declara \"LA SECRETARIA\":", bold = true))),
    paragraph(text = Seq(
      texto(text = "I.1.- ", bold = true),
      texto(text = "Que la Secretaría de Salud Estatal y/o Servicios Estatales de Salud es el órgano rector del Sistema Estatal de Salud, cuyo "),
      texto(text = "objetivo es coordinar el sistema estatal de salud e impulsar integralmente los programas de salud en la entidad, tanto en materia "),
      texto(text = "de salud pública como de atención médica; promover de la interrelación sistemática de acciones que en la materia lleven a cabo "),
      texto(text = "la Federación  y el Estado, y ejercer facultades de autoridad sanitaria en su ámbito de competencia, conforme a lo dispuesto "),
      texto(text = "en los artículos 4° de la Ley General de Salud; 11, 18 apartado A, fracción IX y 28 de la Ley Organica de la Administración "),
      texto(text = "Pública del Estado de Guerrero Número 08.\n\n"))),
    paragraph(text = Seq(
      texto(text = "I.2.- ", bold = true),
      texto(text = "Que el "),
      texto(text = "C. Licenciado Juan Manuel Santamaŕia Ramírez,", bold = true),
      texto(text = "en su carácter de "),
      texto(text = "Subsecretario de Administración y Finanzas, ", bold = true),
      texto(text = "cargo que acredita con el nombramiento de fecha veintisiete del mes de octubre año dos mil quience, suscrito por el Licenciado "),
      texto(text = "Héctor Antonio Astudillo Flores, Gobernador Constitucional del Estado Libre y Soberano de Guerrero, por lo que en ejercicio de la "),
      texto(text = "atribución de facultades conferidas, de conformidad a lo dispuesto por los artículos 28, fracciones I, II, III, y XIV de la Ley "),
      texto(text = "Organica de la Administración Pública del Estado de Guerrero Número 08, así como los artículos 7°, fracción II y 11°, fracción "),
      texto(text = "I de la ley Número 1212 de Salud del Estado de Guerrero, cuenta con facultades Legales suficientes para suscribir el presente "),
      texto(text = "contrato a nombre de "),
      texto(text = "\"LA SECRETARÍA\" ", bold = true),
      texto(text = "en las operaciones de adquisiciones, contratación de servicios y/o enajenación de bienes muebles e inmuebles vigente en la materia.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "I.3.- ", bold = true),
      texto(text = "Que entre sus objetivos primordiales se encuentra el derecho a la protección de la salud, cuya finalidad es el bienestar físico "),
      texto(text = "y mental, para contribuir al ejercicio pleno de sus capacidades; la prolongación y el mejoramiento de la calidad de la vida humana; "),
      texto(text = "la proteccción y el acrecentamiento de los valores que coadyuven a la creación, conservación  y disfrute de condiciones de salud "),
      texto(text = "que contribuyan al desarrollo social; la extensión de actitudes solidarias y responsables de la población en la preservación, "),
      texto(text = "conservación, mejoramiento y restauración de la salud; el disfrute de servicios de salud y asistencia social que satisfagan "),
      texto(text = "eficaz y oportunamente las necesidades de la población; el conocimiento para el adecuado aprovechamiento y utilización de los "),
      texto(text = "servicios de salud, y el desarrollo de la enseñanza y la investigación cientifica y tecnológica para la salud. \n\n")
    )),
    paragraph(text = Seq(
      texto(text = "I.4.- ", bold = true),
      texto(text = "El presente contrato para la Adquisición de "),
      texto(text = descripcionCompra.concepto + "; " + descripcionCompra.descripcion),
      texto(text = ", para: "),
      texto(text = proceso.destino),
      texto(text = ", con cargo a las partidas: "),
      texto(text = partidaAnexo.partida + "; " + partidaAnexo.descripcion),
      texto(text = "Se adjudico a "),
      texto(text = "\"PROVEEDOR\" ", bold = true),
      texto(text = ", mediante el procedimiento de "),
      texto(text = proceso.tipoCompra),
      texto(text = ", cuyo fallo se emitio el día: "),
      texto(text = fechaLarga( calendario.fecha_fallo.fecha), bold = true),
      texto(text = ", Con fundamento en los artículos 134 de la Constitución Política de los Estados Unidos Mexicanos; Artículo 32 Fracción II de la "),
      texto(text = "Ley número 230 de Adquisiciones, Enajenaciones, Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e "),
      texto(text = "Inmuebles del Estado de Guerrero.\n\n"))),
    paragraph(text = Seq(
      texto(text = "I.5.- ", bold = true),
      texto(text = "Que "),
      texto(text = "\"LA SECRETARÍA\"", bold = true),
      texto(text = "para el cumplimiento de las obligaciones derivadas del "),
      texto(text = "del presente contrato, cuenta con los recursos presupuestales necesarios, se efectuarán con cargo al programa: ", bold = true),
      texto(text = programa.programa + "; " + programa.descripcion, bold = true),
      texto(text = ", ejercicio " + proceso.ejercicioPresupuestal.toString + " ,", bold = true),
      texto(text = "mediante memorándum con "),
      texto(text =  proceso.memo, bold = true),
      texto(text = ", de fecha: "),
      texto(text = "***fechaProceso***(fechaRecibido), jefe del departamento: "),
      texto(text = firmaJefeOficina, bold = true),
      texto(text = ", en su caracter de "),
      texto(text = cargoOficina, bold = true),
      texto(text = ", ratificando la suficiencia prespuestal con número: "),
      texto(text = oficioPresu.numero, bold = true),
      texto(text = ", con fecha: "),
      texto(text = fechaLarga(oficioPresu.fecha.fecha), bold = true),
      texto(text = ", por el "),
      texto(text = "LIC. JUAN SANTANA DÍAZ ", bold = true),
      texto(text = "Subdirector de Recursos Financieros.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "I.6.- ", bold = true),
      texto(text = "Que para lograr sus objetivos requiere de adquirir "),
      texto(text = descripcionCompra.concepto + "; " + descripcionCompra.descripcion + ", ", bold = true),
      texto(text = "de la Secretaría de Salud, que ofrece "),
      texto(text = "\"EL PROVEEDOR\", ", bold = true),
      texto(text = "por lo que se considera necesario celebrar el presente contrato para con ello asegurar el bienestar de los Usuarios a través "),
      texto(text = "de los servicios médicos que brinda la Secretaría de Salud y/o Servicios Estatales de Salud del Estado de Guerrero.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "I.7.- ", bold = true),
      texto(text = "Que la clave de su Registro Federal de Contribuyentes es: "),
      texto(text = "SES870401TX8, SERVICIOS ESTATALES DE SALUD; ", bold = true),
      texto(text = "y señala como domicilio fiscal para efectos de este contrato el ubicado en Avenida Ruffo Figueroa número Seis, Colonia Burócratas "),
      texto(text = ", Código Postal 39090, Chilpalcingo de los Bravo, Guerrero, para todos los fines y efectos legales y administrativos que procedan "),
      texto(text = "conforme a derecho.\n\n")
    )),
    paragraph(text = Seq(texto(text = "II.- DECLARA \"EL PROVEEDOR\", BAJO FORMAL PROTESTA DE DECIR VERDAD: ", bold = true))),
    paragraph(text = Seq(
      texto(text = "II.1.- ", bold = true),
      texto(text = "Que es persona "),
      texto(text = tipoPersona, bold = true),
      texto(text = if (tipoPersona == "MORAL") " constituida " else  "", bold = true),
      texto(text = "según consta en el "),
      texto(text = (if (tipoPersona == "MORAL") "acta constitutiva " else  " acta de nacimiento presentada ") + ",", bold = true),
      texto(text = "Que cuenta con las facultades suficientes para la celebración del presente Contrato, y que manifiesta bajo protesta de decir verdad, "),
      texto(text = "que personalmente se encarga de los procesos de venta. Qué está en posibilidad de proporcionar a \"LA SECRETARÍA\", de "),
      texto(text = descripcionCompra.concepto + "; " + descripcionCompra.descripcion, bold = true),
      texto(text = ", para: "),
      texto(text = proceso.destino),
      texto(text = "\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "II.2.- ", bold = true),
      texto(text = "Que cuentan con la capacidad legal para contratar y obligarse en términos de los ordenamientos civiles y mercantiles, a través "),
      texto(text = "de Instrumentos como el que hoy suscribe, además de contar con los recursos materiales, técnicos, humanos y experiencias "),
      texto(text = "necesarias para prestar los servicios objeto de este instrumento en el tiempo de respuesta requerido.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = " II.3.- ", bold = true),
      texto(text = "Señalan como su domicilio fiscal para todos los efectos legales derivados de este contrato el ubicado en "),
      texto(text = "Calle: " + calle + ", ", bold = true),
      texto(text = "Colonia: " + colonia + ", ", bold = true),
      texto(text = "Codigo Postal: " + cp + ", ", bold = true),
      texto(text = "Ciudad: " + ciudad + ", ", bold = true),
      texto(text = "telefono: " + telefonos + ", ", bold = true)
    )),
    paragraph(text = Seq(
      texto(text = "II.4.- ", bold = true),
      texto(text = "Que bajo protesta de decir verdad, declaran que conoce y se ajustan al contenido y los requisitos ley número 230 de Adquisiciones "),
      texto(text = ", Enajenaciones, Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero, "),
      texto(text = "así como a las Disposiciones Legales y Administrativas expedidas en esta materia. Por lo que no tienen impedimentolegal alguno "),
      texto(text = "para celebrar el presente contrato, lo que demostrarán en su oportunidad, cuando "),
      texto(text = "\"LA SECRETARÍA\", ", bold = true),
      texto(text = "así lo requiera.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "II.5.- Que conoce a la perfección el objeto de este contrato, en virtud de estar enterados de las necesidades de "),
      texto(text = "\"LA SECRETARIA\" y que por ello se encuentra en condiciones de satisfacerlas oportunamente al contar con los "),
      texto(text = "recursos Humanos, Técnicos, Financieros y Económicos, propios y necesarios para proveer los servicios objeto "),
      texto(text = "del presente. Es por lo que en consecuencia a Invitación de \"LA SECRETARIA\", a través de la Subdirección de "),
      texto(text = "Recursos Materiales dependiente de la Subsecretaría de Administración y Finanzas, presentó a su consideración "),
      texto(text = "la Propuesta Técnica y Económica en papel membretado del establecimiento de la empresa "),
      texto(text = proveedor.razon_social, bold = true),
      texto(text = "les fue adjudicada la Contratación para la Adquisición de "),
      texto(text = descripcionCompra.concepto + "; " + descripcionCompra.descripcion),
      texto(text = "para: "),
      texto(text =  proceso.destino, bold = true),
      texto(text = ", que se relacionan en el Anexo del presente contrato, "),
      texto(text = "por ofrecer las mejores condiciones para \"LA SECRETARIA\".\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "II.6.- Ante la solicitud de \"LA SECRETARIA\", bajo protesta de decir verdad manifiestan que su representada no se "),
      texto(text = "encuentra en alguno de los supuestos previstos en el artículo 50 y 60 de la Ley de Adquisiciones, Arrendamientos y "),
      texto(text = "Servicios del Sector Público, ni tampoco en la hipótesis a que se refiere el artículo 8, fracción XX de la Ley "),
      texto(text = "Federal de Responsabilidades Administrativas de los Servidores Públicos, publicada en el diario oficial de la "),
      texto(text = "federación, el 13 de marzo de 2002 y que reúnen los requisitos a que se refiere laLey número 230 de Adquisiciones, "),
      texto(text = "Enajenaciones, Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de "),
      texto(text = "Guerreroy de conformidad a lo establecida en la Ley de Adquisiciones, Arrendamientos y Servicios del Sector Público "),
      texto(text = "para proveer los bienes y/o servicios que se describen en el mismo.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "II.7.- Asimismo, manifiestan bajo protesta de decir verdad que se encuentra al corriente del pago de sus obligaciones "),
      texto(text = "fiscales y tributarias en términos de lo dispuesto por el artículo 32-d del Código Fiscal de la Federación y demás "),
      texto(text = "ordenamientos legales que le sean aplicables, por lo que no tiene impedimento legal alguno para celebrar el presente "),
      texto(text = "contrato, lo que demostrará en su oportunidad, cuando \"LA SECRETARIA\" así lo requiera. ")
    )),
    paragraph(text = Seq(
      texto(text = "II.8.- Que conocen y aceptan observar la normatividad que le es aplicable, así como la descripción en forma detallada "),
      texto(text = "de los servicios que requiere \"LA SECRETARIA\".\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "III.- PARA AMBAS PARTES\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "III.1 .- Que se reconocen la capacidad y personalidad jurídica con que se ostentan y con las cuales celebran este "),
      texto(text = "contrato, manifestando que en la celebración del mismo, no existe error, dolo, mala fe, ni cualquier otro vicio que "),
      texto(text = "afecte el consentimiento, ni el conocimiento con que se celebra.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "III.2.- Que reconocen que con la celebración del presente Contrato para la Adquisición de "),
      texto(text = descripcionCompra.concepto + "; " + descripcionCompra.descripcion),
      texto(text = " de la Secretaria de Salud, se formaliza mediante el procedimiento de Invitación a Cuando Menos Tres Personas en los "),
      texto(text = "términos que se precisan en las cláusulas siguientes:\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "EXPUESTO LO ANTERIOR, AMBAS PARTES ESTAN DE ACUERDO EN EXTERNAR SU INTERES PARA OBLIGARSE AL TENOR DE LAS SIGUIENTES:\n\n ")
    )),
    paragraph(text = Seq(texto(text = "CLÁUSULAS\n\n"))),
    paragraph(text = Seq(texto(text = "PRIMERA.- DEL OBJETO DEL CONTRATO.\n\n"))),
    paragraph(text = Seq(
      texto(text = "1.1.- \"EL PROVEEDOR\", se obliga a suministrar a \"LA SECRETARIA\"," ),
      texto(text = descripcionCompra.concepto + "; " + descripcionCompra.descripcion, bold = true),
      texto(text = " para: "),
      texto(text = proceso.destino, bold = true),
      texto(text = ", conforme a los términos, condiciones "),
      texto(text = "y características, que se establecen en el presente contrato número "),
      texto(text = contrato.no_pedido, bold = true),
      texto(text = " fechados con el día: "),
      texto(text = fechaLarga(contrato.fecha_pedido.fecha), bold = true),
        //  "0101 fechados con el día 06 de Junio de "),
      texto(text = ", en las manifestaciones y documentos relacionados en el anexo técnico, desprendidos de la propuesta técnica y "),
      texto(text = "económica, que le presento \"EL PROVEEDOR\" a \"LA SECRETARIA\" y en los documentos que se les vinculen; por lo que en "),
      texto(text = "esas condiciones, los documentos que se citan se tienen aquí por reproducidos como si a la letra se insertaran y al "),
      texto(text = "afecto se integran a LOS ANEXOS DEL CONTRATO.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "1.2.- \"EL PROVEEDOR\", para el desarrollo de la entrega del Contrato para la Adquisición de REFACCIONES Y ACCESORIOS "),
      texto(text = "MENORES DE MAQUINARIA Y OTROS EQUIPOS para la DIRECCIÓN DE EPIDEMIOLOGIA Y MEDICINA PREVENTIVA (DENGUE), se obliga "),
      texto(text = "bajo los principios de los lineamientos legales aplicables en la materia, así como mantener en óptimas condiciones "),
      texto(text = "profesionales su uso para \"LA SECRETARIA\".\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "1.3.- Durante la vigencia del contrato y en caso de que \"LA SECRETARIA\", no acepte los insumos por no estar acordes "),
      texto(text = "\"EL PROVEEDOR\" estará obligado a corregirlos; hará las adecuaciones o repondrá los insumos de las especificaciones "),
      texto(text = "originalmente convenidas; sin que la sustitución implique modificaciones al objeto de este instrumento. En su caso, "),
      texto(text = "\"LA SECRETARIA\" podrá rescindir el contrato sin responsabilidad alguna, actualizándose la hipótesis prevista en la "),
      texto(text = "CLÁUSULA 15 (DÉCIMA QUINTA) del presente contrato.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "1.4.- \"EL PROVEEDOR\" se obliga ante \"LA SECRETARIA\" a responder de cualquier responsabilidad en el término de la "),
      texto(text = "vigencia de la garantía, por lasmedicinas y productos farmacéuticos y que no se ajusten a las especificaciones "),
      texto(text = "solicitadas.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "1.5.- Los insumos que \"EL PROVEEDOR\", se obliga a entregar a \"LA SECRETARIA\" se describen en el anexo del contrato.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Para la Adquisición de REFACCIONES Y ACCESORIOS MENORES DE MAQUINARIA Y OTROS EQUIPOS para la DIRECCIÓN DE "),
      texto(text = "EPIDEMIOLOGIA Y MEDICINA PREVENTIVA (DENGUE) de la Secretaria de Salud, los deberá realizar y ejecutar "),
      texto(text = "\"EL PROVEEDOR\" de conformidad con el presente contrato y a satisfacción de \"LA SECRETARIA\".\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "SEGUNDA.- VIGENCIA PARA LA ENTREGA DE LOS BIENES\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "2.1.- \"EL PROVEEDOR\", se obliga a realizar en términos de la adjudicación directa para la Adquisición de "),
      texto(text = "REFACCIONES Y ACCESORIOS MENORES DE MAQUINARIA Y OTROS EQUIPOS para la DIRECCIÓN DE EPIDEMIOLOGIA Y "),
      texto(text = "MEDICINA PREVENTIVA (DENGUE) de la Secretaria de Salud, las partidas objeto de este contrato, dentro de "),
      texto(text = "los 45 días hábiles contados a partir de la firma del contrato.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "2.2.- Las partes se obligan a que la vigencia de este contrato se prorrogará automáticamente por el tiempo "),
      texto(text = "que se requiera en el caso de que existiera alguna reclamación en contra de “EL PROVEEDOR” por parte de "),
      texto(text = "la \"LA SECRETARIA\" hasta que la reclamación mencionada quede resuelta, por lo que ella no implicará costo "),
      texto(text = "adicional alguno, ni ninguna responsabilidad para \"LA SECRETARIA\".\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "TERCERA.- DEL MONTO.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "3.1.- El importe que \"LA SECRETARÍA\" cubrirá a \"EL PROVEEDOR\" Objeto de este contrato y durante su vigencia, "),
      texto(text = "será la cantidad de $ 600,000.00 (SEISCIENTOS MIL  PESOS 00/100 MONEDA NACIONAL) con IVA incluido. Este mismo "),
      texto(text = "importe ampara el contrato y pedido con número 0101, fechado el 06 de Junio de 2017, del presente instrumento. "),
      texto(text = "Desprendido de la propuesta económica, que le presento \"EL PROVEEDOR\" a \"LA SECRETARIA\".\n\n ")
    )),
    paragraph(text = Seq(
      texto(text = "En consecuencia, \"EL PROVEEDOR\" no podrá exigir mayor retribución que la antes establecida, bajo ninguna "),
  texto(text = " circunstancia; por los servicios que preste en cumplimiento del presente contrato.\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "3.2.-Las partes convienen a dar cumplimiento a las obligaciones Fiscales y Tributarias que les correspondan y "),
    texto(text = "que se encuentren en vigor a la fecha de su exigibilidad.\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "CUARTA.- DE LA FORMA DE PAGO.\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "4.1.- \"LA SECRETARIA\" y \"EL PROVEEDOR\" convienen que la facturación sea a favor de los SERVICIOS ESTATALES "),
    texto(text = "DE SALUD, y que la clave de su Registro Federal de Contribuyentes es: SES-870401TX8, y señala como domicilio "),
    texto(text = "fiscal para efectos de este contrato el de Avenida Ruffo Figueroa número Seis, Colonia Burócratas, Código "),
    texto(text = "Postal 39090, Chilpancingo de los Bravo, Guerrero. Debiendo presentar \"EL PROVEEDOR\" dicha facturación ante "),
    texto(text = "la misma dependencia para su validación.\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "4.2.- \"LA SECRETARIA\" pagará a \"EL PROVEEDOR\" el importe estipulado en la cláusula anterior, el costo de los "),
    texto(text = "bienes en una sola exhibición, dentro de los 20 días hábiles posteriores a la presentación de la(s) factura(s) "),
    texto(text = "correspondientes.\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "Si las facturas que se refiere esta cláusula, no se ajustan a los antes mencionado, no serán recibidas por "),
    texto(text = "\"LA SECRETARIA\" y ello no implicara responsabilidad alguna de su parte.\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "QUINTA.- LUGAR DE ENTREGA\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "5.1.- \"EL PROVEEDOR\" se obliga a entregar los bienes objeto de este contrato a \"LA SECRETARIA\", consistente "),
    texto(text = "en la PARTIDA ESPECÍFICA 29801 (REFACCIONES Y ACCESORIOS MENORES DE MAQUINARIA Y OTROS EQUIPOS), dentro de "),
    texto(text = "los 45 días hábiles contados a partir de la firma del presente contrato en el Almacén Central de la "),
    texto(text = "Dependencia, ubicado en calle Prosperidad s/n Colonia Universal, C.P. 39060, Chilpancingo, Guerrero, "),
    texto(text = "tel. (01-747) 472 33 77, o en su caso en el destino de la compra el cual vendrá especificado en el pedido, "),
    texto(text = "en horario de 9:00 a 14:00 de lunes a viernes. Etiquetado en Caja Colectiva.\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "5.2.- Asimismo, \"EL PROVEEDOR\", cubrirá todo tipo de cuotas compensatorias y demás impuestos que genere, "),
    texto(text = "la fabricación, traslado, así como el embalaje de los materiales y accesorios que componen los insumos "),
    texto(text = "objeto de este contrato, además de las contribuciones impositivas del lugar de origen de dichos materiales, "),
    texto(text = "y en ningún caso podrá aumentarse el precio pactado.\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "5.3.- Por lo anterior, quedará bajo la responsabilidad de \"EL PROVEEDOR\" hacer la entrega oportuna a "),
    texto(text = "\"LA SECRETARÍA\", en el lugar y plazo señalado,\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "SEXTA.- GARANTIA DE CALIDAD DE LOS BIENES.\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "6.1.- \"EL PROVEEDOR\" garantiza que los bienes, suministrados a \"LA SECRETARIA\" al tenor de este contrato, "),
    texto(text = "cumplen rigurosamente los estándares de calidad ofrecidos en su propuesta técnica, misma que también se "),
    texto(text = "considera parte integrante del presente Instrumento, para todos los efectos legales y administrativos que "),
    texto(text = "conforme a derecho procedan.\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "6.2.- “EL PROVEEDOR” responderá de los defectos de fábrica, defectos de la mano de obra o en los materiales "),
    texto(text = "empleados en la fabricación, así como de cualquiera otra responsabilidad en que hubiere incurrido. ")
  )),
  paragraph(text = Seq(
    texto(text = "“EL PROVEEDOR” deberá apegarse y cumplir con las especificaciones técnicas requeridas por  “LA SECRETARIA” "),
    texto(text = "de acuerdo a los anexos del contrato. ")
  )),
  paragraph(text = Seq(
    texto(text = "SEPTIMA.- GARANTÍA DE CUMPLIMIENTO DE CONTRATO.\n\n")
  )),
  paragraph(text = Seq(
    texto(text = "7.1.- Con fundamento a lo establecido en el Artículo 68 Fracción IV de la Ley número 230 de Adquisiciones, "),
    texto(text = "Enajenaciones, Arrendamientos, Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del "),
    texto(text = "Estado de Guerrero y numeral 12 apartado 12.2 del Manual de Políticas, Bases y Lineamientos en Materia de "),
    texto(text = "Adquisiciones, Arrendamientos de Bienes Muebles y Contratación de Servicios del Estado de Guerrero, así "),
    texto(text = "como lo estipulado por la Ley Federal de Instituciones de Fianzas y su Reglamento; \"EL PROVEEDOR\" garantizará "),
    texto(text = "el cumplimiento del presente contrato con una fianza, equivalente al 10% del importe total, sin considerar el "),
    texto(text = "Impuesto al Valor Agregado, establecido en la CLÁUSULA TERCERA, a favor de LOS SERVICIOS ESTATALES DE SALUD, "),
    texto(text = "debiendo ser entregada la garantía referida, en un plazo no mayor de 10 (diez) días naturales posteriores a "),
    texto(text = "la firma del presente documento legal, obligándose \"EL PROVEEDOR\" a mantener vigente la fianza, hasta que "),
      texto(text = "\"LA SECRETARÍA\" otorgué el finiquito del contrato por cumplimiento a todas sus obligaciones. ")
    )),
    paragraph(text = Seq(
      texto(text = "Fianzas en la materia establece, lo siguiente:\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "La fianza se otorga para garantizar todas y cada una de las obligaciones establecidas a cargo de \"EL PROVEEDOR\" "),
      texto(text = "en el presente contrato, el cual fue adjudicado mediante el procedimiento de INVITACION A CUANDO MENOS TRES PERSONAS.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "La afianzadora y \"EL PROVEEDOR\", están de acuerdo en que esta fianza continuara vigente aun cuando se le "),
      texto(text = "otorguen a éste último, prórrogas o esperas para el cumplimiento de las obligaciones que se afianzan.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "La presente fianza permanecerá en vigor desde la fecha de expedición y hasta el cumplimiento de todas y "),
      texto(text = "cada una de las obligaciones estipuladas a cargo de “EL PROVEEDOR” en el presente contrato, o en su caso, "),
      texto(text = "durante la substanciación de todos los recursos legales o juicios que se interpongan, hasta que se dicte la "),
      texto(text = "resolución definitiva por autoridad competente.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Para la cancelación de la fianza, será requisito indispensable la conformidad expresa y por escrito de "),
      texto(text = "\"LA SECRETARÍA\", quien podrá expedir dicha conformidad una vez que se hayan cumplido a su satisfacción "),
      texto(text = "las obligaciones pactadas a cargo de \"EL PROVEEDOR\", en el presente contrato.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "OCTAVA.- PENA CONVENCIONAL.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "8.1.- \"EL PROVEEDOR\" será el único responsable y relevara  a \"LA SECRETARÍA\" respecto de cualquier "),
      texto(text = "tipo de reclamación o demanda que se entable en su contra por cuestiones que versen sobre derechos de "),
      texto(text = "patente, invención o cualquiera otra prerrogativa de carácter exclusivo que reclame otra persona sobre los bienes o "),
      texto(text = "servicios materia del presente instrumento contractual.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "NOVENA.- RESPONSABILIDADES LABORALES.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "9.1.- \"EL PROVEEDOR\" se obliga a asumir todas las obligaciones derivadas de las disposiciones laborales y de "),
      texto(text = "seguridad social, así como a responder de cualquier controversia o litigio que en lo sucesivo sus trabajadores "),
      texto(text = "o empleados, sin importar la naturaleza de la relación laboral (de planta, de confianza, eventual, por tiempo "),
      texto(text = "determinado, por obra determinada etc.): presenten en su contra o en contra de \"LA SECRETARÍA\" y en caso de que "),
      texto(text = "por laudo ejecutoriado, esta fuera condenada a pagar o indemnizar a alguien, \"EL PROVEEDOR\" dentro de los tres "),
      texto(text = "días hábiles siguiente a la fecha  en que \"LA SECRETARÍA\", le haga de su conocimiento el requerimiento de que haya "),
      texto(text = "sido objeto de la ejecución, se obliga con ésta a proporcionarle los recursos líquidos necesarios para cumplimentar "),
      texto(text = "el laudo relativo.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "DÉCIMA.- GARANTÍA EN MATERIA DE PROPIEDAD INTELECTUAL.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "10.1.- \"EL PROVEEDOR\", será el único responsable y relevará a \"LA SECRETARIA\", respecto de cualquier tipo de "),
      texto(text = "reclamación o demanda que se entable en su contra por cuestiones que versen sobre derechos de patente, "),
      texto(text = "invención o cualquier otra prerrogativa de carácter exclusivo que pretenda persona sobre \"LA SECRETARIA\".\n\n ")
    )),
    paragraph(text = Seq(texto(text = "DÉCIMA PRIMERA.- RESPONSABILIDADES LABORALES.\n\n"))),
    paragraph(text = Seq(
      texto(text = "11.1.- \"EL PROVEEDOR\" se obliga a asumir todas las obligaciones derivadas de las disposiciones laborales y "),
      texto(text = "de seguridad social, así como a responder de cualquier controversia o litigio que en lo sucesivo sus trabajadores "),
      texto(text = "o empleados, sin importar la naturaleza de la relación laboral (trabajadores de confianza, eventual, por "),
      texto(text = "absorberá en cualquier caso las responsabilidades que se deriven de las relaciones de trabajo, de conformidad "),
      texto(text = "con lo dispuesto por la Ley Federal del Trabajo, con motivo de la Contratación para la Adquisición de REFACCIONES "),
      texto(text = "Y ACCESORIOS MENORES DE MAQUINARIA Y OTROS EQUIPOS para la DIRECCIÓN DE EPIDEMIOLOGIA Y MEDICINA PREVENTIVA "),
      texto(text = "(DENGUE) de la Secretaria de Salud y/o Servicios Estatales de Salud, objeto del presente contrato.\n\n")
    )),
    paragraph(text = Seq(texto(text = "DÉCIMA SEGUNDA.- MODIFICACIONES AL CONTRATO.\n\n"))),
    paragraph(text = Seq(
      texto(text = "12.1.- Con fundamento en el Artículo 65 de la Ley número 230 de Adquisiciones, Enajenaciones, Arrendamientos, "),
      texto(text = "Prestación de Servicios y Administración de Bienes Muebles e Inmuebles del Estado de Guerrero; y numeral 15 "),
      texto(text = "apartados 15.2 y 15.3 del Manual de Políticas, Bases y Lineamientos en Materia de Adquisiciones, Arrendamientos "),
      texto(text = "de Bienes Muebles y Contratación de Servicios del Estado de Guerrero, “LA SECRETARÍA” podrá, dentro de su "),
      texto(text = "presupuesto aprobado y disponible, bajo su responsabilidad y por razones fundadas y explícitas, acordar el "),
      texto(text = "incremento del monto del contrato vigente, siempre que las modificaciones no rebasen en conjunto, el treinta "),
      texto(text = "por ciento del monto o cantidad de los conceptos o volúmenes establecidos originalmente en los mismos y el "),
      texto(text = "precio de los bienes sea igual al pactado originalmente.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "12.2.- Cualquier modificación al contrato deberá formalizarse por escrito por parte de \"LA SECRETARÍA\". Los "),
      texto(text = "instrumentos legales respectivos serán suscritos por el servidor público que lo haya hecho en el contrato o "),
      texto(text = "quien lo sustituya o esté facultado para ello.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "12.3.- \"LA SECRETARÍA\" se abstendrá de hacer modificaciones que se refieran a precios, anticipos, pagos "),
      texto(text = "progresivos, especificaciones y, en general, cualquier cambio que implique otorgar condiciones más ventajosas "),
      texto(text = "a \"EL PROVEEDOR\" comparadas con las establecidas originalmente.\n\n")
    )),
    paragraph(text = Seq(texto(text = "DÉCIMA TERCERA.- CESIÓN DE DERECHOS Y OBLIGACIONES.\n\n"))),
    paragraph(text = Seq(
      texto(text = "13.1.- \"EL PROVEEDOR\" se obliga a no ceder o transferir de manera alguna, a terceras personas, físicas o morales, "),
      texto(text = "sus derechos y obligaciones que le corresponden en virtud de este contrato. La transmisión por cualquier acto "),
      texto(text = "jurídico, de los derechos de cobro por la Adquisición de REFACCIONES Y ACCESORIOS MENORES DE MAQUINARIA Y OTROS "),
      texto(text = "EQUIPOS para la DIRECCIÓN DE EPIDEMIOLOGIA Y MEDICINA PREVENTIVA (DENGUE), objeto de este contrato, requerirá "),
      texto(text = "de previa autorización y con el consentimiento por escrito de \"LA SECRETARIA\". ")
    )),
    paragraph(text = Seq(texto(text = "DÉCIMA CUARTA.- PENAS CONVENCIONALES.\n\n"))),
    paragraph(text = Seq(
      texto(text = "14.1.- Durante la vigencia del contrato, “LA SECRETARÍA”  en caso de incumplimiento de “EL PROVEEDOR” de las "),
      texto(text = "obligaciones que contrae en virtud de este contrato, tendrá derecho de imponerle penas convencionales, o podrá "),
      texto(text = "optar entre promover su recisión o exigir el cumplimiento del mismo, sin necesidad de intervención judicial "),
      texto(text = "al efecto.\n\n")
    )),
    paragraph(text = Seq(texto(text = "Las penas convencionales se aplicaran por \"LA SECRETARÍA\":\n\n"))),
    paragraph(text = Seq(
      texto(text = "1.- Cuando “EL PROVEEDOR” incurra en retraso en el cumplimiento oportuno de sus obligaciones o de su indebido cumplimiento.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "2.- Cuando no correspondan los insumos a la calidad, contenidos y especificaciones técnicas requerida o por no "),
      texto(text = "ajustarse a los requerimientos establecidos en los demás documentos fuente del contrato y este mismo le señalen.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "14.2.- Para el caso de la pena convencional por retraso en la prestación de los servicios, se fija una pena "),
      texto(text = "convencional del 1% por día de retraso y hasta por el 10% del monto  total de los servicios no prestados.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Las penas convencionales en ningún caso podrán ser superiores, en su conjunto, al monto de la garantía de "),
      texto(text = "cumplimiento del contrato.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Dichas penas se aplicarán con base en la parte proporcional en función de los bienes entregados, en la que "),
      texto(text = "se haya incurrido en algún incumplimiento.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "14.3.- La aplicación de las penas convencionales no son excluyentes una de la otra, esto es, pueden aplicarse "),
      texto(text = "simultáneamente y no excederá en su conjunto al monto de la fianza de garantía de cumplimiento por lo tanto.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "14.4.- Para efectos de lo anterior, el área usuaria informara con la debida oportunidad dentro de los siguientes "),
      texto(text = "tres días hábiles al vencimiento de la entrega de los insumos objeto de este contrato, a través de formatos o "),
      texto(text = "registro en el que se establezca los periodos en los cuales se ha cumplido con las obligaciones contraídas por "),
      texto(text = "\"EL PROVEEDOR\" así como las que no llegare a cumplir, bitácora o registro que deberá ser firmada por el "),
      texto(text = "responsable de almacén en que debe entregarse los insumos, en el supuesto caso a detectar un incumplimiento "),
      texto(text = "durante el periodo de entrega de insumos se dará inicio al procedimiento de penalización y responsabilidades "),
      texto(text = "que establece la ley de la materia.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "El procedimiento para la aplicación de las penas convencionales será el siguiente:\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "14.5.- En caso de que \"EL PROVEEDOR\" incurriera en retraso o no prestara de la manera convenida las obligaciones "),
      texto(text = "establecidas en el presente contrato, \"LA SECRETARÍA\" por parte de enlace designado en este contrato por al área "),
      texto(text = "usuaria; le girara oficio con efectos de notificación, informándole en que consiste el retraso o el indebido "),
      texto(text = "cumplimiento que lo motiva, así como lo requiera que de inmediato cumplan con sus obligaciones en términos de "),
      texto(text = "este contrato, si ha incurrido en retraso, o bien para que subsane su incumplimiento, señalo detalladamente este "),
      texto(text = "e indicando la fecha en que se inició el incumplimiento y por lo cual se hará acreedor a la aplicación de la pena "),
      texto(text = "convencional pactada.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "14.6.- Dentro de 24 horas siguiente a que \"EL PROVEEDOR\" reciba el citado oficio de notificación, deberá corregir "),
      texto(text = "su retraso o a dar debido cumplimiento a las obligaciones reclamadas, o bien si considera que no ha incurrido en "),
      texto(text = "el incumplimiento que se le imputa, manifestara por escrito lo que a su derecho convenga, aportando las pruebas "),
      texto(text = "relativas  y solicitando día y hora para la celebración de una junta de conciliación o aclaración.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "14.7.- En caso de que \"EL PROVEEDOR\" no desvirtué el incumplimiento de las obligaciones señaladas en el oficio "),
      texto(text = "de notificación y requerimiento que se le hizo llegar, dentro de un plazo de 72 horas a partir de que se haya "),
      texto(text = "efectuado la junta a que se refiere el párrafo anterior, \"LA SECRETARÍA\" aplicara la pena convencional "),
      texto(text = "correspondiente.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "14.8.- Ninguna actuación podrá ser invalidada, si a \"EL PROVEEDOR\" se le concedió su derecho de audiencia "),
      texto(text = "cuando fuese oportuna y legalmente citado a comparecer al acta de que se trate; y sin causa justificada no "),
      texto(text = "hubiera comparecido a la respectiva audiencia, a expresar lo que a su derecho convenga.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "14.9.- La pena convencional se continuara causando y procederá su aplicación, hasta en tanto \"EL PROVEEDOR\" "),
      texto(text = "no haya cumplido fielmente con sus obligaciones; por lo que en caso de que el monto de la pena convencional "),
      texto(text = "se agotara hasta el máximo señalado, \"LA SECRETARÍA\" podrá ejercer el derecho para rescindir "),
      texto(text = "administrativamente este contrato.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "14.10.- La aplicación de las penas convencionales que aquí las partes convienen es independiente del derecho "),
      texto(text = "de \"LA SECRETARÍA\" de solicitar se haga exigible la fianza otorgada a \"EL PROVEEDOR\" en garantía de "),
      texto(text = "cumplimiento de este contrato, por lo que después de agotado el monto de la fianza se rescindirá el "),
      texto(text = "contrato sin responsabilidad alguna para \"LA SECRETARÍA\", en el entendido de que en el supuesto de que "),
      texto(text = "sea rescindido el contrato, no procederá el cobro de dichas penalizaciones ni la contabilización de las "),
      texto(text = "mismas para hacer efectiva la garantía de cumplimiento.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "14.11.- El anterior procedimiento convencional no requerirá de previa reclamación judicial alguna, para "),
      texto(text = "obtener el pago de las penas convencionales aplicables a “EL PROVEEDOR” y en que llegare a incurrir "),
      texto(text = "por el incumplimiento de las obligaciones a su cargo.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "14.12.- En caso de que \"EL PROVEEDOR\" no realice el pago del importe de la pena convencional, dentro de termino que "),
      texto(text = "al efecto se le conceda, \"EL PROVEEDOR\" pagara a \"LA SECRETARÍA\" intereses como si se tratara del supuesto de "),
      texto(text = "prórrogas para el pago de créditos fiscales. Dichos intereses se calcularán sobre las cantidades no pagadas y se "),
      texto(text = "computarán por días naturales desde que venció el plazo determinado y hasta la fecha que las cantidades se pongan "),
      texto(text = "efectivamente a disposición de \"LA SECRETARÍA\".\n\n")
    )),
    paragraph(text = Seq(texto(text = "DÉCIMA QUINTA. - TERMINACIÓN ANTICIPADA.\n\n"))),
    paragraph(text = Seq(
      texto(text = "15.1.- \"LA SECRETARIA\" se reserva el derecho de dar por terminado anticipadamente el presente contrato "),
      texto(text = "para la Adquisición de REFACCIONES Y ACCESORIOS MENORES DE MAQUINARIA Y OTROS EQUIPOS para la DIRECCIÓN "),
      texto(text = "DE EPIDEMIOLOGIA Y MEDICINA PREVENTIVA (DENGUE) de la Secretaria de Salud, sin responsabilidad para "),
      texto(text = "\"LA SECRETARIA\" él, cuando concurran las razones siguientes:\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Cuando no se tuviera disponibilidad presupuestal o los recursos presupuestales fueran insuficientes "),
      texto(text = "o cancelados.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Variación en la asignación presupuestal por la conformación y monto del presupuesto asignado.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Por causas justificadas que obliguen a extinguir la necesidad de requerir los servicios originalmente "),
      texto(text = "contratados y que se demuestre que de continuar con el cumplimiento de las obligaciones pactadas, se "),
      texto(text = "ocasionaría algún daño o perjuicio al Estado y/o Institución.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Ó cuando se determine la nulidad total o parcial de los actos que dieron origen al contrato, con motivo "),
      texto(text = "de la resolución de una inconformidad emitida por órgano administrativo o tribunal competente, sin "),
      texto(text = "responsabilidad alguna para \"LA SECRETARIA\".\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "En los supuestos anteriormente citados \"LA SECRETARIA\" reembolsará a \"EL PROVEEDOR\" los gastos en que haya "),
      texto(text = "incurrido, siempre que estos sean razonables, estén debidamente comprobados y se relacionen directamente "),
      texto(text = "con este contrato.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "15.2.- \"LA SECRETARIA\" podrá dar por terminado anticipadamente el presente contrato, mediante notificación "),
      texto(text = "por escrito cuando existan condiciones imputables a \"EL PROVEEDOR\" que modifique sustancialmente el esquema "),
      texto(text = "de la Adquisición de REFACCIONES Y ACCESORIOS MENORES DE MAQUINARIA Y OTROS EQUIPOS para la DIRECCIÓN DE "),
      texto(text = "EPIDEMIOLOGIA Y MEDICINA PREVENTIVA (DENGUE), la notificación de referencia se tendrá que presentar dentro "),
      texto(text = "de un término de treinta días hábiles de anticipación.\n\n")
    )),
    paragraph(text = Seq(texto(text = "DÉCIMA SEXTA.- RESCISIÓN.\n\n"))),
    paragraph(text = Seq(
      texto(text = "16.1.- \"LA SECRETARIA\" en cualquier momento, podrá rescindir administrativamente este contrato por "),
      texto(text = "causas que a continuación se enumera:\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Por incumplimiento de las obligaciones asentadas en el presente Instrumento legal en el lapso "),
      texto(text = "convenido a cargo de \"EL PROVEEDOR\".\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Si la Adquisición de REFACCIONES Y ACCESORIOS MENORES DE MAQUINARIA Y OTROS EQUIPOS para la "),
      texto(text = "DIRECCIÓN DE EPIDEMIOLOGIA Y MEDICINA PREVENTIVA (DENGUE), que lleve a cabo \"EL PROVEEDOR\", que "),
      texto(text = "injustificadamente suspenda sin existir notificación con antelación, objeto del presente contrato.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "Cuando concurran razones de interés público.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "16.2.- La contravención a las disposiciones, lineamientos, bases, procedimientos y requisitos que "),
      texto(text = "establece la Ley número 230 de Adquisiciones, Enajenaciones, Arrendamientos, Prestación de Servicios y "),
      texto(text = "Administración de Bienes Muebles e Inmuebles del Estado de Guerreroy demás disposiciones sobre la materia, "),
      texto(text = "así como el incumplimiento de cualquiera de las obligaciones de \"EL PROVEEDOR\" que se estipulan en el "),
      texto(text = "presente contrato; da derecho a su rescisión inmediata, sin responsabilidad para \"LA SECRETARIA\", además "),
      texto(text = "que se aplique la pena convencional, conforme a lo establecido por este contrato.\n\n")
    )),
    paragraph(text = Seq(texto(text = "DECIMA SÉPTIMA.- ORIGEN DEL RECURSO.\n\n"))),
    paragraph(text = Seq(
      texto(text = "17.1.- El pago que se realizara derivado de este Instrumento legal se efectuarán con cargo a la fuente de "),
      texto(text = "financiamiento: DENGUE RAMO 12 AFASPE, EJERCICIO 2017, para la DIRECCIÓN DE EPIDEMIOLOGIA Y MEDICINA "),
      texto(text = "PREVENTIVA (DENGUE), correspondiente a las partidas 29801 (REFACCIONES Y ACCESORIOS MENORES DE "),
      texto(text = "MAQUINARIA Y OTROS EQUIPOS) \n\n")
    )),
    paragraph(text = Seq(texto(text = "DÉCIMA OCTAVA.- DE LA COMPETENCIA E INTERPRETACIÓN.\n\n"))),
    paragraph(text = Seq(
      texto(text = "18.1.- Para la realización de los servicios objeto de este contrato, las partes sujetan estrictamente "),
      texto(text = "a todas y cada una de las cláusulas que lo integran.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "18.2.- Para la interpretación y cumplimiento del presente contrato, así como para todo aquello que no "),
      texto(text = "esté expresamente estipulado en el mismo, las partes se someten a las Leyes aplicables y a los "),
      texto(text = "tribunales competentes con sede en la Ciudad de Chilpancingo de los Bravo, Estado de Guerrero, por "),
      texto(text = "lo tanto \"EL PROVEEDOR\" renuncia al fuero que pudiera corresponderle por razón de su domicilio "),
      texto(text = "presente, futuro o cualquier otra causa.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "18.3.- Las partes acuerdan que el presente contrato se ha instrumentado en estricto apego a la Ley "),
      texto(text = "número 230 de Adquisiciones, Enajenaciones, Arrendamientos, Prestación de Servicios y Administración "),
      texto(text = "de Bienes Muebles e Inmuebles del Estado de Guerrero, y demás disposiciones administrativas aplicables "),
      texto(text = "y de igual forma existe voluntad de las partes para firmar el presente contrato sin que exista error, "),
      texto(text = "dolo, mala fe, o coacción alguna.\n\n")
    )),
    paragraph(text = Seq(
      texto(text = "UNA VEZ QUE LAS PARTES LEYERON Y ENTENDIERON EL CONTENIDO Y ALCANCE LEGAL DEL PRESENTE CONTRATO, "),
      texto(text = "MANIFIESTAN SU CONFORMIDAD CON EL MISMO, POR LO QUE PARA CONSTANCIA LO SUSCRIBEN Y FORMALIZAN EN "),
      texto(text = "ORIGINAL FIRMANDO AL CALCE Y AL MARGEN POR TRIPLICADO, EN LA CIUDAD DE CHILPANCINGO DE LOS BRAVO, "),
      texto(text = "CAPITAL DEL ESTADO DE GUERRERO, EL DIA SEIS  DE JUNIO DE 2017; QUEDÁNDOSE UN EJEMPLAR EN PODER DE "),
      texto(text = "\"EL PROVEEDOR\" Y LOS DEMÁS EN PODER DE \"LA SECRETARIA\" PARA QUE REALICEN EL ADECUADO SEGUIMIENTO "),
      texto(text = "Y CONTROL DE LOS EFECTOS DEL PROPIO CONTRATO.\n\n")
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
