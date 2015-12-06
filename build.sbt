import sbt._
import Keys._

scalaVersion := "2.11.7"
name := "Graph-BFS"
version := "1.0-SNAPSHOT"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.5.1" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-graphx" % "1.5.1" % "provided"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.3.0"
resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  Resolver.sonatypeRepo("public"))

