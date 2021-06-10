name := """crud-movies"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.6"

libraryDependencies += guice


libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.20.13-play28",
  "org.reactivemongo" %% "reactivemongo-play-json-compat" % "1.0.1-play28",
  "org.reactivemongo" %% "reactivemongo-bson-compat" % "0.20.13",
  "com.typesafe.play" %% "play-json-joda" % "2.7.4"
)
