import Dependencies._

lazy val root = project
  .in(file("."))
  .settings(
    name := "spark-job",
    version := "1.0.0",
    organization := "omise.co",
    scalaVersion := "2.12.11",
    // Must run Spark tests sequentially because they compete for port 4040!
    parallelExecution in Test := false,
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
    },
    // Dependencies
    libraryDependencies ++= SparkDependencies
  )
  .configs(IntegrationTest)
