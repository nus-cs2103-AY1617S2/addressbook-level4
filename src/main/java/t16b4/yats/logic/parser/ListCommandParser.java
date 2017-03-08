package t16b4.yats.logic.parser;

import t16b4.yats.logic.commands.Command;
import t16b4.yats.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

	/**
	 * Parses the give {@code String} of arguments in the context of the ListCommand
	 * and returns a ListCommand object for execution.
	 */
	public Command parse(String args) {
		if (args.contains(ListCommand.COMMAND_WORD_EXTENTION)) {
			switch(args) {
			case (ListCommand.COMMAND_WORD_SUFFIX_TITLE):
			case (ListCommand.COMMAND_WORD_SUFFIX_DEADLINE): 
			case (ListCommand.COMMAND_WORD_SUFFIX_TIMING): 
			case (ListCommand.COMMAND_WORD_SUFFIX_TAG): 
			}
		}
		return null;
	}
}

	/**
	 * list
	 * list by deadline
	 * list by timing
	 * list by title
	 * list by tag
	 * 
	 * wrong input format
	 */