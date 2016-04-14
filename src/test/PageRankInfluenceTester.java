package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import twitter.Influence;
import twitter.KatzInfluence;
import twitter.PageRankInfluence;
import twitter.TwitterNetwork;
import util.TwitterNetworkLoader;

public class PageRankInfluenceTester {
	private Influence influence;
	
	private TwitterNetwork tn_small;
	private TwitterNetwork tn;
	private TwitterNetwork tn_large;
	
	@Before
	public void setUp() {
		tn_large = new TwitterNetwork();
		tn_small = new TwitterNetwork();
		tn = new TwitterNetwork();
		TwitterNetworkLoader.loadTwitterNetwork(tn_small, "data/small_test_graph.txt", "data/small_test_retweets_graph.txt");
	}
	
	@Test
	public void testConstructor(){
		try{
			influence = new PageRankInfluence(tn_small);
		} catch(Exception e){
			fail("Exception occured when creating a PageRankInfluence instance from small graph: " + e.getMessage() + " " + e.getStackTrace());
		}
	}
	
	@Test
	public void testGetFollowersOrderingSmallGraph(){
		try{
			influence = new PageRankInfluence(tn_small);
			List<Integer> ordering = influence.getFollowersOrdering(10);
			if (ordering.size() != tn_small.getNumSocialUsers()) {
				fail("Ordering does not contain the same number of users as the network: Ordering users = " + ordering.size() + " network users = " + tn_small.getNumSocialUsers());
			}
		} catch(Exception e){
			fail("Exception occured when getting followers ordering of small graph: " + e.getMessage() + " " + e.getStackTrace());
		}
	}
	
	@Test
	public void testGetFollowersOrderingSimpleGraph(){
		for(int i=0; i<5; i++){
			tn.addSocialUser(i);
		}
		for(int i=0; i<5; i++){
			for(int j=i+1; j<5; j++){
				try {
					tn.addFollowsLink(j, i);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			influence = new PageRankInfluence(tn);
			List<Integer> ordering = influence.getFollowersOrdering(100);
			for(int i=0; i<5; i++){
				if(ordering.get(i) != i){
					fail("Ordering is different from the expected ordering: " + ordering);
				}
			}
		} catch(Exception e){
			fail("Getting ordering of simple test graph causes exception: " + e.getMessage() + " " + e.getStackTrace());
		}
	}
	
	@Test
	public void testGetFollowersOrderingEmptyGraph(){
		try{
			influence = new PageRankInfluence(tn);
			List<Integer> ordering = influence.getFollowersOrdering(100);
			if(ordering.size() != 0){
				fail("Ordering size for empty graph is not 0: " + ordering.size());
			}
		} catch(Exception e){
			fail("Getting ordering of empty graph causes exception: " + e.getMessage() + " " + e.getStackTrace());
		}
	}
	
	@Test
	public void testGetFollowersOrderingLargeGraph(){
		try{
			TwitterNetworkLoader.loadTwitterNetwork(tn_large, "data/higgs-social_network.edgelist", "data/higgs-retweet_network.edgelist");
			influence = new PageRankInfluence(tn_large);
			List<Integer> ordering = influence.getFollowersOrdering(10);
			if (ordering.size() != tn_large.getNumSocialUsers()) {
				fail("Ordering does not have the same number of users as the graph: Ordering users = " + ordering.size() + " network users = " + tn_large.getNumSocialUsers());
			}
		} catch(Exception e){
			fail("Getting ordering of large graph causes exception: " + e.getMessage() + " " + e.getStackTrace());
		}
	}
	
	@Test
	public void testGetRetweetOrderingSmallGraph(){
		try{
			influence = new PageRankInfluence(tn_small);
			List<Integer> ordering = influence.getRetweetOrdering(10);
			if (ordering.size() != tn_small.getNumRetweetUsers()) {
				fail("Ordering does not contain the same number of users as the network: Ordering users = " + ordering.size() + " network users = " + tn_small.getNumRetweetUsers());
			}
		} catch(Exception e){
			fail("Exception occured when getting retweet ordering of small graph: " + e.getMessage() + " " + e.getStackTrace());
		}
	}
	
	@Test
	public void testGetRetweetOrderingSimpleGraph(){
		for(int i=0; i<5; i++){
			tn.addSocialUser(i);
			try {
				tn.addRetweetUser(i);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int i=0; i<5; i++){
			for(int j=i+1; j<5; j++){
				try {
					tn.addRetweetsLink(j, i);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			influence = new PageRankInfluence(tn);
			List<Integer> ordering = influence.getRetweetOrdering(100);
			for(int i=0; i<5; i++){
				if(ordering.get(i) != i){
					fail("Ordering is different from the expected ordering: " + ordering);
				}
			}
		} catch(Exception e){
			fail("Getting ordering of simple test graph causes exception: " + e.getMessage() + " " + e.getStackTrace());
		}
	}
	
	@Test
	public void testGetRetweetOrderingEmptyGraph(){
		try{
			influence = new PageRankInfluence(tn);
			List<Integer> ordering = influence.getRetweetOrdering(100);
			if(ordering.size() != 0){
				fail("Ordering size for empty graph is not 0: " + ordering.size());
			}
		} catch(Exception e){
			fail("Getting ordering of empty graph causes exception: " + e.getMessage() + " " + e.getStackTrace());
		}
	}
	
	@Test
	public void testGetRetweetOrderingLargeGraph(){
		try{
			TwitterNetworkLoader.loadTwitterNetwork(tn_large, "data/higgs-social_network.edgelist", "data/higgs-retweet_network.edgelist");
			influence = new PageRankInfluence(tn_large);
			List<Integer> ordering = influence.getRetweetOrdering(10);
			if (ordering.size() != tn_large.getNumRetweetUsers()) {
				fail("Ordering does not have the same number of users as the graph: Ordering users = " + ordering.size() + " network users = " + tn_large.getNumRetweetUsers());
			}
		} catch(Exception e){
			fail("Getting ordering of large graph causes exception: " + e.getMessage() + " " + e.getStackTrace());
		}
	}
}
