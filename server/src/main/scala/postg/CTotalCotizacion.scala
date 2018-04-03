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

class CTotalCotizacion @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider )
extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val query = TableQuery[tabla]

	private class tabla(tag: Tag) extends Table[TotalCotizacion](tag, Some("zickaprpts"), "qry_total_cotizacion") {
		def id_comparativo  = column[String]("id_comparativo")
		def rfc             = column[String]("rfc")
		def razon_social    = column[String]("razon_social")
		def monto           = column[Double]("monto")

		def * = (id_comparativo, rfc, razon_social, monto )<> (TotalCotizacion.tupled, TotalCotizacion.unapply)
	}

	def ListAll: Future[Seq[TotalCotizacion]] = db.run(query.result)

	def ById(id: String): Future[(Seq[TotalCotizacion], String)] =
		db.run(query.filter(_.id_comparativo === id).result.map((p => (p, "found"))))

}