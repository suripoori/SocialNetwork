package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import twitter.TwitterNetwork;
import util.TwitterNetworkLoader;

public class TwitterNetworkTester {
	public TwitterNetwork tn1;
	public TwitterNetwork tn2;
	public TwitterNetwork tn3;
	
	@Before
	public void setUp() throws Exception {
		tn1 = new TwitterNetwork();
		tn2 = new TwitterNetwork();
		tn3 = new TwitterNetwork();
	}
	
	@Test
	public void testAddSocialUser() {
		try{
			tn1.addSocialUser(1);
			List<Integer> vertexList = new ArrayList<Integer>();
			vertexList.add(1);
			testAddSocialUserValidity(tn1, vertexList);
			for (int i=1; i<100000; i++) {
				tn1.addSocialUser(i);
				vertexList.add(i);
			}
			testAddSocialUserValidity(tn1, vertexList);
		} catch(Exception e) {
			fail("addSocialUser on a TwitterNetwork raises exception \n" + e.getStackTrace());
		}
	}
	
	@Test
	public void testAddRetweetUser() {
		try{
			tn1.addSocialUser(1);
			tn1.addRetweetUser(1);
			List<Integer> vertexList = new ArrayList<Integer>();
			vertexList.add(1);
			testAddRetweetUserValidity(tn1, vertexList);
			for (int i=1; i<100000; i++) {
				tn1.addSocialUser(i);
				tn1.addRetweetUser(i);
				vertexList.add(i);
			}
			testAddRetweetUserValidity(tn1, vertexList);
		} catch(Exception e) {
			fail("addRetweetUser on a TwitterNetwork raises exception \n" + e.getStackTrace());
		}
	}
	
	@Test
	public void testAddFollowsLink() {
		try{
			List<Integer> vertexList = new ArrayList<Integer>();
			for(int i=0; i<1000; i++) {
				tn1.addSocialUser(i);
				vertexList.add(i);
			}
			for (int i=0; i<1000; i++) {
				for (int j=0; j<1000; j++) {
					tn1.addFollowsLink(i, j);
				}
			}
			testAddFollowsLinkValidity(tn1, vertexList);
		} catch(Exception e) {
			fail("addFollowsLink between existing social users raises exception \n" + e.getMessage() + "\n" + e.getStackTrace());
		}
		boolean thrown = false;
		try{
			tn1.addFollowsLink(1, 1000);
		} catch(Exception e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void testAddRetweetsLink() {
		try{
			List<Integer> vertexList = new ArrayList<Integer>();
			boolean thrown = false;
			try {
				tn1.addRetweetsLink(1, 2);
			} catch(Exception e) {
				thrown = true;
			}
			assertTrue(thrown);
			
			thrown = false;
			try {
				tn1.addSocialUser(1);
				tn1.addSocialUser(2);
				tn1.addRetweetsLink(1, 2);
			} catch(Exception e) {
				thrown = true;
			}
			assertTrue(thrown);
			
			for(int i=0; i<1000; i++){
				tn1.addSocialUser(i);
				tn1.addRetweetUser(i);
				vertexList.add(i);
			}
			for (int i=0; i<1000; i++) {
				for (int j=0; j<1000; j++) {
					if (i != j){
						tn1.addRetweetsLink(i, j);
					}
				}
			}
			testAddRetweetsLinkValidity(tn1, vertexList);
		} catch(Exception e) {
			fail("addRetweetsLink between existing social users and retweet users raises exception " + e.getMessage());
		}
	}
	
	@Test
	public void testSmallTwitterData(){
		try{
			TwitterNetworkLoader.loadTwitterNetwork(tn1, "data/small_test_graph.txt", "data/small_test_retweets_graph.txt");
			
		} catch(Exception e) {
			fail("Failure when loading a small twitter network " + e.getMessage() + " " + e.getStackTrace());
		}
	}
	
	@Test
	public void testLargeTwitterData(){
		try{
			TwitterNetworkLoader.loadTwitterNetwork(tn2, "data/higgs-social_network.edgelist", "data/higgs-retweet_network.edgelist");
		} catch(Exception e) {
			fail("Exception when loading a large twitter network " + e.getMessage() + " " + e.getStackTrace());
		}
	}
	
	private void testAddSocialUserValidity(TwitterNetwork tn, List<Integer> vertexList) {
		Map<String, HashMap<Integer, HashSet<Integer>>> graphs = tn.exportGraphs();
		for (int i : vertexList) {
			if (!graphs.get("follows").containsKey(i)) {
				fail("In addSocialUser, User did not get added to the follows graph");
			}
			else {
				if (graphs.get("follows").get(i).size() != 0) {
					fail("In addSocialUser, new user's follows set has non zero size: " + graphs.get("follows").get(i));
				}
			}
			if (!graphs.get("followedBy").containsKey(i)) {
				fail("In addSocialUser, User did not get added to the followedBy graph");
			}
			else {
				if (graphs.get("followedBy").get(i).size() != 0) {
					fail("In addSocialUser, new user's followedBy set has non zero size: " + graphs.get("followedBy").get(i));
				}
			}
			if (graphs.get("retweets").containsKey(i)) {
				fail("In addSocialUser, User got added to the retweets graph");
			}
			if (graphs.get("retweetedBy").containsKey(i)) {
				fail("In addSocialUser, User got added to the retweetedBy graph");
			}
		}
	}
	
	private void testAddRetweetUserValidity(TwitterNetwork tn, List<Integer> vertexList) {
		Map<String, HashMap<Integer, HashSet<Integer>>> graphs = tn.exportGraphs();
		for (int i : vertexList) {
			if (!graphs.get("follows").containsKey(i)) {
				fail("In addRetweetUser, failed to raise exception when adding a user not yet added as a social user");
			}
			if (graphs.get("retweets").get(i).size() != 0) {
				fail("In addRetweetUser, new user's retweets set has non zero size: " + graphs.get("follows").get(i));
			}
			if (!graphs.get("followedBy").containsKey(i)) {
				fail("In addRetweetUser, failed to raise exception when adding a user not yet added as a social user");
			}
			if (graphs.get("retweetedBy").get(i).size() != 0) {
				fail("In addSocialUser, new user's retweetedBy set has non zero size: " + graphs.get("followedBy").get(i));
			}
			if (!graphs.get("retweets").containsKey(i)) {
				fail("In addRetweetUser, User not added to the retweets graph");
			}
			if (!graphs.get("retweetedBy").containsKey(i)) {
				fail("In addRetweetUser, User not added to the retweetedBy graph");
			}
		}
	}
	
	private void testAddFollowsLinkValidity(TwitterNetwork tn, List<Integer> vertexList) {
		Map<String, HashMap<Integer, HashSet<Integer>>> graphs = tn.exportGraphs();
		for (int i : vertexList) {
			for (int j : vertexList) {
				// They should all be following each other
				if (i != j) {
					if (!graphs.get("follows").containsKey(i)){
						fail("Follows graph does not have vertex " + i);
					}
					else {
						if (!graphs.get("follows").get(i).contains(j)) {
							fail("In the follows graph, " + i + " does not follow " + j);
						}
					}
					if (!graphs.get("followedBy").containsKey(j)) {
						fail("FollwedBy graph does not have vertex " + j);
					}
					else {
						if (!graphs.get("followedBy").get(j).contains(i)) {
							fail("In the follwedBy graph, " + j + " does not have " + i + " as a follower");
						}
					}
					if (graphs.get("retweets").containsKey(i)) {
						fail(i + " is not expected in the retweets graph");
					}
					if (graphs.get("retweetedBy").containsKey(j)) {
						fail(j + " is not expected in the retweetedBy graph");
					}
				}
			}
		}
	}
	
	private void testAddRetweetsLinkValidity(TwitterNetwork tn, List<Integer> vertexList){
		Map<String, HashMap<Integer, HashSet<Integer>>> graphs = tn.exportGraphs();
		for (int i : vertexList) {
			for (int j : vertexList) {
				if(i != j) {
					if (!graphs.get("follows").containsKey(i)){
						fail("Follows graph does not have vertex " + i);
					}
					if (!graphs.get("followedBy").containsKey(i)){
						fail("FollwedBy graph does not contain vertex " + i);
					}
					if (!graphs.get("retweets").containsKey(i)) {
						fail("Retweets graph does not contain vertex " + i);
					}
					if (!graphs.get("retweets").get(i).contains(j)) {
						fail("In the retweets graph, " + i + " does not retweet " + j);
					}
					if (!graphs.get("retweetedBy").containsKey(j)) {
						fail("RetweetedBy graph does not have vertex " + j);
					}
					if (!graphs.get("retweetedBy").get(j).contains(i)) {
						fail("RetweetedBy set of " + j + " does not have vertex " + i);
					}
					if (!graphs.get("followedBy").containsKey(j)) {
						fail("FollowedBy graph does not contain vertex " + j);
					}
					if (!graphs.get("follows").containsKey(j)){
						fail("Follows graph does not contain vertex " + j);
					}
				}
			}
		}
	}
}
