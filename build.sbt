name := "cats-tutorial"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.7"

scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:higherKinds",
  "-language:existentials",
  "-Xlint",
  // "-Xfatal-warnings",
  "-Ypartial-unification"
)

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7+",
  "org.typelevel" %% "cats-core" % "1.6.0",
  "org.typelevel" %% "cats-effect" % "1.2.0",
  "org.specs2" %% "specs2-core" % "4.2.0" % "test",
   "org.specs2" %% "specs2-scalacheck" % "4.5.1"  % "test",
  "io.monix" %% "monix" % "3.0.0-RC1")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
