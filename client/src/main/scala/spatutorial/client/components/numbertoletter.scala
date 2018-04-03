package spatutorial.client.components

//import javax.swing.GroupLayout.Alignment

//import com.sun.tracing.Probe
//import diode.data.Pot
//import diode.react.ModelProxy
//import diode._
//import spatutorial.client.pdfMake._
//import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.html_<^._
//import spatutorial.client.components.Bootstrap.{Button, Combo, CommonStyle, Text}
//import spatutorial.client.components._
//import spatutorial.client.logger._
//import spatutorial.client.services._
//import spatutorial.shared._

//import scala.concurrent.Future
//import scala.util.{Failure, Success, Try}
//import scala.concurrent.duration._
//import scala.concurrent.Await
//import scalacss.ScalaCssReact._
//import chandu0101.scalajs.react.components.Implicits._

//import autowire._
//import boopickle.Default._
//import spatutorial.client.services.AjaxClient
//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

//import scalajs.js
//import js.Dynamic.{ global => g }
//import scala.scalajs.js.{|, Date => jsDate}

//import java.time._

//import java.util._      //Problem with the Map[Int, String]

//import java.text.SimpleDateFormat

//import spatutorial.client.actionhandlers.{GetQryPedidoDatGrales, GetPartidaAnexo}

//import scala.scalajs.js.JSConverters._
//import scala.scalajs.js.annotation.JSName
//import scala.scalajs.js

//https://github.com/jducoeur/bootstrap-datepicker-scalajs
//import org.querki.facades.bootstrap.datepicker._ // this adds ".datepicker" implicit

//import java.time.LocalDate
//import java.time.format.DateTimeFormatter

class numbertoletter {

  private def addComa(str: String): String = {
    val partEntera = str.substring(0, str.length - 3)
    val partDecimal = str.substring(str.length - 3, str.length)

    val miles: String = if (partEntera.length > 6) {
      val len = partEntera.length
      partEntera.substring(0, len - 6) + "," + partEntera.substring(len - 6, (len - 3)) + "," + partEntera.substring(len - 3, len)
    } else if (partEntera.length > 3) {
      partEntera.substring(0, partEntera.length - 3) + "," + partEntera.substring(partEntera.length - 3, partEntera.length)
    } else partEntera
    miles + partDecimal
  }

  private def fmtMiles(valor: BigDecimal, dec: Int = 2): String = {
    import scala.scalajs.js.JSNumberOps._
    val newValor = if (dec > 0) valor.toDouble.toFixed(dec) else valor.toDouble.toFixed(0)
    addComa(newValor)
  }

  //import scala.collection.JavaConversions._

  private val unidades = Map( 0->"", 1->"UNO ", 2->"DOS ", 3->"TRES ", 4->"CUATRO ", 5->"CINCO ", 6->"SEIS ", 7->"SIETE ", 8->"OCHO ", 9->"NUEVE ")

  private val decenas = Map( 10->"DIEZ", 11->"ONCE", 12->"DOCE", 13->"TRECE", 14->"CATORCE",15->"QUINCE",16->"DIECISEIS",17->"DIECISIETE",18->"DIECIOCHO",19->"DIECINUEVE")

  private val centenas = Map( 1->"CIENTO", 2->"DOSCIENTOS", 3->"TRESCIENTOS",4->"CUATROCIENTOS",5->"QUINIENTOS", 6->"SEISCIENTOS",
                      7->"SETECIENTOS", 8->"OCHOCIENTOS", 9->"NOVECIENTOS")

  def numberToLetter(valor: Int): String = valor match {
    case v if v < 10 => unidades(v)
    case v if v < 20 => decenas(v)
    case v if v >= 20 && v < 100 => mayora20menor100(v)
    case v if v >= 100 && v < 1000 =>
      if ( v == 100) "CIEN"
      else {

        val valorDecena = valor.toString.takeRight(2).toInt

        /*val resto = if (valorDecena < 10 ) unidades(valorDecena)
        else if( valorDecena < 20) decenas(valorDecena)
        else mayora20menor100(valorDecena)*/
        /*println("Ver que pasa!!")
        println(valor.toString.takeRight(2))*/

        centenas(valor.toString.take(1).toInt) + " " + numberToLetter(valor.toString.takeRight(2).toInt)
      }
      case v if v >= 1000 && v < 10000 => {
        val valorMil = v.toString.take(1).toInt
        val resto = v.toString.takeRight(3).toInt
        val txtMil = if (valorMil == 1)  "MIL " else unidades(valorMil) + " MIL "
        txtMil + numberToLetter(v.toString.takeRight(3).toInt)
      }
    case v if v >= 10000 && v < 1000000 => {

      //val mil = v.toString.takeRight(v.toString.length - 5)

      if (v < 20000)
        decenas(v.toString.take(2).toInt) + " MIL " + numberToLetter(valor.toString.takeRight(3).toInt)
      else if (v >= 20000 && v < 100000)
        mayora20menor100(v.toString.take(2).toInt) + " MIL " + numberToLetter(valor.toString.takeRight(3).toInt)
      else {
        val cent = v.toString.take(3).toInt
        if (cent == 100) "CIEN "
        else numberToLetter(cent) + " MIL " + numberToLetter(valor.toString.takeRight(3).toInt)
      }
    }
    case v if v >= 1000000 && v < 100000000 => {
      val cant = v.toString
      val millon = cant.take(v.toString.length - 6).toInt

      val result = millon match {
        case 1 => "UN MILLON "
        case m if m >= 2 && m < 10 => unidades(millon) + " MILLONES "
        case m if m >= 10 && m < 20 => decenas(millon) + " MILLONES "
        case m if m >= 20 && m < 100 => mayora20menor100(millon) + " MILLONES "
        case _ => centenas(millon) + " MILLONES "
      }

      result + numberToLetter(v.toString.takeRight(6).toInt)
    }
  }

  private def mayora20menor100(valor: Int) = valor match {
      case v if v >= 20 && v < 30 => if (v == 20) "VEINTE " else "VEINTI" + unidades(v - 20)          //.getOrElse("")
      case v if v >= 30 && v < 40 => if (v == 30) "TREINTA" else "TREINTA Y " + unidades(v - 30)      //.getOrElse("")
      case v if v >= 40 && v < 50 => if (v == 40) "CUARENTA" else "CUARENTA Y " + unidades(v - 40)    //.getOrElse("")
      case v if v >= 50 && v < 60 => if (v == 50) "CINCUENTA" else "CINCUENTA Y " + unidades(v - 50)  //.getOrElse("")
      case v if v >= 60 && v < 70 => if (v == 60) "SESENTA" else "SESENTA Y " + unidades(v - 60)      //.getOrElse("")
      case v if v >= 70 && v < 80 => if (v == 70) "SETENTA" else "SETENTA Y " + unidades(v - 70)      //.getOrElse("")
      case v if v >= 80 && v < 90 => if (v == 80) "OCHENTA" else "OCHENTA Y " + unidades(v - 80)      //.getOrElse("")
      case v if v >= 90 && v < 100 => if (v == 90) "NOVENTA" else "NOVENTA Y " + unidades(v - 90)     //.getOrElse("")
  }

}
