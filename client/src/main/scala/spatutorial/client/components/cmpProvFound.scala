package spatutorial.client.components

import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.html_<^._

//import spatutorial.client.actionhandlers.SetShowFormRenglonesBienes
import spatutorial.client.components.Bootstrap.{Button, CommonStyle}
import spatutorial.shared._

import scalacss.ScalaCssReact._

object cmpProvFound {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class ItemsFoundProps (items: Seq[Proveedor], selectItem: Proveedor => Callback)

  private val ListArticulosfound = ScalaComponent.builder[ItemsFoundProps]("cmpProvFound")
    .render_P( p => {
      val style = bss.listGroup
      def renderItem(item: Proveedor) = {
        val itemStyle = style.itemOpt(CommonStyle.info)


        <.li(itemStyle, ^.border := "1px solid", /*^.marginTop := 3,*/ ^.borderColor := "#20B2AA",
          ^.height := "auto",  ^.overflow := "hidden",
          <.div(^.clear:="both", ^.float:="left", ^.padding := "10", ^.border := "1px solid", ^.marginTop := "3",
            ^.borderColor := "#20B2AA", ^.height := "auto", ^.overflow := "hidden", ^.width := "600",
            <.span(item.cve_provedor + " : " + item.razon_social)
          ),
          Button(Button.Props(p.selectItem(item), addStyles = Seq(bss.pullRight, bss.buttonXS)), "Participa")
        )
      }
      <.ul(style.listGroup)(p.items toTagMod renderItem)
    })
    .build

  def apply(  items: Seq[Proveedor], selectItem: Proveedor => Callback) =
      ListArticulosfound(ItemsFoundProps(items, selectItem))
}
