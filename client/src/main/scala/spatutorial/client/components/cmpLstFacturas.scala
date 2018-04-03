package spatutorial.client.components

import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.html_<^._

//import spatutorial.client.actionhandlers.SetShowFormRenglonesBienes
import spatutorial.client.components.Bootstrap.{Button, CommonStyle}
import spatutorial.shared._

import scalacss.ScalaCssReact._

object cmpLstFacturas {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class ItemsFoundProps (
                               items: Seq[DetSolicitudPago],
                               //selectItem: DetSolicitudPago => Callback
                                editItem: DetSolicitudPago => Callback,
                                deleteItem: DetSolicitudPago => Callback
                              )

  private val LstFacturas = ScalaComponent.builder[ItemsFoundProps]("cmpLstFacturas")
    .render_P( p => {
      val style = bss.listGroup
      def renderItem(item: DetSolicitudPago) = {

        val itemStyle = style.itemOpt(CommonStyle.info)

        <.li(itemStyle, ^.border := "1px solid", /*^.marginTop := 3,*/ ^.borderColor := "#20B2AA",
          ^.height := "auto",  ^.overflow := "hidden",
          <.div(^.clear:="both", ^.float:="left", ^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
            ^.borderColor := "#20B2AA", ^.height := "auto", ^.overflow := "hidden", ^.width := "500",
            <.span(item.factura, ^.fontSize := "10")
          ),
          //Button(Button.Props(p.selectItem(item), addStyles = Seq(bss.pullRight, bss.buttonXS)), "Seleccionar"),
          Button(Button.Props(p.editItem(item), addStyles = Seq(bss.pullRight, bss.buttonXS)), "Editar"),
          Button(Button.Props(p.deleteItem(item), addStyles = Seq(bss.pullRight, bss.buttonXS)), "Eliminar")

        )
      }
      <.ul(style.listGroup)(p.items toTagMod renderItem)
    })
    .build

  def apply(items: Seq[DetSolicitudPago],
              //selectItem: DetSolicitudPago => Callback
              editItem: DetSolicitudPago => Callback,
              deleteItem: DetSolicitudPago => Callback
           ) = LstFacturas(ItemsFoundProps(items, editItem, deleteItem))
}
