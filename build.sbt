name := """als"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

val appDependencies = Seq(
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)
