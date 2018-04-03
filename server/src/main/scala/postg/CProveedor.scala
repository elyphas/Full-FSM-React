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

import scala.language.postfixOps

//import java.text.SimpleDateFormat;
//import java.sql.Date
//import java.util._
//import boopickle.Default._

//import spatutorial.shared.Fechas

class CProveedores @Inject()( @NamedDatabase("sicap")
                          protected val dbConfigProvider: DatabaseConfigProvider
                        ) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val query = TableQuery[tabla]

  private class tabla(tag: Tag) extends Table[Proveedor](tag, "tblprov") {
    def cve_provedor          = column[String]("cve_provedor")
    def razon_social          = column[String]("razon_social")
    def propietario           = column[Option[String]]("propietario")
    def calle                 = column[Option[String]]("calle")
    def colonia               = column[Option[String]]("colonia")
    def delegacion            = column[Option[String]]("delegacion")
    def cp                    = column[Option[String]]("cp")
    def ciudad                = column[Option[String]]("ciudad")
    def telefonos             = column[Option[String]]("telefonos")
    def fax                   = column[Option[String]]("fax")
    def observaciones         = column[Option[String]]("observaciones")
    def activo                = column[Boolean]("activo")
    def elaboro               = column[Option[String]]("elaboro")
    def giro                  = column[Option[String]]("giro")
    def descuento             = column[Option[String]]("descuento")

    override def * = (cve_provedor, razon_social,propietario,calle,colonia,delegacion,cp,ciudad,telefonos,fax,observaciones,activo,elaboro,giro,descuento)<> (Proveedor.tupled, Proveedor.unapply)
  }

  def ById(key: String): Future[Seq[Proveedor]] = {
    val Search = query.filter(_.cve_provedor === key)
    db.run(Search.result)
  }

  def addNew(item: Proveedor): Future[Seq[Proveedor]] = db.run(query += item).map(p => Seq(item))

  def updateProveedor(item: Proveedor): Future[Seq[Proveedor]] = {
    val guardar = query
          .filter(_.cve_provedor === item.cve_provedor)
          .map(p => (p.cve_provedor, p.cve_provedor, p.razon_social, p.propietario, p.calle,
                p.colonia, p.delegacion, p.cp, p.ciudad, p.telefonos, p.fax, p.observaciones, p.activo,
                p.elaboro, p.giro, p.descuento))
          .update((
              item.cve_provedor, item.cve_provedor, item.razon_social, item.propietario, item.calle,
              item.colonia, item.delegacion, item.cp, item.ciudad, item.telefonos, item.fax,
              item.observaciones, item.activo, item.elaboro, item.giro, item.descuento))


    val result = for {
      _ <- db.run(guardar)
      prov <- ById(item.cve_provedor)
    } yield { prov }

    result

  }

  def deleteProveedor(Id: String) : Future[Int] = {
    val Delete = query.filter( _.cve_provedor === Id )
    db.run(Delete.delete)
  }

  def searchByDescripcion(str: String): Future[Seq[Proveedor]] = {

    //val words = str.split(" ")

    val wordsCond = for {
      word <- str.split(" ")
    } yield ( "%" + word + "%")

    val Search = query.filter { item =>
      wordsCond.map( w => item.razon_social like w ).reduceLeft(_ && _)
    }

    db.run(Search result) //println(Search.result.statements.headOption) //imprimir consulta sql
  }


}