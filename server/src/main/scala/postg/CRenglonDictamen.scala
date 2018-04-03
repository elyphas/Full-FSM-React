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

class CRenglonDictamen @Inject()( @NamedDatabase("sicap")
                          protected val dbConfigProvider: DatabaseConfigProvider
                        ) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val query = TableQuery[tabla]

  private class tabla(tag: Tag) extends Table[RenglonDictamen](tag, "qry_dictamen_2") {
    def id_comparativo        = column[String]("id_comparativo")
    def rfc                   = column[String]("rfc")
    def razon_social          = column[String]("razon_social")
    def renglon               = column[Int]("renglon")
    def cve_articulo          = column[String]("cve_articulo")
    def descripcion           = column[String]("descripcion")
    def unidad                = column[String]("unidad")
    def cantidad              = column[Int]("cantidad")
    def precio                = column[Double]("precio")
    def importe               = column[Double]("importe")
    def minimo                = column[Double]("minimo")
    def precio_referencia     = column[Double]("precio_referencia")
    def incremento            = column[Double]("incremento")
    def descalificado         = column[Boolean]("descalificado")
    def precio_index          = column[Double]("precio_index")

    override def * = (id_comparativo, rfc, razon_social, renglon, cve_articulo, descripcion, unidad, cantidad, precio, importe, minimo, precio_referencia, incremento, descalificado, precio_index)<> (RenglonDictamen.tupled, RenglonDictamen.unapply)
  }

  def ById(id: String): Future[(Seq[RenglonDictamen], String)] =
    db.run(query.filter(_.id_comparativo === id).result.map((p =>(p, "found"))))
}