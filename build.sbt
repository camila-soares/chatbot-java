name := """chatbot-java"""
organization := "digital.muchmore"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  javaWs,
  filters,
  guice,
  "io.jsonwebtoken" % "jjwt" % "0.9.0",
  "com.ibm.watson.developer_cloud" % "java-sdk" % "4.0.0",
  "com.typesafe.play" %% "play-json" % "2.6.7",
  "io.lettuce" % "lettuce-core" % "5.0.0.RELEASE",
  "org.webjars" %% "webjars-play" % "2.6.2",
  "org.webjars" % "flot" % "0.8.3",
  "org.webjars" % "bootstrap" % "3.3.6"



)


