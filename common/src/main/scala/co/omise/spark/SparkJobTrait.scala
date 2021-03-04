package co.omise.spark

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession

trait SparkJobTrait {

  val config: Config = ConfigFactory.load()

  def jobName: String

  def main(args: Array[String]): Unit

  def acquireSparkSession(): SparkSession = SparkSession.builder
    .appName(jobName)
    .enableHiveSupport()
    .getOrCreate()
}
