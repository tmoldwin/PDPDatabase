package database;

import java.util.HashMap;

/**
 * @author Toviah
 * A cluster is essentially an data type. A cluster can be associated with a variety of nodes.
 */
public class Cluster {
	String clusterName;
	HashMap <String, Node> nodes;

	public Cluster(String name) {
		clusterName = name;
		nodes = new HashMap<String, Node>();
	}
	
	/**
	 * Adds a node to the cluster
	 * @param Node n
	 */
	public void put(Node n){
		nodes.put(n.getName(), n);
	}

}
