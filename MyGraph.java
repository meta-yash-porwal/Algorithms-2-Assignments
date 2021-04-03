import java.util.*;

/**
 * this class is for edges on Graph
 * and is used to compare different edges using Comparable
 * @author yash.porwal_metacube
 *
 */
class Edge implements Comparable<Edge>{
	int source;
	int destination;
	int weight;
	Edge(int s,int d,int w){
		source = s;
		destination = d;
		weight = w;
	}
	
	/**
	 * it is used to compare edges 
	 */
	public int compareTo(Edge compareEdge)
    {
        return this.weight - compareEdge.weight;
    }
}

/**
 * it is used to store parent of graph
 * and the rank
 * @author yash.porwal_metacube
 *
 */
class subset
{
    int parent, rank;
};


/**
 * this class used to make graph by implementing graph class
 * @author yash.porwal_metacube
 *
 */
public class MyGraph implements Graph{
	
	List<Edge> graph;
	int V;

	/**
	 * constructor used to initialize the object by using node(vertices)
	 * @param v is integer which represent a node or vertices
	 */
	MyGraph(int v){
		graph = new ArrayList<Edge>();
		this.V = v;

	}
	/**
	 * add edges to the node or vertices
	 * @param s is source node in integer 
	 * @param d is destination in integer
	 * @param w is weight of the edge in integer
	 */
	public void addEdge(int s,int d,int w){
		graph.add(new Edge(s,d,w));
	}

	
	@Override
	public boolean isConnected(int root) {
		Set<Integer> visited = new HashSet<Integer>();
		
		Stack<Integer> st = new Stack<Integer>();
		st.push(root);
		while(!st.empty()){
			int curr = st.peek();
			st.pop();
			visited.add(curr);
			for(Edge e:graph){
				if(e.source == curr){
					if(!visited.contains(e.destination)){
						st.push(e.destination);
					}
				}
				else if(e.destination == curr){
					if(!visited.contains(e.source)){
						st.push(e.source);
					}
				}
			}
		}
		//System.out.println(visited.size());
		return visited.size() == this.V;
	}

	@Override
	public List<Integer> reachable(int a) {
		List<Integer> reachableNodes = new ArrayList<Integer>();
		for(Edge e:graph){
			if(e.source == a){
				reachableNodes.add(e.destination);
			}
			else if(e.destination == a){
				reachableNodes.add(e.source);				
			}
		}
		return reachableNodes;
	}
	
	/**
	 * find root & make root as parent 
	 * @param subsets 
	 * @param i - node
	 * @return integer
	 */
    int find(subset subsets[], int i)
    {
        // find root and make root as parent of i
        // (path compression)
        if (subsets[i].parent != i)
            subsets[i].parent
                = find(subsets, subsets[i].parent);
 
        return subsets[i].parent;
    }
    
    /**
     * it is used in minimum spanning tree (mst)
     * to make a new tree
     * with vertices & edges so that it is mst
     * @param subsets
     * @param x
     * @param y
     */
    void Union(subset subsets[], int x, int y)
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
 
        // Attach smaller rank tree under root
        // of high rank tree (Union by Rank)
        if (subsets[xroot].rank
            < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank
                 > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
 
        // If ranks are same, then make one as
        // root and increment its rank by one
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

	@Override
	public Edge[] mst() {
		Edge[] result = new Edge[V];
	       
        // An index variable, used for result[]
        int e = 0;
       
        // An index variable, used for sorted edges
        int i = 0;
        for (i = 0; i < V; ++i)
            result[i] = new Edge(0,0,0);
 
        // Step 1:  Sort all the edges in non-decreasing
        // order of their weight.  If we are not allowed to
        // change the given graph, we can create a copy of
        // array of edges
        Collections.sort(graph);
 
        // Allocate memory for creating V subsets
        subset subsets[] = new subset[V];
        for (i = 0; i < V; ++i)
            subsets[i] = new subset();
 
        // Create V subsets with single elements
        for (int v = 0; v < V; ++v)
        {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
 
        i = 0; // Index used to pick next edge
 
        // Number of edges to be taken is equal to V-1
        while (e < V - 1)
        {
            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            Edge next_edge = graph.get(i);
            i++;
 
            int x = find(subsets, next_edge.source);
            int y = find(subsets, next_edge.destination);
 
            // If including this edge does't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }
 
        // print the contents of result[] to display
        // the built MST
        System.out.println("Following are the edges in "
                           + "the constructed MST");
        int minimumCost = 0;
        for (i = 0; i < e; ++i)
        {
            System.out.println(result[i].source + " -- "
                               + result[i].destination
                               + " == " + result[i].weight);
            minimumCost += result[i].weight;
        }
        System.out.println("Minimum Cost Spanning Tree "
                           + minimumCost);
		return result;
	}

	@Override
	public int shortestPath(int a, int b) {
		return 0;
	}
	
	public static void main(String[] args){
		MyGraph mg = new MyGraph(4);
		mg.addEdge(0, 1, 10);
		mg.addEdge(0, 2, 6);
		mg.addEdge(0, 3, 5);
		mg.addEdge(1, 3, 15);
		mg.addEdge(2, 3, 4);
		System.out.println(mg.isConnected(0));
		List<Integer> x = mg.reachable(0);
		for(int a:x){
			System.out.println(a);
		}
		mg.mst();
	}

}