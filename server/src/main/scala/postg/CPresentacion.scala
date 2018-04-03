package postg

import spatutorial.shared._

import scala.concurrent.Future

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
//import scala.concurrent.ExecutionContext.Implicits.global
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.db.NamedDatabase

//import slick.driver.JdbcProfile
import slick.jdbc.JdbcProfile

class CPresentacion @Inject()(
        @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider )
    extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val qryPresentacion = TableQuery[tblPresentacion]

	private class tblPresentacion(tag: Tag) extends Table[Presentacion](tag, "presentaciones") {
		def unidad              = column[String]("unidad")
		def presentacion    = column[Option[Double]]("presentacion")
		def unidad_present  = column[Option[String]]("unidad_present")
		def observaciones     = column[Option[String]]("observaciones")

		def * = ( unidad, presentacion, unidad_present, observaciones )<> ( Presentacion.tupled, Presentacion.unapply)
	}

	def ListAll: Future[Seq[Presentacion]] = {
		val orderedPresent = qryPresentacion sortBy(p => p.unidad.asc)
		db.run(orderedPresent.result)
	}

	def ById(id: String): Future[Seq[Presentacion]] = {
		val Search = qryPresentacion.filter( _.unidad === id )
		db.run( Search.result )
	}

}