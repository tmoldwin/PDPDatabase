package database;

public class Node implements Comparable<Node>{

	private String name;
	private String clusterName;
	private double activation;
	
	public Node(String nodeValue, String nodeCluster) {
		name = nodeValue;
		clusterName = nodeCluster;
		activation = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public String getClusterName(){
		return clusterName;
	}

	public double getActivation() {
		return activation;
	}

	public void setActivation(double activation) {
		this.activation = activation;
	}

	public int compareTo(Node other) {
		// TODO Auto-generated method stub
		return Double.compare(activation, other.activation);
	}
}
