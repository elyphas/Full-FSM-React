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

import java.text.SimpleDateFormat;
//import java.sql.Date
//import java.util._
//import boopickle.Default._

import spatutorial.shared.Fechas

class CLicitantes @Inject()( @NamedDatabase("sicap")
                          protected val dbConfigProvider: DatabaseConfigProvider
                        ) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val query = TableQuery[tabla]

  implicit val fechasColumnType = MappedColumnType.base[Fechas, java.sql.Date](
    {fecha =>
      val format = new SimpleDateFormat("dd/mm/yyyy")
      val parsed = format.parse(fecha.fecha)
      new java.sql.Date(parsed.getTime());
    },
    {sql =>
      val df = new SimpleDateFormat("dd/MM/yyyy")
      val text = df.format(sql)
      Fechas(fecha = text)
    }
  )

  private class tabla(tag: Tag) extends Table[Licitante](tag, "qry_solicitud_cotizacion") {
    def id_comparativo        = column[String]("id_comparativo")
    def cve_provedor          = column[Option[String]]("cve_provedor")
    def razon_social          = column[Option[String]]("razon_social")
    def propietario           = column[Option[String]]("propietario")
    def calle                 = column[Option[String]]("calle")
    def colonia               = column[Option[String]]("colonia")
    def delegacion            = column[Option[String]]("delegacion")
    def cp                    = column[Option[String]]("cp")
    def ciudad                = column[Option[String]]("ciudad")
    def telefonos             = column[Option[String]]("telefonos")
    def fax                   = column[Option[String]]("fax")
    /*def fecha_invitacion      = column[Option[Fechas]]("fecha_invitacion")*/
    def folio                 = column[Option[String]]("plazo")

    override def * = (id_comparativo, cve_provedor, razon_social, propietario, calle, colonia, delegacion, cp, ciudad, telefonos, fax/*, fecha_invitacion*/, folio)<> (Licitante.tupled, Licitante.unapply)
  }

  def ById(id: String): Future[(Seq[Licitante], String)] =
    db.run(query.filter(_.id_comparativo === id).result.map((p => (p, "found") )))

  def delete(Id: String) : Future[Int] = {
    val Delete = query.filter( _.cve_provedor === Id )
    db.run(Delete.delete)
  }

}