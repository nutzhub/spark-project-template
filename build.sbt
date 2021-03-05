import Dependencies._

ThisBuild / organization := "omise.co"
ThisBuild / scalaVersion := "2.12.11"

// TODO: please change jar name to consistent to your work
val jarName = "charge-job"

lazy val settings = Seq(
  // Must run Spark tests sequentially because they compete for port 4040!
  parallelExecution in Test := false,
  testForkedParallel in Test := false,
  fork in Test := true,
  // Scoverage settings
  coverageMinimum := 70,
  coverageFailOnMinimum := true,
  // Scalastyle settings
  scalastyleFailOnWarning := false,
  scalastyleFailOnError := true,
  // skip test when assembly
  test in assembly := {}
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "root"
  )
  .settings(settings: _*)
  .aggregate(
    sparkJob,
    sparkStreamJob
  )

lazy val sparkJob = (project in file("spark-job"))
  .settings(
    name := "spark-job",
    // Dependencies
    libraryDependencies ++= SparkDependencies,
    // Assembly
    assemblyJarName in assembly := s"$jarName-${version.value}.jar",
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case _                             => MergeStrategy.first
    }
  )
  .settings(settings: _*)
  .dependsOn(
    common
  )

lazy val sparkStreamJob = (project in file("spark-stream-job"))
  .settings(
    name := "spark-stream-job",
    // Dependencies
    libraryDependencies ++= SparkDependencies,
    // Assembly
    assemblyJarName in assembly := s"$jarName-stream-${version.value}.jar",
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case _                             => MergeStrategy.first
    }
  )
  .settings(settings: _*)
  .dependsOn(
    common
  )

lazy val common = (project in file("common")).settings(
  name := "common",
  // Dependencies
  libraryDependencies ++= SparkDependencies
)
