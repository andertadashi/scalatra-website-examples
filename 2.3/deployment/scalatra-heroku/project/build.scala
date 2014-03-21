import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._
import com.typesafe.sbt.SbtStartScript

object HerokuExampleBuild extends Build {
  val Organization = "org.scalatra"
  val Name = "Heroku Example"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.10.3"
  val ScalatraVersion = "2.3.0-SNAPSHOT"
  val jettyVersion = "9.1.3.v20140225"


  lazy val project = Project (
    "heroku-example",
    file("."),
    settings = SbtStartScript.startScriptForClassesSettings ++ Defaults.defaultSettings ++  scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
      libraryDependencies ++= Seq(
        "com.basho.riak" % "riak-client" % "1.1.0",
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
        "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
        "ch.qos.logback"              %  "logback-classic"     % "1.1.1"          % "runtime",
        "org.eclipse.jetty"           %  "jetty-plus"          % jettyVersion     % "runtime;provided",
        "org.eclipse.jetty"           %  "jetty-webapp"        % jettyVersion     % "runtime",
        "javax.servlet"               %  "javax.servlet-api"   % "3.1.0"          % "runtime;compile;provided;test" artifacts Artifact("javax.servlet-api", "jar", "jar")
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}



