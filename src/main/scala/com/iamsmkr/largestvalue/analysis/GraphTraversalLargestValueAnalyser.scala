package com.iamsmkr.largestvalue.analysis

import com.raphtory.api.Analyser

import scala.collection.mutable.ArrayBuffer
import scala.collection.parallel.ParIterable
import scala.util.control.NonFatal

object GraphTraversalLargestValueAnalyser {
  def apply(): GraphTraversalLargestValueAnalyser = new GraphTraversalLargestValueAnalyser(Array())
}

class GraphTraversalLargestValueAnalyser(args: Array[String]) extends Analyser(args) {

  override def analyse(): Unit =
    view.getMessagedVertices().foreach { vertex =>
      val largestValueState = vertex.getState[Long]("largestvalue")
      val largestValueReceived = vertex.messageQueue[Long].max

      if (largestValueReceived > largestValueState) {
        vertex.setState("largestvalue", largestValueReceived)
        vertex.messageAllNeighbours(largestValueReceived)
      }
    }


  override def setup(): Unit =
    view.getVertices().foreach { vertex =>
      val largestValue = vertex.getPropertyValue("value").getOrElse(0L)
      vertex.setState("largestvalue", largestValue)
      vertex.messageAllNeighbours(largestValue)
    }

  override def returnResults(): Any = view.getVertices().map(_.getState[Long]("largestvalue"))

  override def defineMaxSteps(): Int = 100

  override def processResults(results: ArrayBuffer[Any], timeStamp: Long, viewCompleteTime: Long): Unit = {
    val res = results.asInstanceOf[ArrayBuffer[ParIterable[Long]]]
    try {
      println(s"largest value = ${res.flatten.max}")
    } catch {
      case NonFatal(e) => println(e.getMessage)
    }
  }

}
