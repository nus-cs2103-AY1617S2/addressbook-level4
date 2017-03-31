package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.task.exceptions.InvalidDurationException;
import seedu.address.model.task.exceptions.PastDateTimeException;


//@@author A0140023E
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        DateTimeExtractor dateTimeExtractor = new DateTimeExtractor(args);
        // TODO Returns an exception in a method? Doesn't make sense
        // Returns a string? seems brittle, therefore to rewrite the class to preserve state
        try {
            // process StartEndDateTime first because it is more constrained
            dateTimeExtractor.processStartEndDateTime();
        } catch (PastDateTimeException e) {
            return new IncorrectCommand(e.getMessage());
        } catch (InvalidDurationException e) {
            return new IncorrectCommand(e.getMessage());
        } catch (IllegalValueException e) {
            // Dates can't be parsed so we silently skip first
            // all other exceptions have been handled
            // Pass rose from Uncle to Jane by tmr
            // we should not return an error because that case is a valid task
            System.out.println("No date is found for start and end date");
        }
        // TODO Returns an exception in a method? Doesn't make sense
        // Returns a string? seems brittle
        try {
            dateTimeExtractor.processDeadline();
        } catch (PastDateTimeException e) {
            return new IncorrectCommand(e.getMessage());
        } catch (IllegalValueException e) {
            // No date is found so we silently skip
            System.out.println("No date found for deadline!");
        }

        // TODO ArgumentTokenizer became very irrelevant in this class but is it still relevant for other classes?
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_TAG);
        argsTokenizer.tokenize(dateTimeExtractor.getProcessedArgs());
        try {
            String nameArgs = argsTokenizer.getPreamble().get();

            return new AddCommand(nameArgs, dateTimeExtractor.getProcessedDeadline(),
                    dateTimeExtractor.getProcessedStartEndDateTime(),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
