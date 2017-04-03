package seedu.task.logic.parser;

import static seedu.task.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.task.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.task.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.NoSuchElementException;

import seedu.task.logic.parser.ParserUtil;
import seedu.task.model.task.TaskName;
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
	ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DATE, PREFIX_START_TIME, PREFIX_END_TIME,
		PREFIX_DESCRIPTION, PREFIX_TAG);
	argsTokenizer.tokenize(args);
	try {
	    return new AddCommand(new TaskName(argsTokenizer.getPreamble().get()),
		    ParserUtil.parseDate(argsTokenizer.getValue(PREFIX_DATE)),
		    ParserUtil.parseTime(argsTokenizer.getValue(PREFIX_START_TIME)),
		    ParserUtil.parseTime(argsTokenizer.getValue(PREFIX_END_TIME)),
		    ParserUtil.parseString(argsTokenizer.getValue(PREFIX_DESCRIPTION)));

	} catch (NoSuchElementException nsee) {
	    return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
	} catch (IllegalValueException ive) {
	    return new IncorrectCommand(ive.getMessage());
	}
    }

}
