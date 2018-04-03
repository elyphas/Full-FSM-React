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

class CQryDetallePedido @Inject()(@NamedDatabase("sicap")
													 protected val dbConfigProvider: DatabaseConfigProvider)
	extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val qryDetPedido = TableQuery[qryDetPedido]

	private class qryDetPedido(tag: Tag) extends Table[DetallePedido](tag, "qry_detallepedido2") {

		def id_comparativo				= column[String]("id_comparativo")
		def no_pedido							= column[String]("no_pedido")
		def compra								= column[String]("compra")
		def ejercicio							= column[Int]("ejercicio")
		def partida								= column[String]("partida")
		def descripcion_partida		= column[String]("descripcion_partida")
		def cve_articulo					= column[String]("cve_articulo")
		def descripcion_articulo	= column[String]("descripcion_articulo")
		def unidad								= column[String]("unidad")
		def iva										= column[Int]("iva")
		def cantidad							= column[Int]("cantidad")
		def precio								= column[Double]("precio")
		def subtotal							= column[Double]("subtotal")
		def iva_Monto							= column[Double]("iva_monto")
		def total									= column[Double]("total")

		def * = (id_comparativo, no_pedido, compra, ejercicio, partida, descripcion_partida, cve_articulo, descripcion_articulo, unidad, iva,cantidad, precio, subtotal, iva_Monto, total)<> ( DetallePedido.tupled, DetallePedido.unapply)
	}

	def ListAll: Future[Seq[DetallePedido]] = db.run( qryDetPedido.result )

	def ById(id: PedidoId): Future[Seq[DetallePedido]] = {

		val Search = qryDetPedido.filter( p =>
				(p.no_pedido === id.no_pedido) &&
				(p.ejercicio === id.ejercicio) &&
				(p.compra === id.compra)
		)
		db.run(Search.result)
	}

	def insertPed(ped: DetallePedido): Future[Seq[DetallePedido]] = db.run(qryDetPedido += ped).map(p=>Seq(ped))

	def DelPedidos(Id: String) : Future[Int] = {
		val Delete = qryDetPedido.filter( _.id_comparativo === Id )
		db.run(Delete.delete)
	}

	def CancelarPedido(noPedido: String, compra: String, ejercicio: Int): Future[Int] = {

		val Cancelar = qryDetPedido.filter( detped =>
			(	detped.no_pedido === noPedido  &&
				detped.compra === compra &&
				detped.ejercicio === ejercicio
			)
		)
		.map( detped => ( detped.cantidad) )
		.update( 0 )

		//val Result = db.run(Cancelar)
		//true
		db.run(Cancelar)
	}

}