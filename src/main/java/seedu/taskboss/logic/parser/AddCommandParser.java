package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_INFORMATION;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.AddCommand;
import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private static final String EMPTY_STRING = "";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws InvalidDatesException
     */
    public Command parse(String args) throws InvalidDatesException {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_START_DATE,
                        PREFIX_END_DATE, PREFIX_INFORMATION, PREFIX_CATEGORY);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    checkEmpty(argsTokenizer.getValue(PREFIX_PRIORITY)),
                    checkStartDateTimeEmpty(argsTokenizer.getValue(PREFIX_START_DATE)),
                    checkEndDateTimeEmpty(argsTokenizer.getValue(PREFIX_END_DATE)),
                    checkEmpty(argsTokenizer.getValue(PREFIX_INFORMATION)),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (InvalidDatesException ide) {
            return new IncorrectCommand(ide.getMessage());
        }
    }

    //@@author A0147990R
    private String checkEmpty(Optional<String> test) {
        try {
            return test.get();
        } catch (NoSuchElementException nsee) {
            return EMPTY_STRING;
        }
    }

    private String checkStartDateTimeEmpty(Optional<String> test) throws IllegalValueException {
        try {
            return test.get();
        } catch (NoSuchElementException nsee) {
            return EMPTY_STRING;
        }
    }

    private String checkEndDateTimeEmpty(Optional<String> test) throws IllegalValueException {
        try {
            return test.get();
        } catch (NoSuchElementException nsee) {
            return EMPTY_STRING;
        }
    }

}
