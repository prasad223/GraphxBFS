// Program to print BFS traversal from a given source vertex. BFS(int s) 
// traverses vertices reachable from s.
#include <iostream>
#include <fstream>
#include <sstream>
#include <cstdio>
#include <ctime>
#include <deque>

using namespace std;
 
// This class represents a directed graph using adjacency list representation

class Graph
{
    int V;    // No. of vertices
    deque<int> *adj;    // Pointer to an array containing adjacency lists
public:
    Graph(int V);  // Constructor
    void addEdge(int v, int w); // function to add an edge to graph
    void BFS(int s);  // prints BFS traversal from a given source s
    void display();
};
 
Graph::Graph(int V)
{
    this->V = V;
    adj = new deque<int>[V];
}

void Graph::display()
{
  for(int i = 0; i < V;i++)
  {
     deque<int>::iterator v;
     for( v = adj[i].begin(); v != adj[i].end(); ++v)
     { 
        cout << i << "  " << *v << "\n";
     }
  }
} 

void Graph::addEdge(int v, int w)
{
    adj[v].push_back(w); // Add w to v’s list.
}
 
void Graph::BFS(int s)
{
    // Mark all the vertices as not visited
    bool *visited = new bool[V];
    for(int i = 0; i < V; i++)
        visited[i] = false;
 
    // Create a queue for BFS
    deque<int> queue;
 
    // Mark the current node as visited and enqueue it
    visited[s] = true;
    queue.push_back(s);
 
    // 'i' will be used to get all adjacent vertices of a vertex
    deque<int>::iterator i;
 
    while(!queue.empty())
    {
        // Dequeue a vertex from queue and print it
        s = queue.front();
        //cout << s << " ";
        queue.pop_front();
 
        // Get all adjacent vertices of the dequeued vertex s
        // If a adjacent has not been visited, then mark it visited
        // and enqueue it
        for(i = adj[s].begin(); i != adj[s].end(); ++i)
        {
            if(!visited[*i])
            {
                visited[*i] = true;
                queue.push_back(*i);
            }
        }
    }
}
 
// Driver program to test methods of graph class
int main(int argc, char* argv[])
{
   if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << "--file numvertices" << std::endl;
        return 1;
    }
   ifstream infile(argv[1]);
   istringstream s_str(argv[2]);
    int V;
    s_str >> V;
    // Create a graph given in the above diagram
    Graph g(V);
    int a,b;
    while(infile >> a >> b)
       g.addEdge(a,b);
 
    clock_t start;
    double duration;
    start = std::clock();
    g.BFS(0);
    duration = ( std::clock() - start ) / (double) CLOCKS_PER_SEC;
    cout<<"time: " << duration<<"\n";
    return 0;
}
