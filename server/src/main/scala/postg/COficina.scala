package postg

//import akka.japi.function.Procedure
import spatutorial.shared._

import scala.concurrent.Future

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.db.NamedDatabase

//import slick.driver.JdbcProfile
import slick.jdbc.JdbcProfile

class COficina @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider )
extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val query = TableQuery[tabla]

	private class tabla(tag: Tag) extends Table[Oficina](tag, "tblofic") {
		def clave          	= column[String]("cve_depto")
		def descripcion    	= column[String]("descripcion")
		def firma    				= column[Option[String]]("firma")
		def cargo    				= column[Option[String]]("cargo")

		def * = (clave, descripcion, firma, cargo) <> (Oficina.tupled, Oficina.unapply)
	}

	def ListAll: Future[Seq[Oficina]] = db.run( query.result )

	def ById(id: String): Future[(Oficina, String)] = db.run(query.filter(_.clave === id).result.map(( p => (p.head, "found"))))

	def getDescripcion(clave: String): Future[Seq[String]] = {
		val Search = query.filter( _.clave === clave ).map ( _.descripcion )
		db.run(Search.result)
	}

}