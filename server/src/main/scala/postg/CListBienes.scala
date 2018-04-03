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

class CListBienes @Inject()(@NamedDatabase("sicap")
													 protected val dbConfigProvider: DatabaseConfigProvider)
	extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val qryListBienes = TableQuery[tblListBienes]

	private class tblListBienes(tag: Tag) extends Table[RenglonListBienes](tag, "busca_list_licitacion") {
		def id_comparativo		= column[String]("id_comparativo")
		def renglon						= column[Int]("renglon")
		def programa					=	column[String]("programa")
		def cve_articulo			=	column[String]("cve_articulo")
		def descripcion				= column[String]("descripcion")
		def unidad						= column[String]("unidad")
		def cantidad					= column[Int]("cantidad")
		def precio_referencia	= column[Double]("precio_referencia")
		def importe						= column[BigDecimal]("importe")
		def iva								= column[Double]("iva")
		def total							= column[Double]("total")

		def * = (id_comparativo, renglon, programa, cve_articulo, descripcion, unidad, cantidad, precio_referencia, importe, iva, total)<> (RenglonListBienes.tupled, RenglonListBienes.unapply)
	}

	def ListAll: Future[Seq[RenglonListBienes]] = db.run( qryListBienes.result )

	def ById(id: String): Future[Seq[RenglonListBienes]] = {
		val Search = qryListBienes.filter(_.id_comparativo === id)
		db.run(Search.result)
	}

	def rearrangeRenglones(id: String, rengl: Int): DBIO[Int] =
		sqlu"""update claves set renglon=renglon-1 where
			id_comparativo = '#$id' and renglon > #$rengl;"""

	def DelRenglon(id: String) : Future[Int] = {
			val Delete = qryListBienes.filter(_.id_comparativo === id)
			db.run(Delete.delete)
	}

}