package sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import filter.Message;

/*
 * Manages the parsing and execution of a command by splitting input into subcommands, creating
 * filters, and linking filters into a list.
 * 
 */
public class SequentialCommandBuilder {
	
	// keeps a list of SequentialFilters for each command
	public static List<SequentialFilter> createFiltersFromCommand(String command) {	
		
		SequentialFilter finalF = determineFinalFilter(command); // finalF states if to print or perform a new file action
		command = adjustCommandToRemoveFinalFilter(command); // remove the finalF to perform actions up to this filter	
		
		List<SequentialFilter> filter = new ArrayList<SequentialFilter>(); // list of filters to hold input commands
		
		List<String> splitFilters = Arrays.asList(command.split("\\|")); // split based off pipe
		for (int i = 0; i < splitFilters.size(); i++) {
			SequentialFilter currF = constructFilterFromSubCommand(splitFilters.get(i).trim()); // trim and get first command
			SequentialFilter priorF = null;
			if (!filter.isEmpty()) {
				priorF = filter.get(filter.size()-1); // if the filter is not empty, set priorF to be the one before the current
			}
			
			if (currF instanceof Error) { // both conditions are met if the command/file is missing or the argument passed thru is invalid
				if (((Error)currF).isError) {
					filter.clear();
					filter.add(currF);
					return filter;
				}
			}

			SequentialFilter wrongOrder = null; // ensures command/action order is correct
			
			List<String> in = new ArrayList<String>() {{add("Grep"); add("Uniq"); add("Wc"); add("DisplaySequential"); add("Error");}}; // list of commands that can be piped in
			List<String> out = new ArrayList<String>() {{add("Cat"); add("Ls"); add("Pwd"); add("Grep"); add("Wc"); add("Uniq");}}; // list of commands that can be piped out
			
			// checks for error in filters and piping
			if (((priorF!=null) && out.contains(priorF.getClass().getSimpleName())) && !(in.contains(currF.getClass().getSimpleName()))) {
				wrongOrder = new Error(Message.CANNOT_HAVE_INPUT.with_parameter(splitFilters.get(i)),true); // no input
			} else if (!((priorF!=null) && out.contains(priorF.getClass().getSimpleName())) && (in.contains(currF.getClass().getSimpleName()))) {
				wrongOrder = new Error(Message.REQUIRES_INPUT.with_parameter(splitFilters.get(i)),true);
			}
			
			if (wrongOrder != null) { // if there is an error
				filter.clear();
				filter.add(wrongOrder);
				return filter;
			}
			filter.add(currF); // either way, add currF to the list of filters 
		}
		filter.add(finalF); // after all this processing, add finalF to list of filters
		linkFilters(filter); // link the filters and return
		return filter;
	}
	
	// joins filters
	private static void linkFilters(List<SequentialFilter> filters){	
		int i = 0;
		while (i < filters.size() - 1) { // iterates until the last filter and links them/in the queue 
			filters.get(i).setNextFilter(filters.get(i + 1));
			i++;
		}
	}

	// iterates thru subCommands to return a SequentialFilter,
	private static SequentialFilter constructFilterFromSubCommand(String subCommand){
		String[] splitSubComm = subCommand.split(" ",2); // get command
		String command = splitSubComm[0].toLowerCase();
		String p = null;
		if (splitSubComm.length > 1) { // if length > 1, trim the excess
			p = splitSubComm[1].trim();
		}
			
		switch(command) { // determine which command (some checks were put in place but I could not cover all cases, as seen by the handful of errors)
		case "cd":
			try {
				return new Cd(p);
			} catch (IOException e) {
				return new Error(Message.DIRECTORY_NOT_FOUND.with_parameter(subCommand),true);
			} 
		case "cat":
			try {
				return new Cat(p);
			} catch (FileNotFoundException e) {
				return new Error(Message.FILE_NOT_FOUND.with_parameter(subCommand),true);
			} 
		case "grep":
			try {
				return new Grep(p);
			} catch (Exception e) {
				if (p==null) {
					return new Error(Message.REQUIRES_PARAMETER.with_parameter(subCommand),true);
				} else if (p.contains(" ")) {
					return new Error(Message.INVALID_PARAMETER.with_parameter(subCommand),true);
				}
			}
		case "pwd":
			return new Pwd();
		case "ls":
			return new Ls();
		case "wc":
			return new Wc();
		case "uniq":
			return new Uniq();
		default:
			return new Error(Message.COMMAND_NOT_FOUND.with_parameter(subCommand),true);
		}	
	}
	
	// if the final filter contains ">", parse for just the command
	private static String adjustCommandToRemoveFinalFilter(String command){
		if (!command.contains(">")) {
			return command;
		} else {
			return command.substring(0, command.lastIndexOf(">"));
		}
	}
	
	// determines the final filter in the path
	private static SequentialFilter determineFinalFilter(String command){
		PrintStream p = null;
		if (command.contains(">")){
			String fName = command.substring(command.lastIndexOf(">") + 1).trim(); // parses based off of last occurrence of >
			try {
				String cwd = SequentialREPL.currentWorkingDirectory;
				File f = new File(cwd + "/" + fName); // creates a file from the CWD location and printstream
				p = new PrintStream(f);
			} catch (FileNotFoundException e) {
				System.out.println(e);
			}
			return new DisplaySequential(p); // returns SequentialFilter object, essentially in the form of a printstream
		}
		return new Error(); // this is not actually an error, as the arguments are empty. This allows the final filter to be printed by calling the super within the method
	}
}