package twitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Community {
	
	/* Gets a List of communities using the Greedy Algorithm*/
	public List<HashSet<Integer>> getCommunitiesGreedy(Integer communitySize);
	
	/* Gets a List of communities using the WEBA algorithm */
	public List<HashSet<Integer>> getCommunitiesWEBA(Integer communitySize);
}
