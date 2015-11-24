/*
Author: Prasad

Scala code to utilise Spark's GraphX library to read an Edge List file 
and perform Breadth First Search using the pregel API

*/

//System imports
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx.util._

// Custom exceptions defined
case class NoArgumentException(message: String) extends Exception(message)


object GraphBFS{
   
   // Simple function to evaluate CMD arguments
   def parseArgs(args): Unit = {
	if(args.length == 0){
	    println("Please provide the graph edgelist file")
	    throw new NoArgumentException("No arguments provided")
	}
	val edgeFile = new java.io.File(args(0))
	if(!edgeFile.exists){
		throw new FileNotFoundException(s"${args(0)}")
	}
	if(!edgeFile.canRead)){
 	    throw new IOException(s"Unable to read edgeListFile : ${args(0)}")
	}
   }

   // Vertex function to be used by pregel API     
   def vprog(id: VertexId, attr: Double, msg: Double): Double = math.min(attr,msg) 

   // Reduce Message function to be used in pregel
   def reduceMessage(a: Double, b: Double) = math.min(a,b)

   // Send Message() to be used in pregel	
   def sendMessage(triplet: EdgeTriplet[Double, Int]) : Iterator[(VertexId, Double)] = {
	var iter:Iterator[(VertexId, Double)] = Iterator.empty
	val isSrcMarked = triplet.srcAttr != Double.PositiveInfinity
	val isDstMarked = triplet.dstAttr != Double.PositiveInfinity
	if(!(isSrcMarked && isDstMarked)){
	   if(isSrcMarked){
		iter = Iterator((triplet.dstId,triplet.srcAttr+1))
	   }else if(isDstMarked){
		iter = Iterator((triplet.srcId,triplet.dstAttr+1))
	   }
	}
	iter
   }

   
   def main(args: Array[String]) {
   	parseArgs(args)
	val sConf = new SparkConf()
		    .setMaster("local[2]")
		    .setAppName("GraphBFS")
	sConf.set("spark.app.id","BFSGraph")
	val sContext = new SparkContext(sConf)
	val graph = GraphLoader.edgeListFile(sContext,args(0))

	val root: VertexId = graph.vertices.first()._1
        val initialGraph = graph.mapVertices((id, _) =>  if (id == root) 0.0 else Double.PositiveInfinity)
	
	val initialMessage = Double.PositiveInfinity
	val maxIterations = Int.MaxValue
	val activeEdgeDirection = EdgeDirection.Either
	
	val bfs = initialGraph.pregel
	(initialMessage, maxIterations, activeEdgeDirection)
	(vprog, sendMessage, reduceMessage)
	println(bfs.vertices.collect.mkString("\n"))
   }
}
