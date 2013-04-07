import sbt._
import sbt.Keys._

object Build extends Build {

  lazy val todomvc = Project("todomvc", file(".")).settings(
    name := "TodoMVC",
    organization := "se.yobriefca",
    version := "0.1-SNAPSHOT",
    scalaVersion := "2.10.0",
    resolvers += "spray" at "http://repo.spray.io/",
    libraryDependencies ++= Seq(
      "org.mashupbots.socko" %% "socko-webserver" % "0.2.4",
      "io.spray" %%  "spray-json" % "1.2.3"
    )
  )
}
