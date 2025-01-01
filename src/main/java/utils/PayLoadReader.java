package utils;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PayLoadReader {

	public String JSONread(String filepath) {
		try {
			// Read JSON file content into a String
			String jsonPayload = new String(Files.readAllBytes(Paths.get(filepath)));
			return jsonPayload;
		} catch (Exception e) {
			System.out.println("Issue in fetching the details from JSON file");
			return null;
		}

	}
	
	public String XMLread(String filepath) {
		try {
			// Read JSON file content into a String
			String xmlPayload = new String(Files.readAllBytes(Paths.get(filepath)));
			return xmlPayload;
		} catch (Exception e) {
			System.out.println("Issue in fetching the details from XML file");
			return null;
		}

	}
	

}
