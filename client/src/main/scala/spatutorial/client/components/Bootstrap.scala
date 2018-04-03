package spatutorial.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
//import japgolly.univeq.UnivEq

import scala.language.implicitConversions
import scala.scalajs.js
import scalacss.ScalaCssReact._
import spatutorial.client.CssSettings._

/**
 * Common Bootstrap components for scalajs-react
 */
object Bootstrap {

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  @js.native
  trait BootstrapJQuery extends JQuery {
    def modal(action: String): BootstrapJQuery = js.native
    def modal(options: js.Any): BootstrapJQuery = js.native
  }

  implicit def jq2bootstrap(jq: JQuery): BootstrapJQuery = jq.asInstanceOf[BootstrapJQuery]

  // Common Bootstrap contextual styles
  object CommonStyle extends Enumeration {
    val default, primary, success, info, warning, danger = Value
  }

  /*object Button {

    case class Props(onClick: Callback, style: CommonStyle.Value = CommonStyle.default, addStyles: Seq[StyleA] = Seq())

    val component = ScalaComponent.builder[Props]("Button")
      .renderPC((_, p, c) =>
        <.button(bss.buttonOpt(p.style), p.addStyles.toTagMod, ^.tpe := "button", ^.onClick --> p.onClick, c)
      ).build

    def apply(props: Props, children: VdomNode*) = component(props)(children: _*)
    def apply() = component
  }*/


  object Button {

    case class Props(onClick: Callback, style: CommonStyle.Value = CommonStyle.default,
                     addStyles: Seq[StyleA] = Seq(),
                     otrosStyles: Seq[TagMod]=Seq()
                    )

    val component = ScalaComponent.builder[Props]("Button")
      .renderPC((_, p, c) =>
        <.button(bss.buttonOpt(p.style), p.addStyles.toTagMod, p.otrosStyles.toTagMod, ^.tpe := "button", ^.onClick --> p.onClick, c)
      ).build

    def apply(props: Props, children: VdomNode*) = component(props)(children: _*)
    def apply() = component
  }

  object CheckBox {
    case class Props( idx:String="", label: String = "", onClick: Callback, value: Boolean = false,
                      style: CommonStyle.Value = CommonStyle.default,
                      addStyles: Seq[StyleA] = Seq(), otrosStyles: Seq[TagMod]=Seq() )

    val component = ScalaComponent.builder[Props]("Checkbox")
      .renderPC((_, p, c) =>
        <.div(^.float := "left",
          <.label(p.label, ^.`for` := p.idx, ^.fontWeight:="normal", ^.display := "block", ^.marginTop := "10"),
          <.input.checkbox(bss.buttonOpt(p.style), p.addStyles.toTagMod,
            p.otrosStyles.toTagMod, ^.paddingRight := "50", ^.onClick --> p.onClick, ^.checked := p.value)
          //<.input( ^.tpe:="checkbox", bss.buttonOpt(p.style), p.addStyles, ^.onClick --> p.onClick, c )
        )
      ).build

    def apply(props: Props, children: VdomNode*) = component(props)(children: _*)
    def apply() = component
  }

  object RadioButton {
    case class Props( idx:String="", label: String = "", onClick: Callback, value: Boolean = false,
                      style: CommonStyle.Value = CommonStyle.default,
                      addStyles: Seq[StyleA] = Seq(), otrosStyles: Seq[TagMod]=Seq() )

    val component = ScalaComponent.builder[Props]("RadioButton")
      .renderPC((_, p, c) =>
        <.div(^.float := "left", ^.margin:= "10",
          <.label(p.label, ^.`for` := p.idx, ^.fontWeight:="normal", ^.display := "block", ^.marginTop:= "10"),
          <.input.radio(bss.buttonOpt(p.style), p.addStyles.toTagMod,
            p.otrosStyles.toTagMod, ^.paddingRight := "10", ^.onClick --> p.onClick, ^.checked := p.value)
        )
      ).build

    def apply(props: Props, children: VdomNode*) = component(props)(children: _*)
    def apply() = component
  }

  object Combo {
    case class Props( idx:String="", label: String = "", onChange: ReactEventFromInput => Callback,
                      values: Seq[String] = Seq(),  value: String = "", style: CommonStyle.Value = CommonStyle.default,
                      addStyles: Seq[StyleA] = Seq())

    val component = ScalaComponent.builder[Props]("Combo")
      .renderPC( ( _, p, c ) => {

        def renderItem( cve: String ) = <.option( ^.value := cve, cve )

        <.div(^.float := "left",
          <.label(p.label, ^.`for` := p.idx, ^.display:="block", ^.marginTop := "10" ),
          <.select(bss.buttonOpt(p.style), p.addStyles.toTagMod, ^.id := p.idx, ^.onChange ==> p.onChange,
            ^.display :="block", ^.marginRight := "20", ^.value := p.value
          )( p.values toTagMod renderItem )
        )
      }
      )
      .build

    def apply( props: Props, children: VdomNode* ) = component( props)(children: _*)
    def apply() = component

  }

  object Text {
    case class Props(idx:String = "",
                     label: String = "",
                     onChange: ReactEventFromInput => Callback,
                     onFocus: Callback = Callback.empty,
                     onBlur: Callback = Callback.empty,
                     typeInput: String = "text",
                     value: String = "", style: CommonStyle.Value = CommonStyle.default,
                     addStyles: Seq[StyleA] = Seq(), otrosStyles: Seq[TagMod]=Seq())


    val component = ScalaComponent.builder[Props]("Text")
      .renderPC{ ( _, p, c ) =>

        val idx = "txt" + p.label.replaceAllLiterally(" ", "")

        <.div ( ^.float:="left", ^.marginRight := "5",
          <.label( p.label, ^.`for` := idx,  ^.display := "block",^.fontWeight := "normal", ^.marginTop := "10" ),
          if ( p.typeInput == "text" )
            <.input(bss.buttonOpt(p.style), p.addStyles.toTagMod, ^.id := idx,
              ^.onChange ==> p.onChange, ^.onFocus --> p.onFocus, ^.onBlur-->p.onBlur,
              p.otrosStyles.toTagMod, ^.display := "block", ^.value := p.value,
              ^.marginRight := "5", ^.textAlign:= "left" )
          else if( p.typeInput == "numero" )
            <.input(bss.buttonOpt(p.style), p.addStyles.toTagMod, ^.id := idx, ^.onChange ==> p.onChange,
              p.addStyles.toTagMod, p.otrosStyles.toTagMod, ^.display := "block", ^.value := p.value,
              ^.marginRight := "5", ^.rows := 3, ^.textAlign:= "right")
          else if( p.typeInput == "password" )
            <.input(bss.buttonOpt(p.style), p.addStyles.toTagMod, ^.id := idx, ^.onChange ==> p.onChange,
              p.addStyles.toTagMod, p.otrosStyles.toTagMod, ^.display := "block", ^.value := p.value,
              ^.marginRight := "5", ^.rows := 3, ^.textAlign:= "left", ^.tpe := p.typeInput)
          else
            <.textarea( /*bss.buttonOpt(p.style), p.addStyles, */
              ^.id := idx, ^.onChange ==> p.onChange,
              p.addStyles.toTagMod, p.otrosStyles.toTagMod,  ^.value:=p.value, ^.marginRight := "5",
              ^.textAlign := "left", ^.rows := 3 /*, ^.wrap:="hard"*/)
        )
      }.build



    def apply( props: Props, children: VdomNode* ) = component( props)(children: _*)
    def apply() = component
  }

  object Number {
    case class Props(idx:String="", label: String = "", onChange: ReactEventFromInput => Callback,
                     value: String = "", style: CommonStyle.Value = CommonStyle.default,
                     addStyles: Seq[StyleA] = Seq(), otrosStyles: Seq[TagMod]=Seq())

    val component = ScalaComponent.builder[Props]("Text")
      .renderPC{ ( _, p, c ) =>
        <.div ( ^.float:="left", ^.marginRight := "20",

          <.input(bss.buttonOpt(p.style), p.addStyles.toTagMod, ^.id := p.idx, ^.tpe := "number",
            ^.onChange ==> p.onChange, ^.step := ".01",
            p.addStyles.toTagMod, p.otrosStyles.toTagMod, ^.display := "block", ^.value := p.value,
            ^.marginRight := "20", ^.rows := 3, ^.textAlign:= "right")
        )
      }.build



    def apply( props: Props, children: VdomNode* ) = component( props)(children: _*)
    def apply() = component
  }

  object Fecha {
    case class Props(idx:String="", label: String = "", onChange: ReactEventFromInput => Callback,
                     value: String = "", style: CommonStyle.Value = CommonStyle.default,
                     addStyles: Seq[StyleA] = Seq(), otrosStyles: Seq[TagMod]=Seq())

    val component = ScalaComponent.builder[Props]("Text")
      .renderPC{ ( _, p, c ) =>
        <.div ( ^.float:="left", ^.marginRight := "20",
          <.label( p.label, ^.`for` := p.idx,  ^.display := "block",^.fontWeight := "normal", ^.marginTop := "10" ),
          <.input(bss.buttonOpt(p.style), p.addStyles.toTagMod, ^.id := p.idx, ^.tpe := "date",
            ^.onChange ==> p.onChange, ^.step := ".01",
            p.addStyles.toTagMod, p.otrosStyles.toTagMod, ^.display := "block", ^.value := p.value,
            ^.marginRight := "20", ^.rows := 3, ^.textAlign:= "right")
        )
      }.build



    def apply( props: Props, children: VdomNode* ) = component( props)(children: _*)
    def apply() = component
  }

  object TextArea {

    case class Props(idx:String="", label: String = "", onChange: ReactEventFromInput => Callback,
                     value: String = "", style: CommonStyle.Value = CommonStyle.default,
                     addStyles: Seq[StyleA] = Seq(), otrosStyles: Seq[TagMod]=Seq())

    val component = ScalaComponent.builder[Props]("TextArea")
      .renderPC((_, p, c) =>
        <.div (^.float := "left", ^.marginRight := "20",
          <.label(p.label, ^.`for` := p.idx,  ^.display:="block",^.fontWeight:="normal", ^.marginTop := "10"),
          <.textarea(//bss.buttonOpt(p.style), p.addStyles,
            ^.id := p.idx, ^.onChange ==> p.onChange,
            /*p.addStyles, p.otrosStyles,  */^.value := p.value, ^.marginRight := "20",
            ^.textAlign:="left", ^.rows := 2, ^.cols := 30/*, ^.wrap:="off"*/, ^.display:="block")
        )
      ).build

    def apply( props: Props, children: VdomNode* ) = component( props)(children: _*)
    def apply() = component
  }

  object Panel {

    case class Props(heading: String, style: CommonStyle.Value = CommonStyle.default)

    val component = ScalaComponent.builder[Props]("Panel")
      .renderPC((_, p, c) =>
        <.div(bss.panelOpt(p.style),
          <.div(bss.panelHeading, p.heading),
          <.div(bss.panelBody, c)
        )
      ).build

    def apply(props: Props, children: VdomNode*) = component(props)(children: _*)
    def apply() = component
  }

  object Modal {

    // header and footer are functions, so that they can get access to the the hide() function for their buttons
    case class Props(header: Callback => VdomNode, footer: Callback => VdomNode, closed: Callback,
                     backdrop: Boolean = true, keyboard: Boolean = true)

    class Backend(t: BackendScope[Props, Unit]) {
      def hide =
        // instruct Bootstrap to hide the modal
        t.getDOMNode.map(jQuery(_).modal("hide")).void

      // jQuery event handler to be fired when the modal has been hidden
      def hidden(e: JQueryEventObject): js.Any = {
        // inform the owner of the component that the modal was closed/hidden
        t.props.flatMap(_.closed).runNow()
      }

      def render(p: Props, c: PropsChildren) = {
        val modalStyle = bss.modal
        <.div(modalStyle.modal, modalStyle.fade, ^.role := "dialog", ^.aria.hidden := true,
            <.div(modalStyle.dialog,
              <.div(modalStyle.content,
                  <.div(modalStyle.header, p.header(hide)),
                  <.div(modalStyle.body, c),
                  <.div(modalStyle.footer, p.footer(hide))
              )
            )
        )
      }
    }

    val component = ScalaComponent.builder[Props]("Modal")
      .renderBackendWithChildren[Backend]
      .componentDidMount(scope => Callback {
        val p = scope.props
        // instruct Bootstrap to show the modal
        jQuery(scope.getDOMNode).modal(js.Dynamic.literal("backdrop" -> p.backdrop, "keyboard" -> p.keyboard, "show" -> true))
        // register event listener to be notified when the modal is closed
        jQuery(scope.getDOMNode).on("hidden.bs.modal", null, null, scope.backend.hidden _)
      })
      .build

    def apply(props: Props, children: VdomElement*) = component(props)(children: _*)
    def apply() = component
  }

}
