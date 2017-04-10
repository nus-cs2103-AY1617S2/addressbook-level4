package org.teamstbf.yats.logic.parser;

import static org.teamstbf.yats.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;
import java.util.Stack;

import org.teamstbf.yats.logic.commands.BatchUnmarkDoneCommand;
import org.teamstbf.yats.logic.commands.Command;
import org.teamstbf.yats.logic.commands.IncorrectCommand;
import org.teamstbf.yats.logic.commands.MarkUndoneCommand;

//@@author A0139448U
/**
 * Parses input arguments and creates a new MarkUndoneCommand object
 */
public class MarkUndoneCommandParser {

    private Stack<Integer> markStack = new Stack<Integer>();

    /**
     * Parses the given {@code String} of arguments in the context of the
     * MarkDoneCommand and returns an MarkDoneCommand object for execution.
     */
    public Command parse(String args) {

        String[] markIndexArr = ParserUtil.stringTokenizer(args);
        if (markIndexArr.length > 1) {
            if (ParserUtil.isAllIntegers(markIndexArr)) {
                return convertToBatchUnmarkDoneCommand(markIndexArr);
            } else if (!ParserUtil.isAllIntegers(markIndexArr)) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchUnmarkDoneCommand.MESSAGE_USAGE));
            }
        }

        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUndoneCommand.MESSAGE_USAGE));
        }

        return new MarkUndoneCommand(index.get());
    }

    public Command convertToBatchUnmarkDoneCommand(String[] markIndexArr) {
        markIndexArr = ParserUtil.sortIndexArr(markIndexArr);
        for (String element : markIndexArr) {
            if (!markStack.contains(Integer.valueOf(element) - 1)) {
                markStack.push(Integer.valueOf(element) - 1);
            }
        }
        return new BatchUnmarkDoneCommand(markStack);
    }

}
