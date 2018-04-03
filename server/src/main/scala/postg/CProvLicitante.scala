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

//import java.text.SimpleDateFormat;
//import java.sql.Date
//import java.util._
//import boopickle.Default._

//import spatutorial.shared.Fechas

import postg.CLicitantes

import scala.language.postfixOps

class CProvLicitante @Inject()( @NamedDatabase("sicap")
                          protected val dbConfigProvider: DatabaseConfigProvider,
                          licitantes: CLicitantes
                        ) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val query = TableQuery[tabla]

  private class tabla(tag: Tag) extends Table[ProvLicitante](tag, "proveedores") {
    def id_comparativo        = column[String]("id_comparativo")
    def rfc                   = column[String]("rfc")
    def plazo                 = column[String]("plazo")
    def pago                  = column[String]("pago")
    def garantia              = column[String]("garantia")
    def todo                  = column[Boolean]("todo")
    def descalificado         = column[Boolean]("descalificado")

    override def * = (id_comparativo, rfc, plazo, pago, garantia, todo, descalificado)<> (ProvLicitante.tupled, ProvLicitante.unapply)
  }

  def ById(id: String): Future[(ProvLicitante, String)]  = {
    db.run(query.filter(_.id_comparativo === id).result.map((p => (p.head, "found") )))
  }

  //def addNew(item: ProvLicitante): Future[ProvLicitante] = db.run(query += item).map(p => item)

  def insert(item: ProvLicitante): Future[(Seq[Licitante], String)] = {
    val retur = for {
        _ <- db.run(query += item) //.map(p => Seq(item))
        lic <- licitantes.ById(item.id_comparativo)
    } yield ((lic._1, "guardado"))
    retur
  }

  def delete(item: ProvLicitante): Future[(Seq[Licitante], String)] = {
    val Delete = query.filter( r => r.id_comparativo === item.id_comparativo && r.rfc === item.rfc)

    val retur = for {
      _ <- db.run(Delete.delete)
      lic <- licitantes.ById(item.id_comparativo)
    } yield ((lic._1, "borrado"))
    retur
  }

}