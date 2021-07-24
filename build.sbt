name := "raphtory-largest-value"

version := "0.1.0-SNAPSHOT"

ThisBuild / organization := "com.iamsmkr"
ThisBuild / scalaVersion := "2.13.4"

lazy val root = (project in file(".")).
  settings(
    name := "raphtory.g8"
  )

unmanagedJars in Compile += baseDirectory.value / "lib/raphtory-v0.11.jar"
