package test;
import graph.CapGraph;
import graph.Graph;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import org.junit.Before;

public class graphTester {
	public Graph graph1;
	
	@Before
	public void setUp() throws Exception {
		graph1 = new CapGraph();
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
}
