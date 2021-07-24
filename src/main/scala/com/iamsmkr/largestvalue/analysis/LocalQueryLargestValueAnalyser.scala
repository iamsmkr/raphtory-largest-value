package com.iamsmkr.largestvalue.analysis

import com.raphtory.api.Analyser

import scala.collection.mutable.ArrayBuffer
import scala.util.control.NonFatal

object LocalQueryLargestValueAnalyser {
  def apply(): LocalQueryLargestValueAnalyser = new LocalQueryLargestValueAnalyser(Array())
}

class LocalQueryLargestValueAnalyser(args: Array[String]) extends Analyser(args) {
  override def analyse(): Unit = {}

  override def setup(): Unit =
    view.getVertices().foreach { vertex =>
      vertex.setState("largestvalue", vertex.getPropertyValue("value").getOrElse(0L))
    }

  override def returnResults(): Option[Long] = {
    val vs = view.getVertices()
    if (vs.nonEmpty) Some(vs.map { x =>
      x.getPropertyValue("value").getOrElse(0L).asInstanceOf[Long]
    }.max)
    else None
  }

  override def defineMaxSteps(): Int = 1

  override def processResults(results: ArrayBuffer[Any], timeStamp: Long, viewCompleteTime: Long): Unit = {
    val res = results.asInstanceOf[ArrayBuffer[Option[Long]]]
    try {
      println(s"largest value = ${res.flatten.max}")
    } catch {
      case NonFatal(e) => println(e.getMessage)
    }
  }
}
