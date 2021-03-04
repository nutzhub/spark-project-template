package co.omise.spark.storage

import org.apache.spark.sql.{DataFrame, SaveMode}

trait Writer {
  def write(
      df: DataFrame,
      format: Format,
      saveMode: SaveMode,
      location: String,
      partitionBy: String*
  ): Unit
}

trait StreamWriter {
  def write(
      df: DataFrame,
      format: Format,
      saveMode: SaveMode,
      location: String,
      partitionBy: String*
  ): Unit
}
