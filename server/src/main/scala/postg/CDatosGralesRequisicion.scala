package postg

import spatutorial.shared._

import scala.concurrent.Future
//import scala.util.{Failure,Success}
//import scala.concurrent.ExecutionContext.Implicits.global

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

import scala.language.postfixOps

class CDatosGralesRequisicion @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider )
extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	implicit val fechasColumnType = MappedColumnType.base[Fechas, java.sql.Date](
		{fecha =>
			val format = new SimpleDateFormat("dd/mm/yyyy")
			val parsed = format.parse(fecha.fecha)
			new java.sql.Date(parsed.getTime());
		},
		{sql =>
			val df = new SimpleDateFormat("dd/MM/yyyy")
			//val text = df.format(sql)
			Fechas(fecha = df.format(sql))
		}
	)

	private val qryDatosGralesRequisicion = TableQuery[tblDatosGralesRequisicion]

	private class tblDatosGralesRequisicion(tag: Tag) extends Table[DatosGralesRequisicion](tag, "tblrequisicion") {
		def cve_oficina							= column[String]("cve_oficina")
		def folio										= column[Int]("folio")
		def fecha										= column[Fechas]("fecha")
		def ejercicio								= column[Int]("ejercicio")
		def ejercicio_presupuestal	= column[Int]("ejercicio_presupuestal")
		def fuente_financiamiento		= column[String]("fuente_financiamiento")
		def programa								= column[String]("programa")
		def destino									=	column[String]("destino")
		def observaciones						= column[String]("observaciones")

		def * = (cve_oficina, folio, fecha, ejercicio, ejercicio_presupuestal, fuente_financiamiento, programa, destino, observaciones)<> (DatosGralesRequisicion.tupled, DatosGralesRequisicion.unapply)
	}

	def ListAll: Future[Seq[DatosGralesRequisicion]] = db.run(qryDatosGralesRequisicion result)

	def ById(cve_depto: String, ejercicio: Int, folio: Int) : Future[Seq[DatosGralesRequisicion]] = {
		val Search = qryDatosGralesRequisicion filter{ r =>
			(r.cve_oficina === cve_depto) && (r.folio === folio) && (r.ejercicio === ejercicio)
		}
		db.run(Search.result)
	}

	def createId(cve_depto: String, año: Int): String = "calcular el id"

	//def save(art: Articulo): Future[Seq[Articulo]] = db.run(qryArticulo += art).map(p => Seq(art))

}