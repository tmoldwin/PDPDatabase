package database;

import java.util.HashMap;

public class Cluster {
	String clusterName;
	HashMap <String, Node> nodes;

	public Cluster(String name) {
		clusterName = name;
		nodes = new HashMap<String, Node>();
	}
	
	public void put(Node n){
		nodes.put(n.getData(), n);
	}

}
