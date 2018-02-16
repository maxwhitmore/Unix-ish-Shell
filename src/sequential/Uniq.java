package sequential;

import java.util.HashSet;
import java.util.Set;

/*
 * Using a HashSet, checks for duplicates in the given line. If none exist, the line is added to the set and returned
 */
public class Uniq extends SequentialFilter {

	private Set<String> duplicate;
	
	public Uniq(){
		duplicate = new HashSet<String>();
	}
	
	@Override
	protected String processLine(String line) {
		if (duplicate.contains(line)) { // set contains no duplicates
			return null;
		} else {
			duplicate.add(line); // add line if does not exist and return
			return line;
		}
	}
}