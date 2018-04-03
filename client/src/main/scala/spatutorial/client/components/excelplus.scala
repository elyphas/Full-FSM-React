package spatutorial.client.excelPlus

//import com.sun.xml.internal.ws.client.sei.ResponseBuilder.Header
//import japgolly.scalajs.react.vdom.prefix_<^
//import japgolly.scalajs.react.{Callback, ReactComponentB}

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSName
import js.annotation.JSGlobal

@js.native
//@JSName("ExcelPlus")
@JSGlobal("pdfMake")
class ExcelPlus extends js.Object {
	def createFile( param: String ): js.Any = js.native
	def saveAs( filename: String ): js.Any = js.native
	def write( options: Options ): js.Any = js.native
}

@js.native
trait Options extends js.Object {
	def content: Seq[ Seq[ String | Double | Int ] ] = js.native

}

object Options {
	def apply (content: Seq[ Seq[ String | Double | Int ] ] ): Options = {
		js.Dynamic.literal(content = content.map( _.toJSArray ).toJSArray).asInstanceOf[Options]
	}
}

@js.native
trait params extends  js.Object {
	def sheetnames: String = js.native
}
object params {
	def apply( sheetnames: String ): params = {
		js.Dynamic.literal( sheetnames = sheetnames ).asInstanceOf[params]
	}
}