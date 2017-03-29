package org.teamstbf.yats.logic.parser;

import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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

	String[] markIndexArr = ParserUtil.stringTokenizer(args);
	if (markIndexArr.length > 1) {
	    if (ParserUtil.isAllIntegers(markIndexArr)) {
		markIndexArr = ParserUtil.sortIndexArr(markIndexArr);
		for (String element : markIndexArr) {
		    markStack.push(Integer.valueOf(element) - 1);
		}
		return new BatchMarkDoneCommand(markStack);
	    } else if (!ParserUtil.isAllIntegers(markIndexArr)) {
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

}
