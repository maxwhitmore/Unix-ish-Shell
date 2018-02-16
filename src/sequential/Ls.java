package sequential;

import java.io.File;

/*
 * Lists the elements in the current directory
 */
public class Ls extends SequentialFilter {
	
	@Override
	public void process() {
		File currDirectory = new File(SequentialREPL.currentWorkingDirectory); // creates a tmp file holding the position of the CWD
		String[] allF = currDirectory.list();
		for (int i = 0; i < allF.length; i++) { // iterates thru currDirectory as an array and adds each item to output to be printed
			output.add(allF[i]);
		}
	}
	
	@Override
	protected String processLine(String line) {
		// Should never be called, as we have overridden process.
		return null;
	}
}