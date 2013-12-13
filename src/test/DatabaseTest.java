package test;

import static org.junit.Assert.*;

import org.javatuples.Pair;
import org.junit.Test;

import database.DataReader;
import database.Database;

public class DatabaseTest {

	@Test
	public void testAddDBRow() {
		Database db = new Database();
		String[] clusters = {"Name", "Gang", "Age", "Edu", "Occupation"};
		String[] nodes = {"Art", "Jets", "40's", "sing.", "pusher"};
		db.addRow(clusters, nodes);
		String[] nodes2 = {"Al", "Jets", "30's", "mar.", "burglar"};
		db.addRow(clusters, nodes2);
		db.printConnections();
	}
	
	@Test
	public void testAddRows(){
		Database db = new Database();
		DataReader dr = new DataReader();
		Pair<String[], String[][]> pair= dr.loadData("JetsAndSharks.txt");
		db.addRows(pair.getValue0(), pair.getValue1());
		db.printConnections();
	}
}
