package org.teamstbf.yats.logic.parser;

import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;

import org.teamstbf.yats.logic.commands.BatchDeleteCommand;
import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.DeleteCommand;
import org.teamstbf.yats.logic.commands.IncorrectCommand;

//@@author A0138952W
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {

	private Stack<Integer> deleteStack = new Stack<Integer>();

	/**
	 * Parses the given {@code String} of arguments in the context of the
	 * DeleteCommand and returns an DeleteCommand object for execution.
	 */
	public Command parse(String args) {

		String[] deleteIndexArr = stringTokenizer(args);
		if (deleteIndexArr.length > 1) {
			if (isAllIntegers(deleteIndexArr)) {
				deleteIndexArr = sortIndexArr(deleteIndexArr);
				for (String element : deleteIndexArr) {
					deleteStack.push(Integer.valueOf(element));
				}
				return new BatchDeleteCommand(deleteStack);
			} else if (!isAllIntegers(deleteIndexArr)) {
				return new IncorrectCommand(
						String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchDeleteCommand.MESSAGE_USAGE));
			}
		}

		Optional<Integer> index = ParserUtil.parseIndex(args);
		if (!index.isPresent()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
		}
		return new DeleteCommand(index.get());
	}

	/**
	 * Returns a {@code String[]} of the command text tokens
	 *
	 * @param commandText
	 */
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
