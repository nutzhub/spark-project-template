package co.omise.spark.job

import co.omise.spark.storage.{Reader, StorageImpl}
import org.apache.spark.sql.SparkSession

trait SparkSessionTestWrapper {
  implicit val spark: SparkSession = {
    SparkSession
      .builder()
      .master("local")
      .appName("spark session")
      .config("spark.sql.shuffle.partitions", "1")
      .getOrCreate()
  }
  val reader: Reader = new StorageImpl
}
