package twitter;

import graph.Graph;

import java.util.List;
import java.util.Set;

import graph.CapGraph;

public class TwitterNetwork {
	private Graph followedBy;
	private Graph follows;
	private Graph retweets;
	private Graph retweetedBy;
	
	public TwitterNetwork() {
		followedBy = new CapGraph();
		follows = new CapGraph();
		retweets = new CapGraph();
		retweetedBy = new CapGraph();
	}
	
	public int getNumSocialUsers() {
		return follows.getNumVertices();
	}
	
	public int getNumRetweetUsers() {
		return retweets.getNumEdges();
	}
	
	public Set<Integer> getSocialUsers() {
		return follows.exportGraph().keySet();
	}
	
	public Set<Integer> getRetweetUsers() {
		return retweets.exportGraph().keySet();
	}
	
	public void addSocialUser(Integer u) {
		followedBy.addVertex(u);
		follows.addVertex(u);
	}
	
	public void addRetweetUser(Integer r) {
		retweets.addVertex(r);
		retweetedBy.addVertex(r);
	}
	
	public void addFollowsLink(Integer u1, Integer u2) {
		followedBy.addEdge(u2, u1);
		follows.addEdge(u1, u2);
	}
	
	public void addRetweetsLink(Integer r1, Integer r2) {
		retweets.addEdge(r1, r2);
		retweetedBy.addEdge(r2, r1);
	}
	
	public Set<Integer> getFollowersOfUser(Integer u) throws Exception {
		try {
			return followedBy.getNeighborsSet(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("User " + u + " not present");
		}
	}
	
	public Set<Integer> getUsersFollowedBy(Integer u) throws Exception {
		try {
			return follows.getNeighborsSet(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("User " + u + " not present");
		}
	}
	
	public Set<Integer> getRetweetersOfUser(Integer u) throws Exception {
		try {
			return retweetedBy.getNeighborsSet(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("User " + u + " not present");
		}
	}
	
	public Set<Integer> getUsersRetweetedBy(Integer u) throws Exception {
		try {
			return retweets.getNeighborsSet(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("User " + u + " not present");
		}
	}
	
	public int getNumFollwersOfUser(Integer u) throws Exception {
		try {
			return followedBy.getNumNeighbors(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("User " + u + " not present");
		}
	}
	
	public int getNumUsersFollowedBy(Integer u) throws Exception {
		try { 
			return follows.getNumNeighbors(u);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("User " + u + " not present");
		}
	}
	
	public int getNumRetweetersOfUser(Integer u) throws Exception {
		try {
			return retweetedBy.getNumNeighbors(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("User " + u + " not present");
		}
	}
	
	public int getNumUsersRetweetedBy(Integer u) throws Exception {
		try {
			return retweets.getNumNeighbors(u);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("User " + u + " not present");
		}
	}
}
