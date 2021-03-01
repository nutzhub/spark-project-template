package co.omise.spark

import co.omise.spark.storage.Storage
import com.typesafe.config.Config
import com.typesafe.scalalogging.StrictLogging
import org.apache.spark.sql.SparkSession

object WordCount extends SparkJob with StrictLogging {
  override def appName: String = "word count"

  override def run(
      spark: SparkSession,
      config: Config,
      inputStorage: Storage,
      outputStorage: Storage
  ): Unit = {
    logger.info(s"SPARK JOB ${appName}")
  }
}
