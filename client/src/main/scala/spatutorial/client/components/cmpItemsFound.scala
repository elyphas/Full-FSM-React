package spatutorial.client.components

import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.html_<^._

//import spatutorial.client.actionhandlers.SetShowFormRenglonesBienes
import spatutorial.client.components.Bootstrap.{CommonStyle /*Button, */ }
import spatutorial.shared._

import scalacss.ScalaCssReact._

object cmpItemsFound {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class ItemsFoundProps (items: Seq[Articulo],
                                  selectItem: Articulo => Callback,
                                  editItem: Articulo => Callback,
                                  deleteItem: Articulo => Callback)

  private val ListArticulosfound = ScalaComponent.builder[ItemsFoundProps]("cmpItemsFound")
    .render_P( p => {

      val style = bss.listGroup
      def renderItem(item: Articulo) = {
        val itemStyle = style.itemOpt(CommonStyle.info)

        val unid = item.unidad + (if ( item.presentacion.getOrElse(0) > 0 ) " CON " + item.presentacion.getOrElse(0) + " " + item.unid_med_pres.getOrElse("") else "")

        <.li(itemStyle, ^.border := "1px solid", ^.borderColor := "#20B2AA", ^.width:="300",
          <.span( item.cve_articulo + " : " + item.descripcion + " :****: " + unid, ^.onClick-->p.selectItem(item))
        )
      }
      <.ul(style.listGroup)(p.items toTagMod renderItem)
    })
    .build

  def apply(  items: Seq[Articulo],
              selectItem: Articulo => Callback,
              editItem: Articulo => Callback,
              deleteItem: Articulo => Callback) =
      ListArticulosfound(ItemsFoundProps(items, selectItem, editItem, deleteItem))
}
