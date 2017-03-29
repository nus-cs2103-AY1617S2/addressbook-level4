package org.teamstbf.yats.logic.parser;

import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;

import org.teamstbf.yats.logic.commands.BatchDeleteCommand;
import org.teamstbf.yats.logic.commands.BatchMarkDoneCommand;
import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.IncorrectCommand;
import org.teamstbf.yats.logic.commands.MarkDoneCommand;

/**
 * Parses input arguments and creates a new MarkDoneCommand object
 */
// @@author A0139448U
public class MarkDoneCommandParser {

	private Stack<Integer> markStack = new Stack<Integer>();

	/**
	 * Parses the given {@code String} of arguments in the context of the
	 * MarkDoneCommand and returns an MarkDoneCommand object for execution.
	 */
	public Command parse(String args) {

		String[] markIndexArr = stringTokenizer(args);
		if (markIndexArr.length > 1) {
			if (isAllIntegers(markIndexArr)) {
				markIndexArr = sortIndexArr(markIndexArr);
				for (String element : markIndexArr) {
					markStack.push(Integer.valueOf(element));
				}
				return new BatchMarkDoneCommand(markStack);
			} else if (!isAllIntegers(markIndexArr)) {
				return new IncorrectCommand(
						String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchDeleteCommand.MESSAGE_USAGE));
			}
		}

		Optional<Integer> index = ParserUtil.parseIndex(args);
		if (!index.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
		}

		return new MarkDoneCommand(index.get());
	}

	private String[] stringTokenizer(String commandText) {
		String[] splitText = commandText.trim().split(" ");
		for (String element : splitText) {
			element.trim();
		}
		return splitText;
	}

	private String[] sortIndexArr(String[] stringArray) {
		int[] intArray = new int[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			intArray[i] = Integer.parseInt(stringArray[i]);
		}
		Arrays.sort(intArray);
		for (int i = 0; i < intArray.length; i++) {
			stringArray[i] = Integer.toString(intArray[i]);
		}
		return stringArray;
	}

	private boolean isAllIntegers(String[] stringArray) {
		for (int i = 0; i < stringArray.length; i++) {
			if (!isInteger(stringArray[i])) {
				return false;
			}
		}
		return true;
	}

	private boolean isInteger(String toCheck) {
		try {
			Integer.parseInt(toCheck);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
