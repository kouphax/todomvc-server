import sbt._
import sbt.Keys._

object TodomvcBuild extends Build {

  lazy val webbitRest = Project("webbit-rest", file(".")).settings(
    libraryDependencies ++= Seq(
      "org.webbitserver"     % "webbit-rest"        % "0.3.0",
      "org.codehaus.jackson" % "jackson-core-asl"   % "1.9.12",
      "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.12",
      "com.google.guava"     % "guava"              % "14.0.1"
    )
  )
}
