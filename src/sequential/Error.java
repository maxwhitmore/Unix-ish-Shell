package sequential;

/*
 * A helper class to determine if there is an error (parameter, input, FnF, etc) and output the resulting message in processLine.
 */
public class Error extends SequentialFilter {
	
	public boolean isError = false;
	private String type;
	
	// holds type of error (passed as Message enum)
	public Error(String e, boolean b) {
		type = e;
		isError = b;	
	}
	
	// construct SequentialFilter object normally
	public Error() {
		super();
	}
	
	// isError is only useful when called from the process method, as it determines whether to print the message or call the super process
	@Override
	public void process() {
		if (!isError) {
			super.process();
		} else {
			System.out.print(type);
		}
	}
	
	@Override
	protected String processLine(String line) {
		System.out.println(line);
		return null;
	}	
}