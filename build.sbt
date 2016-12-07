import sbt.Keys._
import sbt._

name := "credsbot"

resolvers += Resolver.sonatypeRepo("public")

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-native" % "3.4.2",
  "com.github.gilbertw1" %% "slack-scala-client" % "0.1.8",
  "com.typesafe" % "config" % "1.3.1",
  "com.iheart" %% "ficus" % "1.3.4"
)
