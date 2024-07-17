import sbt.*
import Versions.*

object Versions {
  val catsEffect = "3.5.3"
  val circeVersion = "0.14.7"
  val http4sVersion = "0.23.19-RC3"
  val tapirVersion = "1.4.0"
  val doobieVersion = "1.0.0-RC5"
  val odinVersion = "0.13.0"
  val sttpClientVersion = "3.9.6"
}

object Dependencies {

  val dependencies: Seq[ModuleID] = Seq(
    "ch.qos.logback" % "logback-classic" % "1.5.6",
    "com.github.pureconfig" %% "pureconfig" % "0.17.6",
    "io.circe" %% "circe-generic-extras" % "0.14.3",
    "io.circe" %% "circe-core" % circeVersion,
    "org.typelevel" %% "cats-core" % "2.10.0",
    "org.typelevel" %% "cats-effect" % catsEffect,
    "com.github.valskalla" %% "odin-core" % odinVersion,
    "com.github.valskalla" %% "odin-slf4j" % odinVersion,
    "com.github.valskalla" %% "odin-json" % odinVersion,
    "org.http4s" %% "http4s-core" % http4sVersion,
    "org.http4s" %% "http4s-circe" % http4sVersion,
    "org.http4s" %% "http4s-ember-server" % http4sVersion,
    "org.tpolecat" %% "doobie-core" % doobieVersion,
    "org.tpolecat" %% "doobie-postgres" % doobieVersion,
    "org.tpolecat" %% "doobie-hikari" % doobieVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-enumeratum" % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % tapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion exclude("org.yaml", "snakeyaml"),
    "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % tapirVersion,
    "com.softwaremill.sttp.apispec" %% "apispec-model" % "0.3.2",
    // Testing
    "org.scalatest" %% "scalatest" % "3.2.18" % Test,
    "com.codecommit" %% "cats-effect-testing-scalatest" % "0.5.4" % Test,
    "com.disneystreaming" %% "weaver-cats" % "0.8.4" % Test
  )

}
