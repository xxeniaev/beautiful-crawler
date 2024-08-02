ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "crawler",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-circe" % "0.23.27",
      "org.http4s" %% "http4s-dsl" % "0.23.27",
      "org.http4s" %% "http4s-ember-server" % "0.23.27",
      "io.circe" %% "circe-generic" % "0.14.3",
      "org.jsoup" % "jsoup" % "1.16.2",
      "com.comcast" %% "ip4s-core" % "3.0.1",
      "ch.qos.logback" % "logback-classic" % "1.2.11"
    )
  )
