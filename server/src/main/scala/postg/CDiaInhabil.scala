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

class CDiaInhabil @Inject()( @NamedDatabase("sicap")
													 protected val dbConfigProvider: DatabaseConfigProvider )
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
			val text = df.format(sql)
			Fechas(fecha = text)
		}
	)


	private val query = TableQuery[tabla]

	private class tabla(tag: Tag) extends Table[Dia_Inhabil](tag, "tbldias_inhabiles") {
		def fecha           		= column[Fechas]("fecha")
		def observacion      		= column[String]("observacion")

		def * = (fecha, observacion)<> (Dia_Inhabil.tupled, Dia_Inhabil.unapply)
	}

	def ListAll: Future[Seq[Dia_Inhabil]] = db.run(query.result)

	def ById(id: Fechas) : Future[(Seq[Dia_Inhabil], String)] = {

		db.run(query.filter(_.fecha === id).result.map((p => (p, "found"))))
		/*val Search = query filter(_.id_comparativo === id)
		db.run(Search result)*/
	}

	//Return a data type and a Event; (found, saved, inserted, deleted, not found)
	def insert(item: Dia_Inhabil): Future[(Seq[Dia_Inhabil], String)] = {
			db.run(query += item).map(p => (Seq(item), "inserted"))
	}


}