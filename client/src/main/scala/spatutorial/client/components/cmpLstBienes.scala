package spatutorial.client.components

import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.html_<^._

import spatutorial.client.components.Bootstrap.{Button, CommonStyle/*, Text, Combo*/}
import spatutorial.shared._
import scalacss.ScalaCssReact._

object cmpLstBienes {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class LstBienesListProps(
        listBienes: Seq[RenglonListBienes],
        stateChange: RenglonListBienes => Callback,
        editItem: RenglonListBienes => Callback,
        deleteItem: Renglon => Callback
    )

  def onChageCveArticulo(e: ReactEventFromInput) = Callback.alert("cambiando")

  private val TodoList = ScalaComponent.builder[LstBienesListProps]("DetPedido")
    .render_P( p => {
      val style = bss.listGroup
      def renderItem(item: RenglonListBienes) = {

        val rengl: Renglon = Renglon(
          id_comparativo = item.id_comparativo,
          programa = item.programa,
          renglon = item.renglon,
          cve_articulo = item.cve_articulo,
          memo = "", entrega = 1, cantidad = 0,
          anexo = "", marca = "", modelo = "", cancelado = false, tipo = 1)

        val itemStyle = style.itemOpt(CommonStyle.info)

        <.li(itemStyle, ^.border := "1px solid", /*^.marginTop := 3,*/ ^.borderColor := "#20B2AA",
          ^.height := "auto", ^.overflow := "hidden",
          <.span(item.cve_articulo + " : " + item.descripcion + " " + item.unidad),
          //<.span(item.cantidad + " " + item.precio_referencia + " " + item.importe),
          Button(Button.Props(p.editItem(item), addStyles = Seq(bss.pullRight, bss.buttonXS)), "Edit"),
          Button(Button.Props(p.deleteItem(rengl), addStyles = Seq(bss.pullRight, bss.buttonXS)), "Delete")
        )
      }
      <.ul(style.listGroup)(p.listBienes toTagMod renderItem)
    })
    .build

  def apply(  listBienes: Seq[RenglonListBienes], /*listBienes: ListadoBienes,*/
              stateChange: RenglonListBienes => Callback,
              editItem: RenglonListBienes => Callback,
              deleteItem: Renglon => Callback
           ) = TodoList(LstBienesListProps(listBienes, stateChange, editItem, deleteItem))
}
