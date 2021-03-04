package co.omise.spark.storage

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.StructType

trait Reader {
  def read(
      paths: Seq[String],
      format: Format,
      schema: Option[StructType],
      options: (String, String)*
  ): DataFrame
}

trait StreamReader {
  def readStream(schema: Option[StructType], options: (String, String)*): DataFrame
}
