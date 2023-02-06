package com.magento.framework.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

public class CSVHelper {
	public static void writeDataLineByLineInCSV(String filePath, String[] header, List<String[]> list) {
		File file = new File(filePath);
		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			writer.writeNext(header);
			for (int i = 0; i < list.size(); i++) {
				writer.writeNext(list.get(i));
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String file = System.getProperty("user.dir") + "/src/main/resources/uploads/" + "test.csv";
		ArrayList<String[]> list = new ArrayList<String[]>();
		list.add(new String[] { "1", "Dishant", "Doshi" });
		list.add(new String[] { "2", "Badal", "Gandhi" });
		writeDataLineByLineInCSV(file, new String[] { "Sr No", "First Name", "Last Name" }, list);
	}
}
