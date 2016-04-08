package twitter;

import java.util.List;

public abstract class Influence {
	
	private TwitterNetwork tn;
	
	public Influence() {
		tn = new TwitterNetwork();
	}
	/* Gets ordering of the Users based on the followers */
	public abstract List<Integer> getFollowersOrdering(Integer numIterations);
	
	/* Gets ordering of the Users based on the retweets */
	public abstract List<Integer> getRetweetOrdering(Integer numIterations);
}
