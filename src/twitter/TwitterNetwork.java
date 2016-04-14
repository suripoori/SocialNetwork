package twitter;

import graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
		return retweets.getNumVertices();
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
	
	public void addRetweetUser(Integer r) throws Exception {
		if (!follows.isVertex(r) || !followedBy.isVertex(r)) {
			throw new Exception("User " + r + " does not exist in the social graph");
		}
		retweets.addVertex(r);
		retweetedBy.addVertex(r);
	}
	
	public void addFollowsLink(Integer u1, Integer u2) throws Exception {
		if (!follows.isVertex(u1) || !followedBy.isVertex(u1)) {
			throw new Exception("User " + u1 + " does not exist in the social graph");
		}
		if (!follows.isVertex(u2) || !followedBy.isVertex(u2)) {
			throw new Exception("User " + u2 + " does not exist in the social graph");
		}
		followedBy.addEdge(u2, u1);
		follows.addEdge(u1, u2);
	}
	
	public void addRetweetsLink(Integer r1, Integer r2) throws Exception {
		if (!follows.isVertex(r1) || !followedBy.isVertex(r1)) {
			throw new Exception("User " + r1 + " does not exist in the social graph");
		}
		if (!follows.isVertex(r2) || !followedBy.isVertex(r2)) {
			throw new Exception("User " + r2 + " does not exist in the social graph");
		}
		if (!retweets.isVertex(r1) || !retweetedBy.isVertex(r1)) {
			throw new Exception("User " + r1 + " does not exist in the social graph");
		}
		if (!retweets.isVertex(r2) || !retweetedBy.isVertex(r2)) {
			throw new Exception("User " + r2 + " does not exist in the social graph");
		}
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
	
	public Map<String, HashMap<Integer, HashSet<Integer>>> exportGraphs() {
		Map<String, HashMap<Integer, HashSet<Integer>>> returnMap = new HashMap<String, HashMap<Integer, HashSet<Integer>>>();
		returnMap.put("follows", follows.exportGraph());
		returnMap.put("followedBy", followedBy.exportGraph());
		returnMap.put("retweets", retweets.exportGraph());
		returnMap.put("retweetedBy", retweetedBy.exportGraph());
		return returnMap;
	}
}
