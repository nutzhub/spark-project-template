package co.omise.spark

import com.github.mrpowers.spark.fast.tests.DatasetComparer
import org.scalatest.FlatSpec

class EqualDataFrameTest
    extends FlatSpec
    with SparkSessionTestWrapper
    with DatasetComparer {
  import spark.implicits._

  "2 DataFrame" should "be equal" in {
    val sourceDF = Seq(
      (1, "one"),
      (5, "five")
    ).toDF("number", "word")

    val expectedDF = Seq(
      (1, "one"),
      (5, "five")
    ).toDF("number", "word")

    assertSmallDatasetEquality(sourceDF, expectedDF)
  }
}
