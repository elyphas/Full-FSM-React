package postg

import spatutorial.shared._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
//import play.api.libs.concurrent.Execution.Implicits.defaultContext

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import play.db.NamedDatabase
//import slick.driver.JdbcProfile
import slick.jdbc.JdbcProfile

import java.text.SimpleDateFormat
//import java.sql.Date
//import java.util._
//import boopickle.Default._

import spatutorial.shared.Fechas

class CPedido @Inject()( @NamedDatabase("sicap")
                          protected val dbConfigProvider: DatabaseConfigProvider,
                          protected val detped: CDetPedido,
                          protected val qrydetped: CQryDetallePedido
                        )

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

  private class tabla(tag: Tag) extends Table[Pedido](tag, "tblpedi") {
    def no_pedido               = column[String]("no_pedido")
    def compra                  = column[String]("compra")
    def ejercicio               = column[Int]("ejercicio")
    def rfc_dependencia   			= column[String]("rfc_dependencia")
    def fecha_pedido         	  = column[Fechas]("fecha_pedido")
    def cve_provedor         	  = column[String]("cve_provedor")
    def cve_depto               = column[String]("cve_depto")
    def cve_presup              = column[String]("cve_presup")
    def fecha_entrega           = column[String]("fecha_entrega")
    def no_sol_compra           = column[String]("no_sol_compra")
    def tipo_compra             = column[String]("tipo_compra")
    def destino                 = column[String]("destino")
    def elaboro                 = column[String]("elaboro")
    def iva                     = column[Boolean]("iva")
    def capturo                 = column[String]("capturo")
    def id_comparativo          = column[String]("id_comparativo")
    def cancelado               = column[Option[Boolean]]("cancelado")
    def obsercancel             = column[Option[String]]("obsercancel")
    def ejercicio_presupuesto   = column[Int]("ejercicio_presupuesto")

    override def * = (no_pedido, compra, ejercicio, rfc_dependencia, fecha_pedido, cve_provedor, cve_depto, cve_presup, fecha_entrega, no_sol_compra, tipo_compra, destino, elaboro, iva, capturo,id_comparativo, cancelado, obsercancel, ejercicio_presupuesto )<> ( Pedido.tupled, Pedido.unapply)
  }

  def ListAll: Future[Seq[Pedido]] = db.run( query.result )

  def ById(id: String): Future[(Pedido, String)] = db.run( query.filter(_.id_comparativo === id).result.map(p => (p.head, "found")))

  //Creo que esta duplicado revisar esta función.
  def getData(id: EnviarAlmacen): Future[Seq[Pedido]] = {
    val Aprov = query.filter{ p => p.no_pedido === id.no_pedido && p.ejercicio === id.ejercicio && p.compra === id.compra}
    db.run(Aprov.result)
  }

  def getPedido(idPedido: PedidoId): Future[(Pedido, String)] = {
    val Search = query.filter( p => p.no_pedido === idPedido.no_pedido && p.ejercicio === idPedido.ejercicio && p.compra === idPedido.compra)
    val resultados = for {
      p <- db.run(Search.result)
      //d <- qrydetped.ById(idPedido)
    } yield (p.head, if(p.isEmpty) "not found" else "found")
    resultados
  }

  def updatePedido(pedido: Pedido): Future[(Pedido, String)] = {

    val Save = query.filter( ped => ped.no_pedido === pedido.no_pedido  && ped.compra === pedido.compra && ped.ejercicio === pedido.ejercicio)
      .map( p => (p.no_pedido, p.compra, p.ejercicio, p.rfc_dependencia, p.fecha_pedido, p.cve_provedor, p.cve_depto,
         p.cve_presup, p.fecha_entrega, p.no_sol_compra, p.tipo_compra, p.destino, p.elaboro, p.iva,
        p.capturo, p.id_comparativo, p.cancelado, p.obsercancel, p.ejercicio_presupuesto))
      .update((pedido.no_pedido, pedido.compra, pedido.ejercicio, pedido.rfc_dependencia, pedido.fecha_pedido,
        pedido.cve_provedor, pedido.cve_depto, pedido.cve_presup, pedido.fecha_entrega, pedido.no_sol_compra,
        pedido.tipo_compra, pedido.destino, pedido.elaboro, pedido.iva, pedido.capturo, pedido.id_comparativo,
        pedido.cancelado, pedido.obsercancel, pedido.ejercicio_presupuesto))

    val result = for {
      r <- db.run(Save)
      p <- getPedido(PedidoId ( no_pedido = pedido.no_pedido, compra = pedido.compra, ejercicio = pedido.ejercicio) )
    } yield (p._1, "saved")
    result
  }

  def insertPed(item: Pedido): Future[Seq[Pedido]] = db.run(query += item).map(p => Seq(item))

  def DelPedidos(Id: String) : Future[Int] = {
    val Delete = query.filter( _.id_comparativo === Id )
    db.run(Delete.delete)
  }

  def CancelarPedido(idPedido: PedidoId, reason: String): Future[Option[Pedido]] = {
    val Cancelar = query.filter( pedido =>
          (	pedido.no_pedido === idPedido.no_pedido  &&
            pedido.compra === idPedido.compra &&
            pedido.ejercicio === idPedido.ejercicio)
        )
        .map(pedido => (pedido.cancelado, pedido.obsercancel))
        .update((Some(true), Some(reason)))

    //val result =
    for {
      r <- db.run(Cancelar)
      d <- detped.CancelarPedido(idPedido.no_pedido, idPedido.compra, idPedido.ejercicio)
      p <- getPedido(idPedido)
    } yield { p }

    Future(Some(Pedido()))	//.map( p => Some(p) )
  }
}