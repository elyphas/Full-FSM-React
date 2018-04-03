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

class CPrecioReferencia @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider )
extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val query = TableQuery[tabla]

	private class tabla(tag: Tag) extends Table[PrecioReferencia](tag, Some("zickaprpts"), "qry_orders_base2") {

		def cve_articulo            = column[String]("cve_articulo")
		/*def descripcion             = column[String]("descripcion")
		def unidad                  = column[String]("unidad")
		def presentacion           	= column[Option[Int]]("presentacion")
		def unid_med_pres       		= column[Option[String]]("unid_med_pres")
		def partida                 = column[String]("partida")*/
		def precio_referencia				= column[Double]("precio")
		def rfc											= column[String]("cve_provedor")
		def razon_social						= column[String]("razon_social")

		def * = (cve_articulo, precio_referencia, rfc, razon_social) <> (PrecioReferencia.tupled, PrecioReferencia.unapply)
	}

	def ListAll: Future[Seq[PrecioReferencia]] = db.run(query.result)

	def ById(id: String) : Future[(Seq[PrecioReferencia], String)] =
			db.run(query.filter(_.cve_articulo === id).result.map(( p => (p,"found") )))
}