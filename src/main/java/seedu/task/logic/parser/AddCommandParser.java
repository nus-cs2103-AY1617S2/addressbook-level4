package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PREFIX_TASK_DATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;
import static seedu.task.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.task.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.NoSuchElementException;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

	/**
	 * Parses the given {@code String} of arguments in the context of the
	 * AddCommand and returns an AddCommand object for execution.
	 */
	public Command parse(String args) {
		ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_TASK_DATE, PREFIX_START_TIME, PREFIX_END_TIME,
				PREFIX_TASK_DESCRIPTION);
		argsTokenizer.tokenize(args);
		try {
			return new AddCommand(argsTokenizer.getPreamble().get(), argsTokenizer.getValue(PREFIX_TASK_DATE).get(),
					argsTokenizer.getValue(PREFIX_START_TIME).get(), argsTokenizer.getValue(PREFIX_END_TIME).get(),
					argsTokenizer.getValue(PREFIX_TASK_DESCRIPTION).get());
		} catch (NoSuchElementException nsee) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
		} catch (IllegalValueException ive) {
			return new IncorrectCommand(ive.getMessage());
		}
	}

}
