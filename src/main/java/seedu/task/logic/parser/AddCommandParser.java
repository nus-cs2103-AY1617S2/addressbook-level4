package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.commons.core.Messages.MESSSAGE_INVALID_TIMING_ORDER;
import static seedu.task.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.task.logic.parser.CliSyntax.PREFIX_RECURRING;
import static seedu.task.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.task.commons.exceptions.IllegalTimingOrderException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;

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
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_START_DATE,
                        PREFIX_END_DATE, PREFIX_TAG, PREFIX_RECURRING);
        argsTokenizer.tokenize(args);
        String priority;
        String startDate;
        String endDate;
        String recur;

        try {
            priority = argsTokenizer.getValue(PREFIX_PRIORITY).get();
        } catch (NoSuchElementException e) {
            priority = null;
        }

        try {
            startDate = argsTokenizer.getValue(PREFIX_START_DATE).get();
        } catch (NoSuchElementException e) {
            startDate = null;
        }

        try {
            endDate = argsTokenizer.getValue(PREFIX_END_DATE).get();
        } catch (NoSuchElementException e) {
            endDate = null;
        }

        try {
            recur = argsTokenizer.getValue(PREFIX_RECURRING).get();
        } catch (NoSuchElementException e) {
            recur = null;
        }

        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    priority, startDate, endDate, recur,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
                    );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (IllegalTimingOrderException itoe) {
            return new IncorrectCommand(MESSSAGE_INVALID_TIMING_ORDER);
        }
    }

}
