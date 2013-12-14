package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;

import org.javatuples.Pair;
import org.junit.Test;

import database.DataReader;
import database.Database;
import database.NodeBinaryComparator;

public class ComparatorTest {

	@Test
	public void Comparatortest() {
		Database db = new Database();
		DataReader dr = new DataReader();
		Pair<String[], String[][]> pair= dr.loadData("JetsAndSharks.txt");
		db.addRows(pair.getValue0(), pair.getValue1());
		String[] params = {"Jets", "sing."};
		System.out.println(Arrays.toString(params));
		HashMap<String, Integer> nti =  db.getNodeToIndex();
		NodeBinaryComparator c = new NodeBinaryComparator(db.getConnections(), db.getNodeToIndex(), params);
		System.out.println(c.compare(db.getNodes().get("Ned"),db.getNodes().get("John")));
		System.out.println(c.compare(db.getNodes().get("Lance"),db.getNodes().get("Doug")));
	}

}
