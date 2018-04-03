package spatutorial.client.components

import japgolly.scalajs.react._
//import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.vdom.html_<^._

import spatutorial.client.components.Bootstrap.{CommonStyle/*, Button, Combo, Text*/}
import spatutorial.shared._
import scalacss.ScalaCssReact._

object cmpLstProgramas {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class LstProgramasProps(
        lstProgramas: Seq[Programa] = Seq.empty,
        stateChange: Programa => Callback,
        editItem: Programa => Callback,
        deleteItem: Programa => Callback
    )

  private val TodoList = ScalaComponent.builder[LstProgramasProps]("cmpLstProgramas")
    .render_P( p => {
      val style = bss.listGroup
      def renderItem(item: Programa) = {
        val itemStyle = style.itemOpt(CommonStyle.info)

        <.li(itemStyle, ^.border := "1px solid", ^.borderColor := "#20B2AA", ^.width:="400",
          <.span(item.programa + " : " + item.descripcion, ^.onClick-->p.stateChange(item) )
        )
      }
      <.ul(style.listGroup)(p.lstProgramas toTagMod renderItem)
    })
    .build

  def apply(  lstProgramas: Seq[Programa], /*listBienes: ListadoBienes,*/
              stateChange: Programa => Callback,
              editItem: Programa => Callback,
              deleteItem: Programa => Callback
           ) = TodoList(LstProgramasProps(lstProgramas, stateChange, editItem, deleteItem))
}
