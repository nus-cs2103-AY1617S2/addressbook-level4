package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.AddCommand;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.IncorrectCommand;

//@@author A0143076J-unused
//not used because decided to try to instead implement flexible ordering of the task description.
//Using this code makes it necessary for the task description to immediately follow the add command word.
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DEADLINE, PREFIX_START_DATETIME, PREFIX_END_DATETIME, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_DEADLINE).isPresent(), argsTokenizer.getValue(PREFIX_DEADLINE),
                    argsTokenizer.getValue(PREFIX_START_DATETIME).isPresent(), argsTokenizer.getValue(PREFIX_START_DATETIME),
                    argsTokenizer.getValue(PREFIX_END_DATETIME).isPresent(), argsTokenizer.getValue(PREFIX_END_DATETIME),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
