/**
 * 
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

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
	public String toString() {
		return "CapGraph [adjListMap=" + adjListMap + ", numVertices=" + numVertices + ", numEdges=" + numEdges + "]";
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
				//Do not allow to add a duplicate edge: Cannot be friends multiple times, cannot retweet multiple times
				String message4 = "Attempt to add a duplicate edge!";
				System.out.println(message4);
			}
		}
		else {
			if (!this.adjListMap.containsKey(from)) {
				String message1 = "Vertex " + from + " does not exist!";
				message1 += "\n Error when trying to add edge between " + from + " and " + to;
				System.out.println(message1);
				//throw new Exception(message1);
			}
			if (!this.adjListMap.containsKey(to)) {
				String message2 = "Vertex " + to + " does not exist!";
				message2 += "\n Error when trying to add edge between " + from + " and " + to;
				System.out.println(message2);
				//throw new Exception(message2);
			}
			if (from == to) {
				//Do not allow edge from vertex to itself: Cannot retweet yourself, cannot be friends with yourself
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
		if(this.adjListMap.containsKey(center)) {
			Graph egoNet = new CapGraph();
			egoNet.addVertex(center);
			for (Integer v : this.adjListMap.get(center)) {
				egoNet.addVertex(v);
				egoNet.addEdge(center, v);
			}
			HashMap<Integer, HashSet<Integer>> egoNetGraph = egoNet.exportGraph();
			for (Integer v : egoNetGraph.keySet()) {
				for (Integer neighbor : this.adjListMap.get(v)) {
					if (egoNetGraph.containsKey(neighbor)) {
						egoNet.addEdge(v, neighbor);
					}
				}
			}
			return egoNet;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		List<Graph> sccs = new ArrayList<Graph>();
		Stack<Integer> vertices = new Stack<Integer>();
		for (Integer i : this.adjListMap.keySet()) {
			vertices.push(i);
		}
		//System.out.println(vertices);
		Stack<Integer> dfs1Finished = DFS(null, this, vertices);
		//System.out.println(dfs1Finished);
		Stack<Integer> dfs2Finished = DFS(sccs, this.getTranspose(), dfs1Finished);
		//System.out.println(sccs);
		return sccs;
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
	
	public Set<Integer> getNeighborsSet(int v) throws Exception{
		return new HashSet<Integer>(this.getNeighbors(v));
	}
	
	public int getNumNeighbors(int v) throws Exception {
		return this.getNeighbors(v).size();
	}
	
	public boolean isVertex(int v) {
		if (this.adjListMap.containsKey(v)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private Stack<Integer> DFS(List<Graph> SCCs, CapGraph g, Stack<Integer> vertices) {
		HashSet<Integer> visited = new HashSet<Integer>();
		Stack<Integer> finished = new Stack<Integer>();
		while(!vertices.empty()) {
			Integer v = vertices.pop();
			Graph sccg = null;
			if (!visited.contains(v)) {
				if(SCCs != null) {
					sccg = new CapGraph();
					SCCs.add(sccg);
					sccg.addVertex(v);
				}
				DFSVisit(g, v, visited, finished, sccg);
			}
		}
		return finished;
	}
	
	private void DFSVisit(CapGraph g, Integer v, HashSet<Integer> visited, Stack<Integer> finished, Graph sccg) {
		visited.add(v);
		try{
			for (Integer n : g.getNeighbors(v)) {
				if (!visited.contains(n)) {
					if (sccg != null) {
						sccg.addVertex(n);
						sccg.addEdge(v, n);
					}
					DFSVisit(g, n, visited, finished, sccg);
				}
			}
			finished.push(v);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error when getting neighbors of " + v);
		}
	}
	
	private CapGraph getTranspose() {
		CapGraph gTranspose = new CapGraph();
		for (Integer v : this.adjListMap.keySet()) {
			gTranspose.addVertex(v);
		}
		for (Integer v : this.adjListMap.keySet()) {
			for (Integer n : this.adjListMap.get(v)) {
				gTranspose.addEdge(n, v);
			}
		}
		//System.out.println(gTranspose.exportGraph());
		return gTranspose;
	}
	
	private HashSet<Integer> getNeighbors(Integer v) throws Exception {
		if (this.adjListMap.containsKey(v)) {
			return this.adjListMap.get(v);
		}
		else {
			throw new Exception("Vertex " + v + " not found");
		}
	}
}
