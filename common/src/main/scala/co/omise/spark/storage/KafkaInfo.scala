package co.omise.spark.storage

case class KafkaInfo(bootstrapServers: Seq[String], topics: Seq[String])
