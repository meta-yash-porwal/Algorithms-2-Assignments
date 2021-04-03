import java.util.*;

/**
 * this is a interface of Graph
 * @author yash.porwal_metacube
 *
 */
public interface Graph {
	
	/**
	 * checks that all the nodes in a graph is connected or not
	 * @param root value as integer value
	 * @return boolean - true/false
	 */
	public boolean isConnected(int root);
	
	/**
	 * provides the list of integer
	 * which nodes are reachable of
	 * @param a as integer
	 * @return list of integer
	 */
	public List<Integer> reachable(int a);
	
	/**
	 * finds the minimum spanning tree of a tree
	 * @return Edge's object array which has weight
	 */
	public Edge[] mst();
	
	/**
	 * finds the shortest path between a & b integer
	 * @param a integer
	 * @param b integer
	 * @return an integer value of shortest path
	 */
	public int shortestPath(int a,int b);
}