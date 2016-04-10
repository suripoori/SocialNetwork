package util;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TwitterNetworkLoader {
    /**
     * Loads graph with data from a file.
     * The file should consist of lines with 2 integers each, corresponding
     * to a "from" vertex and a "to" vertex.
     */ 
    public static void loadTwitterNetwork(twitter.TwitterNetwork tn, String followerEdgelist, String retweetEdgelist) {
        Set<Integer> seen = new HashSet<Integer>();
        Scanner sc;
        try {
            sc = new Scanner(new File(followerEdgelist));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
        while (sc.hasNextInt()) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            if (!seen.contains(v1)) {
                try {
					tn.addSocialUser(v1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                seen.add(v1);
            }
            if (!seen.contains(v2)) {
                try {
					tn.addSocialUser(v2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                seen.add(v2);
            }
            try {
				tn.addFollowsLink(v1, v2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        sc.close();
        
        try {
            sc = new Scanner(new File(retweetEdgelist));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        seen = new HashSet<Integer>();
        // Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
        while (sc.hasNextInt()) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            if (!seen.contains(v1)) {
                try {
					tn.addRetweetUser(v1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                seen.add(v1);
            }
            if (!seen.contains(v2)) {
                try {
					tn.addRetweetUser(v2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                seen.add(v2);
            }
            try {
				tn.addRetweetsLink(v1, v2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        sc.close();
    }
}
