package sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/*
 * A helper class which extends SequentialFilter, allowing me to create a prinstream from a given file and compare it
 * to other SequentialFilter objects
 * 
 * Its main functionality is printing whatever gets passed to processLine
 */
public class DisplaySequential extends SequentialFilter {
	public PrintStream printWriter;
	
	public DisplaySequential(PrintStream p){
		printWriter = p;	
	}
	
	@Override
	protected String processLine(String line) {
		printWriter.println(line);
		return null;
	}
}
