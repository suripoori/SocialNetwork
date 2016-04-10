# SocialNetwork
Some interesting things with social network data

# DataSet:

I will be using the Twitter social network friends/followers dataset and the retweet dataset collected during the Higgs boson discovery announcement. The data contains two user ids in each row. In each row, the user ids are in the order of the action performed with the first user id being the action taker. So, the first user id retweets the second user id in the retweet dataset. The first user id follows the second user id in the follows dataset. 

These datasets were obtained by the Stanford Network analysis project and is present at: http://snap.stanford.edu/data/higgs-twitter.html

The data will be stored as four directed graphs with each user id being a node. 
The first graph called the "following" graph, captures the "following" relationship. Whenever a node A follows another node B, an edge is added to this graph from A to B. 
The second graph called the "followers" graph, captures the "followed by" relationship. Whenever a node A is followed by another node B, an edge is added to this graph from A to B. 
The third graph called the "retweets" graph, captures the "retweets" relationship. Whenever a node A retweets another node B, an edge is added to this graph from A to B.
The fourth graph called the "retweetedBy" graph, captures the "retweeted by" relationship. Whenever a node A is retweeted by another node B, an edge is added to this graph from A to B.
There are going to be a total of 456626 nodes and 14855842 edges in the "following" and "followers" graphs. There will be a total of 223833 nodes and 308596 edges in the "retweets" and "retweetedBy" graphs.
Just two graphs could have been used instead of four and duplicate data could have been removed. But, I feel this will lead to a cleaner code design and faster lookup times for some of the operations I will be targeting.

# Questions:
## Easy Question:
Who are the influencers in the "followed by" network based on their PageRank computation after a fixed number of iterations? Does it correlate with the PageRank computation on the "retweeted by" network?
## Hard Question:
Who are the influencers in the "followed by" network based on their Katz Centrality computation of upto 6 levels? Build communities of size k each on the "followed by" network, identify what percentage of Katz centrality influencers are in each community.
Now, with this data about influencers and communities, the following questions can be answered:
1. Percentage of retweets within communties. 
2. How many times did influencers from one community retweet influencers from another community?

# Algorithms and Data Structures:
The algorithm for calculating PageRank is as follows:
1. Initially at t=0, PageRank of all the users is 1/N where N is the number of nodes.
2. PageRank of vertex v at iteration t+1 is, PR(v, t+1) = (1-d)/V + d*sum(PR(u, t)/L(u)). Here, u is every node which "follows" v, or every node which "retweets" v depending on which graph this calculation is being done on. L(u) is the number of nodes u follows or the number of nodes u retweets. d is a damping factor, usually set to 0.85 when calculating PageRank of web pages. I will retain the same damping factor for identifying influencers as well.

The algorithm for calculating the Katz centrality is as follows:
1. Katz centrality of vertex v is, Katz(v) = sum_for_each_level_k(pow(alpha, k) * sum(Ak_u_v))
2. Here, the variable alpha can be taken as a parameter but it needs to be between 0 and 1. pow(alpha, k) is the value of alpha to the power of k. Ak_u_v is 1 if there is a path within k levels from node u to node v, 0 otherwise. So, for every other node u, we identify if there is a path from u to v within k levels to find the Katz centrality of v.
3. BFS will be run on each node to reach every other node, and a hashmap mapping level to a set of vertices reachable at that level will be calculated.  

The algorithm for community building is the greedy algorithm presented in http://www.cs.cornell.edu/~lwang/Wang11ICDM.pdf
1. Initialize a subset S to be a random vertex v not belonging to any other subset.
2. Iteratively enlarge S to size k by adding vertex with the maximum number of connections to S(both followed by and follows relationsips). If there are multiple vertices with the maximum number of connections to S, pick the one with the highest degree. If there are multiple vertices with the highest degree, randomly pick one of them.
3. Execute this subroutine V/k times to form k communities. An ArrayList of HashSets will be created to store the communities where each community is a HashSet of vertex ids. 


# Algorithm Analysis, Limitation, Risks:
The PageRank computation will run in O(n*(V+E)) time where n is the number of iterations we want the page rank algorithm to run. V is the number of nodes and E is the number of Edges. This will also take a space complexity of O(V) to store the page ranks of each of the nodes.

For the Katz centrality computation, the run will take a time complexity of O(V(V+E)) and space complexity of O(V*V). A risk for this algorithm is that O(V*V) space complexity may be unacceptable. Optimizations will be needed to be made on the hashmap, or upper bounds may need to be set on the number of levels.

For the Community Building greedy algorithm, the time and space complexities for building the communities with this algorithm will be O(V/k(V+E)).
If kernel sizes are small compared to V, this algorithm will take a long time and a lot of space. 

# Bonus:
The community building algorithm paper also discusses a more accurate community forming algorithm - WEBA, which uses the output of the Greedy approach to give a better performance. Implement this algorithm if time permits.