package database;

import java.util.ArrayList;

public class Node {

	private String data;
	private String clusterName;
	
	public Node(String nodeValue, String nodeCluster) {
		data = nodeValue;
		clusterName = nodeCluster;
	}
	
	public String getData() {
		return data;
	}
	
	public String getClusteName(){
		return clusterName;
	}
}
