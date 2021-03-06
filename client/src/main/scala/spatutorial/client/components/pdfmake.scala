package spatutorial.client.pdfMake

//import com.sun.xml.internal.ws.client.sei.ResponseBuilder.Header
//import japgolly.scalajs.react.vdom.prefix_<^
import japgolly.scalajs.react.vdom.html_<^._

//import japgolly.scalajs.react._ 	//{Callback, ScalaComponent.builder}

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.JSConverters._
//import scala.scalajs.js.annotation.JSName

import scala.scalajs.js.annotation.JSGlobal

//Tiene algo que ver el error: "TypeError: C is undefined"
//con los anchos de cada columna que se definen en widths

@js.native
trait texto extends js.Object {
	def text: js.UndefOr[String] = js.native
	def alignment: js.UndefOr[String] = js.native
	def bold: js.UndefOr[Boolean] = js.native
	def fontSize: js.UndefOr[Int] = js.native
	def pageBreak: js.UndefOr[String] = js.native
	def margin: js.UndefOr[Seq[Int]] = js.native
	def table: js.UndefOr[bodyTable] = js.native
}

object texto {
	@inline
	def apply(	text: js.UndefOr[String] = js.undefined,
							//table: js.UndefOr[bodyTable] = js.undefined,
							alignment: js.UndefOr[String] = "justify",
							bold: js.UndefOr[Boolean] = false,
							fontSize: js.UndefOr[Int] = 10,
							pageBreak: js.UndefOr[String] = js.undefined,
							margin: js.UndefOr[Seq[Int]] = js.undefined
					 ): texto = {

		val result = js.Dynamic.literal()
		text.foreach(result.text = _)
		//result.margin = margin.map(_.toJSArray)
		margin.foreach( m => result.margin = m.toJSArray)
		pageBreak.foreach(result.pageBreak = _)
		alignment.foreach(result.alignment = _)
		bold.foreach(result.bold = _)
		fontSize.foreach(result.fontSize = _)
		result.asInstanceOf[texto]
	}
}

@js.native
trait paragraph extends js.Object {
	def text: js.UndefOr[Seq[texto]] = js.native
	def table: js.UndefOr[bodyTable] = js.native
	def margin: js.UndefOr[Seq[Int]] = js.native
	def pageBreak: js.UndefOr[String] = js.native
}

object paragraph {
	@inline
	def apply( text: js.UndefOr[Seq[texto]] = js.undefined,
						 table: js.UndefOr[bodyTable] = js.undefined,
						 margin: js.UndefOr[Seq[Int]] = js.undefined,
						 pageBreak: js.UndefOr[String] = js.undefined
					 ): paragraph = {
		val result = js.Dynamic.literal()
		text.foreach(b => result.text = b.toJSArray)

		result.table = table.map(_.asInstanceOf[js.Any])

		pageBreak.foreach(result.pageBreak = _)
		margin.foreach( m => result.margin = m.toJSArray)
		result.asInstanceOf[paragraph]
	}
}

@js.native
trait celda extends js.Object {
	//def text: paragraph = js.native
	//def text: js.UndefOr[paragraph] = js.native
	//def text: String|Seq[texto] = js.native
	//def text: js.UndefOr[String|Seq[texto]] = js.native
	//def text: String|Seq[texto] = js.native
	def text: js.UndefOr[String|Seq[texto]] = js.native
	def table: js.UndefOr[bodyTable] = js.native
	def layout: js.UndefOr[String|layoutObj] = js.native
	def style: js.UndefOr[String] = js.native
	def alignment: js.UndefOr[String] = js.native
	def fontSize: js.UndefOr[Int] = js.native
	def bold: js.UndefOr[Boolean] = js.native
	def rowSpan: js.UndefOr[Int] = js.native
	def colSpan: js.UndefOr[Int] = js.native
	def border:  js.UndefOr[Seq[Boolean]] = js.native
	def fillColor: js.UndefOr[String] = js.native
	def paddingLeft: js.UndefOr[Int] = js.native
	def margin: js.UndefOr[Seq[Int]] = js.native
}

object celda {
	@inline
	def apply(
						border: js.UndefOr[Seq[Boolean]] = js.undefined,
						table: js.UndefOr[bodyTable] = js.undefined,
						//text: paragraph,
						//text: js.UndefOr[paragraph] = js.undefined,
						//text: String|Seq[texto],
						//text: js.UndefOr[String|Seq[texto]] = js.undefined,
						//text: String|Seq[texto] /*= js.undefined*/,
						text: js.UndefOr[String|Seq[texto]] = js.undefined,
						layout: js.UndefOr[String|layoutObj] = js.undefined,
						style: js.UndefOr[String] = js.undefined,
						rowSpan: js.UndefOr[Int] = js.undefined,
						colSpan: js.UndefOr[Int] = js.undefined,
						fillColor: js.UndefOr[String] = js.undefined,
						paddingLeft: js.UndefOr[Int] = js.undefined,
						fontSize: js.UndefOr[Int] = 7,
						alignment: js.UndefOr[String] = "left",
						bold: js.UndefOr[Boolean] = false,
						margin: js.UndefOr[Seq[Int]] = js.undefined
					 ): celda = {

		val result = js.Dynamic.literal()

		border.foreach(b => result.border = b.toJSArray)
		margin.foreach(b => result.margin = b.toJSArray)
		table.foreach(result.table = _)
		paddingLeft.foreach(result.paddingLeft = _)

		result.text =	  text.map { t =>
			t.isInstanceOf[String] match {
				case true => t.asInstanceOf[js.Any]
				case false => t.asInstanceOf[Seq[texto]].toJSArray.asInstanceOf[js.Any]
				//case _	=> 	t.asInstanceOf[js.Any]
			}
		}

		layout.foreach( l => result.layout = l.asInstanceOf[js.Any] )

		style.foreach(result.style = _)
		rowSpan.foreach(result.rowSpan = _)
		colSpan.foreach(result.colSpan = _)
		fillColor.foreach(result.fillColor = _)
		alignment.foreach(result.alignment = _)
		fontSize.foreach(result.fontSize = _)
		bold.foreach(result.bold = _)

		result.asInstanceOf[celda]

	}
}

@js.native
trait imagen extends js.Object {
	def image: String = js.native
	val width: js.UndefOr[Int] = js.native
	def rowSpan: js.UndefOr[Int] = js.native
	def colSpan: js.UndefOr[Int] = js.native
}
object imagen {
	@inline
	def apply(image: String,
						rowSpan: js.UndefOr[Int] = js.undefined,
						colSpan: js.UndefOr[Int] = js.undefined,
						width: js.UndefOr[Int] = js.undefined ): imagen = {
		val result = js.Dynamic.literal()
		result.image = image
		width.foreach( result.width = _ )
		rowSpan.foreach(result.rowSpan = _)
		colSpan.foreach(result.colSpan = _)
		result.asInstanceOf[imagen]
	}
}

@js.native
trait docDefinition extends js.Object {
	def background: js.UndefOr[tabla] = js.native
	def header(currentPage: Int, pageCount: Int): tabla = js.native
	def footer(currentPage: Int, pageCount: Int): tabla = js.native
	def content: Seq[js.Object] = js.native
	def pageMargins: Seq[Int] = js.native
	def pageOrientation: String = js.native
	def pageSize: String = js.native

}

object docDefinition {
	def apply ( background: js.UndefOr[tabla] = js.undefined,
							header: (Int, Int) => tabla,
							footer: (Int, Int) => tabla,
							pageMargins: Seq[Int],
							content: Seq[js.Object],
							pageOrientation: String = "landscape",
							pageSize: String = "LETTER" ): docDefinition = {
		val result = js.Dynamic.literal()
		background.foreach(result.background = _)
		result.header = header
		result.footer = footer
		result.pageMargins = pageMargins.toJSArray
		result.content = content.toJSArray
		result.pageOrientation = pageOrientation
		result.pageSize = pageSize
		result.asInstanceOf[docDefinition]
	}
}

@js.native
trait Document extends js.Object {
	def open():js.Any = js.native
	def print():js.Any = js.native
	def download(fileName: String): js.Any = js.native
}
object Document {
	def apply(definit: docDefinition, fonts: String, vfs: String ): Document =
		js.Dynamic.literal(definit=definit ,fonts=fonts,vfs=vfs).asInstanceOf[ Document ]
}

@js.native
//@JSName("pdfMake")
@JSGlobal("pdfMake")
object pdfMake extends js.Object {
	def createPdf(definition: docDefinition): Document = js.native
}

@js.native
trait tabla extends js.Object {
	def table: bodyTable = js.native
	def margin: js.UndefOr[Seq[Int]] = js.native
	def layout: js.UndefOr[String|layoutObj] = js.native
	def vlineColor: js.UndefOr[String] = js.native

}

object tabla {
	@inline
	def apply(table: js.Object,
						layout: js.UndefOr[String|layoutObj] =  js.undefined/*layoutObj(defaultBorder = false)*/,
						vlineColor: js.UndefOr[String] = js.undefined,
						margin: js.UndefOr[Seq[Int]] = js.undefined
					 ): tabla = {
		val result = js.Dynamic.literal()
		result.table = table
		vlineColor.foreach(result.vlineColor = _)
		result.layout = layout.asInstanceOf[js.Any]
		margin.foreach(result.margin = _)
		result.asInstanceOf[tabla]
	}
}

@js.native
trait row extends js.Object{
	def rowSpan: js.UndefOr[Int] = js.native
	def colSpan: js.UndefOr[Int] = js.native
	def valor: Seq[String|Double|Int|imagen|celda|tabla|columnas] = js.native
}
object row {
	@inline
	def apply(valor: Seq[String|Double|Int|imagen|celda|tabla|columnas],
						rowSpan: js.UndefOr[Int] = js.undefined,
						colSpan: js.UndefOr[Int] = js.undefined): row = {
		val result = js.Dynamic.literal()
		rowSpan.foreach(result.rowSpan= _)
		colSpan.foreach(result.colSpan=_)
		result.valor = valor.toJSArray
		result.asInstanceOf[row]
	}
}

@js.native
trait bodyTable extends js.Object {
	//Creo que debe ser Seq[Seq[]] //por que indican filas y columnas :P
	def body: Seq[Seq[String|Double|Int|imagen|celda|tabla|columnas]] = js.native
	//def body: Seq[js.Any] = js.native
	//def body: Seq[row] = js.native
	def headerRows: js.UndefOr[Int] = js.native
	def widths: Seq[Int] = js.native
	def margin: js.UndefOr[Seq[Int]] = js.native
	def layout: js.UndefOr[layoutObj] = js.native
}

object bodyTable {
	def apply(
						 body: Seq[Seq[String|Double|Int|imagen|celda|tabla|columnas]],
						 widths: Seq[Int], headerRows: js.UndefOr[Int] = js.undefined,
						 margin: js.UndefOr[Seq[Int]] = js.undefined,
						 layout: js.UndefOr[layoutObj] = js.undefined
					 ): bodyTable = {

		val result = js.Dynamic.literal()

		result.body = body.map(_.toJSArray).toJSArray			//body = body.asInstanceOf[js.Any],

		headerRows.foreach(result.headerRows = _)
		result.widths = widths.toJSArray
		margin.foreach(result.margin = _)
		layout.foreach(result.layout = _)
		result.asInstanceOf[bodyTable]
	}
}

@js.native
trait columnas extends js.Object {
	def columns : Seq[celda|imagen|tabla] = js.native
	def margin: Seq[Int]
}
object columnas {
	def apply(columns: Seq[celda|imagen|tabla], margin: Seq[Int]): columnas = {
		js.Dynamic.literal(
			columns = columns.toJSArray,
			margin = margin.toJSArray
		).asInstanceOf[columnas]
	}
}

@js.native
trait col extends js.Object {	//para la tabla
	def width: js.UndefOr[Int|String] //= js.native	//No se debe resolver js.native con js.undefined "creo".
	def imagenCol: js.UndefOr[imagen] = js.undefined
}

object col {
	def apply( width: js.UndefOr[Int|String] = js.undefined, imagenCol: js.UndefOr[imagen] = js.undefined ): col = {
		js.Dynamic.literal().asInstanceOf[col]

		val result = js.Dynamic.literal()
		width.foreach( ancho => result.width = ancho.asInstanceOf[js.Any] )
		imagenCol.foreach(result.image = _)
		result.asInstanceOf[col]
	}
}

/*@js.native
trait encabezado extends js.Object {
	def text: celda = js.native
}
object encabezado {
	def apply ( text: celda ): encabezado =
		js.Dynamic.literal(
			text = text
		).asInstanceOf[encabezado]
}*/

@js.native
trait layoutObj extends js.Object {
	def defaultBorder: Boolean = js.native
}

object layoutObj {
	@inline
	def apply (defaultBorder: Boolean): layoutObj =
		js.Dynamic.literal(defaultBorder = defaultBorder).asInstanceOf[layoutObj]
}


//////////****************************************************
/*@js.native
trait celducha extends js.Object {
	def text: js.UndefOr[paragraph] = js.native
	def table: js.UndefOr[bodyTable] = js.native
	def layout: js.UndefOr[String|layoutObj] = js.native
	def style: js.UndefOr[String] = js.native
	def alignment: js.UndefOr[String] = js.native
	def fontSize: js.UndefOr[Int] = js.native
	def bold: js.UndefOr[Boolean] = js.native
	def rowSpan: js.UndefOr[Int] = js.native
	def colSpan: js.UndefOr[Int] = js.native
	def border:  js.UndefOr[Seq[Boolean]] = js.native
	def fillColor: js.UndefOr[String] = js.native
	def paddingLeft: js.UndefOr[Int] = js.native
	def margin: js.UndefOr[Seq[Int]] = js.native
}

object celducha {
	@inline
	def apply(
						 border: js.UndefOr[Seq[Boolean]] = js.undefined,
						 table: js.UndefOr[bodyTable] = js.undefined,
						 text: js.UndefOr[paragraph] = js.undefined,
						 layout: js.UndefOr[String|layoutObj] = js.undefined,
						 style: js.UndefOr[String] = js.undefined,
						 rowSpan: js.UndefOr[Int] = js.undefined,
						 colSpan: js.UndefOr[Int] = js.undefined,
						 fillColor: js.UndefOr[String] = js.undefined,
						 paddingLeft: js.UndefOr[Int] = js.undefined,
						 fontSize: js.UndefOr[Int] = 7,
						 alignment: js.UndefOr[String] = "left",
						 bold: js.UndefOr[Boolean] = false,
						 margin: js.UndefOr[Seq[Int]] = js.undefined
					 ): celducha = {

		val result = js.Dynamic.literal()

		border.foreach(b => result.border = b.toJSArray)
		margin.foreach(b => result.margin = b.toJSArray)
		table.foreach(result.table = _)
		paddingLeft.foreach(result.paddingLeft = _)

		text.foreach(l => result.text = l.asInstanceOf[js.Any])

		layout.foreach( l => result.layout = l.asInstanceOf[js.Any])

		style.foreach(result.style = _)
		rowSpan.foreach(result.rowSpan = _)
		colSpan.foreach(result.colSpan = _)
		fillColor.foreach(result.fillColor = _)
		alignment.foreach(result.alignment = _)
		fontSize.foreach(result.fontSize = _)
		bold.foreach(result.bold = _)

		result.asInstanceOf[celducha]
	}
}*/