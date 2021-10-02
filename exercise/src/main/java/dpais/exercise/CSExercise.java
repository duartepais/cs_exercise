package dpais.exercise;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.lang.Math;

import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.*;

import java.sql.SQLException;

public class CSExercise {

	public static void main(String[] args) {
		
		

		long startTime = System.nanoTime();			// start timestamp
		
		// get input path of the file to analyse
		System.out.println("Type the path of your file: ");
		Scanner scanner = new Scanner(System.in);
		String directory = scanner.nextLine();
		System.out.println("Analysing the file " + directory);
		scanner.close();

		// initialise hashmaps (dictionaires)
		HashMap<String, InputEvent> startedEvents = new HashMap<>();
		HashMap<String, InputEvent> finishedEvents = new HashMap<>();
		
		
		//read the input file
		try (BufferedReader br = new BufferedReader(new FileReader(directory))) {

			String line;
			Gson gson = new Gson();

			//read file line by line
			while ((line = br.readLine()) != null) {

				InputEvent inputEv = gson.fromJson(line, InputEvent.class);			// save each line as an InputEvent object

				// append the event to the appropriate hashmap
				if (inputEv.state.equals("STARTED")) {

					startedEvents.put(inputEv.id, inputEv);
				} else {
					finishedEvents.put(inputEv.id, inputEv);
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		
		// start the database
		Database analysedDB = new Database();

		try {
			 analysedDB.create();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int duration = 0;

		// traverse through the hashmap with the started events
		for (String key : startedEvents.keySet()) {

			InputEvent startedEv = startedEvents.get(key);
			
			duration = Math.abs((int) (finishedEvents.get(key).timestamp - startedEv.timestamp));			// calculate the duration of each event
			AnalysedEvent outputEv = new AnalysedEvent(startedEv.host, startedEv.type, duration);			// get the corresponding output event object
			
			// insert the output event into the database
			try {
				analysedDB.insert(key, outputEv);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		analysedDB.closeConnection();			// close the database

		// calculate the execution time and print it
		long endTime = System.nanoTime();		
		long timeElapsed = endTime - startTime;

		System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
		System.out.println("Execution time in seconds: " + timeElapsed / 1000000000);

	}


}
