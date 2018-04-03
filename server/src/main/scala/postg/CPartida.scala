package postg

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

class CPartida @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider )
	extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val query = TableQuery[tabla]

	private class tabla(tag: Tag) extends Table[Partida](tag, "tblpart") {

		def cve_partida      = column[String]("cve_partida")
		def descripcion      = column[Option[String]]("descripcion")
		def observaciones    = column[Option[String]]("observaciones")
		def presupuesto      = column[Option[Double]]("presupuesto")
		def activo           = column[Option[Boolean]]("activo")

		def * = (cve_partida, descripcion, observaciones, presupuesto, activo )<> ( Partida.tupled, Partida.unapply)
	}

	def ListAll: Future[Seq[Partida]] = {
		val orderedPart = query filter(_.cve_partida.length>4) sortBy(p => p.cve_partida.asc)
		db.run(orderedPart.result)
	}

	def ById(id: String): Future[(Seq[Partida], String)] = db.run(query.filter(_.cve_partida === id).result.map((p=>(p,"found"))))

	def getDescripcion(clave: String) : Future[Seq[Option[String]]] = {
		//val qryPartida = TableQuery[tblPartida]
		val Search = query.filter( _.cve_partida === clave ).map ( _.descripcion )
		db.run( Search.result )
	}

	def insert(item: Partida): Future[(Seq[Partida], String)] = db.run(query += item).map(p => (Seq(item), "inserted"))

	def save(item: Partida): Future[(Seq[Partida], String)] = {
		val Guardar = query.filter( rcd =>
			(rcd.cve_partida === item.cve_partida))
			.map( rcd => (rcd.cve_partida, rcd.descripcion, rcd.observaciones, rcd.presupuesto, rcd.activo))
			.update((item.cve_partida, item.descripcion, item.observaciones, item.presupuesto, item.activo))

			db.run(Guardar).map(p => (Seq(item), "saved"))
	}

}