package postg

//import akka.japi.function.Procedure

import spatutorial.shared._

import scala.concurrent.Future
//import scala.util.{Failure,Success}
import scala.concurrent.ExecutionContext.Implicits.global

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.db.NamedDatabase
//import slick.driver.JdbcProfile
import slick.jdbc.JdbcProfile

//import scala.language.postfixOp
import scala.language.postfixOps

class CArticulo @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider )
extends HasDatabaseConfigProvider[JdbcProfile] {

  //import driver.api._
  import profile.api._

  private val query = TableQuery[tabla]

  private class tabla(tag: Tag) extends Table[Articulo](tag, "tblartic") {
    def cve_articulo            = column[String]("cve_articulo")
    def descripcion             = column[String]("descripcion")
    def unidad                  = column[String]("unidad")
    def presentacion           	= column[Option[Int]]("presentacion")
    def unid_med_pres       		= column[Option[String]]("unid_med_pres")
    def partida                 = column[String]("partida")
    def clave_cabms         		= column[Option[String]]("clave_cabms")
    def cb                      = column[Option[Boolean]]("cb")
    def iva                     = column[Option[Double]]("iva")
    def baja                    = column[Option[Boolean]]("baja")

    def * = (cve_articulo, descripcion, unidad, presentacion, unid_med_pres, partida, clave_cabms, cb, iva, baja )<> (Articulo.tupled, Articulo.unapply)
  }

  def ListAll: Future[Seq[Articulo]] = db.run(query.result)

  def ById(id: String) : Future[Seq[Articulo]] = {
    val Search = query filter(_.cve_articulo === id)
    db.run(Search.result)
  }

  def createCve(partida: String): String = partida match {
    case "25301" => ""
    case s => {
      val (fst, snd) = s.splitAt(2)
      implicit val part = "0" + fst + '.' + snd + '.'
      getLastConsecut
    }
    //case _=> ""
  }

  def getLastConsecut( implicit part: String ): String = {
    val Search = query.filter(_.cve_articulo.startsWith(part))  sortBy(p => p.cve_articulo.asc) map (_.cve_articulo)
    //val consec = db.run(Search result)
    //.last
    "todavía No"
    //part + (Consec.takeRight(4).toInt + 1)
  }


  def getDescripcion(clave: String): Future[Seq[String]] = {
    val Search = query.filter(_.cve_articulo === clave) map (_.descripcion)
    db.run(Search.result)
  }


  def searchDescripcion(str: String): Future[Seq[Articulo]] = {
    //val words = str.split(" ")

    val wordsCond = for {
      word <- str.split(" ")
    } yield ( "%" + word + "%")

    val Search = query.filter { item =>
        wordsCond.map( w => item.descripcion like w ).reduceLeft(_ && _)
    }	//.filter( art => art.partida === "21101")

    val finalResult = db.run(Search result) //println(Search.result.statements.headOption) //imprimir consulta sql

    val retornar = for {
      r <- finalResult
    } yield {
        if (r.length > 50) Seq(Articulo())
        else
          for {
            f <- r
          } yield (f)
    }
    retornar
  }


  def insert(art: Articulo): Future[Seq[Articulo]] = db.run(query += art).map(p => Seq(art))

  def save(art: Articulo): Future[Seq[Articulo]] ={
    val Save = query.filter(_.cve_articulo === art.cve_articulo)
      .map( a =>
        (	a.cve_articulo,
          a.descripcion,
          a.unidad,
          a.presentacion,
          a.unid_med_pres,
          a.partida,
          a.clave_cabms,
          a.cb,
          a.iva,
          a.baja
          )
      )
      .update((
        art.cve_articulo, art.descripcion,
        art.unidad, art.presentacion, art.unid_med_pres, art.partida,
        art.clave_cabms, art.cb, art.iva, art.baja))

    val returna = for {
      _ <- db.run(Save)
      articulo <- ById(art.cve_articulo)
    } yield { articulo }

    returna

  }

}