package twitter;

import java.util.List;
import java.util.Set;

public interface Community {
	
	/* Gets a List of communities using the Greedy Algorithm*/
	public List<Set<Integer>> getCommunitiesGreedy(Integer communitySize);
	
	/* Gets a List of communities using the WEBA algorithm */
	public List<Set<Integer>> getCommunitiesWEBA(Integer communitySize);
}
