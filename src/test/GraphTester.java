package test;
import graph.CapGraph;
import graph.Graph;
import util.GraphLoader;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class GraphTester {
	public Graph graph1;
	public Graph graph2;
	public Graph graph3;
	
	@Before
	public void setUp() throws Exception {
		graph1 = new CapGraph();
		graph2 = new CapGraph();
		graph3 = new CapGraph();
	}
	
	@Test
	public void testAddVertex() {
		try {
			graph1.addVertex(1);
			if(!graph1.exportGraph().containsKey(1)) {
				fail("Unable to find vertex " + 1 + " in the graph");
			}
			if(graph1.getNumVertices() != 1) {
				fail("Number of vertices was not correct: " + graph1.getNumVertices());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Failed to add a vertex");
		}
		
		try {
			graph1.addVertex(1);
			if (!graph1.exportGraph().containsKey(1)) {
				fail("Added same vertex twice, unable to find it in the graph");
			}
			if (graph1.getNumVertices() != 1) {
				fail("Number of vertices was not correct: " + graph1.getNumVertices());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Adding same vertex again causes exception");
		}
		
		try {
			for(int i=0; i<100; i++) {
				graph1.addVertex(i);
			}
			HashMap<Integer, HashSet<Integer>> g = graph1.exportGraph();
			for (int i=0; i<100; i++) {
				if (!g.containsKey(i)) {
					fail("One of the vertices added continuously is not present");
				}
			}
			if (graph1.getNumVertices() != 100) {
				fail("Number of vertices was not correct: " + graph1.getNumVertices());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Adding 100 vertices continuously causes exception");
		}
	}
	
	@Test
	public void testAddEdge() {
		try {
			graph1.addEdge(1, 1001);
			if (graph1.getNumVertices() != 0) {
				fail("Adding an edge between non-existent vertices "
						+ "increments number of vertices: " + graph1.getNumVertices());
			}
			if (graph1.getNumEdges() != 0) {
				fail("Adding an edge between non-existent vertices "
						+ "increments number of edges: " + graph1.getNumEdges());
			}
			if (graph1.exportGraph().containsKey(1) || graph1.exportGraph().containsKey(1001)) {
				fail("Adding an edge between non-existent vertices "
						+ "adds the vertices");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Adding an edge between non-existent vertices causes exception");
		}
		
		try {
			graph1.addVertex(1);
			graph1.addEdge(1, 1001);
			if (graph1.getNumVertices() != 1) {
				fail("Adding an edge from existing vertex to "
						+ "non-existing vertex increments number of vertices: " + graph1.getNumVertices());
			}
			if (graph1.getNumEdges() != 0) {
				fail("Adding an edge from existing vertex to "
						+ "non-existing vertex increments number of edges: " + graph1.getNumEdges());
			}
			if (graph1.exportGraph().containsKey(1001)) {
				fail("Adding an edge from existing vertex to non-existing vertex "
						+ "adds the non-existing vertex");
			}
			if (graph1.exportGraph().get(1).contains(1001)) {
				fail("Adding an edge from existing vertex to non-existing vertex "
						+ "adds the non-existing vertex into adjacency set of existing vertex");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Adding an edge from existing vertex to non-existent vertex causes exception");
		}
		
		try {
			graph1.addEdge(1, 1);
			if (graph1.getNumVertices() != 1) {
				fail("Adding an edge from a vertex to itself increases the number of vertices: " + graph1.getNumVertices());
			}
			if (graph1.getNumEdges() != 0) {
				fail("Adding an edge from a vertex to itself increases the number of edges: " + graph1.getNumEdges());
			}
			if (graph1.exportGraph().get(1).contains(1)) {
				fail("Adding an edge from a vertex to itself adds that vertex to its own adjacency list");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Adding an edge from existing vertex to itself causes exception");
		}
		
		try {
			graph1.addVertex(1001);
			graph1.addEdge(1, 1001);
			if (graph1.getNumEdges() != 1) {
				fail("Adding an edge between two existing vertices "
						+ "gives incorrect number of edges: " + graph1.getNumEdges());
			}
			if (graph1.getNumVertices() != 2) {
				fail("Adding an edge between two existing vertices "
						+ "gives incorrect number of vertices: " + graph1.getNumVertices());
			}
			if (!graph1.exportGraph().get(1).contains(1001)) {
				fail("Adding an edge between two existing vertices "
						+ " is not adding 2nd vertex to first vertex's adjacency set");
			}
			if (graph1.exportGraph().get(1001).contains(1)) {
				fail("Adding an edge between two existing vertices "
						+ " is adding 1st vertex into 2nd vertex's adjacency set");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Adding an edge between two existing vertices causes exception");
		}
		
		try {
			graph1.addEdge(1, 1001);
			if (graph1.getNumVertices() != 2) {
				fail("Adding a duplicate edge increases the number of vertices: " + graph1.getNumVertices());
			}
			if (graph1.getNumEdges() != 1) {
				fail("Adding a duplicate edge increases the number of edges: " + graph1.getNumEdges());
			}
			if (!graph1.exportGraph().containsKey(1) || !graph1.exportGraph().containsKey(1001)) {
				fail("Adding a duplicate edge removes the vertices");
			}
			if (!graph1.exportGraph().get(1).contains(1001)) {
				fail("Adding a duplicate edge removes the to vertex from the adjacency set of the from vertex");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Adding a duplicate edge causes an exception");
		}
		
		try {
			graph1.addEdge(1001, 1);
			if (graph1.getNumVertices() != 2) {
				fail("Adding an edge opposite to an existing edge changes the vertex count: " + graph1.getNumVertices());
			}
			if (graph1.getNumEdges() != 2) {
				fail("Adding an edge opposite to an existing edge does not show the right number of edges: " + graph1.getNumEdges());
			}
			if (!graph1.exportGraph().get(1001).contains(1) || !graph1.exportGraph().get(1).contains(1001)) {
				fail("Adding an edge opposite to an existing edge is not making the right changes in the adjacency set");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Adding an edge opposite to an existing edge causes an exception");
		}
		try {
			for (int i=0; i<10; i++) {
				graph1.addVertex(i);
			}
			for (int i=0; i<10; i++) {
				for (int j=0; j<10; j++) {
					graph1.addEdge(i, j);
				}
			}
			if (graph1.getNumEdges() != 92) {
				fail("Adding edges between 10 existing vertices "
						+ "gives incorrect number of edges: " + graph1.getNumEdges());
			}
			if (graph1.getNumVertices() != 11) {
				fail("Adding edges between 10 existing vertices "
						+ "gives incorrect number of vertices: " + graph1.getNumVertices());
			}
			HashMap<Integer, HashSet<Integer>> g = graph1.exportGraph();
			for (int i=0; i<10; i++) {
				for (int j=0; j<10; j++) {
					if(i != j && !g.get(i).contains(j)) {
						System.out.println(g);
						fail("Not all of the added edges have updated the adjacency sets");
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Adding a lot of edges between existing vertices causes exception");
		}
	}
	
	@Test
	public void testGetNumNeighbors() {
		graph1.addVertex(1);
		try{
			if (graph1.getNumNeighbors(1) != 0){
				fail("New vertex's number of neighbors is non zero: " + graph1.getNumNeighbors(1));
			}
		} catch(Exception e) {
			e.printStackTrace();
			fail("Getting number of neighbors of new vertex throws error");
		}
		graph1.addVertex(2);
		graph1.addEdge(1, 2);
		try{
			if (graph1.getNumNeighbors(1) != 1) {
				fail("A single edge should mean a single neighbor: " + graph1.getNumNeighbors(1));
			}
		} catch(Exception e) {
			e.printStackTrace();
			fail("Getting number of neighbors of a vertex with single edge throws error");
		}
		for (int i=3; i<1000000; i++) {
			graph1.addVertex(i);
			graph1.addEdge(1, i);
		}
		try {
			if (graph1.getNumNeighbors(1) != 999998) {
				fail("Scale testing failed: " + graph1.getNumNeighbors(1));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Getting number of neighbors of a large test case caused exception\n" + e.getStackTrace());
		}
	}
	
	@Test
	public void testGetNeighborsSet() {
		graph1.addVertex(1);
		try{
			if (graph1.getNeighborsSet(1).size() != 0){
				fail("New vertex's number of neighbors is non zero: " + graph1.getNeighborsSet(1));
			}
		} catch(Exception e) {
			fail("Getting number of neighbors of new vertex throws error: \n" + e.getStackTrace());
		}
		graph1.addVertex(2);
		graph1.addEdge(1, 2);
		try{
			if (graph1.getNeighborsSet(1).size() != 1) {
				fail("A single edge should mean a single neighbor: " + graph1.getNeighborsSet(1));
			}
			if (!graph1.getNeighborsSet(1).contains(2)) {
				fail("Neighbors set of 1 does not contain 2: " + graph1.getNeighborsSet(1));
			}
		} catch(Exception e) {
			e.printStackTrace();
			fail("Getting neighbors of a vertex with single edge throws error");
		}
		for (int i=3; i<1000000; i++) {
			graph1.addVertex(i);
			graph1.addEdge(1, i);
		}
		try {
			if (graph1.getNeighborsSet(1).size() != 999998) {
				fail("Scale testing failed: " + graph1.getNeighborsSet(1).size());
			}
			Set<Integer> neighborsSet = graph1.getNeighborsSet(1);
			for (int i=2; i<1000000; i++) {
				if (!neighborsSet.contains(i)) {
					fail("Scale testing failed. Neighbors set does not contain " + i);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Getting neighbors of a large test case caused exception\n" + e.getStackTrace());
		}
	}
	
	@Test
	public void testRealWorld1() {
		try{
			GraphLoader.loadGraph(graph1, "data/facebook_2000.txt");
			System.out.println(graph1.exportGraph());
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Real world test with facebook_2000.txt failed");
		}
	}
	
	@Test
	public void testRealWorld2() {
		try{
			GraphLoader.loadGraph(graph1, "data/small_test_graph.txt");
			System.out.println(graph1.exportGraph());
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Real world test with small_test_graph.txt failed");
		}
	}
	
	@Test
	public void testRealWorld3() {
		try{
			GraphLoader.loadGraph(graph3, "data/twitter_higgs.txt");
			//System.out.println(graph3.exportGraph());
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Real world test with larg test graph twitter_higgs.txt failed");
		}
	}
	
	@Test
	public void testEgoNet() {
		try{
			if (graph1.getEgonet(3) != null) {
				fail("Getting EgoNet of an empty graph is not null");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Getting EgoNet from an empty graph causes exception");
		}
		
		GraphLoader.loadGraph(graph1, "data/small_test_graph.txt");
		
		try{
			Graph ego = graph1.getEgonet(1);
			for (int i=1; i<4; i++) {
				if (!ego.exportGraph().containsKey(i)) {
					fail("Egonet of 1 in small_test_graph should contain " + i);
				}
			}
			if (ego.getNumVertices() > 3) {
				fail("Egonet of 1 should not contain more than 3 vertices in small test graph");
			}
			if (!ego.exportGraph().get(1).contains(2) || !ego.exportGraph().get(1).contains(3)) {
				fail("Egonet of 1 should have 2 and 3 as 1's neighbors");
			}
			if (ego.exportGraph().get(1).contains(1)) {
				fail("Egonet of 1 should not have 1 as 1's neighbor");
			}
			if (!ego.exportGraph().get(2).contains(3) || !ego.exportGraph().get(2).contains(1)) {
				fail("Egonet of 1 should have 1 and 3 as 2's neighbors");
			}
			if (ego.exportGraph().get(2).contains(2)) {
				fail("Egonet of 1 should not have 2 as 2's neighbor");
			}
			if (!ego.exportGraph().get(3).contains(1) || !ego.exportGraph().get(3).contains(2)) {
				fail("Egonet of 1 should have 1 and 2 as 3's neighbors");
			}
			if (ego.exportGraph().get(3).contains(3)) {
				fail("Egonet of 1 should not have 3 as 3's neighbor");
			}
			if (ego.exportGraph().get(3).contains(7)) {
				fail("Egonet of 1 should not contain 7 as 3's neighbor");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Getting EgoNet from a small test graph causes exception");
		}
		
		try{
			GraphLoader.loadGraph(graph2, "data/facebook_2000.txt");
			Graph ego;
			for (Integer v : graph2.exportGraph().keySet()) {
				ego = graph2.getEgonet(v);
				System.out.println(ego.exportGraph());
			}
		}
		catch(Exception e) {
			fail("Getting EgoNet from facebook_2000.txt graph causes exception");
		}
		
		try{
			GraphLoader.loadGraph(graph3, "data/twitter_higgs.txt");
			Graph ego;
			for (Integer v : graph3.exportGraph().keySet()) {
				ego = graph3.getEgonet(v);
				//System.out.println(ego.exportGraph());
			}
		}
		catch(Exception e) {
			fail("Getting EgoNet from large twitter_higgs.txt dataset causes exception");
		}
	}
	
	@Test
	public void testSCCs() {
		try {
			if (graph1.getSCCs().size() != 0) {
				fail("Getting SCCs of empty graph returns non-empty list");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Getting SCCs of empty graph causes exception");
		}
		
		try {
			graph1.addVertex(1);
			graph1.addVertex(2);
			graph1.addVertex(3);
			graph1.addVertex(4);
			graph1.addEdge(1, 2);
			graph1.addEdge(2, 3);
			graph1.addEdge(3, 4);
			graph1.addEdge(4, 1);
			for(Graph g : graph1.getSCCs()){
				System.out.println(g.exportGraph());
			}
			if (graph1.getSCCs().size() != 1) {
				fail("Getting SCCs of simple test returns list of size " + graph1.getSCCs().size() + "\n");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Getting SCCs of simple test causes exception");
		}
		graph1 = new CapGraph();
		
		GraphLoader.loadGraph(graph1, "data/small_test_graph.txt");
		try {
			if (graph1.getSCCs().size() == 0) {
				fail("Getting SCCs of small_test_graph returns empty list");
			}
			if (graph1.getSCCs().size() != 1) {
				fail("Getting SCCs of small_test_graph returns list with more than one SCC: " + graph1.getSCCs().size());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Getting SCCs of small_test_graph causes exception");
		}
		
		GraphLoader.loadGraph(graph2, "data/facebook_2000.txt");
		try {
			if (graph2.getSCCs().size() == 0) {
				fail("Getting SCCs of facebook_2000 graph returns empty list");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Getting SCCs of facebook_2000 graph causes exception");
		}
		
		GraphLoader.loadGraph(graph3, "data/twitter_higgs.txt");
		try {
			if (graph3.getSCCs().size() == 0) {
				fail("Getting SCCs of twitter_higgs graph returns empty list");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			fail("Getting SCCs of twitter_higgs graph causes exception");
		}
	}
}
