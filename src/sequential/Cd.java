package sequential;

import java.io.File;
import java.io.IOException;

/*
 * This class changes directory, but does not accept for the special directory (not enough time)
 */
public class Cd extends SequentialFilter{
	
	String destination; // holds the destination to change directory
	
	public Cd(String location) throws IOException {
		File location2 = new File(location); // a temp location
		if (!location2.isAbsolute()) { // if the path is not absolute, the destination gets to be the currdirectory
			destination = new File(SequentialREPL.currentWorkingDirectory+FILE_SEPARATOR+location2).getCanonicalPath();
			File f = new File(destination);
			if (!f.exists()) { // error check
				throw new IOException();
			}
		} else { // if the path is absolute, the workingdirectory gets updated
			destination = location2.getCanonicalPath();
			File f = new File(destination);
			if (!f.exists()) { // error check
				throw new IOException();
			}
		}	
	}
	
	// if destination has changed, the CWD gets updated
	public void process() {
		SequentialREPL.currentWorkingDirectory = destination;
	}
	
	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}
}