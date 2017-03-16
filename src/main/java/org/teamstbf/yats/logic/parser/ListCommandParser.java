package org.teamstbf.yats.logic.parser;

import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.ListCommand;
import org.teamstbf.yats.logic.commands.ListCommandDate;
import org.teamstbf.yats.logic.commands.ListCommandDone;
import org.teamstbf.yats.logic.commands.ListCommandLocation;
import org.teamstbf.yats.logic.commands.ListCommandTag;
import org.teamstbf.yats.logic.commands.ListCommandTiming;
import org.teamstbf.yats.logic.commands.ListCommandTitle;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

	private final int LISTCOMMANDSUFFIX = 2;

	/**
	 * Parses the given {@code String} of arguments in the context of the
	 * ListCommand and returns a ListCommand object that is according to the
	 * suffix for execution.
	 */
	public Command parse(String args) {
		if (args.contains(ListCommand.COMMAND_WORD_EXTENTION)) {
			String[] commandTextArray = stringTokenizer(args);
			switch (commandTextArray[LISTCOMMANDSUFFIX]) {
			case (ListCommand.COMMAND_WORD_SUFFIX_TITLE):
				return new ListCommandTitle();
			case (ListCommand.COMMAND_WORD_SUFFIX_DEADLINE):
				return new ListCommandDate();
			case (ListCommand.COMMAND_WORD_SUFFIX_TIMING):
				return new ListCommandTiming();
			case (ListCommand.COMMAND_WORD_SUFFIX_LOCATION):
				return new ListCommandLocation();
			case (ListCommand.COMMAND_WORD_SUFFIX_DONE):
				return new ListCommandDone();
			case (ListCommand.COMMAND_WORD_SUFFIX_TAG):
				return new ListCommandTag();
			}
		}
		return new ListCommand();

		// Need to add command input variations such as for the case of no
		// extension
		// for the case of wrong command
		// for the case of repeating events, only need to list one
	}

	/**
	 * Returns a {@code String[]} of the command text tokens
	 *
	 * @param commandText
	 */
	public String[] stringTokenizer(String commandText) {
		return commandText.split(" ");
	}
}
