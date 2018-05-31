name := """chatbot"""
organization := "muchmore.digital"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  javaWs,
  filters,
  guice,
  "io.jsonwebtoken" % "jjwt" % "0.9.0",
  "org.springframework.data" % "spring-data-mongodb" % "2.0.1.RELEASE",
  "com.ibm.watson.developer_cloud" % "java-sdk" % "4.0.0",
  "com.typesafe.play" %% "play-json" % "2.6.7",
  "io.lettuce" % "lettuce-core" % "5.0.0.RELEASE"
)


fork in run := true
