package postg

import spatutorial.shared._

import scala.concurrent.Future

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
//import scala.concurrent.ExecutionContext.Implicits.global
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.db.NamedDatabase

//import slick.driver.JdbcProfile
import slick.jdbc.JdbcProfile

class CPedido_toAlmacen @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider )
		extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val tblPedidos = TableQuery[tblPedido]

	private class tblPedido(tag: Tag) extends Table[PedidoAlmacen](tag, Some("almacen"), "datosgrales_aprobados"  ) {

		def no_pedido 						= column[String]("no_pedido")
		def	compra								=	column[String]("compra")
		def fecha_pedido					= column[java.sql.Date]("fecha_pedido")
		def tipo_compra						= column[String]("tipo_compra")
		def cve_depto							=	column[String]("cve_depto")
		def destino								= column[String]("destino")
		def cve_provedor					=	column[String]("cve_provedor")
		def razon_social					=	column[String]("razon_social")
		def programa							= column[String]("programa")
		def descripcion_programa 	= column[String]("descripcion_programa")
		def fuente_financiamiento = column[String]("fuente_financiamiento")
		def descripcion_fuente		= column[String]("descripcion_fuente")
		//def	fecha_entrega				= column[String]("fecha_entrega")
		//def no_sol_compra				=	column[String]("no_sol_compra")
		def ejercicio							= column[Int]("ejercicio")

		def * = (no_pedido, compra, fecha_pedido, tipo_compra, cve_depto, destino, cve_provedor, razon_social, programa,descripcion_programa, fuente_financiamiento, descripcion_fuente, ejercicio )<> ( PedidoAlmacen.tupled, PedidoAlmacen.unapply)

	}

	def getData(ped: EnviarAlmacen): Future[Seq[PedidoAlmacen]] = {
		val Aprov = tblPedidos.filter{ p =>
			(p.no_pedido === ped.no_pedido && p.ejercicio === ped.ejercicio && p.compra === ped.compra)
		}
		db.run(Aprov.result)
	}



	/*def ListAll( ): Seq[Pedido] = {
		val qryPartida = TableQuery[tblPedido]
		exec( qryPartida.result )
	}

	def ById( id: String ) : Seq[Pedido] = {
		val qryPedido = TableQuery[ tblPedido ]
		val Search = qryPedido.filter( _.id_comparativo === id )
		exec( Search.result )
	}*/

	/*def DelPedidos(Id: String) : Int = {
		val qryPedido = TableQuery[tblPedido]
		val Delete = qryPedido.filter( _.id_comparativo === Id )
		val Result = exec(Delete.delete)
		Result
	}*/

}
