package sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import sequential.SequentialREPL;

/*
 * This class does not accept piped input. This just parses the given command
 * and prints out the file's contents.
 */
public class Cat extends SequentialFilter {

	private List<Scanner> allScan = new ArrayList<Scanner>(); // list of scanners
	
	public Cat(String f) throws FileNotFoundException {
		String [] files = f.split("\\s+"); // parse for file
		for (int i = 0; i < files.length; i++) { // for each file, create a new scanner to read (pointed by the current directory)
			Scanner scan = new Scanner(new File(SequentialREPL.currentWorkingDirectory + FILE_SEPARATOR + files[i]));
			allScan.add(scan); // add to list of scanners
		}
	}	

	@Override
	public void process(){
		int count = 0;
		String s;
		for (int i = 0; i < allScan.size(); i++) { // iterate until the count is as large as the number of scanners
			Scanner scan = allScan.get(count);
			while (scan.hasNextLine()){ // for each scanner, read the line and add to output
				s = scan.nextLine();
				output.add(s);
			}
		}
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}
}