import sbt._

object Version {
  val spark = "3.0.2"
  val sparkTest = "0.21.3"
  val scalaLogging = "3.9.2"
  val scalaTest = "3.0.5"
  val scalaCheck = "1.13.4"
  val config = "1.4.1"
  val ficus = "1.5.0"
  val enumeratum = "1.6.1"
  val logback = "1.2.3"
  val commandlineArg = "0.6.0"
}

object Dependencies {
  val CommandLine = "com.concurrentthought.cla" %% "command-line-arguments" % Version.commandlineArg
  val Config = "com.typesafe" % "config" % Version.config
  val Enumeratum = "com.beachape" %% "enumeratum" % Version.enumeratum
  val Ficus = "com.iheart" %% "ficus" % Version.ficus
  val LogBack = "ch.qos.logback" % "logback-classic" % Version.logback

  val ScalaCheck = "org.scalacheck" %% "scalacheck" % Version.scalaCheck % Test
  val ScalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLogging
  val ScalaParser = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
  val ScalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest % Test

  val SparkCore = "org.apache.spark" %% "spark-core" % Version.spark % Provided
  val SparkHive = "org.apache.spark" %% "spark-hive" % Version.spark % Provided
  val SparkKafka = "org.apache.spark" %% "spark-sql-kafka-0-10" % Version.spark % Provided
  val SparkSql = "org.apache.spark" %% "spark-sql" % Version.spark % Provided
  val SparkStreaming = "org.apache.spark" %% "spark-streaming" % Version.spark % Provided
  val SparkTest = "com.github.mrpowers" %% "spark-fast-tests" % Version.sparkTest % Test

  //  Group dependencies
  val SparkDependencies = Seq(
//    CommandLine,
    Config,
    Enumeratum,
    Ficus,
    LogBack,
    ScalaCheck,
    ScalaLogging,
    ScalaParser,
    ScalaTest,
    SparkCore,
    SparkKafka,
    SparkSql,
    SparkStreaming,
    SparkTest
  )
}
