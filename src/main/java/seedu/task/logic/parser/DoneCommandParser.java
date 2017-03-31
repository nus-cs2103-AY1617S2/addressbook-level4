package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.DONE_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.DoneCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.IncorrectCommand;


//@@author A0146757R
public class DoneCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {

	final Matcher matcher = DONE_ARGS_FORMAT.matcher(args.trim());
	if (!matcher.matches()) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
	}
	final String[] index = matcher.group("index").split("\\s+");
	final int[] targetIndex = new int[index.length];
	for (int i = 0; i < index.length; i++)
	    targetIndex[i] = Integer.parseInt(index[i]);

	return new DoneCommand(targetIndex);
    }
}
//@@author
