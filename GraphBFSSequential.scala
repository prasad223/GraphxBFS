import scala.io.Source
import scala.collection.mutable
import scala.util.Random

object GraphBFSSequential{
    type Vertex = BigInt
    type Graph = Map[Vertex,List[Vertex]]

    def bfs(g:Graph,unmarkedSet:Set[Vertex],queue:List[Vertex]):List[Vertex] = queue match{
        case Nil => Nil
        case head::tail => head::bfs(g,unmarkedSet - head,
            tail++ g.getOrElse(head,List()).reverse intersect(unmarkedSet.toList))
    }
  def main(args: Array[String]){
    val mainTime = System.nanoTime
    if(args.isEmpty){
	    println("Please provide the graph edgelist file")
	    System.exit(1); }
    val edgeFile = new java.io.File(args(0))
	if(!(edgeFile.exists && edgeFile.canRead)){
 	    println("$edgeFile does not exist or is not readable")
	    System.exit(1); }
    val fileReadTime = System.nanoTime
    var nodes:mutable.Map[Vertex, mutable.Set[Vertex]] = mutable.Map()
    for(line <- Source.fromFile(edgeFile).getLines()){
        var verts:Array[Vertex] = line.trim.split("\\s+").map(BigInt(_))
		var u = verts.min
		var v = verts.max
    	if(u != v){
			nodes += ((u, nodes.getOrElse(u, mutable.Set[Vertex]())+v))
            nodes += ((v, nodes.getOrElse(v, mutable.Set[Vertex]())+u)) 
        }else{    
            nodes += ((u, nodes.getOrElse(u, mutable.Set[Vertex]())+u))
        }
   	}
    val g:Graph = nodes.map{ case(k,v) => (k,v.toList) }(collection.breakOut):Graph
    println("Graph Read Time: " + (System.nanoTime-fileReadTime)/1e6+" ms")
    val firstNode = g.keySet.head
    val bfs1 = System.nanoTime()
    val bfsnodes = bfs(g,g.keySet,List(firstNode))
    println("BFS("+firstNode+") Time: "+ (System.nanoTime-bfs1)/1e6+" ms") 
    println("BFS(1): " +bfsnodes.mkString(","))
    
    val node:Vertex = g.keySet.toList(Random.nextInt(g.keySet.size))
    val bfsr = System.nanoTime()
    val bfsNodes = bfs(g,g.keySet,List(node))
    println("BFS("+node+") Time: "+(System.nanoTime - bfsr)/1e6+" ms")
    println("BFS("+node+"): " + bfsNodes.mkString(","))
    println("TotalProgramRunTime: "+(System.nanoTime - mainTime)/1e6+" ms")
   } 
}

GraphBFSSequential.main(args)
