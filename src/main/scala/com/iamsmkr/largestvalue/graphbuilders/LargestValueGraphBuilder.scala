package com.iamsmkr.largestvalue.graphbuilders

import com.raphtory.core.actors.Router.GraphBuilder
import com.raphtory.core.model.communication.{LongProperty, Properties}

class LargestValueGraphBuilder extends GraphBuilder[String] {
  override protected def parseTuple(tuple: String): Unit = {
    val line = tuple.split(",").map(_.trim)
    val node1 = line(0)
    val node1Id = assignID(node1)

    val node2 = line(1)
    val node2Id = assignID(node2)

    val ts = line(2).toLong

    addVertex(ts, node1Id, Properties(LongProperty("value", node1.toLong)))
    addVertex(ts, node2Id, Properties(LongProperty("value", node2.toLong)))

    addEdge(ts, node1Id, node2Id)
  }
}
