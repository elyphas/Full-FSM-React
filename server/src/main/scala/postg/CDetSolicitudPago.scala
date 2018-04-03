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

class CDetSolicitudPago @Inject()( @NamedDatabase("sicap")
                          protected val dbConfigProvider: DatabaseConfigProvider)

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

  private class tabla(tag: Tag) extends Table[DetSolicitudPago](tag, "tbldetsolicitudpago") {
    def folio               = column[String]("folio")
    def ejercicio           = column[Int]("ejercicio")
    def renglon             = column[Int]("renglon")

    def alta_almacen        = column[String]("alta_almacen")
    def ejercicio_alta      = column[Int]("ejercicio_alta")
    def fecha_alta          = column[Fechas]("fecha_alta")

    def factura             = column[String]("factura")
    def fecha               = column[Fechas]("fecha")
    def importe   			    = column[Double]("importe")
    def folio_control       = column[Option[String]]("folio_control")
    def contrarecibo        = column[String]("contrarecibo")
    def observaciones_alta  = column[String]("observaciones_alta")

    override def * = (folio, ejercicio, renglon, alta_almacen, ejercicio_alta, fecha_alta, factura, fecha, importe, folio_control, contrarecibo, observaciones_alta) <> (DetSolicitudPago.tupled, DetSolicitudPago.unapply)
  }

  def ListAll: Future[Seq[DetSolicitudPago]] = db.run( query.result )

  def ById(id: IdSolicitudPago): Future[(Seq[DetSolicitudPago], String)] =
    db.run( query.filter( rcd => rcd.folio === id.folio && rcd.ejercicio === id.ejercicio).result.map(( p => (p, "found"))))

  def update(item: DetSolicitudPago): Future[(Seq[DetSolicitudPago], String)] = {
    val Save = query.filter( rcd =>
      (rcd.folio === item.folio && rcd.ejercicio === item.ejercicio && rcd.renglon === item.renglon))
      .map( rcd => (rcd.folio, rcd.ejercicio, rcd.renglon, rcd.alta_almacen, rcd.ejercicio_alta, rcd.fecha_alta, rcd.factura, rcd.fecha, rcd.importe, rcd.folio_control))
      .update((item.folio, item.ejercicio, item.renglon, item.alta_almacen, item.ejercicio_alta, item.fecha_alta, item.factura, item.fecha, item.importe, item.folio_control))

    /*val result = for {
      r <- db.run(Save)
      p <- getPedido(PedidoId ( no_pedido = pedido.no_pedido, compra = pedido.compra, ejercicio = pedido.ejercicio) )
    } yield (p._1, "saved")
    result*/
    //db.run(Save).map( p => (Seq(item), "saved"))
    for {
      _ <- db.run(Save).map( p => (Seq(item), "saved"))
      lst <- ById(IdSolicitudPago(folio = item.folio, ejercicio = item.ejercicio))
    } yield (lst)
  }

  def insert(item: DetSolicitudPago): Future[(Seq[DetSolicitudPago], String)] = for {
      _ <- db.run(query += item).map(p => (Seq(item), "inserted"))
      lst <- ById( IdSolicitudPago(folio = item.folio, ejercicio = item.ejercicio))
    } yield (lst)

  def delete(item: DetSolicitudPago): Future[(Seq[DetSolicitudPago], String)] = {
    val Delete = query.filter( rcd => rcd.folio === item.folio && rcd.ejercicio === item.ejercicio && rcd.renglon === item.renglon)
    for {
      _ <- db.run(Delete.delete).map(p => (Seq(item), "borrado"))
      lst <- ById( IdSolicitudPago(folio = item.folio, ejercicio = item.ejercicio))
    } yield (lst)
  }

  def deleteById(id: IdSolicitudPago): Future[(Seq[DetSolicitudPago], String)] = {
    val Delete = query.filter( rcd => rcd.folio === id.folio && rcd.ejercicio === id.ejercicio)
      db.run(Delete.delete).map(p => (Seq(DetSolicitudPago()), "borrado"))
  }


  /*def delete(id: IdSolicitudPago) : Future[Int] = {
    val Delete = query.filter( rcd => rcd.folio === id.folio && rcd.ejercicio === id.ejercicio)
    db.run(Delete.delete)
  }*/

}