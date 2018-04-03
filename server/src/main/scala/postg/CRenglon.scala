package postg

import spatutorial.shared._

import scala.concurrent.Future

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.db.NamedDatabase

//import slick.driver.JdbcProfile
import slick.jdbc.JdbcProfile

import scala.language.postfixOps

class CRenglon @Inject()(@NamedDatabase("sicap")
													 	protected val dbConfigProvider: DatabaseConfigProvider,
												 		protected val lstbienes: CListBienes
												)
	extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val qryClaves = TableQuery[tblclaves]

	private class tblclaves(tag: Tag) extends Table[Renglon](tag, "claves") {

		def id_comparativo 			= column[String]("id_comparativo")
		def programa						= column[String]("programa")
		def renglon							= column[Int]("renglon")
		def cve_articulo				= column[String]("cve_articulo")
		def memo								= column[String]("memo")
		def entrega							= column[Int]("entrega")
		def cantidad						= column[Int]("cantidad")
		def anexo								= column[String]("anexo")
		def marca								= column[String]("marca")
		def modelo							= column[String]("modelo")
		def cancelado						= column[Boolean]("cancelado")
		def tipo								= column[Int]("tipo")


		//id_comparativo: String,	programa: String, renglon: Int, cve_articulo: String, memo: String, entrega: Int,
		//cantidad: Int, anexo: String, marca: String, modelo: String, cancelado: Boolean, tipo: Int


		def * = (id_comparativo, programa, renglon, cve_articulo, memo, entrega, cantidad, anexo, marca, modelo, cancelado, tipo)<> (Renglon.tupled, Renglon.unapply)
	}

	def ListAll: Future[Seq[Renglon]] = db.run(qryClaves.result)

	//PRIMARY KEY, btree (id_comparativo, programa, renglon, cve_articulo, memo, entrega)
	def ById(id: Renglon): Future[Seq[Renglon]] = {

			val Search = qryClaves.filter( l =>
									( l.id_comparativo === id.id_comparativo) &&
									(l.programa === id.programa) &&
									(l.renglon === id.renglon) &&
									(l.cve_articulo === id.cve_articulo) &&
									(l.memo === id.memo) &&
									(l.entrega === id.entrega)
						)

			db.run(Search.result)

	}

	//def addItem(item: Renglon): Future[Seq[RenglonListBienes]] = {
	def addItem(item: Renglon): Future[Seq[RenglonListBienes]] = {

		val res = for {
			_ <- db.run(qryClaves += item)				//.map(p=> Seq(item))
			lst <- lstbienes.ById(item.id_comparativo)
		} yield {lst}
		//db.run(qryListBienes += item).map(p=> ListadoBienes(items = Seq(item)))
		res
	}

	//def editItem(item: Renglon): Future[Seq[RenglonListBienes]] = {
	def editItem(item: Renglon): Future[Seq[RenglonListBienes]] = {

		val Update = qryClaves
				.filter(l =>
						(l.id_comparativo === item.id_comparativo) &&
						(l.programa === item.programa) &&
						(l.renglon === item.renglon) &&
						(l.cve_articulo === item.cve_articulo) &&
						(l.memo === item.memo) &&
						(l.entrega === item.entrega)
				).map( r => r. cantidad).update(item.cantidad)

		val res = for {
			_ <- db.run(Update)
			lst <- lstbienes.ById(item.id_comparativo)
		} yield {lst}
		res
	}

	def deleteRenglon(item: Renglon): Future[Seq[RenglonListBienes]] = {
		val Delete = qryClaves
					.filter(l =>
						(l.id_comparativo === item.id_comparativo) &&
							(l.programa === item.programa) &&
							(l.renglon === item.renglon) &&
							(l.cve_articulo === item.cve_articulo)
							//despues arreglar!!!!
							/*&&
							(l.memo === item.memo) &&
							(l.entrega === item.entrega)*/
					)

		val res = for {
			_ <- db.run(Delete delete)
			_ <- db.run(lstbienes.rearrangeRenglones(item.id_comparativo, item.renglon))
			lst <- lstbienes.ById(item.id_comparativo)
		} yield {lst}
		res
	}

	/*def deleteAllRequisicion(id: RequisicionID): Future[Int] = {
		val Delete = qryClaves filter { r =>
			(r.cve_oficina === id.cve_oficina) && (r.folio === id.folio) && (r.ejercicio === id.ejercicio)
		}
		db.run(Delete delete)
	}*/

	/*def updateNumRenglon(rng: Renglon, increase: Boolean): Future[Int] = {
		val updateRenglon = qrydetRequisicion
			.filter(l =>
				(l.cve_oficina === rng.cve_oficina) &&
					(l.ejercicio === rng.ejercicio) &&
					(l.folio === rng.folio) &&
					(l.renglon === rng.renglon ) &&
					(l.cve_articulo === rng.cve_articulo)
			).map(r => r.renglon)
			.update(r => (renglon = r.renglon + 1) )
		db.run(updateRenglon.transactionally)
	}*/

}