# Spark Project Template
> references: https://godatadriven.com/blog/how-to-setup-and-structure-a-spark-application-in-scala/

## Project structure

This project follows the principle of data processing **Separates Concerns**

[Separates Concerns](./images/spark-sep-con.png)

**Input and Output** are defined in storage package `co.omise.spark.storage`. This area is an action execution

**Transformation** `co.omise.spark.transform` where we put all transformation logic which is a transformation execution (Lazy execution)


## How to use this project as spark template


## Essential sbt command
