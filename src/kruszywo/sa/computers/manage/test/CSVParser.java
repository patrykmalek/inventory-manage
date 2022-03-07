package kruszywo.sa.computers.manage.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CSVParser {
	
	private final String COMMA_DELIMITER = ",";

	public CSVParser() {}
	
	public List<List<String>> readCSVFile(String filePath) {
		
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(COMMA_DELIMITER );
		        records.add(Arrays.asList(values));
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return records;
	}

}
