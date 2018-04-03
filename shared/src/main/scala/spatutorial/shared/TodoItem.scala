package spatutorial.shared



import boopickle.Default._

import spatutorial.shared.Hora

/*import scalaz._
import syntax.validation._
import syntax.semigroup._
import Validation._
import std.AllInstances._
import std.option._*/

//case class Aprobar(noPedido:String, compra:String, ejercicio:Int, fechaAprobado:String)

case class EnviarAlmacen(no_pedido: String, compra: String, ejercicio: Int, fechaAprobado: String = "01/01/2018")

object Event extends Enumeration {
	val Found, Saved, Inserted, Deleted, NotFound = Value
}

case class ActiveID(
									 id_comparativo: String = "",
									 programa: String = ""
									 )

case class Programa(
									 programa: String = "",
									 descripcion: String = "",
									 destino: String = "",
									 depto: String = "1221",
									 mostrar: Boolean = true,
                   rfc_dependencia: String = "SES890417TX8",
									 nivel: String = "ESTATAL",
									 encargado: Option[String] = None,
									 activo: Boolean = true,
									 fuente_financiamiento: String = "")

case class Fuente(fuente: String, descripcion: String, observaciones: String)

case class PartidaAnexo(
												 id_comparativo: String = "",
												 partida: String = "",
												 descripcion: String = "")

case class DescripcionCompra(
															id_comparativo: String = "",
															concepto: String = "",
															descripcion: String = "")

case class Dia_Inhabil(
												fecha: Fechas = Fechas(fecha = "01/01/2017"),
												observacion: String = "")

case class Proceso(
								id_comparativo: String = "",
								fecha: Fechas = Fechas(fecha = ""),
								memo: String = "",
								destino: String = "",
								cveOficina: String = "",
								tipoCompra: String = "",
								iva: Boolean = true,
								plazo: String = "30 DIAS HABILES",
								elaboro: String = "",
								fechaPedido: Fechas = Fechas(fecha = "01/01/2017"),
								fin: Boolean = true,
								programa: String = "",
								rfcDependencia: String = "",
								folio: Int = 0,
								ejercicio: Int = 2017,
								ejercicioPresupuestal: Int = 2017) {
	/*def validate = {
			import scalaz.Scalaz._
			val Elabora = if (elaboro == "") "elaboro no debe estar vacío".failureNel[String] else "valido".successNel[String]
			val Fecha = if (fecha == "") "fecha no debe estar vacío".failureNel[String] else "valido".successNel[String]
			val Ejercicio = if (ejercicio < 2016 && ejercicio > 2030) "ejercicio incorrecto".failureNel[String] else "valido".successNel[String]
			(Elabora |@| Fecha |@| Ejercicio){_+_+_}
	}*/
}

case class DatosGralesRequisicion( cve_oficina: String = "",
																	 folio: Int = 0,
																	 fecha: Fechas = Fechas(fecha = ""),
																	 ejercicio: Int = 0,
																	 ejercicio_presupuestal: Int = 0,
																	 fuente_financiamiento: String = "",
																	 programa: String = "",
																	 destino: String = "",
																	 observaciones: String	= "")

case class LastID(ejercicio: Int = 0, lastID: Int = 0)

case class Oficina(	clave: String = "",
									 	descripcion: String = "",
									 	firma: Option[String] = None,
									 	cargo: Option[String] = None
									)

case class Articulo(
	          cve_articulo: String = "",
	          descripcion:String = "",
						unidad: String = "",

						presentacion: Option[Int] = None,
						unid_med_pres: Option[String] = None,
						partida: String = "",
						clave_cabms: Option[String] = None,
						cb: Option[Boolean] = None,
						iva: Option[Double] = None,
						baja: Option[Boolean] = None
				)

case class Partida (	cve_partida: String = "",
                      descripcion: Option[String] = None,
											observaciones: Option[String] = None,
	                    presupuesto: Option[Double] = None,
                      activo: Option[Boolean] = None)

case class Presentacion(	unidad: String,
                       		presentacion: Option[Double],
                       		unidad_present: Option[String],
                       		observaciones: Option[String])

case class DetPedido(	no_pedido: String = "",
											compra: String = "PEDI",
											ejercicio: Int = 2017,
											fecha_pedido: String = "",
											cve_articulo: String = "",
											cantidad: Int = 0,
											precio: Double = 0.0,
											anexo: Option[String] = Some(""),
											marcas: Option[String] = Some(""),
											año: Int = 2017,
											id_comparativo: String = "",
											modelo: Option[String] = Some(""))

case class DetallePedido(	id_comparativo: String = "",
													no_pedido: String = "",
													compra: String = "",
													ejercicio: Int = 0,
													partida: String = "",
													descripcion_partida: String = "",
													cve_articulo: String = "",
													descripcion_articulo: String = "",
													unidad: String = "",
													iva: Int = 0,
													cantidad: Int = 0,
													precio: Double = 0.0,
													subtotal: Double = 0.0,
													iva_Monto: Double = 0.0,
													total: Double = 0.0)

//descripcion del renglon como debe aparecer en un listado de bienes.
case class RenglonListBienes(
							id_comparativo: String = "",
							renglon: Int = 0,
							programa: String = "",
							cve_articulo: String = "",
							descripcion: String = "",
							unidad: String = "",
							cantidad: Int = 0,
							precio_referencia: Double = 0.0,
							importe: BigDecimal = 0.0,
							iva: Double = 0.0,
							total: Double = 0.0)

case class Renglon(	id_comparativo: String,
										 programa: String,
										 renglon: Int,
										 cve_articulo: String,
										 memo: String,
										 entrega: Int,
										 cantidad: Int,
										 anexo: String, marca: String, modelo: String, cancelado: Boolean, tipo: Int)

case class ListadoBienes(items: Seq[RenglonListBienes]) {
	/*def updated(newItem: RenglonListBienes) = {
				ListadoBienes(items :+ newItem)
	}*/

	def remove(item: RenglonListBienes) = ListadoBienes(items.filterNot(_ == item))

	def add(newItem: RenglonListBienes) = {
		val total = items :+ newItem
		ListadoBienes(items = total)
	}

}


case class Usuarios(
							usuario: String = "",
							contraseña: String = "",
							tipo: String = "",
							nombre: String = "",
							area: String = "",
							activo: Boolean = true,
							nivel: Int = 0
		)

case class Proveedor(
					cve_provedor: String = "",
					razon_social: String = "",
					propietario: Option[String] = None,
					calle: Option[String] = None,
					colonia: Option[String] = None,
					delegacion: Option[String] = None,
					cp: Option[String] = None,
					ciudad: Option[String] = None,
					telefonos: Option[String] = None,
					fax: Option[String] = None,
					observaciones: Option[String] = None,
					activo: Boolean = true,
					elaboro: Option[String] = None,
					giro: Option[String] = None,
					descuento: Option[String] = None)

case class Calendario (
			id_comparativo: String = "", 															//     	| character varying(50)  | not null
			fecha_junta_aclaraciones: Fechas = Fechas(fecha = ""), 		//		 	| date                   | not null
			hora_junta_aclaraciones: Hora = Hora(hora = ""),	 				//			| time without time zone | not null
			observacion_junta_aclaraciones: String = "", 										//			| character varying      |

			fecha_apertura_tecnica: Fechas = Fechas(fecha = ""),  		//			| date                   | not null
			hora_apertura_tecnica: Hora = Hora(hora = ""),						//			| time without time zone | not null
			observacion_aper_tecnica: String = "", 										//			| character varying      |

			fecha_apertura_economica: Fechas = Fechas(fecha = ""),		//			| date                   | not null
			hora_apertura_economica: Hora = Hora(hora = ""),					//			| time without time zone | not null
			observacion_aper_econ: String = "",												//    	| character varying      |

			fecha_fallo: Fechas  = Fechas(fecha = ""),								//			| date                   | not null
			hora_fallo: Hora = Hora(hora = ""),												//			| time without time zone | not null
			observacion_fallo: String = "",														//			| character varying

			fecha_firma_contrato: Fechas  = Fechas(fecha = ""),				//			| date                   | not null
			hora_firma_contrato: Hora = Hora(hora = ""),							//			| time without time zone | not null
			observacion_firma_contrato: String = "",										//			| character varying

			fecha_inicio_disponible_base:  Fechas  = Fechas(fecha = ""),
			fecha_final_disponible_base: Fechas  = Fechas(fecha = "")

)


case class Licitante (
				id_comparativo: String = "",
				cve_provedor: Option[String] = None,
				razon_social: Option[String] = None,
				propietario: Option[String] = None,
				calle: Option[String] = None,
				colonia: Option[String] = None,
				delegacion: Option[String] = None,
				cp: Option[String] = None,
				ciudad: Option[String] = None,
				telefonos: Option[String] = None,
				fax: Option[String] = None,
				/*fecha_invitacion: Fechas = Fechas(fecha = ""),*/
				folio: Option[String] = None
		)

case class ProvLicitante (
				id_comparativo: String = "",
				rfc: String = "",
				plazo: String = "",
				pago: String = "",
				garantia: String = "",
				todo: Boolean = false,
				descalificado: Boolean = false
)

case class RenglonDictamen (
				id_comparativo: String = "",
				rfc: String = "",
				razon_social: String = "",
				renglon: Int = 0,
				cve_articulo: String = "",
				descripcion: String = "",
				unidad: String = "",
				cantidad: Int = 0,
				precio: Double = 0.0,
				importe: Double = 0.0,
				minimo: Double = 0.0,
				precio_referencia: Double = 0.0,
				incremento: Double = 0.0,
				descalificado: Boolean = false,
				precio_index: Double = 0.0
)


case class TotalCotizacion(
				id_comparativo: String = "", 		//| character varying(50) |
				rfc: String = "",          			//| character varying(13) |
				razon_social: String = "",
				monto: Double = 0          			//| double precision      |
	)


case class OficioPresupuesto (
				id_comparativo: String = "", 									//character varying(50) NOT NULL,
				numero: String = "",													//character varying(20) NOT NULL,
				fecha: Fechas = Fechas(fecha = "01/01/2017"), 																				//date NOT NULL,
				fecha_recibido: Fechas = Fechas(fecha = "01/01/2017"), 									//date NOT NULL,
				monto: Double = 0,
				observaciones: String = "" 									//character varying(5000),
		)

case class Seguimiento (
				 id_comparativo: String = "",
				 elaboro: String = "",
				 pedido: Option[String] = None,
				 fecha: Fechas = Fechas(fecha = "01/01/2017"),
				 tipo_compra: Option[String] = None,
				 compra: Option[String] = None,
				 no_sol_compra: Option[String] = None,
				 cve_presup: Option[String] = None,
				 dias: String = "",
				 cotizaciones: Option[Int] = None
		)


case class IdSolicitudPago (
				folio: String = "",
				ejercicio: Int = 0
)

case class SolicitudPago(
				folio: String = "",
				ejercicio: Int = 2018,
				fecha: Fechas = Fechas(fecha = "01/01/2018"),
				fecha_plazo_entrega: Option[Fechas] = Some(Fechas(fecha = "01/01/2018")),
				id_comparativo: String = "",
				elaboro: String = "",
				folio_recibido_recurs_mat: String = "",
				//dias_habiles: Int = 0,
				sancion: Boolean = false,
				fecha_recibido_rec_finan: Fechas = Fechas(fecha = "01/01/2018")
)

case class DetSolicitudPago(
				folio: String = "",
				ejercicio: Int = 0,
				renglon: Int = 0,

				alta_almacen: String = "", 																	//character varying(4) NOT NULL,
				ejercicio_alta: Int = 2018, 																		//integer,
				fecha_alta:Fechas = Fechas(fecha = "01/01/2018"), 			//date NOT NULL,

				factura: String = "",
				fecha: Fechas = Fechas(fecha = "01/01/2018"),
				importe: Double = 0.0,
				folio_control: Option[String] = None,

				contrarecibo: String = "",
				observaciones_alta: String = ""

)

case class RptSolicitudPago(
				id_comparativo: String = "",
				folio: String = "",
				ejercicio: Int = 0,
				fecha: Fechas = Fechas(fecha = "01/01/2018"),
				alta_almacen: String = "",
				fecha_alta: Fechas = Fechas(fecha = "01/01/2018") ,
				no_pedido: String = "",
				fecha_pedido: Fechas = Fechas(fecha = "01/01/2018"),
				elaboro: String = "",
				ejercicio_pedido: Int = 0,
				ejercicio_presupuesto: Int = 0,
				tipo_compra: String = "",
				requerimiento: String = "",
				oficina: String = "",
				destino: String = "",
				cve_provedor: String = "",
				razon_social: String = "",
				programa: String = "",
				partida: String = "",
				subtotal: Double = 0.0,
				iva: Double = 0.0,
				total: Double = 0.0
)


case class RptSolicitudPago2(
				 id_comparativo: String = "",															//1
				 folio: String = "",																			//2
				 ejercicio: Int = 0,																			//3
				 fecha: Fechas = Fechas(fecha = "01/01/2018"),						//4
				 alta_almacen: String = "",																//5
				 fecha_alta: Fechas = Fechas(fecha = "01/01/2018"),				//6
				 no_pedido: String = "",																	//7
				 fecha_pedido: Fechas = Fechas(fecha = "01/01/2018"),			//8
				 elaboro: String = "",																		//9
				 ejercicio_pedido: Int = 0,																//10
				 ejercicio_presupuesto: Int = 0,													//11
				 tipo_compra: String = "",																//12
				 requerimiento: String = "",															//13
				 oficina: String = "",																		//14
				 destino: String = "",																		//15
				 cve_provedor: String = "",																//16
				 razon_social: String = "",																//17
				 programa: String = "",																		//18
				 partida: String = "",																		//19
				 subtotal: Double = 0.0,																	//20
				 iva: Double = 0.0,																				//21
				 total: Double = 0.0																			//22
	)

case class SolicPago_DataExtra(
				ejercicio: String = "",
				folio: String = "",
				id_comparativo: String = "",
				fecha_entrega_controlpatrimonial: Fechas = Fechas(fecha = "01/01/2018")
	)

case class PrecioReferencia(
				cve_articulo: String = "",
				precio_referencia: Double = 0.0,
				rfc: String = "",
				razon_social: String = ""
	)
