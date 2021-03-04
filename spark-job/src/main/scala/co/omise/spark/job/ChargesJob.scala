package co.omise.spark.job

import co.omise.spark.SparkJobTrait
import co.omise.spark.job.tranformation.ChargeAmountAggregate
import co.omise.spark.storage.{Format, Reader, StorageImpl, Writer}
import com.typesafe.scalalogging.StrictLogging
import org.apache.spark.sql.{SaveMode, SparkSession}

object ChargesJob extends SparkJobTrait with StrictLogging {
  override def main(args: Array[String]): Unit = {

    implicit val spark: SparkSession = acquireSparkSession()
    spark.sparkContext.setLogLevel("WARN")

    logger.info(s"Start $jobName")
    val reader: Reader = new StorageImpl
    val writer: Writer = new StorageImpl

    val df =
      reader.read(Seq("test-data/charge_txn.json"), Format.Json, schema = None, "multiLine" -> "true")

    /** Transformation area */
    val aggDf = ChargeAmountAggregate(df)

    /** End */
    writer.write(aggDf, Format.Json, SaveMode.Overwrite, "test-result")
    logger.info(s"End $jobName")
    spark.close()
  }

  override def jobName: String = "charges-amount-aggregation"
}
