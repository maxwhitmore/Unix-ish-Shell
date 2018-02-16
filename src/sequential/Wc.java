package sequential;

import filter.Message;

/*
 * Counts the lines, words, and characters with each call to process line
 */
public class Wc extends SequentialFilter {
	
	private int l,w,c;
	
	public Wc(){
		l=w=c=0;
	}
	
	@Override
	public void process() {
		if (input==null) {
			System.out.println(Message.REQUIRES_INPUT);
		}
		super.process();
		output.add(l + " " + w + " " + c); // adds l,w,c to output queue in specified order
	}
	
	@Override
	protected String processLine(String line) {
		l++; // lines increment
		w += line.split(" ").length; // words are counted by using a space delimiter
		c += line.length(); // characters are counted by getting the length of the entire line
		return null;
	}
}