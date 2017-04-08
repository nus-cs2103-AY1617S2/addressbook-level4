package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.DELETE_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.DeleteCommand;
import seedu.task.logic.commands.FindCommand;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.ShowcaseCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ShowcaseCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
    	args = args.trim();
    	int numberOfTasks = Integer.parseInt(args);
    	return new ShowcaseCommand(numberOfTasks);
    }

}
