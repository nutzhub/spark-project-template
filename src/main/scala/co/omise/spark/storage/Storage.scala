package co.omise.spark.storage

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, SparkSession}

trait Storage {
  val S3_SCHEME: String = "s3://"
  def read(
      paths: Seq[String],
      format: Format,
      schema: Option[StructType],
      options: (String, String)*
  ): DataFrame

  def write(
      ds: DataFrame,
      format: Format,
      location: String,
      partitionBy: String*
  ): Unit
}

class StorageImpl(spark: SparkSession) extends Storage {
  override def read(
      paths: Seq[String],
      format: Format,
      schema: Option[StructType],
      options: (String, String)*
  ): DataFrame = format match {
    case Format.Json =>
      spark.read.options(options.toMap).schema(schema.get).json(paths: _*)
    case Format.Parquet =>
      spark.read.options(options.toMap).schema(schema.get).parquet(paths: _*)
    case Format.Csv =>
      spark.read.options(options.toMap).schema(schema.get).csv(paths: _*)
  }

  override def write(
      ds: DataFrame,
      format: Format,
      location: String,
      partitionBy: String*
  ): Unit = format match {
    case Format.Json => ds.write.partitionBy(partitionBy: _*).json(location)
    case Format.Parquet =>
      ds.write.partitionBy(partitionBy: _*).parquet(location)
    case Format.Csv => ds.write.partitionBy(partitionBy: _*).csv(location)
  }
}
