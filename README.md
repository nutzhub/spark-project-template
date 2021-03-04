# Spark Project Template
> references: https://godatadriven.com/blog/how-to-setup-and-structure-a-spark-application-in-scala/

## Project structure

This project follows the principle of data processing **Separates Concerns**

![Separates Concerns](./images/spark-sep-con.png)

**Input and Output** `co.omise.spark.storage` This area is an action execution

**Transformation** `co.omise.spark.transform` We put all transformation logic which is a transformation execution (Lazy execution)


## How to use this project as spark template

Main function snippet

```scala
object ChargesJob extends SparkJobTrait with StrictLogging {
  override def main(args: Array[String]): Unit = {
    // Spark session instanciate
    implicit val spark: SparkSession = acquireSparkSession()
    spark.sparkContext.setLogLevel("WARN")

    logger.info(s"Start $jobName")
    // Declare DataFrame reader and writer
    val reader: Reader = new StorageImpl
    val writer: Writer = new StorageImpl
    
    // Read source file
    val df =
      reader.read(Seq("test-data/charge_txn.json"), Format.Json, schema = None, "multiLine" -> "true")

    /** Transformation area */
    val aggDf = ChargeAmountAggregate(df)

    /** End */
    // Read source file
    writer.write(aggDf, Format.Json, SaveMode.Overwrite, "test-result")
    logger.info(s"End $jobName")
    spark.close()
  }

  override def jobName: String = "charges-amount-aggregation"
}

```

Transformation Snippet

```scala
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
```

## Essential sbt command

### Run test

```shell
sbt clean test
```

### Package uber jar
```shell
sbt clean assembly
```

### Spark submit cli

```shell
spark-submit --class co.omise.spark.WordCount
    $(pwd)/target/scala-2.12/spark-job-assembly-1.0.0.jar
```
