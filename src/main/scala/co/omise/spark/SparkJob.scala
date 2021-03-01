package co.omise.spark

import co.omise.spark.storage.{Storage, StorageImpl}
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession

trait SparkJob {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName(appName)
      .enableHiveSupport()
      .getOrCreate()
    val config: Config = ConfigFactory.load("defaults.conf")
    run(
      spark = spark,
      config = config,
      inputStorage = new StorageImpl(spark),
      outputStorage = new StorageImpl(spark)
    )
  }

  def run(
      spark: SparkSession,
      config: Config,
      inputStorage: Storage,
      outputStorage: Storage
  )

  def appName: String
}
