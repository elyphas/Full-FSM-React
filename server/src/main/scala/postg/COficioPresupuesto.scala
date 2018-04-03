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

//import scala.concurrent.Await
//import scala.concurrent.duration._

import postg.CPedido

import java.text.SimpleDateFormat
//import java.sql.Date
//import java.util._

//import postg.CPedido._

class COficioPresupuesto @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider,
													 	pedido: CPedido,
												 		lastID: CLastIDProceso
												 )
													extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val query = TableQuery[tabla]

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

	private class tabla(tag: Tag) extends Table[OficioPresupuesto](tag, "tbloficiopresupuesto") {
		def id_comparativo 					= column[String]("id_comparativo")
		def numero          				= column[String]("numero")
		def fecha          					= column[Fechas]("fecha")
		def fecha_recibido    			= column[Fechas]("fecha_recibido")
		def monto										= column[Double]("monto")
		def observaciones    				= column[String]("observaciones")

		def * = (id_comparativo, numero, fecha, fecha_recibido, monto, observaciones) <> (OficioPresupuesto.tupled, OficioPresupuesto.unapply)
	}

	def ListAll: Future[Seq[OficioPresupuesto]] = db.run(query.result)

	def ById(id: String): Future[(OficioPresupuesto, String)] = {
		db.run(query.filter(_.id_comparativo === id).result.map{ p =>
			if (p.isEmpty)
				(OficioPresupuesto(), "Not Found")
			else
				(p.head, "found")
		})
	}

	//Return a data type and a Event; (found, saved, inserted, deleted, not found)
	def insert(item: OficioPresupuesto): Future[(OficioPresupuesto, String)] =
		db.run(query += item).map( p => (item, "inserted"))

	def save(item: OficioPresupuesto): Future[(OficioPresupuesto, String)] = {
		val Update = query
			.filter(_.id_comparativo === item.id_comparativo)
			.map( d => (d.id_comparativo, d.numero, d.fecha, d.fecha_recibido, d.monto, d.observaciones))
			.update((item.id_comparativo, item.numero, item.fecha, item.fecha_recibido, item.monto, item.observaciones))

		db.run(Update).map(p => (item, "saved"))
	}

	def delete(Id: String) : Future[(Int, String)] = {
		val Delete = query.filter(_.id_comparativo === Id)
		db.run(Delete.delete).map(p=> (p, "deleted"))
	}

}