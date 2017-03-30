package org.teamstbf.yats.logic.parser;

import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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

	String[] deleteIndexArr = ParserUtil.stringTokenizer(args);
	if (deleteIndexArr.length > 1) {
	    if (ParserUtil.isAllIntegers(deleteIndexArr)) {
		deleteIndexArr = ParserUtil.sortIndexArr(deleteIndexArr);
		for (String element : deleteIndexArr) {
		    deleteStack.push(Integer.valueOf(element));
		}
		return new BatchDeleteCommand(deleteStack);
	    } else if (!ParserUtil.isAllIntegers(deleteIndexArr)) {
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

}
