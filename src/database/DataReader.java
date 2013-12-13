package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.javatuples.Pair;

/**
 * @author Toviah
 * @description: Reads data from a file and stores the data in the database. 
 * The first line of the file specifies the cluster names (separated by spaces);
 * The remaining lines contain the data, also separated by spaces. Each row of the database
 * must have an equal number of values to the 
 */
public class DataReader {

	
	public Pair<String[], String[][]> loadData(String file){
		Reader in;
		try {
			in = new InputStreamReader(new FileInputStream(file), "UTF-8");
			char[] cbuf = new char[1000000];
			in.read(cbuf);
			String bufString = new String(cbuf).trim();
			String[] lines = bufString.split("\n");
			String[] clusters = lines[0].split(" ");
			String[][] data = new String[lines.length-1][clusters.length];
			for(int i = 1; i<lines.length; i++){
				data[i-1] = lines[i].split(" ");
			}
			return new Pair<String[], String[][]>(clusters, data);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
}
