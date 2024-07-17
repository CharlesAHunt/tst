logLevel := Level.Warn

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.0.10")

addSbtPlugin("org.wartremover" % "sbt-wartremover" % "3.1.6")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.6")

addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.9.16")

addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.10.4")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.10.0")

addSbtPlugin("net.vonbuchholtz" % "sbt-dependency-check" % "4.1.0")
