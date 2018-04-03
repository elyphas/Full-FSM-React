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

class CPrograma @Inject()( @NamedDatabase("sicap")
													 protected val dbConfigProvider: DatabaseConfigProvider )
					extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val query = TableQuery[tabla]

	private class tabla(tag: Tag) extends Table[Programa](tag, "tblpresu") {
		def programa            	= column[String]("programa")
		def descripcion           = column[String]("descripcio")
		def destino               = column[String]("destino")
		def depto           			= column[String]("depto")
		def mostrar       				= column[Boolean]("mostrar")
		def rfc_dependencia       = column[String]("rfc_dependencia")
		def nivel         				= column[String]("nivel")
		def encargado             = column[Option[String]]("encargado")
		def activo                = column[Boolean]("activo")
		def fuente_financiamiento = column[String]("fuente_financiamiento")

		def * = (programa, descripcion, destino, depto, mostrar, rfc_dependencia, nivel, encargado, activo, fuente_financiamiento)<> (Programa.tupled, Programa.unapply)
	}

	def ListAll: Future[Seq[Programa]] = {
		println("obteniendo todos los programas")
		db.run(query.result)
	}

	def ById(id: String) : Future[(Programa, String)] =
		db.run(query.filter(_.programa === id).result.map { p =>
				if (p.isEmpty ) (Programa(), "Not Found") else (p.head, "found")
		})

	def getDescripcion(clave: String): Future[Seq[String]] = {
		val Search = query.filter(_.programa === clave) map (_.descripcion)
		db.run(Search.result)
	}


	def save(item: Programa): Future[Programa] = {
		val Update = query
				.filter(_.programa === item.programa)
			  .map(r => (r.programa, r.descripcion, r.destino, r.depto, r.mostrar, r.rfc_dependencia, r.nivel, r.encargado, r.activo, r.fuente_financiamiento))
				.update((item.programa, item.descripcion, item.destino, item.depto, item.mostrar, item.rfc_dependencia, item.nivel, item.encargado, item.activo, item.fuente_financiamiento))

		for {
			_ <- db.run(Update)
		} yield (item)

	}

	def insert(programa: Programa): Future[Programa] = db.run(query += programa).map(p => programa)

  def edit(programa: Programa): Future[Seq[Programa]] = {
    val updatePrograma = query.filter( prog => prog.programa === programa.programa )
    Future(Seq(programa))
  }
}