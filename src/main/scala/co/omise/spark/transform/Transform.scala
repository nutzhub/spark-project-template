package co.omise.spark.transform

import org.apache.spark.sql.DataFrame

trait Transform {
  def transform(srcDataFrame: DataFrame): DataFrame
}
