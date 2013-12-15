package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.PriorityQueue;

import org.javatuples.Pair;
import org.junit.Test;

import database.DataReader;
import database.Database;
import database.Node;

public class DatabaseTest {

	private Database setupDB(String fileName){
		Database db = new Database();
		DataReader dr = new DataReader();
		Pair<String[], String[][]> pair= dr.loadData(fileName);
		db.addRows(pair.getValue0(), pair.getValue1());
		return db;
	}
	public void testAddDBRow() {
		Database db = new Database();
		String[] clusters = {"Name", "Gang", "Age", "Edu", "Occupation"};
		String[] nodes = {"Art", "Jets", "40's", "sing.", "pusher"};
		db.addRow(clusters, nodes);
		String[] nodes2 = {"Al", "Jets", "30's", "mar.", "burglar"};
		db.addRow(clusters, nodes2);
		db.printConnections();
	}
	
	
	public void testAddRows(){
		Database db = new Database();
		DataReader dr = new DataReader();
		Pair<String[], String[][]> pair= dr.loadData("JetsAndSharks.txt");
		db.addRows(pair.getValue0(), pair.getValue1());
		db.printConnections();
	}
	
	
	public void testNodes(){
		Database db = new Database();
		DataReader dr = new DataReader();
		Pair<String[], String[][]> pair= dr.loadData("JetsAndSharks.txt");
		db.addRows(pair.getValue0(), pair.getValue1());
		for(String s:db.getNodes().keySet()){
			System.out.println(db.getNodes().get(s).getName() + " " + db.getNodes().get(s).getClusterName());
			}
		}
	

	public void testActivations(){
		Database db = setupDB("JetsAndSharks.txt");
		String[] params = {"Jets", "sing."};
		int[] indices = db.findParamIndices(params);
		Node node1 = db.getNodes().get("John");
		Node node2 = db.getNodes().get("Doug");
		db.setActivationBinary(node1, indices);
		db.setActivationBinary(node2, indices);
		System.out.println(node1.getActivation());
		System.out.println(node2.getActivation());
		System.out.println(node1.compareTo(node2));
	}
	
	
	public void testBinaryQueryAllData(){
		Database db = setupDB("JetsAndSharks.txt");
		String[] params = {"Jets", "sing."};
		Node[] nodes = db.BinaryQueryAllData("Name", params);
		for(int i = 0; i < nodes.length; i++){
			System.out.println(nodes[i].getName()+" " + nodes[i].getActivation());
		}
	}
	
	
	public void testNumConnectionsQueryAllData(){
		Database db = setupDB("JetsAndSharks.txt");
		String[] params = { "div."};
		Node[] nodes = db.numConnectionsQueryAllData("Age", params);
		for(int i = 0; i < nodes.length; i++){
			System.out.println(nodes[i].getName()+" " + nodes[i].getActivation());
		}
	}
	
	public void testFrequencyInCluster(){
		Database db = setupDB("JetsAndSharks.txt");
		System.out.println(db.getFrequencyInCluster(db.getNodes().get("John"), db.getClusters().get("Name")));
	}
	
	@Test
	public void testNaiveBayesQueryAllData(){
		Database db = setupDB("WeatherPlay.txt");
		String[] params = { "sunny", "cool", "high", "true"};
		Node[] nodes = db.naiveBayesQueryAllData("play", params);
		for(int i = 0; i < nodes.length; i++){
			System.out.println(nodes[i].getName()+" " + nodes[i].getActivation());
		}
	}
		
	}

