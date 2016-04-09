package twitter;

import java.util.List;

public abstract class Influence {
	
	protected TwitterNetwork t;
	
	public Influence(TwitterNetwork t) {
		this.t = t;
	}
	
	protected TwitterNetwork getTwitterNetwork() {
		return t;
	}
	
	/* Gets ordering of the Users based on the followers */
	public abstract List<Integer> getFollowersOrdering(Integer numIterations);
	
	/* Gets ordering of the Users based on the retweets */
	public abstract List<Integer> getRetweetOrdering(Integer numIterations);
}
