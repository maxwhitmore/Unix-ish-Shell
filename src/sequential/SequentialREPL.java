package sequential;

import filter.Message;
import java.util.List;
import java.util.Scanner;

/*
 * Loops in the read-eval-print manner to get each new command from the user
 */
public class SequentialREPL {

	static String currentWorkingDirectory;
	
	public static void main(String[] args){
		System.out.print(Message.WELCOME);
		currentWorkingDirectory = System.getProperty("user.dir"); // sets CWD to user directory
		Scanner s = new Scanner(System.in);		

		boolean run = true;
		while (run) {
			System.out.print(Message.NEWCOMMAND);
			String cmd = s.nextLine(); // get input from user
			if (cmd.trim().equals("exit")) { // check for exit
				System.out.print(Message.GOODBYE);
				run = false;
			} else {
				process(cmd); // evaluate and print
			}
		}
	}
	
	public static void process(String cmd) {
		try {
			List<SequentialFilter> cmdList = SequentialCommandBuilder.createFiltersFromCommand(cmd); // create filters and store as a list
			for (int i = 0; i < cmdList.size(); i++) { // each filter is processed
				cmdList.get(i).process();
			}
		} catch (Exception e) { // catch any general exeption and print
			System.out.print(e.getMessage());
		}
	}
}