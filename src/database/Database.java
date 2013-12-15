package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * @author Toviah This is a database based on the parallel distributed
 *         processing ideas set forth by McClelland in his 1981 Paper. TODO: BinaryQueryPerfectMatch, NumConnectionsWeightedQuery, ArbitraryWeightedQuery, NumConnectionsAndArbitraryWeightedQuery, NaiveBayesQuery 
 */
public class Database {
	private HashMap<String, Cluster> clusters;
	private HashMap<String, Node> nodes;
	private HashMap<String, Integer> nodeToIndex;
	private ArrayList<ArrayList<Integer>> connections;
	private int nodeCount;

	public HashMap<String, Cluster> getClusters() {
		return clusters;
	}

	public HashMap<String, Node> getNodes() {
		return nodes;
	}

	public HashMap<String, Integer> getNodeToIndex() {
		return nodeToIndex;
	}

	public ArrayList<ArrayList<Integer>> getConnections() {
		return connections;
	}

	public int getNodeCount() {
		return nodeCount;
	}



	public Database() {
		clusters = new HashMap<String, Cluster>();
		nodes = new HashMap<String, Node>();
		nodeToIndex = new HashMap<String, Integer>();
		connections = new ArrayList<ArrayList<Integer>>();
		nodeCount = 0;
	}

	public void addRow(String[] clusterNames, String[] nodeNames) {
		try {
			if (nodeNames.length != clusterNames.length) {
				throw new Exception(
						"The data size is unequal to the number of clusters.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Add necessary nodes and clusters
		for (int i = 0; i < nodeNames.length; i++) {
			String nodeName = nodeNames[i];
			String clusterName = clusterNames[i];
			
			//if the cluster hasn't been seen before
			if (!clusters.keySet().contains(clusterName)) {
				Cluster cluster = new Cluster(clusterName);
				clusters.put(clusterName, cluster);
			}

			// If the node hasn't been seen before, i.e. if this data item does
			// not exist
			if (!nodes.keySet().contains(nodeName)) {
				Cluster cluster = clusters.get(clusterName);
				Node node = new Node(nodeName, clusterName);
				nodes.put(nodeName, node);
				cluster.put(node);
				ArrayList<Integer> al = new ArrayList<Integer>();
				connections.add(al); // Add a new node to connections
				for (int k = 0; k < nodeCount; k++) {
					al.add(0); //fill out the matrix row for the new node
				}
				for (ArrayList<Integer> arr : connections) {
					arr.add(0); // ensure that the array sizes are all equal
				}
				nodeCount++;
				nodeToIndex.put(nodeName, nodeCount - 1);
			}
		}

		for (int i = 0; i < nodeNames.length; i++) {
			for (int j = i; j < nodeNames.length; j++) {
				int iIndex = nodeToIndex.get(nodeNames[i]);
				int jIndex = nodeToIndex.get(nodeNames[j]);
				//increment current connections between i and j
				int val = connections.get(iIndex).get(jIndex) + 1; 
				connections.get(iIndex).set(jIndex, val);
				//get the transpose also
				connections.get(jIndex).set(iIndex, val);
			}
		}
	}

	public void printConnections() {
		for (ArrayList<Integer> arr : connections) {
			for (Integer i : arr) {
				System.out.print("" + i + "\t");
			}
			System.out.println();
		}
	}

	public int[] findParamIndices(String[] params) {
		int[] queryIndices = new int[params.length];
		for (int i = 0; i < params.length; i++) {
			queryIndices[i] = nodeToIndex.get(params[i]);
		}
		return queryIndices;
	}

	/**
	 * @param node: the node whose activation is to be set
	 * @param queryIndices: an array of node indices
	 * Performs a simple count of how many of the query nodes are connected to the current node.
	 */
	public void setActivationBinary(Node node, int[] queryIndices) {
		int ind = nodeToIndex.get(node.getName());
		int score = 0;
		for (int i = 0; i < queryIndices.length; i++) {
			if (connections.get(ind).get(queryIndices[i]) > 0) {
				score++;
			}
		}
		node.setActivation(score);
	}
	
	public void setActivationNumConnections(Node node, int[] queryIndices) {
		int ind = nodeToIndex.get(node.getName());
		int score = 0;
		for (int i = 0; i < queryIndices.length; i++) {
				score+=connections.get(ind).get(queryIndices[i]);
		}
		node.setActivation(score);
	}
	
	public void setActivationNaiveBayes(Node label, Node[] features){
		double activation = getFrequencyInCluster(label, clusters.get(label.getClusterName()));
		for(Node feature:features){
			activation = activation*getConditionalProbability(label, feature); //multiply conditional probabilities
		}
		label.setActivation(activation);
	}
	
	public Node[] getNodesFromStrings(String[] queryParams){
		Node[] nodes = new Node[queryParams.length];
		for(int i = 0; i< queryParams.length; i++){
			nodes[i] = this.nodes.get(queryParams[i]);
		}
		return nodes;
	}
	
	public Node[] naiveBayesQueryAllData(String clusterName, String[] queryParams){
		Cluster cluster = clusters.get(clusterName);
		Node[] nodes = cluster.getNodeArray();
		for(Node node:nodes){
			setActivationNaiveBayes(node, getNodesFromStrings(queryParams));
		}
		Arrays.sort(nodes);
		return nodes;
	}
	
	public int getNumRowsInCluster(Cluster cluster){
		int rowCount = 0;
		Node[] nodes = cluster.getNodeArray();
		for (Node n:nodes){
			int nodeIndex = nodeToIndex.get(n.getName());
			rowCount += connections.get(nodeIndex).get(nodeIndex); //adds together the values along the diagonals for each node in the cluster to get the frequency
		}
		return rowCount;
	}
	
	public double getFrequencyInCluster(Node node, Cluster cluster){
		double denominator = getNumRowsInCluster(cluster);
		int thisIndex = nodeToIndex.get(node.getName());
		double numerator = connections.get(thisIndex).get(thisIndex);
		return numerator/denominator;
	}
	
	public double getConditionalProbability(Node feature, Node label){
		int featureIndex = nodeToIndex.get(feature.getName());
		int labelIndex = nodeToIndex.get(label.getName());
		double featureToLabelConnectionCount = connections.get(featureIndex).get(labelIndex);
		double labelCount = connections.get(featureIndex).get(featureIndex);
		return featureToLabelConnectionCount/labelCount;
	}
	
	public Node[] numConnectionsQueryAllData(String clusterName, String[] queryParams){
		Cluster cluster = clusters.get(clusterName);
		Node[] nodes = cluster.getNodeArray();
		for(Node node:nodes){
			setActivationNumConnections(node, findParamIndices(queryParams));
		}
		Arrays.sort(nodes);
		return nodes;
	}
	
	public Node[] BinaryQueryAllData(String clusterName, String[] queryParams){
		Cluster cluster = clusters.get(clusterName);
		Node[] nodes = cluster.getNodeArray();
		for(Node node:nodes){
			setActivationBinary(node, findParamIndices(queryParams));
		}
		Arrays.sort(nodes);
		return nodes;
	}

	public void resetNodes(Node[] nodes) {
		for (Node n : nodes) {
			n.setActivation(0);
		}
	}

	public void addRows(String[] clusters, String[][] rows) {
		for (int i = 0; i < rows.length; i++) {
			addRow(clusters, rows[i]);
		}
	}
}
