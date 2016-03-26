/**
 * 
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Your name here.
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {

	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	private HashMap<Integer, HashSet<Integer>> adjListMap;
	private int numVertices;
	private int numEdges;
	
	public CapGraph() {
		adjListMap = new HashMap<Integer, HashSet<Integer>>();
		numVertices = 0;
		numEdges = 0;
	}
	
	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		if (!this.adjListMap.containsKey(num)) {
			this.adjListMap.put(num, new HashSet<Integer>());
			numVertices++;
		}
		else {
			String message = "Vertex " + num + " already exists!";
			System.out.println(message);
		}
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		if (this.adjListMap.containsKey(from) && this.adjListMap.containsKey(to) && from != to) {
			if (!this.adjListMap.get(from).contains(to)) {
				this.adjListMap.get(from).add(to);
				this.numEdges++;
			}
			else {
				String message4 = "Attempt to add a duplicate edge!";
				System.out.println(message4);
			}
		}
		else {
			if (!this.adjListMap.containsKey(from)) {
				String message1 = "Vertex " + from + " does not exist!";
				System.out.println(message1);
				//throw new Exception(message1);
			}
			if (!this.adjListMap.containsKey(to)) {
				String message2 = "Vertex " + to + " does not exist!";
				System.out.println(message2);
				//throw new Exception(message2);
			}
			if (from == to) {
				String message3 = "Attempt to add edge from " + from + " to itself!";
				System.out.println(message3);
			}
		}
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		return new HashMap<Integer, HashSet<Integer>>(this.adjListMap);
	}
	
	public int getNumVertices(){
		return this.numVertices;
	}
	
	public int getNumEdges(){
		return this.numEdges;
	}
}
