package org.teamstbf.yats.logic.parser;

import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

	private final int LISTCOMMANDSUFFIX = 3;
	/**
	 * Parses the given {@code String} of arguments in the context of the ListCommand
	 * and returns a ListCommand object for execution.
	 */
	public Command parse(String args) {
		switch(stringTokenizer(args)) {
		case(ListCommand.COMMAND_WORD_SUFFIX_TITLE):
			return new ListCommand();
		case(ListCommand.COMMAND_WORD_SUFFIX_DEADLINE):
			return new ListCommand();
		}
		return new ListCommand();
	}

	public String stringTokenizer(String commandText) {
		String commandTextArray[] = commandText.split(" ");
		return commandTextArray[LISTCOMMANDSUFFIX];
	}
}
