package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * @author Toviah
 *	This is a database based on the parallel distributed processing ideas set forth by McClelland and Rumelhart 
 */
public class Database {
	private HashMap<String, Cluster> clusters;
	private HashMap<String, Node> nodes;
	private HashMap<String, Integer> nodeToIndex;
	private ArrayList<ArrayList<Integer>> connections;
	private int nodeCount;
	 
	public Database(){
		clusters = new HashMap<String, Cluster>();
		nodes = new HashMap<String, Node>();
		nodeToIndex = new HashMap<String, Integer>();
		connections = new ArrayList<ArrayList<Integer>>();
		nodeCount = 0;
	}

	public void addRow(String[] clusterNames, String[] nodeNames){
		try{
			if (nodeNames.length != clusterNames.length){
				throw new Exception("The data size is unequal to the number of clusters.");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		//Add necessary nodes and clusters
		for(int i = 0; i<nodeNames.length; i++){
			String nodeName = nodeNames[i];
			String clusterName = clusterNames[i];
			if (!clusters.keySet().contains(clusterName)){
				Cluster cluster = new Cluster(clusterName);
				clusters.put(clusterName, cluster);
			}
						
			//If the node hasn't been seen before, i.e. if this data item does not exist
			if(!nodes.keySet().contains(nodeName)){
				Cluster cluster = clusters.get(clusterName);
				Node node = new Node(nodeName, clusterName);
				nodes.put(nodeName, node);
				cluster.put(node);
				ArrayList<Integer> al = new ArrayList<Integer>();
				connections.add(al); //Add a new node to connections
				for(int k = 0; k <nodeCount; k++){
					al.add(0);
				}				
				for(ArrayList<Integer> arr: connections){
					arr.add(0); //ensure that the array sizes are all equal
				}
				nodeCount++;
				nodeToIndex.put(nodeName, nodeCount-1);
			}
		}
			
		for(int i = 0; i< nodeNames.length; i++){
			for(int j = i; j < nodeNames.length; j++){
				int iIndex = nodeToIndex.get(nodeNames[i]);
				int jIndex = nodeToIndex.get(nodeNames[j]);
				int val = connections.get(iIndex).get(jIndex) + 1; //increment current connections between i and j
				connections.get(iIndex).set(jIndex, val);
				connections.get(jIndex).set(iIndex, val);				
			}
		}
	}
	
	public PriorityQueue<Node> query(String[] params, String clusterName){
		PriorityQueue<Node> pq = new PriorityQueue<Node>();
		return null;
		
	}

	public void printConnections(){
		for(ArrayList<Integer> arr:connections){
			for(Integer i:arr){
				System.out.print("" + i + "\t");
			}
			System.out.println();
		}
	}
	
	public void addRows(String[] clusters, String[][] rows){
		for(int i = 0; i<rows.length; i++){
			addRow(clusters, rows[i]);
		}		
	}
}
