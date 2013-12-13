package test;

import java.util.Arrays;

import org.javatuples.Pair;
import org.junit.Test;

import database.DataReader;

public class DataReaderTest {

	@Test
	public void testDataReader() {
		DataReader dr = new DataReader();
		Pair<String[], String[][]> pair= dr.loadData("JetsAndSharks.txt");
		for(String s:pair.getValue0()){
			System.out.print(s+" ");
		}
		for(String[] s:pair.getValue1()){
			System.out.println(Arrays.toString(s));
		}
	}

}
