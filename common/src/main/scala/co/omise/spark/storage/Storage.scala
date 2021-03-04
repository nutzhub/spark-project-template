package co.omise.spark.storage

import com.typesafe.config.{Config, ConfigFactory}
import net.ceedubs.ficus.Ficus._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, DataFrameReader, DataFrameWriter, Row, SaveMode, SparkSession}

abstract class Storage extends Reader with Writer {}

class StorageImpl(implicit spark: SparkSession) extends Storage {
  private val config: Config = ConfigFactory.load()

  override def read(
      paths: Seq[String],
      format: Format,
      schema: Option[StructType],
      options: (String, String)*
  ): DataFrame = format match {
    case Format.Json    => withSchema(spark.read.options(options.toMap), schema).json(paths: _*)
    case Format.Parquet => withSchema(spark.read.options(options.toMap), schema).parquet(paths: _*)
    case Format.Csv     => withSchema(spark.read.options(options.toMap), schema).csv(paths: _*)
  }

  private def withSchema(dfReader: DataFrameReader, schema: Option[StructType]): DataFrameReader = if (
    schema.nonEmpty
  ) {
    dfReader.schema(schema.get)
  } else {
    dfReader
  }

  override def write(
      df: DataFrame,
      format: Format,
      saveMode: SaveMode,
      location: String,
      partitionBy: String*
  ): Unit = format match {
    case Format.Json =>
      withPartition(df.coalesce(config.as[Int]("write_config.coalesce")).write, partitionBy: _*).json(location)
    case Format.Parquet =>
      withPartition(
        df
          .coalesce(config.as[Int]("write_config.coalesce"))
          .write,
        partitionBy: _*
      )
        .mode(saveMode)
        .option("maxRecordsPerFile", config.as[Int]("write_config.max_records_per_file"))
        .parquet(location)

    case Format.Csv => withPartition(df.write, partitionBy: _*).csv(location)
  }

  private def withPartition(dfWriter: DataFrameWriter[Row], partitionBy: String*): DataFrameWriter[Row] = if (
    partitionBy.nonEmpty
  ) {
    dfWriter.partitionBy(partitionBy: _*)
  } else {
    dfWriter
  }
}

abstract class StreamStorage extends StreamReader with StreamWriter {
  protected val FORMAT: String = "kafka"
}

class KafkaStorage(implicit spark: SparkSession) extends StreamStorage {
  override def readStream(schema: Option[StructType], options: (String, String)*): DataFrame = if (schema.nonEmpty) {
    spark.readStream
      .format(FORMAT)
      .schema(schema.get)
      .options(options.toMap)
      .load()
  } else {
    spark.readStream
      .format(FORMAT)
      .options(options.toMap)
      .load()
  }

  override def write(df: DataFrame, format: Format, saveMode: SaveMode, location: String, partitionBy: String*): Unit =
    ???
}
