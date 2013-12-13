package test;

import static org.junit.Assert.*;

import org.junit.Test;

import database.Database;

public class DatabaseTest {

	@Test
	public void testAddDBRow() {
		Database db = new Database();
		String[] clusters = {"Name", "Gang", "Age", "Edu", "Occupation"};
		String[] nodes = {"Art", "Jets", "40's", "sing.", "pusher"};
		db.addRow(nodes, clusters);
		String[] nodes2 = {"Al", "Jets", "30's", "mar.", "burglar"};
		db.addRow(nodes2, clusters);
		db.printConnections();
	}
}
