package database;

import java.util.ArrayList;

public class Node {

	private String name;
	private String clusterName;
	
	public Node(String nodeValue, String nodeCluster) {
		name = nodeValue;
		clusterName = nodeCluster;
	}
	
	public String getName() {
		return name;
	}
	
	public String getClusterName(){
		return clusterName;
	}
}
