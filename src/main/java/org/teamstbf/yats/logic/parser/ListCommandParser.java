package org.teamstbf.yats.logic.parser;

import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

	/**
	 * Parses the give {@code String} of arguments in the context of the ListCommand
	 * and returns a ListCommand object for execution.
	 */
	public Command parse(String args) {
		// if (args.contains(ListCommand.COMMAND_WORD_EXTENTION)) {
		//	switch(args) {
		//	case (ListCommand.COMMAND_WORD_SUFFIX_TITLE):
		//	case (ListCommand.COMMAND_WORD_SUFFIX_DEADLINE):
		//	case (ListCommand.COMMAND_WORD_SUFFIX_TIMING):
		//	case (ListCommand.COMMAND_WORD_SUFFIX_TAG):
		return new ListCommand();
	}
}
