import sbt._
import Keys._

name := "Graph-BFS"
version := "1.0-SNAPSHOT"
mainClass in (Compile, packageBin) := Some("Driver")
mainClass in (Compile, run) := Some("Driver")


libraryDependencies += "org.apache.spark" %% "spark-core" % "1.5.1" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-graphx" % "1.5.1" % "provided"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.3.0"
resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.sonatypeRepo("public"))

