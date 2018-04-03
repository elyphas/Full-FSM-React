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

//import scala.concurrent.Await
//import scala.concurrent.duration._

import java.text.SimpleDateFormat

//import java.sql.Date
//import java.util._

//import postg.CPedido._

class CCalendario @Inject()( @NamedDatabase("sicap") protected val dbConfigProvider: DatabaseConfigProvider,
													 	pedido: CPedido,
												 		lastID: CLastIDProceso
												 )
													extends HasDatabaseConfigProvider[JdbcProfile] {

	import profile.api._

	private val tblCalendario = TableQuery[tblCalendario]


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


	implicit val horaColumnType = MappedColumnType.base[Hora, java.sql.Time](
		{hora =>
			val format = new SimpleDateFormat("hh:mm")
			val parsed = format.parse(hora.hora)
			new java.sql.Time(parsed.getTime())			//Date(parsed.getTime());
		},
		{sql =>
			val df = new SimpleDateFormat("hh:mm a")
			val text = df.format(sql)
			Hora(hora = text)
		}
	)


	private class tblCalendario(tag: Tag) extends Table[Calendario](tag, "tblcalendario_licitacion") {

		def id_comparativo 										= column[String]("id_comparativo")
		def fecha_junta_aclaraciones    			= column[Fechas]("fecha_junta_aclaraciones")
		def hora_junta_aclaraciones						= column[Hora]("hora_junta_aclaraciones")
		def observacion_junta_aclaraciones		= column[String]("observacion_junta_aclaraciones")
		def fecha_apertura_tecnica						= column[Fechas]("fecha_apertura_tecnica")
		def hora_apertura_tecnica							= column[Hora]("hora_apertura_tecnica")
		def observacion_aper_tecnica					= column[String]("observacion_aper_tecnica")
		def fecha_apertura_economica					= column[Fechas]("fecha_apertura_economica")
		def hora_apertura_economica						= column[Hora]("hora_apertura_economica")
		def observacion_aper_econ							= column[String]("observacion_aper_econ")

		def fecha_fallo												= column[Fechas]("fecha_fallo")
		def hora_fallo												= column[Hora]("hora_fallo")
		def observacion_fallo									= column[String]("observacion_fallo")

		def fecha_firma_contrato							= column[Fechas]("fecha_firma_contrato")
		def hora_firma_contrato								= column[Hora]("hora_firma_contrato")
		def observacion_firma_contrato				= column[String]("observacion_firma_contrato")

		def fecha_inicio_disponible_base			= column[Fechas]("fecha_inicio_disponible_base")
		def fecha_final_disponible_base				= column[Fechas]("fecha_final_disponible_base")

		def * = (id_comparativo, fecha_junta_aclaraciones,	hora_junta_aclaraciones, observacion_junta_aclaraciones, fecha_apertura_tecnica, hora_apertura_tecnica, observacion_aper_tecnica, fecha_apertura_economica, hora_apertura_economica, observacion_aper_econ, fecha_fallo, hora_fallo, observacion_fallo, fecha_firma_contrato, hora_firma_contrato, observacion_firma_contrato, fecha_inicio_disponible_base, fecha_final_disponible_base) <> (Calendario.tupled, Calendario.unapply)
	}

	def ListAll: Future[Seq[Calendario]] = db.run(tblCalendario.result)

	def ById(id: String): Future[(Seq[Calendario], String)] =
		db.run(tblCalendario.filter(_.id_comparativo === id).result.map((p=>(p,"found"))))

	//Return a data type and a Event; (found, saved, inserted, deleted, not found)
	def insert(item: Calendario): Future[(Seq[Calendario], String)] = {
		db.run(tblCalendario += item).map(p => (Seq(item), "inserted"))
	}

	def save(item: Calendario): Future[(Seq[Calendario], String)] = {
		val updateDatos = tblCalendario.filter(_.id_comparativo === item.id_comparativo)
			.map( d => (
				d.fecha_junta_aclaraciones,
				d.hora_junta_aclaraciones,
				d.observacion_junta_aclaraciones,
				d.fecha_apertura_tecnica,
				d.hora_apertura_tecnica,
				d.observacion_aper_tecnica,
				d.fecha_apertura_economica,
				d.hora_apertura_economica,
				d.observacion_aper_econ,
				d.fecha_fallo,
				d.hora_fallo,
				d.observacion_fallo,
				d.fecha_inicio_disponible_base,
				d.fecha_final_disponible_base
			))
			.update((
				item.fecha_junta_aclaraciones,
				item.hora_junta_aclaraciones,
				item.observacion_junta_aclaraciones,
				item.fecha_apertura_tecnica,
				item.hora_apertura_tecnica,
				item.observacion_aper_tecnica,
				item.fecha_apertura_economica,
				item.hora_apertura_economica,
				item.observacion_aper_econ,
				item.fecha_fallo,
				item.hora_fallo,
				item.observacion_fallo,
				item.fecha_inicio_disponible_base,
				item.fecha_final_disponible_base
			))

		db.run(updateDatos).map(p => (Seq(item), "saved"))
	}

	def delete(Id: String) : Future[(Int, String)] = {
		val Delete = tblCalendario.filter(_.id_comparativo === Id)
		db.run(Delete.delete).map(p=> (p, "deleted"))
	}

	def calcularDias_aPartirDe(fecha: String): Future[Int] = {
		import java.time.{LocalDate, Period}
		val hoy = LocalDate.now()

		import java.time.format.DateTimeFormatter

		val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
		val date: LocalDate = LocalDate.parse("24/07/2017", formatter)
		val totalDias: Int = Period.between(date, hoy).getDays

		Future(totalDias)
	}


}