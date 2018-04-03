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

class CSeguimiento @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider )
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

	private class tabla(tag: Tag) extends Table[Seguimiento](tag, Some("zickaprpts"), "seguimiento_procesos") {
		def id_comparativo  		= column[String]("id_comparativo")
		def elaboro             = column[String]("elaboro")
		def pedido   						= column[Option[String]]("pedido")
		def fecha           		= column[Fechas]("fecha")
		def tipo_compra					= column[Option[String]]("tipo_compra")
		def compra							= column[Option[String]]("compra")
		def no_sol_compra				= column[Option[String]]("no_sol_compra")
		def cve_presup					= column[Option[String]]("cve_presup")
		def dias           			= column[String]("dias")
		def cotizaciones     		= column[Option[Int]]("cotizaciones")

		def * = (id_comparativo, elaboro, pedido, fecha, tipo_compra, compra, no_sol_compra, cve_presup, dias, cotizaciones)<> (Seguimiento.tupled, Seguimiento.unapply)
	}

	def getAll: Future[(Seq[Seguimiento], String)] =
		db.run(query.result.map((p => (p, "found"))))
		//db.run(query result)

	def getBetweenDate(dateStart: String, dateEnd: String): Future[(Seq[Seguimiento], String)] = {
		/*val ver = query.filter( rcd =>
			rcd.fecha >= Fechas(fecha = dateStart) &&
				rcd.fecha <= Fechas(fecha = dateStart)
		)
		println(ver.result.statements.headOption)*/ //imprimir consulta sql

		db.run(query.filter( rcd =>
			rcd.fecha >= Fechas(fecha = dateStart) &&
				rcd.fecha <= Fechas(fecha = dateEnd)
		)
		.result.map((p => (p, "found"))))
	}



	/*def ById(id: String): Future[(Seq[Seguimiento], String)] =
		db.run(query.filter(_.id_comparativo === id).result.map((p => (p, "found"))))*/

}