package postg

//import akka.japi.function.Procedure

import spatutorial.shared._

import scala.concurrent.Future
//import scala.util.{Failure,Success}
import scala.concurrent.ExecutionContext.Implicits.global

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.db.NamedDatabase

//import slick.driver.JdbcProfile
import slick.jdbc.JdbcProfile

import java.text.SimpleDateFormat
//import java.sql.Date
//import java.util._

class CRptSolicitudPago @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider )
extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val query = TableQuery[tabla]

	implicit val fechasColumnType = MappedColumnType.base[Fechas, java.sql.Date](
		{fecha =>
			val format = new SimpleDateFormat("dd/MM/yyyy")
			val parsed = format.parse( fecha.fecha )
			new java.sql.Date( parsed.getTime() );
		},
		{sql =>
			val df = new SimpleDateFormat("dd/MM/yyyy")
			val text = df.format(sql)
			Fechas(fecha = text)
		}
	)

	private class tabla(tag: Tag) extends Table[RptSolicitudPago](tag, Some("zickaprpts"), "qry_reporte_solicitudes_pago") {

		def id_comparativo					= column[String]("id_comparativo")
		def folio										= column[String]("folio")
		def ejercicio								= column[Int]("ejercicio")
		def fecha										= column[Fechas]("fecha")
		def alta_almacen						= column[String]("alta_almacen")
		def fecha_alta							= column[Fechas]("fecha_alta")
		def no_pedido								= column[String]("no_pedido")
		def fecha_pedido						= column[Fechas]("fecha_pedido")
		def elaboro									= column[String]("elaboro")

		def ejercicio_pedido				= column[Int]("ejercicio_pedido")
		def ejercicio_presupuesto		= column[Int]("ejercicio_presupuesto")

		def tipo_compra							= column[String]("tipo_compra")
		def requerimiento						= column[String]("requerimiento")
		def oficina									= column[String]("oficina")
		def destino									= column[String]("destino")
		def cve_provedor						= column[String]("cve_provedor")
		def razon_social						= column[String]("razon_social")
		def programa								= column[String]("programa")
		def partida									= column[String]("partida")
		def subtotal								= column[Double]("subtotal")
		def iva											= column[Double]("iva")
		def total										= column[Double]("total")

		def * = (id_comparativo, folio, ejercicio, fecha, alta_almacen, fecha_alta, no_pedido, fecha_pedido, elaboro, ejercicio_pedido, ejercicio_presupuesto, tipo_compra, requerimiento, oficina,	destino, cve_provedor, razon_social,	programa, partida, subtotal, iva, total)<> (RptSolicitudPago.tupled, RptSolicitudPago.unapply)
	}

	def getAll: Future[(Seq[RptSolicitudPago], String)] = db.run(query.result.map((p => (p, "found"))))

	def getBetweenDate(dateStart: String, dateEnd: String): Future[(Seq[RptSolicitudPago], String)] = {
		db.run(query.filter( rcd => rcd.fecha >= Fechas(fecha = dateStart) && rcd.fecha <= Fechas(fecha = dateEnd))
		.result.map((p => (p, "found"))))		//println(ver.result.statements.headOption) 	//imprimir consulta sql
	}

	def ById(folio: String, ejercicio: Int): Future[(RptSolicitudPago, String)] =
			db.run(query.filter( rcd => rcd.folio === folio && rcd.ejercicio === ejercicio).result.map((p => (p.head, "found"))))

}