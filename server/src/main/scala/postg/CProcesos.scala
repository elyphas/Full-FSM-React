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

class CProcesos @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider,
													 	pedido: CPedido, lastID: CLastIDProceso)
													extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val query = TableQuery[tabla]

	implicit val fechasColumnType = MappedColumnType.base[Fechas, java.sql.Date](
		{fecha =>
			val format = new SimpleDateFormat("dd/MM/yyyy")
			val parsed = format.parse(fecha.fecha)
			new java.sql.Date(parsed.getTime());
		},
		{sql =>
			val df = new SimpleDateFormat("dd/MM/yyyy")
			val text = df.format(sql)
			Fechas(fecha = text)
		}
	)

	private class tabla(tag: Tag) extends Table[Proceso](tag, "memo") {
		def id_comparativo 					= column[String]("id_comparativo")
		def fecha          					= column[Fechas]("fecha")
		def memo           					= column[String]("memo")
		def destino        					= column[String]("destino")
		def cveOficina    					= column[String]("cve_oficina")
		def tipoCompra    					= column[String]("tipo_compra")
		def iva            					= column[Boolean]("iva")
		def plazo          					= column[String]("plazo")
		def elaboro        					= column[String]("elaboro")
		def fechaPedido   					= column[Fechas]("fecha_pedido")
		def fin            					= column[Boolean]("fin")
		def programa       					= column[String]("programa")
		def rfcDependencia					= column[String]("rfc_dependencia")
		def folio          					= column[Int]("folio")
		def ejercicio      					= column[Int]("ejercicio")
		def ejercicioPresupuestal 	= column[Int]("ejercicio_presupuestal")

		def * = (id_comparativo, fecha, memo, destino, cveOficina, tipoCompra, iva, plazo, elaboro, fechaPedido, fin, programa, rfcDependencia, folio, ejercicio, ejercicioPresupuestal) <> (Proceso.tupled, Proceso.unapply)
	}

	def ListAll: Future[Seq[Proceso]] = db.run(query.result)

	def ById(id: String): Future[(Seq[Proceso], String)] =
		db.run(query.filter(_.id_comparativo === id).result.map((p=>(p,"found"))))

	//Return a data type and a Event; (found, saved, inserted, deleted, not found)
	def insert(item: Proceso): Future[(Seq[Proceso], String)] = {
		db.run(query += item).map(p => (Seq(item), "inserted"))
	}

	def save(item: Proceso): Future[(Seq[Proceso], String)] = {
		val updateDatos = query.filter(_.id_comparativo === item.id_comparativo)
			.map( d => (d.id_comparativo,d.fecha,d.memo,d.destino,d.cveOficina,d.tipoCompra,d.iva,
					d.plazo,d.elaboro,d.fechaPedido,d.fin,d.programa,d.rfcDependencia,d.folio,
					d.ejercicio,d.ejercicioPresupuestal))
			.update((item.id_comparativo,item.fecha,item.memo,item.destino,item.cveOficina,item.tipoCompra,
				item.iva,item.plazo,item.elaboro,item.fechaPedido,item.fin,item.programa,item.rfcDependencia,
				item.folio,item.ejercicio,item.ejercicioPresupuestal))

		db.run(updateDatos).map(p => (Seq(item), "saved"))
	}

	def delete(Id: String) : Future[(Int, String)] = {
		val Delete = query.filter(_.id_comparativo === Id)
		db.run(Delete.delete).map(p=> (p, "deleted"))
	}

	def DelPedidos(Id: String): Future[Int] = {

    val updateProceso = query.filter(proc => proc.id_comparativo === Id)
      .map( (_.fin) ).update(true)

    db.run(updateProceso)

    val ped = pedido.DelPedidos(Id)

		val result = for {
      p <- ped
      fin <- db.run(updateProceso)
    } yield { p }

		println("Se supone que esta eliminando")

		result
	}

	def Ended(Id: String): Future[Int] = db.run(query.filter(_.id_comparativo === Id).map(_.fin).update(false))

	def getLastId(ejercicio: Int) = lastID.getLastId(ejercicio)

}