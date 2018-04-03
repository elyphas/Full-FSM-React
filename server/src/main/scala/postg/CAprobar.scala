package postg

import spatutorial.shared._
import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
//import scala.util.{Failure,Success}
import com.google.inject.Singleton
//import play.api.libs.concurrent.Execution.Implicits.defaultContext

//*****************************     quitar luego
import scala.concurrent._
import scala.concurrent.duration._
//****************************************

class CAprobar @Inject()( protected val detPedAlmacen: CDetPedAlmacen,
                          protected val pedidoAlmacen: CPedidoAlmacen,
                          protected val pedido: CPedidoEnviarAlmacen,
                          protected val detPed: CDetPed) {

	def aprobar(item: EnviarAlmacen): Future[Boolean] = for {
        p <- pedido.getData(item)
        delPedAlmacen <- pedidoAlmacen.deletePed(item)
        pedAlma <- pedidoAlmacen.insertPed(p.head)
        d <- detPed.getData(item)
        detAlma <- detPedAlmacen.insertDetalles(d)
      } yield (true)

      //println(Await.result(resultado, 20 seconds))

      /*pedido.getData( item ).onComplete {
          case Success( result ) => {
              println( "encontro el pedido" )
              result.map { p =>
                  println( "Debería insertarlo!!" + p )
                  pedidoAlmacen.deletePed( EnviarAlmacen(no_pedido = item.no_pedido, compra = item.compra, ejercicio = item.ejercicio, fechaAprobado = item.fechaAprobado)
                    ).onComplete {
                      case Success(result) => {
                          pedidoAlmacen.insertPed(p).onComplete {
                              case Success(resultAlmacen) => {
                                  detPed.getData(item).onComplete {
                                      case Success(result2) =>  detPedAlmacen.insertDetalles(result2)
                                      case Failure(er) => er.printStackTrace
                                  }
                                  println("Agregado")
                              }
                              case Failure(er) => er.printStackTrace
                          }
                      }
                      case Failure(er) => er.printStackTrace
                  }
              }
          }
          case Failure(er) => {
              println("anda fallando")
              er.printStackTrace
          }
      }*/

      //true
	//}
}



