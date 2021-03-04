# Spark Project Template
> references: https://godatadriven.com/blog/how-to-setup-and-structure-a-spark-application-in-scala/

## Project structure

This project follows the principle of data processing **Separates Concerns**

![Separates Concerns](./images/spark-sep-con.png)

**Input and Output** `co.omise.spark.storage` This area is an action execution

**Transformation** `co.omise.spark.transform` We put all transformation logic which is a transformation execution (Lazy execution)


## How to use this project as spark template


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
