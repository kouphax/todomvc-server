import sbt._
import sbt.Keys._

object TodoMVCBuild extends Build {
  lazy val todomvc = Project("todomvc", file(".")).settings(
    resolvers += "Spark Repo" at "https://oss.sonatype.org/content/repositories/snapshots/",
    libraryDependencies ++= Seq(
      "com.sparkjava"        % "spark-core"         % "0.9.9.7-SNAPSHOT",
      "org.codehaus.jackson" % "jackson-core-asl"   % "1.9.12",
      "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.12",
      "com.google.guava"         % "guava"              % "14.0.1"
    )
  )
}
