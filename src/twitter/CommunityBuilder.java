package twitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface CommunityBuilder {
	
	/* Gets a List of communities using the Greedy Algorithm*/
	public List<HashSet<Integer>> getCommunities(Integer communitySize);
}
