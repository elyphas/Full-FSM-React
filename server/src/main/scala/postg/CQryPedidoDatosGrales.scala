package postg

import java.text.SimpleDateFormat
import javax.inject.Inject

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import scala.concurrent.ExecutionContext.Implicits.global
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.db.NamedDatabase

//import slick.driver.JdbcProfile
import slick.jdbc.JdbcProfile

import spatutorial.shared.{Fechas, _}

import scala.concurrent.Future

class CQryPedidoDatosGrales @Inject()(@NamedDatabase("sicap")
                          protected val dbConfigProvider: DatabaseConfigProvider,
                                      protected val detped: CDetPedido,
                                      protected val qrydetped: CQryDetallePedido
                        )

extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val qryPedido = TableQuery[tblPedido]

  implicit val fechasColumnType = MappedColumnType.base[Fechas, java.sql.Date](
    {fecha => //new java.sql.Date(str)
      val format = new SimpleDateFormat("dd/mm/yyyy")
      val parsed = format.parse(fecha.fecha)
      new java.sql.Date(parsed.getTime());
      /*val sql = new java.sql.Date(parsed.getTime());
      sql*/
    },
    {sql =>
      val df = new SimpleDateFormat("dd/MM/yyyy")
      /*val text = df.format(sql)
      Fechas(fecha = text)*/
      Fechas(fecha = df.format(sql))
    }
  )

  private class tblPedido(tag: Tag) extends Table[QryPedidoDatosGrales](tag, Some("zickaprpts"),"qry_datosgrales_pedidos") {
    def no_pedido             = column[String]("no_pedido")
    def compra                  = column[String]("compra")
    def ejercicio                 = column[Int]("ejercicio")
    //def numero_contrato              = column[Option[String]]("numero_contrato")

    def fecha_pedido         	= column[Fechas]("fecha_pedido")
    def fecha_entrega       = column[String]("fecha_entrega")
    def elaboro                 = column[String]("elaboro")
    def cve_depto              = column[String]("cve_depto")

    def destino                 = column[String]("destino")

    def programa            = column[String]("programa")
    def fuente                  = column[String]("fuente")

    def no_sol_compra      = column[String]("no_sol_compra")
    def tipo_compra         = column[String]("tipo_compra")

    def proveedor         	= column[String]("proveedor")
    def calle                 = column[Option[String]]("calle")
    def colonia               = column[Option[String]]("colonia")
    def ciudad                  = column[Option[String]]("ciudad")
    def telefonos               = column[Option[String]]("telefonos")

    def cancelado            = column[Option[Boolean]]("cancelado")
    def obsercancel          = column[Option[String]]("obsercancel")
    def ejercicio_presupuesto  = column[Int]("ejercicio_presupuesto")
    def id_comparativo      = column[String]("id_comparativo")

    override def * = (no_pedido, compra, ejercicio, fecha_pedido, fecha_entrega, elaboro, cve_depto, destino, programa, fuente, no_sol_compra, tipo_compra, proveedor, calle, colonia, ciudad, telefonos, cancelado,obsercancel, ejercicio_presupuesto, id_comparativo)<> (QryPedidoDatosGrales.tupled, QryPedidoDatosGrales.unapply)
  }

  def ListAll: Future[Seq[QryPedidoDatosGrales]] = db.run( qryPedido.result )

  def ById(idPedido: PedidoId): Future[Seq[QryPedidoDatosGrales]] = {
    val Search = qryPedido.filter( p =>
        (p.no_pedido === idPedido.no_pedido) &&
        (p.ejercicio === idPedido.ejercicio) &&
        (p.compra === idPedido.compra)
    )
    db.run( Search.result )
  }

  def getPedido(idPedido: PedidoId): Future[(Seq[QryPedidoDatosGrales], Seq[DetallePedido], String)] = {

    val Search = qryPedido.filter( p =>
        (p.no_pedido === idPedido.no_pedido) &&
        (p.ejercicio === idPedido.ejercicio) &&
        (p.compra === idPedido.compra)
    )
    val resultados = for {
      p <- db.run(Search.result)
      d <- qrydetped.ById(idPedido)
    } yield (p, d, if(p.isEmpty) "not found" else "found" )

    resultados

  }

}