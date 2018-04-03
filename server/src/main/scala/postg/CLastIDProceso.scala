package postg
//import akka.japi.function.Procedure

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

//import scala.concurrent.Await
//import scala.concurrent.duration._

import postg.CPedido
//import postg.CPedido._

class CLastIDProceso @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider,
													 pedido: CPedido) extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val tbllastID = TableQuery[tblLastID]

	private class tblLastID(tag: Tag) extends Table[LastID](tag, Some("zickaprpts"), "qry_lastidproceso") {
		def ejercicio 					= column[Int]("ejercicio")
		def lastID          			= column[Int]("lastid")

		def * = (ejercicio, lastID) <> (LastID.tupled, LastID.unapply)
	}

	def getLastId(ejercicio: Int): Future[Seq[LastID]] = db.run(tbllastID.filter(_.ejercicio === ejercicio).result)

}