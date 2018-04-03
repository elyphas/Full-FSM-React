package postg

/*import app.utils.PostgresSupport
import slick.driver.PostgresDriver.api._
import slick.lifted.Tag*/

import spatutorial.shared._

//import scala.concurrent.Await
//import scala.concurrent.duration._

import scala.concurrent.Future

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
//import scala.concurrent.ExecutionContext.Implicits.global
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.db.NamedDatabase

//import slick.driver.JdbcProfile
import slick.jdbc.JdbcProfile

class CDetPedAlmacen @Inject()( @NamedDatabase("almacen") protected val dbConfigProvider: DatabaseConfigProvider )
	extends HasDatabaseConfigProvider[JdbcProfile]
//extends  PostgresSupport
{

	import profile.api._

  private val qryDetPed = TableQuery[tbldetpe]

	private class tbldetpe(tag: Tag) extends Table[DetPedidoAlmacen](tag, "qry_detalles_pedidos") {
		def	no_pedido			= column[String]("no_pedido")
		def	compra				= column[String]("compra")
		def	ejercicio			= column[Int]("ejercicio")
		def	partida				=	column[String]("partida")
		def	descripcion_partida	=	column[String]("descripcion_partida")
		def cve_articulo		= column[String]("cve_articulo")
		def	descripcion_articulo		= column[String]("descripcion_articulo")
		def	unidad				= column[String]("unidad")
		def	cantidad			=	column[Int]("cantidad")
		def	iva						= column[Int]("iva")
		def	precio				=	column[Double]("precio")
		def	marcas				= column[Option[String]]("marcas")
		def	anexo					= column[Option[String]]("anexo")
		def	subtotal			= column[Double]("subtotal")

		def * = (no_pedido, compra,ejercicio, partida,descripcion_partida,cve_articulo,descripcion_articulo,unidad,cantidad,iva,precio,marcas,anexo,subtotal)<> ( DetPedidoAlmacen.tupled, DetPedidoAlmacen.unapply)
	}
	//override def exec[T](action: DBIO[T]):T = Await.result( dbAlmacen.run(action.withPinnedSession), 6 seconds)
	//def exec[T](action: DBIO[T]):T = Await.result( db.run(action), 6 seconds)
	def getData(ped: EnviarAlmacen): Future[Seq[DetPedidoAlmacen]] = {
		val Aprov = qryDetPed.filter{p => p.no_pedido === ped.no_pedido && p.ejercicio === ped.ejercicio && p.compra === ped.compra}
		db.run(Aprov.result)
	}

	def insertDetalles(det: Seq[DetPedidoAlmacen]): Future[Option[Int]] = db.run( (qryDetPed ++= det) )

	def deletePed(item: EnviarAlmacen): Future[Int] = {
		println("eliminando detalles del pedido 		******************************")
		val Delete = qryDetPed.filter { p =>
			( p.no_pedido === item.no_pedido &&
				p.compra === item.compra &&
				p.ejercicio === item.ejercicio )
		}
		db.run( Delete.delete )
	}

}