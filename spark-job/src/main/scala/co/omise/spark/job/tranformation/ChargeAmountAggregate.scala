package co.omise.spark.job.tranformation

import co.omise.spark.transform.TransformerTrait
import com.typesafe.scalalogging.StrictLogging
import org.apache.spark.sql.{DataFrame, SparkSession}

object ChargeAmountAggregate extends TransformerTrait with StrictLogging {
  def apply(df: DataFrame)(implicit spark: SparkSession): DataFrame = {
    import org.apache.spark.sql.functions._
    import spark.implicits._
    df
      .select("backend_name", "charged_amount")
      .filter($"captured" === "true")
      .groupBy($"backend_name")
      .agg(sum($"charged_amount") as "sum_charged_amount")
  }
}
