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

class CDescripcionCompra @Inject()( @NamedDatabase("sicap")
													 protected val dbConfigProvider: DatabaseConfigProvider )
					extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val query = TableQuery[tabla]

	private class tabla(tag: Tag) extends Table[DescripcionCompra](tag, "qry_descripcion_adquisicion") {
		def id_comparativo       	= column[String]("id_comparativo")
		def concepto				      = column[String]("concepto")
		def descripcion           = column[String]("descripcion")

		def * = (id_comparativo, concepto, descripcion)<> (DescripcionCompra.tupled, DescripcionCompra.unapply)
	}

	def ListAll: Future[Seq[DescripcionCompra]] = db.run(query.result)

	def ById(id: String): Future[(Seq[DescripcionCompra], String)] =
		db.run(query.filter(_.id_comparativo === id).result.map((p=>(p, "found"))))

}