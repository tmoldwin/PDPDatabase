package database;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author Toviah
 * Compares two nodes by comparing the number of connections they have to the nodes in the query string.
 */
public class NodeComparator implements Comparator<Node> {
	ArrayList<ArrayList<Integer>> connections;
	HashMap<String, Integer> nameToIndex;
	int[] queryIndices;
	

	public NodeComparator(ArrayList<ArrayList<Integer>> connections, HashMap<String, Integer> nameToIndex, String[] params){
		queryIndices = new int[params.length];
		for(int i = 0; i< params.length; i++){
			queryIndices[i] = nameToIndex.get(i);
		}
	}
	
	public int compare(Node arg0, Node arg1) {
		int ind0 = nameToIndex.get(arg0.getName());
		int ind1 = nameToIndex.get(arg1.getName());
		
		int score0 = 0;
		int score1 = 0;
		for(int i = 0; i<queryIndices.length; i++){
			if(connections.get(ind0).get(i) > 0){
				score0++;
			}
			if(connections.get(ind1).get(i) > 0){
				score1++;
			}			
		}
		return Integer.compare(score0, score1);
	}

}
