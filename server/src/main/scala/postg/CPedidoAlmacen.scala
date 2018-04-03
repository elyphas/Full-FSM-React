package postg

import spatutorial.shared._
//import scala.util.{Failure, Success}
import scala.concurrent.Future

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.db.NamedDatabase

//import slick.driver.JdbcProfile
import slick.jdbc.JdbcProfile

//import slick.driver.PostgresDriver.api._

//import scala.concurrent.Await
//import scala.concurrent.duration._

class CPedidoAlmacen  @Inject()( 	@NamedDatabase("almacen")
																 	protected val dbConfigProvider: DatabaseConfigProvider,
																 	protected val detPedido: CDetPedAlmacen
															 )

	extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val qryPed = TableQuery[tblPedido]

	private class tblPedido(tag: Tag) extends Table[PedidoAlmacen](tag, "qry_datosgrales_pedidos") {

		def no_pedido             = column[String]("no_pedido")
		def compra                  = column[String]("compra")
		def fecha_pedido         = column[java.sql.Date]("fecha_pedido")    //fecha_pedido | date
		def tipo_compra         = column[String]("tipo_compra")
		def cve_depto              = column[String]("cve_depto")
		def destino                 = column[String]("destino")
		def cve_provedor         = column[String]("cve_provedor")
		def	razon_social		 			= column[String]("razon_social")
		def programa            	= column[String]("programa")
		def descripcion_programa	= column[String]("descripcion_programa")
		def	fuente_financiamiento	=	column[String]("fuente_financiamiento")
		def	descripcion_fuente		= column[String]("descripcion_fuente")
		//def fecha_entrega       = column[String]("fecha_entrega")
		//def no_sol_compra      = column[String]("no_sol_compra")
		def ejercicio                 = column[Int]("ejercicio")

		def * = (no_pedido, compra, fecha_pedido, tipo_compra, cve_depto, destino, cve_provedor, razon_social, programa,descripcion_programa, fuente_financiamiento, descripcion_fuente, ejercicio )<> ( PedidoAlmacen.tupled, PedidoAlmacen.unapply)
	}

	def ListAll: Future[Seq[PedidoAlmacen]] = db.run(qryPed.result)

	def insertPed(pedido: PedidoAlmacen): Future[Option[Int]] = db.run(qryPed += pedido).map(p=>Some(p))

	def deletePed(item: EnviarAlmacen): Future[Int] = {

		val Delete = qryPed.filter { p =>
			(p.no_pedido === item.no_pedido && p.compra === item.compra && p.ejercicio === item.ejercicio)
		}

		val result = for {
			r1 <- detPedido.deletePed(item)
			r2 <- db.run( Delete.delete )
		} yield {
			r2
		}
		result
	}

}