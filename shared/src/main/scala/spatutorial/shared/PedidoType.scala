package spatutorial.shared




//import boopickle.DefaultBasic._
import boopickle.Default._

import spatutorial.shared.DetallePedido

case class Fechas(fecha: String)

case class Hora(hora: String)

case class PedidoId(no_pedido: String, compra: String, ejercicio: Int)

case class Pedido(
                   no_pedido: String = "",
                   compra: String = "",
                   ejercicio: Int = 2018,
                   rfc_dependencia: String = "SES890417TX8",
                   fecha_pedido: Fechas = Fechas(fecha = ""),
                   cve_provedor: String = "",
                   cve_depto: String = "",
                   cve_presup: String = "",
                   fecha_entrega: String = "",
                   no_sol_compra: String = "",
                   tipo_compra: String = "",
                   destino: String = "",
                   elaboro: String = "",
                   iva: Boolean = true,
                   capturo: String = "",
                   id_comparativo: String = "",
                   cancelado: Option[Boolean] = None,
                   obsercancel: Option[String] = None,
                   ejercicio_presupuesto: Int = 2018
                 )

case class PedidoAlmacen(
									 no_pedido: String = "",
									 compra: String,
									 fecha_pedido: java.sql.Date,
									 tipo_compra: String = "",
									 cve_depto: String = "",
									 destino: String = "",
									 cve_provedor: String = "",
									 razon_social: String = "",
									 programa: String ,
									 descripcion_programa: String,
									 fuente_financiamiento:String,
									 descripcion_fuente: String,
									 //fecha_entrega: String,
									 //no_sol_compra: String,
									 ejercicio: Int
								 )

case class DetPedidoAlmacen (
											no_pedido: String,
											compra: String,
											ejercicio: Int,
											partida: String,
											descripcion_partida: String,
											cve_articulo: String,
											descripcion_articulo: String,
											unidad: String,
											cantidad: Int,
											iva: Int,
											precio: Double,
											marcas: Option[String],
											anexo: Option[String],
											subtotal: Double
										)


/*case class DetPedidoToAlmacen(
															 noPedido: String,
															 compra: String,
															 ejercicio: Int,
															 partida: String,
															 descripcion_partida: String,
															 cve_articulo: String,
															 descripcion_articulo: String,
															 unidad: String,
															 cantidad: Int,
															 iva: Int,
															 precio: Double,
															 marcas: Option[String],
															 anexo: Option[String],
															 subtotal: Double
														 )*/


case class QryPedidoDatosGrales(
									 	no_pedido: String = "",
									 	compra: String = "PEDI",
									 	ejercicio: Int = 2018,
										//numero_contrato: Option[String] = None,
									 	fecha_pedido: Fechas = Fechas(fecha = ""),
										fecha_entrega: String = "",
										elaboro: String = "",
										cve_depto: String = "",
										destino: String = "",
										programa: String = "",
										fuente: String = "",
										no_sol_compra: String = "",
										tipo_compra: String = "",

									 	proveedor: String = "",
										calle: Option[String] = None,
										colonia: Option[String] = None,
										ciudad: Option[String] = None,
										telefonos: Option[String] = None,

									 	cancelado: Option[Boolean] = None,
										obsercancel: Option[String] = None,
									 	ejercicio_presupuesto: Int = 2018,

										id_comparativo: String = ""
							)


case class Pedido2(
										no_pedido: String = "",
										compra: String = "",
										ejercicio: Int = 2018,
										rfc_dependencia: String = "SES890417TX8",
										fecha_pedido: Long = 0L,
										cve_provedor: String = "",
										cve_depto: String = "",
										cve_presup: String = "",
										fecha_entrega: String = "",
										no_sol_compra: String = "",
										tipo_compra: String = "",
										destino: String = "",
										elaboro: String = "",
										iva: Boolean = true,
										capturo: String = "",
										id_comparativo: String = "",
										cancelado: Option[Boolean] = None,
										obsercancel: Option[String] = None,
										//observacion_a_modificacion | character varying(10000)
										ejercicio_presupuesto: Int = 2018
									)