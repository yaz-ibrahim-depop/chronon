package ai.chronon.spark

import ai.chronon.api.{ConfigProperties, ExecutionInfo, MetaData}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.JavaConverters._

class DriverTest extends AnyFlatSpec with Matchers {

  behavior of "Driver"

  it should "merge metadata common conf into online props with explicit props taking precedence" in {
    val conf = new ConfigProperties()
      .setCommon(
        Map(
          "spark.chronon.table_writer.ion_writer.timeout_ms" -> "5400000",
          "explicit" -> "metadata"
        ).asJava
      )
    val metadata = new MetaData().setExecutionInfo(new ExecutionInfo().setConf(conf))

    Driver.onlineProps(metadata, Map("explicit" -> "cli")) shouldBe Map(
      "spark.chronon.table_writer.ion_writer.timeout_ms" -> "5400000",
      "explicit" -> "cli"
    )
  }
}
