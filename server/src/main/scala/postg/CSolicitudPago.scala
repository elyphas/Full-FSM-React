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

class CSolicitudPago @Inject()( @NamedDatabase("sicap")
                          protected val dbConfigProvider: DatabaseConfigProvider,
                          protected val detSolicitudPago: CDetSolicitudPago)

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

  private class tabla(tag: Tag) extends Table[SolicitudPago](tag, "tbl_solicitud_pago") {

    def folio                       = column[String]("folio")
    def ejercicio                   = column[Int]("ejercicio")
    def fecha                       = column[Fechas]("fecha")
    def fecha_plazo_entrega         = column[Option[Fechas]]("fecha_plazo_entrega")
    def id_comparativo              = column[String]("id_comparativo")
    def elaboro                     = column[String]("elaboro")
    def folio_recibido_recurs_mat   = column[String]("folio_recibido_recurs_mat")
    //def dias_habiles                = column[Int]("dias_habiles")
    def sancion                     = column[Boolean]("sancion")
    def fecha_recibido_rec_finan    = column[Fechas]("fecha_recibido_rec_finan")

    override def * = (folio, ejercicio, fecha, fecha_plazo_entrega, id_comparativo, elaboro, folio_recibido_recurs_mat, /*dias_habiles,*/ sancion, fecha_recibido_rec_finan) <> ( SolicitudPago.tupled, SolicitudPago.unapply)
  }

  def ListAll: Future[Seq[SolicitudPago]] = db.run( query.result )

  def ById(id: IdSolicitudPago): Future[(SolicitudPago, String)] =
    db.run( query.filter{ rcd =>
      rcd.folio === id.folio && rcd.ejercicio === id.ejercicio
    }.result.map { p => if (p.isEmpty) (SolicitudPago(folio = id.folio, ejercicio = id.ejercicio), "no encontrado") else (p.head, "encontrado") })

  def ByIdComparativo(id: String): Future[(SolicitudPago, String)] =
    db.run( query.filter(_.id_comparativo === id)
      .result.map { p => if (p.isEmpty) (SolicitudPago(id_comparativo = id), "no encontrado") else (p.head, "encontrado") })


  def update(item: SolicitudPago): Future[(SolicitudPago, String)] = {

    val Save = query.filter(rcd => (rcd.folio === item.folio && rcd.ejercicio === item.ejercicio))
      .map(rcd => (rcd.folio, rcd.ejercicio, rcd.fecha, rcd.fecha_plazo_entrega, rcd.id_comparativo, rcd.elaboro, rcd.folio_recibido_recurs_mat, rcd.sancion, rcd.fecha_recibido_rec_finan))
      .update((item.folio, item.ejercicio, item.fecha, item.fecha_plazo_entrega, item.id_comparativo, item.elaboro, item.folio_recibido_recurs_mat, item.sancion, item.fecha_recibido_rec_finan))

    /*val result = for {
      r <- db.run(Save)
      p <- getPedido(PedidoId ( no_pedido = pedido.no_pedido, compra = pedido.compra, ejercicio = pedido.ejercicio) )
    } yield (p._1, "saved")
    result*/
    db.run(Save).map(p => (item, "saved"))
    //db.run(Save)
  }

  def insert(item: SolicitudPago): Future[(SolicitudPago, String)] =
    db.run(query += item).map (p => (item, "Agregado"))

  def delete(id: IdSolicitudPago): Future[Int] = {
    val Delete = query.filter(rcd => rcd.folio === id.folio && rcd.ejercicio === id.ejercicio)
    val result = for {
      _ <- detSolicitudPago.deleteById(id: IdSolicitudPago)
      r <- db.run(Delete.delete)
    } yield (r)
    result
  }

}