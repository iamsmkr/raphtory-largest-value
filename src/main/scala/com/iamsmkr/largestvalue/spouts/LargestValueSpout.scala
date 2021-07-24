package com.iamsmkr.largestvalue.spouts

import com.raphtory.core.actors.Spout.Spout

import scala.collection.mutable
import scala.io.Source

class LargestValueSpout extends Spout[String] {

  private lazy val filePath = "src/main/resources/data.csv"

  private lazy val source = Source.fromFile(filePath)
  private lazy val lines = mutable.Queue[String]()

  override def setupDataSource(): Unit = lines ++= source.getLines()

  override def generateData(): Option[String] = {
    if (lines.isEmpty) {
      dataSourceComplete()
      None
    } else Some(lines.dequeue())
  }

  override def closeDataSource(): Unit = source.close()

}
