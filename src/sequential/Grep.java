package sequential;

/*
 * Searches for a keyword and returns the line. Simple implementation.
 */
public class Grep extends SequentialFilter {
	
	private String keyword;
	
	public Grep(String s) {		
		keyword = s;
	}
	
	@Override
	protected String processLine(String s) {
		if (s.contains(keyword)) { // if the line contains the keyword, return the line
			return s;
		} else {
			return null;
		}
	}
}