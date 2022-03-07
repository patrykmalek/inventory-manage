package kruszywo.sa.computers.manage.test;

import java.awt.EventQueue;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CSVParser {
	
	private final String COMMA_DELIMITER = ",";
	private String filePath;

	public CSVParser() {}
	
	public List<List<String>> readCSVFile() {
		
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(getFilePath()))) {
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	

	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (ClassNotFoundException | InstantiationException
//				| IllegalAccessException | UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//			return;
//		} 
		try {
			EventQueue.invokeAndWait(new Runnable() {
			    @Override
			    public void run() {
			    	CSVParser csvParser = new CSVParser();
					
					JFileChooser fileChooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("(*.csv) CSV - Dane oddzielone przecinkami", "csv");
					fileChooser.setFileFilter(filter);
					
					int returnValue = fileChooser.showOpenDialog(null);
					List<List<String>> test = null;
					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						csvParser.setFilePath(selectedFile.getAbsolutePath());
						test = csvParser.readCSVFile();
					}
					System.out.println(test);
			    }
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
