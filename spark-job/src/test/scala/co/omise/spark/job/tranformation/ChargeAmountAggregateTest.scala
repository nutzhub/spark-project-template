package co.omise.spark.job.tranformation

import co.omise.spark.job.SparkSessionTestWrapper
import co.omise.spark.storage.Format
import com.github.mrpowers.spark.fast.tests.{ColumnComparer, DataFrameComparer}
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.DataFrame
import org.scalatest.FlatSpec

class ChargeAmountAggregateTest
    extends FlatSpec
    with SparkSessionTestWrapper
    with LazyLogging
    with DataFrameComparer
    with ColumnComparer {

  import spark.implicits._

  "Charge amount" should "sum charge amt per backend_name" in {
    val df = reader.read(
      Seq("spark-job/src/test/resources/test-data/charge_transaction.json"),
      Format.Json,
      schema = None
    )
    val actual = ChargeAmountAggregate(df)
    val expected: DataFrame = Seq(("saison_caps", Some(7000.0))).toDF("backend_name", "sum_charged_amount")
    assertSmallDataFrameEquality(actual, expected)
  }
}
