package co.omise.spark.storage

import enumeratum._

import scala.collection.immutable

sealed trait Format extends EnumEntry

object Format extends Enum[Format] {
  /*
   `findValues` is a protected method that invokes a macro to find all `Format` object declarations inside an `Enum`

   You use it to implement the `val values` member
   */
  val values: immutable.IndexedSeq[Format] = findValues

  case object Json extends Format

  case object Csv extends Format

  case object Parquet extends Format

  def apply(format: String): Format = {
    Option(format) match {
      case Some(format: String) =>
        Format
          .withNameInsensitiveOption(format)
          .getOrElse(throw new IllegalArgumentException(s"Unsupported format - $format"))
      case None => Json
    }
  }
}
