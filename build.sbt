import Dependencies._

lazy val root = project
  .in(file("."))
  .settings(
    name := "root",
    organization := "omise.co",
    scalaVersion := "2.12.11",
    // Must run Spark tests sequentially because they compete for port 4040!
    parallelExecution in Test := false,
    testForkedParallel in Test := false,
    testForkedParallel in IntegrationTest := false,
    fork in Test := true,
    // Scoverage settings
    coverageExcludedPackages := "<empty>;.*storage.*;.*transform.*;",
    coverageMinimum := 70,
    coverageFailOnMinimum := true,
    // Scalastyle settings
    scalastyleFailOnWarning := false,
    scalastyleFailOnError := true,
    // Assembly
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case _                             => MergeStrategy.first
    }
  )
  .aggregate(
    sparkJob,
    sparkStreamJob
  )

lazy val sparkJob = (project in file("spark-job"))
  .settings(
    name := "spark-job",
    // Dependencies
    libraryDependencies ++= SparkDependencies
  )
  .dependsOn(
    common
  )

lazy val sparkStreamJob = (project in file("spark-stream-job"))
  .settings(
    name := "spark-stream-job",
    // Dependencies
    libraryDependencies ++= SparkDependencies
  )
  .dependsOn(
    common
  )

lazy val common = (project in file("common")).settings(
  name := "common",
  // Dependencies
  libraryDependencies ++= SparkDependencies
)
