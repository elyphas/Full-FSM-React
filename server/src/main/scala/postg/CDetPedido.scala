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

class CDetPedido @Inject()(@NamedDatabase("sicap")
													 protected val dbConfigProvider: DatabaseConfigProvider)
	extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val query = TableQuery[tabla]

	private class tabla(tag: Tag) extends Table[DetPedido](tag, "tbldetpe") {

		def no_pedido					= column[String]("no_pedido")
		def compra						= column[String]("compra")
		def ejercicio					= column[Int]("ejercicio")
		def fecha_pedido 			= column[String]("fecha_pedido")
		def cve_articulo			= column[String]("cve_articulo")
		def cantidad					= column[Int]("cantidad")
		def precio						= column[Double]("precio")
		def anexo							= column[Option[String]]("anexo")
		def marcas						= column[Option[String]]("marcas")
		def año								= column[Int]("año")
		def id_comparativo		= column[String]("id_comparativo")
		def modelo						= column[Option[String]]("modelo")

		def * = (no_pedido, compra, ejercicio, fecha_pedido, cve_articulo, cantidad, precio, anexo, marcas, año, id_comparativo, modelo )<> ( DetPedido.tupled, DetPedido.unapply)
	}

	def ListAll: Future[Seq[DetPedido]] = db.run( query.result )

	def ById(id: PedidoId): Future[(Seq[DetPedido], String)] = {
		/*val Search = query.filter( p =>
			(p.no_pedido === id.no_pedido) && (p.ejercicio === id.ejercicio) && (p.compra === id.compra)
		)*/
		db.run(query
			.filter( p => (p.no_pedido === id.no_pedido) && (p.ejercicio === id.ejercicio) && (p.compra === id.compra))
			.result.map((p => ( p, "found" ))))

		//db.run(Search.result)
	}

	def insertPed(ped: DetPedido): Future[Seq[DetPedido]] = db.run(query += ped).map(p=>Seq(ped))

	def DelPedidos(Id: String) : Future[Int] = {
		val Delete = query.filter( _.id_comparativo === Id )
		db.run(Delete.delete)
	}

	def CancelarPedido(noPedido: String, compra: String, ejercicio: Int): Future[Int] = {

		val Cancelar = query.filter( detped =>
			(	detped.no_pedido === noPedido  &&
				detped.compra === compra &&
				detped.ejercicio === ejercicio
			)
		)
		.map( detped => ( detped.cantidad) )
		.update( 0 )

		db.run(Cancelar)
	}

	def getData(ped: EnviarAlmacen): Future[Seq[DetPedido]] = {
		val Aprov = query.filter { p =>
			(p.no_pedido === ped.no_pedido &&
				p.ejercicio === ped.ejercicio &&
				p.compra === ped.compra )
		}
		db.run(Aprov.result)
	}

}