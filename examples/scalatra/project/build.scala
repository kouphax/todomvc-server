import sbt._
import Keys._
import org.scalatra.sbt._

object TodomvcBuild extends Build {
  lazy val todoMVC = Project("todomvc", file("."), settings = Defaults.defaultSettings ++ ScalatraPlugin.scalatraSettings).settings(
    organization  := "se.yobriefca",
    name          := "TodoMVC",
    version       := "0.1.0-SNAPSHOT",
    scalaVersion  := "2.10.0",

    resolvers += Classpaths.typesafeReleases,

    libraryDependencies ++= Seq(
      "org.scalatra"            %% "scalatra"         % "2.2.0",
      "org.scalatra"            %% "scalatra-scalate" % "2.2.0",
      "org.scalatra"            %% "scalatra-specs2"  % "2.2.0"               % "test",
      "ch.qos.logback"          %  "logback-classic"  % "1.0.6"               % "runtime",
      "org.eclipse.jetty"       %  "jetty-webapp"     % "8.1.8.v20121106"     % "container",
      "org.eclipse.jetty.orbit" %  "javax.servlet"    % "3.0.0.v201112011016" % "container;provided;test" artifacts (Artifact("javax.servlet", "jar", "jar")),
      "org.scalatra"            %% "scalatra-json"    % "2.2.0",
      "org.json4s"              %% "json4s-jackson"   % "3.1.0"
    )
  )
}
