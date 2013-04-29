import sbt._
import sbt.Keys._

object WebbitBuild extends Build {

  lazy val webbit = Project("webbit", file(".")).settings(
    libraryDependencies ++= Seq(
      "org.webbitserver"     % "webbit"             % "0.4.15",
      "org.codehaus.jackson" % "jackson-core-asl"   % "1.9.12",
      "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.12",
      "com.google.guava"     % "guava"              % "14.0.1"
    )
  )
}
