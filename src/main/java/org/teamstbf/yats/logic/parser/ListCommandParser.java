package org.teamstbf.yats.logic.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.ListCommand;
import org.teamstbf.yats.logic.commands.ListCommandDate;
import org.teamstbf.yats.logic.commands.ListCommandDone;
import org.teamstbf.yats.logic.commands.ListCommandLocation;
import org.teamstbf.yats.logic.commands.ListCommandTag;
import org.teamstbf.yats.logic.commands.ListCommandTiming;

/**
 * Parses input arguments and creates a new ListCommand object
 */
// @@author A0138952W
public class ListCommandParser {

	private final int LIST_COMMAND_SUFFIX = 2;
	private final String COMMAND_EXTENSION_DATE = "by date";
	private final String COMMAND_EXTENSION_TIMING = "by timing";
	private final String COMMAND_EXTENSION_LOCATION = "by location";
	private final String COMMAND_EXTENSION_DONE = "done";
	private final String COMMAND_EXTENSION_TAG = "by tag";
	private final String KEYWORD_REPLACE_DONE = "Yes";

	/**
	 * Parses the given {@code String} of arguments in the context of the
	 * ListCommand and returns a ListCommand object that is according to the
	 * suffix for execution.
	 */
	public Command parse(String args) {
		if (args.contains(ListCommand.COMMAND_WORD_EXTENTION)) {
			String[] commandTextArray = stringTokenizer(args);
			switch (commandTextArray[LIST_COMMAND_SUFFIX]) {
			case (ListCommand.COMMAND_WORD_SUFFIX_DATE):
				return new ListCommandDate(internalParser(args, COMMAND_EXTENSION_DATE));
			case (ListCommand.COMMAND_WORD_SUFFIX_TIMING):
				return new ListCommandTiming(internalParser(args, COMMAND_EXTENSION_TIMING));
			case (ListCommand.COMMAND_WORD_SUFFIX_LOCATION):
				return new ListCommandLocation(internalParser(args, COMMAND_EXTENSION_LOCATION));
			case (ListCommand.COMMAND_WORD_SUFFIX_TAG):
				return new ListCommandTag(internalParser(args, COMMAND_EXTENSION_TAG));
			}
		} else {
			if (args.contains(ListCommand.COMMAND_WORD_SUFFIX_DONE)) {
				return new ListCommandDone(doneInternalParser(args, COMMAND_EXTENSION_DONE));
			}
		}
		return new ListCommand();
	}

	/**
	 * Parses a {@code String} to remove the Command Extensions
	 *
	 * @param args
	 *            String to parse
	 * @param toReplace
	 *            Command Extension to replace
	 * @return {@code Set<String>} for the ListCommand variants to process
	 */
	private Set<String> internalParser(String args, String toReplace) {
		String[] keywords = args.replaceFirst(toReplace, " ").trim().split(" ");
		Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
		return keywordSet;
	}

	private Set<String> doneInternalParser(String args, String toReplace) {
		String[] keywords = args.replaceFirst(toReplace, KEYWORD_REPLACE_DONE).trim().split(" ");
		Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
		return keywordSet;
	}
	// Need to add command input variations such as for the case of no
	// extension
	// for the case of wrong command
	// for the case of repeating events, only need to list one

	/**
	 * Returns a {@code String[]} of the command text tokens
	 *
	 * @param commandText
	 */
	private String[] stringTokenizer(String commandText) {
		return commandText.split(" ");
	}
}
