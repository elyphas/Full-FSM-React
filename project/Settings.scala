import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

/**
 * Application settings. Configure the build for your application here.
 * You normally don't have to touch the actual build definition after this.
 */
object Settings {
  /** The name of your application */
  val name = "Sicap"

  /** The version of your application */
  val version = "1.1.5"

  /** Options for the scala compiler */
  val scalacOptions = Seq(
    "-Xlint",
    "-unchecked",
    "-deprecation",
    "-feature"
  )

  /** Declare global dependency versions here to avoid mismatches in multi part dependencies */
  object versions {
    val scala = "2.12.4" //"2.12.1"   //"2.11.11"
    val scalaDom = "0.9.4" //"0.9.2"

    val scalajsReact = "1.1.1"  //"1.2.0"

    val scalaCSS = "0.5.5"  //"0.5.3"
    val log4js = "1.4.10"
    val autowire = "0.2.6"
    val booPickle = "1.2.6"
    val diode = "1.1.3" //"1.1.2"
    val uTest = "0.4.7"

    val react = "15.6.1"   //"15.5.4"
    val jQuery = "1.11.1"
    val bootstrap = "3.3.6"
    //val chartjs = "2.1.3"

    val scalajsScripts = "1.1.0"  //"1.0.0"

  }

  /**These dependencies are shared between JS and JVM projects
   * the special %%% function selects the correct version for each project
   */
  val sharedDependencies = Def.setting(Seq(
    //"org.scala-js" % "scalajs-java-time_sjs0.6_2.11" % "0.2.0",
    //"org.scala-js" % "scalajs-java-time_sjs0.6_2.12" % "0.2.0",
    "com.lihaoyi" %%% "autowire" % versions.autowire,
    "io.suzaku" %%% "boopickle" % versions.booPickle,
    //"org.scala-js" % "scalajs-java-time_sjs0.6_2.11" % "0.2.0",
    //"org.scala-js" % "scalajs-java-time_sjs0.6_2.12" % "0.2.0",
    //"org.scalaz" % "scalaz-core_2.12" % "7.2.13"
    //"org.scalaz" %% "scalaz-core" % "7.2.19"
  ))

  /** Dependencies only used by the JVM project */
  val jvmDependencies = Def.setting(Seq(
    "com.vmunier" %% "scalajs-scripts" % versions.scalajsScripts,
    "org.webjars" % "font-awesome" % "4.3.0-1" % Provided,
    "org.webjars" % "bootstrap" % versions.bootstrap % Provided,
    "com.lihaoyi" %% "utest" % versions.uTest % Test,
    //"com.typesafe.play" %% "play-jdbc-api" % "2.5.3", //acabo de comentar.
    //"com.typesafe.slick" %% "slick" % "3.2.1",  //lo acabo de comentar.

    //"com.typesafe.slick" %% "slick-hikaricp" % "3.1.0",
    //"com.typesafe.slick" % "slick-hikaricp_2.11" % "3.2.1",

    //"com.typesafe.play" %% "play-slick" % "2.0.2",
    "com.typesafe.play" %% "play-slick" % "3.0.3",
    "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
    "com.chuusai" %% "shapeless" % "2.3.3",
    "io.underscore" %% "slickless" % "0.3.3"
  ))

  /** Dependencies only used by the JS project (note the use of %%% instead of %%) */
  val scalajsDependencies = Def.setting(Seq(
    //"org.scala-js" % "scalajs-java-time_sjs0.6_2.11" % "0.2.0",
    "com.github.japgolly.scalajs-react" %%% "core" % versions.scalajsReact,
    "com.github.japgolly.scalajs-react" %%% "extra" % versions.scalajsReact,
    //"com.olvind" %%% "scalajs-react-components" % "0.7.0",
    "com.github.japgolly.scalacss" %%% "ext-react" % versions.scalaCSS,
    "io.suzaku" %%% "diode" % versions.diode,
    "io.suzaku" %%% "diode-react" % versions.diode,
    "org.scala-js" %%% "scalajs-dom" % versions.scalaDom,
    "com.lihaoyi" %%% "utest" % versions.uTest % Test,
    "org.akka-js" %%% "akkajsactor" % "1.2.5.11"
    //"com.github.japgolly.scalajs-react" %%% "ext-scalaz72" % "1.0.1"
  ))

  /** Dependencies for external JS libs that are bundled into a single .js file according to dependency order */
  val jsDependencies = Def.setting(Seq(
    "org.webjars.bower" % "react" % versions.react / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React",
    "org.webjars.bower" % "react" % versions.react / "react-dom.js" minified "react-dom.min.js" dependsOn "react-with-addons.js" commonJSName "ReactDOM",
    "org.webjars.bower" % "pdfmake" % "0.1.31" / "pdfmake.js" minified "pdfmake.min.js" commonJSName "pdfmake",
    //"org.webjars.bower" % "pdfmake" % "0.1.31"
    "org.webjars" % "jquery" % versions.jQuery / "jquery.js" minified "jquery.min.js",
    "org.webjars" % "bootstrap" % versions.bootstrap / "bootstrap.js" minified "bootstrap.min.js" dependsOn "jquery.js",
    //"org.webjars" % "chartjs" % versions.chartjs / "Chart.js" minified "Chart.min.js",
    "org.webjars" % "log4javascript" % versions.log4js / "js/log4javascript_uncompressed.js" minified "js/log4javascript.js"/*,
    "org.webjars.bower" % "js-xlsx" % "0.10.8" / "dist/xlsx.js" minified "dist/xlsx.min.js"*/
  ))

}
