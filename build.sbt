import sbt.Keys.organization
import com.typesafe.sbt.packager.docker.*

lazy val scala2 = "2.13.14"

val commonSettings = Seq(
  organization := "com.tst",
  version := "1.63.0",
  semanticdbEnabled := false,
  semanticdbVersion := scalafixSemanticdb.revision,
  scalafmtOnCompile := true,
  shellPrompt := { state =>
    scala.Console.YELLOW + "[" + scala.Console.CYAN + Project
      .extract(state)
      .currentProject
      .id + scala.Console.YELLOW + "]" + scala.Console.RED + " $ " + scala.Console.RESET
  },
  Compile / wartremoverErrors ++= Warts.allBut(
    Wart.Nothing,
    Wart.Any,
    Wart.ImplicitParameter,
    Wart.IterableOps
  ),
  scalacOptions ++= Seq(
    "-Xsource:3", // Enables some Scala 3 syntax and behavior
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-encoding",
    "utf-8", // Specify character encoding used by source files.
    "-explaintypes", // Explain type errors in more detail.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros", // Allow macro definition (besides implementation and application)
    "-language:higherKinds", // Allow higher-kinded types
    "-language:implicitConversions", // Allow definition of implicit functions called views
    "-language:postfixOps", // Allow postfix operator
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit", // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfatal-warnings", // Fail the compilation if there are any warnings.
    "-Xlint:adapted-args", // Warn if an argument list is modified to match the receiver.
    "-Xlint:constant", // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select", // Selecting member of DelayedInit.
    "-Xlint:doc-detached", // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible", // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any", // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator", // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-unit", // Warn when nullary methods return Unit.
    "-Xlint:option-implicit", // Option.apply used implicit view.
    "-Xlint:package-object-classes", // Class or object defined in package object.
    "-Xlint:poly-implicit-overload", // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow", // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align", // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow", // A local type parameter shadows a type already in scope.
    "-Ywarn-dead-code", // Warn when dead code is identified.
    "-Ywarn-extra-implicit", // Warn when more than one implicit parameter section is defined.
    "-Xlint:unused", // Unused imports,privates,locals,implicits
    "-Ywarn-unused:implicits", // Warn if an implicit parameter is unused.
    "-Ywarn-unused:params", // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:locals", // Warn if a local definition is unused.
    "-Ywarn-unused:privates", // Warn if a private member is unused.
    "-Ywarn-value-discard", // Warn when non-Unit expression results are unused.
    "-Ybackend-parallelism",
    "6" // Enable parallelization — change to desired number!
  )
)

lazy val root = project
  .in(file("."))
  .aggregate(service, config)
  .settings(
    commonSettings,
    name := "TST Services",
    description := "TST Services"
  )

lazy val config = project
  .in(file("config"))
  .settings(
    name := "Config",
    description := "Application Configs",
    scalaVersion := scala2,
    libraryDependencies ++= Dependencies.dependencies,
    commonSettings
  )

lazy val service = project
  .in(file("service"))
  .settings(
    name := "TST Service",
    description := "TST Service",
    scalaVersion := scala2,
    dockerBaseImage := "azul/zulu-openjdk-alpine:17-latest",
    testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
    Compile / mainClass := Some("com.tst.service.Main"),
    dockerExposedPorts ++= Seq(8080),
    Universal / javaOptions ++= Seq(
      "-J-Xms2g",
      "-J-Xmx3g"
    ),
    libraryDependencies ++= Dependencies.dependencies,
    Test / coverageEnabled := true,
    Test / coverageFailOnMinimum := true,
    Test / coverageMinimumStmtTotal := 75,
    Test / coverageMinimumBranchTotal := 75,
    Global / onChangedBuildSource := ReloadOnSourceChanges,
    commonSettings
  )
  .enablePlugins(JavaServerAppPackaging)
  .enablePlugins(DockerPlugin)
  .enablePlugins(AshScriptPlugin)
  .enablePlugins(RevolverPlugin)

Revolver.enableDebugging(port = 5050, suspend = false)
