package twitter;

import graph.Graph;

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
	
	public Set<Integer> getFollowersOfUser(Integer u) {
		return followedBy.getNeighborsSet(u);
	}
	
	public Set<Integer> getUsersFollowedBy(Integer u) {
		return follows.getNeighborsSet(u);
	}
	
	public Set<Integer> getRetweetersOfUser(Integer u) {
		return retweetedBy.getNeighborsSet(u);
	}
	
	public Set<Integer> getUsersRetweetedBy(Integer u) {
		return retweets.getNeighborsSet(u);
	}
}
