package sequential;

/*
 * Pipes the working directory to the output queue
 */
public class Pwd extends SequentialFilter {
	
	@Override
	public void process(){
		String s = SequentialREPL.currentWorkingDirectory; // holds the CWD as a string and adds to output
		output.add(s);
	}
	
	@Override
	protected String processLine(String line) {
		// Should never be called, as we have overridden process.
		return null;
	}
}