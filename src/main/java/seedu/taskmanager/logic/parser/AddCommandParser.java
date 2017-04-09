package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_REPEAT;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.taskmanager.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.AddCommand;
import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_STARTDATE, PREFIX_ENDDATE, PREFIX_DESCRIPTION,
                PREFIX_REPEAT, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(argsTokenizer.getPreamble().get(), argsTokenizer.getValue(PREFIX_STARTDATE),
                    argsTokenizer.getValue(PREFIX_ENDDATE), argsTokenizer.getValue(PREFIX_DESCRIPTION),
                    argsTokenizer.getValue(PREFIX_REPEAT),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
