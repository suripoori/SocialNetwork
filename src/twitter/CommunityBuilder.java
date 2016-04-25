package twitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CommunityBuilder implements Community {

	private TwitterNetwork t;
	
	public CommunityBuilder(TwitterNetwork t){
		this.t = t;
	}
	
	@Override
	public List<HashSet<Integer>> getCommunitiesGreedy(Integer communitySize) {
		// TODO Auto-generated method stub
		// Initialize a List of Sets which will contain the communities
		if (communitySize == 0){
			return null;
		}
		HashSet<Integer> socialUsers = (HashSet<Integer>) t.getSocialUsers();
		if (socialUsers.size() == 0){
			return null;
		}
		List<HashSet<Integer>> communities = new ArrayList<HashSet<Integer>>();
		if (communitySize >= socialUsers.size()){
			communities.add(socialUsers);
			return communities;
		}
		
		for (int i=0; i<t.getNumSocialUsers()/communitySize; i++){
			//Build each community and add it to the communities list
			HashSet<Integer> newCommunity = getCommunityGreedy(socialUsers, communitySize);
			socialUsers.removeAll(newCommunity);
			communities.add(newCommunity);
		}
		return communities;
	}

	private HashSet<Integer> getCommunityGreedy(HashSet<Integer> socialUsers, int communitySize){
		// Builds a new community from the given socialUsers list and returns that as a hashset
		HashSet<Integer> newCommunity = new HashSet<Integer>();
		Map<Integer, Integer> commConnectionMap = new HashMap<Integer, Integer>();
		int maxConnections = 0;
		int maxConnUser = getUserToInitializeCommunityWith(socialUsers);
		newCommunity.add(maxConnUser);
		socialUsers.remove(maxConnUser);
		//Build a connection map and add the user with most connections to the community
		for (Integer user : socialUsers){
			int numConnections = getConnections(maxConnUser, user);
			if (maxConnections < numConnections){
				maxConnections = numConnections;
				maxConnUser = user;
			}
			commConnectionMap.put(user, numConnections);
		}
		//While community size is less than the required size, keep iterating over the
		//user set in the connectionMap, identify the new user with maximum connections, add them to the community and 
		//remove them from the user set in the connectionMap 
		while (newCommunity.size() < communitySize){
			newCommunity.add(maxConnUser);
			commConnectionMap.remove(maxConnUser);
			int newMaxConnUser = maxConnUser;
			int newMaxConnections = 0;
			for (Integer user : commConnectionMap.keySet()){
				int updateByVal = getConnections(maxConnUser, user);
				int newConnectionsForUser = commConnectionMap.get(user)+updateByVal;
				if (newConnectionsForUser > newMaxConnections){
					newMaxConnections = newConnectionsForUser;
					newMaxConnUser = user;
				}
				commConnectionMap.put(user, newConnectionsForUser);
			}
			maxConnUser = newMaxConnUser;
		}
		return newCommunity;
	}
	
	private int getUserToInitializeCommunityWith(Set<Integer> users){
		List<Integer> socialUsersList = new ArrayList<Integer>(users);
		Collections.shuffle(socialUsersList, new Random(System.nanoTime()));
		// Initialize the empty community with a random user
		return socialUsersList.get(0);
	}
	
	private int getConnections(Integer user1, Integer user2){
		// Returns the number of connections between users - can be 0, 1 or 2
		// both follows and following connections are counted
		int numConnections = 0;
		Set<Integer> followers;
		try {
			followers = t.getFollowersOfUser(user2);
			if (followers.contains(user1)){
				numConnections++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("User " + user2 + " may not exist: " + e.getMessage());
			e.printStackTrace();
		}
		Set<Integer> following;
		try {
			following = t.getUsersFollowedBy(user2);
			if (following.contains(user1)){
				numConnections++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("User " + user2 + " may not exist: " + e.getMessage());
			e.printStackTrace();
		}
		return numConnections;
	}
	
	@Override
	public List<HashSet<Integer>> getCommunitiesWEBA(Integer communitySize) {
		// TODO Auto-generated method stub
		return null;
	}

}
