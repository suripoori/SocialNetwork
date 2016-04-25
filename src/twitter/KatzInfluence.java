package twitter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.lang.Math;

public class KatzInfluence extends Influence {

	private double alpha;
	
	public KatzInfluence(TwitterNetwork t) {
		super(t);
		// TODO Auto-generated constructor stub
		this.alpha = 0.85;
	}

	public KatzInfluence(TwitterNetwork t, double dampingFactor) {
		super(t);
		this.alpha = dampingFactor;
	}
	
	@Override
	public List<Integer> getFollowersOrdering(Integer numLevels) {
		// TODO Auto-generated method stub
		
		// For each vertex, find its Katz centrality computation and store it in this priority queue
		Queue<KatzCentrality> kcQueue = new PriorityQueue<KatzCentrality>();
		Set<Integer> socialUsers = getTwitterNetwork().getSocialUsers();
		for(Integer u: socialUsers){
			System.out.println("Getting KatzCentrality of " + u);
			kcQueue.add(this.getSocialKatzCentrality(u, numLevels, socialUsers));
		}
		
		// Make a List out of this priority queue and return it
		List<Integer> followersOrdering = new ArrayList<Integer>();
		while(!kcQueue.isEmpty()) {
			followersOrdering.add(kcQueue.poll().vertex);
		}
		
		return followersOrdering;
	}

	@Override
	public List<Integer> getRetweetOrdering(Integer numLevels) {
		// TODO Auto-generated method stub
		// For each vertex, find its Katz centrality computation and store it in this priority queue
		Queue<KatzCentrality> kcQueue = new PriorityQueue<KatzCentrality>();
		Set<Integer> retweetUsers = getTwitterNetwork().getRetweetUsers();
		for(Integer u: retweetUsers) {
			kcQueue.add(this.getRetweetsKatzCentrality(u, numLevels, retweetUsers));
		}
		
		// Make a list out of this priority queue and return it
		List<Integer> retweetsOrdering = new ArrayList<Integer>();
		while(!kcQueue.isEmpty()) {
			retweetsOrdering.add(kcQueue.poll().vertex);
		}
		
		return retweetsOrdering;
	}

	private KatzCentrality getSocialKatzCentrality(int u, int levels, Set<Integer> socialUsers) {
		//Returns a KatzCentrality object for the user 'u' in the social graph
		KatzCentrality kc = new KatzCentrality(u, 0);
		Set<Integer> visitedUsers = new HashSet<Integer>();
		for (int level = 1; level <= levels; level++) {
			//System.out.println("Getting users who are at level: " + level);
			for (Integer v : socialUsers) {
				if (v != u && !visitedUsers.contains(v)){
					if(this.isSocialPath(v, u, level)){
						kc.katzCentrality += Math.pow(this.alpha, level);
						visitedUsers.add(v);
					}
				}
			}
		}
		return kc;
	}
	
	private boolean isSocialPath(int u, int v, int level) {
		// Returns true if there is a path from u to v with exactly level steps in the follows graph
		// Else returns false
		//System.out.println("Checking if there's a path in the Social graph between " + u + " and " + v + " with exactly " + level + " steps");
		HashSet<Integer> visited = new HashSet<Integer>();
		Queue<Integer> bfsQueue = new LinkedList<Integer>();
		bfsQueue.add(u);
		int numLevels = 1;
		// Keep looping until we reach the goal or the queue is empty or level steps have been checked
		while(!bfsQueue.isEmpty() && numLevels <= level){
			Integer next = bfsQueue.remove();
			try {
				for (Integer neighbor : t.getUsersFollowedBy(next)) {
					if (!visited.contains(neighbor)) {
						if (neighbor == v && numLevels == level){
							return true;
						}
						else if (neighbor == v && numLevels != level){
							return false;
						}
						else {
							visited.add(neighbor);
							bfsQueue.add(neighbor);
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			numLevels++;
		}
		return(false);
	}
	
	private KatzCentrality getRetweetsKatzCentrality(int u, int levels, Set<Integer> retweetUsers) {
		//Returns a KatzCentrality object for the user u in the retweets graph
		KatzCentrality kc = new KatzCentrality(u, 0);
		Set<Integer> visitedUsers = new HashSet<Integer>();
		for (int level = 1; level <= levels; level++) {
			for (Integer v : retweetUsers) {
				if (v != u && !visitedUsers.contains(v)){
					if(this.isRetweetPath(v, u, level)){
						kc.katzCentrality += Math.pow(this.alpha, level);
						visitedUsers.add(v);
					}
				}
			}
		}
		return kc;
	}
	
	private boolean isRetweetPath(int u, int v, int level) {
		// Returns true if there is a path from u to v with exactly level steps in the retweets graph
		// Else returns false
		HashSet<Integer> visited = new HashSet<Integer>();
		Queue<Integer> bfsQueue = new LinkedList<Integer>();
		bfsQueue.add(u);
		int numLevels = 0;
		// Keep looping until we reach the goal or the queue is empty or level steps have been checked
		while(!bfsQueue.isEmpty() && numLevels <= level){
			Integer next = bfsQueue.remove();
			if (next == v && numLevels == level){
				return true;
			}
			else if (next == v && numLevels != level){
				return false;
			}
			try {
				for (Integer retweeter : t.getUsersRetweetedBy(next)) {
					if (!visited.contains(retweeter)) {
						visited.add(retweeter);
						bfsQueue.add(retweeter);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			numLevels++;
		}
		return(false);
	}
	
	private class KatzCentrality implements Comparable<KatzCentrality>{
		private Integer vertex;
		private Double katzCentrality;
		
		private KatzCentrality(Integer v, double katzCentrality) {
			this.vertex = v;
			this.katzCentrality = katzCentrality;
		}

		@Override
		public int compareTo(KatzCentrality that) {
			if (this.katzCentrality > that.katzCentrality) {
				return -1;
			}
			else if (this.katzCentrality < that.katzCentrality) {
				return 1;
			}
			return 0;
		}
		
	}
}
