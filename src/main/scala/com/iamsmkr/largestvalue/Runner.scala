package com.iamsmkr.largestvalue

import com.iamsmkr.largestvalue.analysis._
import com.iamsmkr.largestvalue.graphbuilders.LargestValueGraphBuilder
import com.iamsmkr.largestvalue.spouts.LargestValueSpout
import com.raphtory.RaphtoryGraph

object Runner extends App {

  val source = new LargestValueSpout()
  val builder = new LargestValueGraphBuilder()
  val graph = RaphtoryGraph[String](source, builder)
  val arguments = Array[String]()


  graph.viewQuery(GraphTraversalLargestValueAnalyser(), timestamp = 1517386320, arguments)

  graph.viewQuery(LocalQueryLargestValueAnalyser(), timestamp = 1517386300, arguments)
  graph.viewQuery(LocalQueryLargestValueAnalyser(), timestamp = 1517386310, arguments)
  graph.viewQuery(LocalQueryLargestValueAnalyser(), timestamp = 1517386320, arguments)

  graph.viewQuery(LocalQueryLargestValueAnalyser(), timestamp = 1517386320, window = 11, arguments)
  graph.viewQuery(LocalQueryLargestValueAnalyser(), timestamp = 1517386320, window = 21, arguments)

  graph.viewQuery(LocalQueryLargestValueAnalyser(), timestamp = 1517386320, windowBatch = Array(21L, 11L, 6L), arguments)

  graph.rangeQuery(LocalQueryLargestValueAnalyser(), start = 1517386310, end = 1517386320, increment = 10, window = 11, arguments)

  graph.rangeQuery(LocalQueryLargestValueAnalyser(), start = 1517386310, end = 1517386320, increment = 11, windowBatch = Array(11L, 5L), arguments)

}
