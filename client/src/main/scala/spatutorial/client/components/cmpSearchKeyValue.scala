package spatutorial.client.components

import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.html_<^._

//import spatutorial.client.actionhandlers.SetShowFormRenglonesBienes
import spatutorial.client.components.Bootstrap.{CommonStyle /*Button, */ }
import spatutorial.shared._

import scalacss.ScalaCssReact._

object cmpSearchKeyValue {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class ItemsFoundProps (items: Seq[Oficina],
                                  selectItem: Oficina => Callback,
                                  editItem: Oficina => Callback,
                                  deleteItem: Oficina => Callback)

  //class Backend($: BackendScope[Props, State]) {

  private val LstOficinas = ScalaComponent.builder[ItemsFoundProps]("cmpOficinas")
    .render_P( p => {

      val style = bss.listGroup
      def renderItem(item: Oficina) = {
        val itemStyle = style.itemOpt(CommonStyle.info)

        <.li(itemStyle, ^.border := "1px solid", ^.borderColor := "#20B2AA", //^.width:="300",
          <.span( item.clave + ":  " + item.descripcion, ^.onClick-->p.selectItem(item))
        )
      }
      <.ul(style.listGroup)(p.items toTagMod renderItem)
    })
    .build

  def apply(  items: Seq[Oficina],
              selectItem: Oficina => Callback,
              editItem: Oficina => Callback,
              deleteItem: Oficina => Callback) =
      LstOficinas(ItemsFoundProps(items, selectItem, editItem, deleteItem))
}
