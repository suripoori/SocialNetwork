package twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class PageRankInfluence extends Influence {

	private double dampingFactor;
	
	public PageRankInfluence(TwitterNetwork t) {
		super(t);
		// TODO Auto-generated constructor stub
		this.dampingFactor = 0.85;
	}

	public PageRankInfluence(TwitterNetwork t, double dampingFactor) {
		super(t);
		this.dampingFactor = dampingFactor;
	}
	
	@Override
	public List<Integer> getFollowersOrdering(Integer numIterations) {
		// TODO Auto-generated method stub
		List<Integer> ordering = new ArrayList<Integer>();
		double defaultPageRank = 1/getTwitterNetwork().getNumSocialUsers();
		Queue<PageRank> pageRankQueue = new PriorityQueue<PageRank>();
		Map<Integer, Double> pageRankMap = new HashMap<Integer, Double>();
		// Initialize pageRankMap with default page ranks
		for (Integer user : getTwitterNetwork().getSocialUsers()) {
			pageRankMap.put(user, defaultPageRank);
		}
		// Update the page ranks for the required number of iterations
		for (int i = 0; i<numIterations; i++) {
			updatePageRanks(pageRankMap);
		}
		// Put all the page ranks into the pageRankQueue to get the ordering based on the ranks
		for (Integer user : pageRankMap.keySet()) {
			pageRankQueue.add(new PageRank(user, pageRankMap.get(user)));
		}
		// Populate the ordering list with the ordering obtained in the queue.
		while(!pageRankQueue.isEmpty()) {
			ordering.add(pageRankQueue.poll().vertex);
		}
		return ordering;
	}

	@Override
	public List<Integer> getRetweetOrdering(Integer numIterations) {
		// TODO Auto-generated method stub
		return null;
	}

	private void updatePageRanks(Map<Integer, Double> pageRankMap) {
		// Store updated pageRanks for iteration t+1 in this map
		Map<Integer, Double> newPageRankMap = new HashMap<Integer, Double>();
		
		for(Integer user : getTwitterNetwork().getSocialUsers()) {
			double userPageRank = (1-dampingFactor)/getTwitterNetwork().getNumSocialUsers();
			try {
				for (Integer follower : getTwitterNetwork().getFollowersOfUser(user)) {
					userPageRank += (dampingFactor * pageRankMap.get(follower)/getTwitterNetwork().getNumUsersFollowedBy(follower)); 
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newPageRankMap.put(user, userPageRank);
		}
		// Copy iteration t+1's pageRanks into the pageRankMap
		for (Integer user : newPageRankMap.keySet()) {
			pageRankMap.put(user, newPageRankMap.get(user));
		}
	}
	
	private class PageRank implements Comparable<PageRank>{
		private Integer vertex;
		private Double pageRank;
		
		private PageRank(Integer v, double defaultPageRank) {
			this.vertex = v;
			this.pageRank = defaultPageRank;
		}

		@Override
		public int compareTo(PageRank that) {
			if (this.pageRank > that.pageRank) {
				return -1;
			}
			else if (this.pageRank < that.pageRank) {
				return 1;
			}
			return 0;
		}
		
	}
}
