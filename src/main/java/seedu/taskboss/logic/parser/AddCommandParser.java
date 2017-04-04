package seedu.taskboss.logic.parser;

import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_INFORMATION;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_RECURRENCE;
import static seedu.taskboss.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.taskboss.commons.exceptions.BuiltInCategoryException;
import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.AddCommand;
import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.commands.exceptions.InvalidDatesException;
import seedu.taskboss.model.task.Name;
import seedu.taskboss.model.task.Recurrence;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private static final String EMPTY_STRING = "";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws InvalidDatesException
     * @throws BuiltInCategoryException
     */
    public Command parse(String args) throws InvalidDatesException, BuiltInCategoryException {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_START_DATE,
                        PREFIX_END_DATE, PREFIX_INFORMATION, PREFIX_RECURRENCE, PREFIX_CATEGORY);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    checkEmpty(argsTokenizer.getValue(PREFIX_PRIORITY)),
                    checkEmpty(argsTokenizer.getValue(PREFIX_START_DATE)),
                    checkEmpty(argsTokenizer.getValue(PREFIX_END_DATE)),
                    checkEmpty(argsTokenizer.getValue(PREFIX_INFORMATION)),
                    checkEmpty(argsTokenizer.getValue(PREFIX_RECURRENCE)),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_CATEGORY))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(Name.MESSAGE_NAME_CONSTRAINTS);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (InvalidDatesException ide) {
            return new IncorrectCommand(ide.getMessage());
        } catch (IllegalArgumentException iae) {
            return new IncorrectCommand(Recurrence.MESSAGE_RECURRENCE_CONSTRAINTS);
        } catch (BuiltInCategoryException dce) {
            return new IncorrectCommand(dce.getMessage());
        }
    }

    //@@author A0147990R
    private String checkEmpty(Optional<String> test) throws IllegalValueException {
        try {
            return test.get();
        } catch (NoSuchElementException nsee) {
            return EMPTY_STRING;
        }
    }
}
