package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BYDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROMDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TODATE;

import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

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
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_LOCATION, PREFIX_TAG, PREFIX_FROMDATE, PREFIX_TODATE,
                        PREFIX_BYDATE, PREFIX_STARTTIME, PREFIX_ENDTIME);
        argsTokenizer.tokenize(args);
        try {
            String priorityExists = argsTokenizer.getValue(PREFIX_PRIORITY).orElse(null);
            String locationExists = argsTokenizer.getValue(PREFIX_LOCATION).orElse(null);
            String fromdateExists = argsTokenizer.getValue(PREFIX_FROMDATE).orElse(null);
            String todateExists = argsTokenizer.getValue(PREFIX_TODATE).orElse(null);
            String bydateExists = argsTokenizer.getValue(PREFIX_BYDATE).orElse(null);
            String starttimeExists = argsTokenizer.getValue(PREFIX_STARTTIME).orElse(null);
            String endtimeExists = argsTokenizer.getValue(PREFIX_ENDTIME).orElse(null);

            if (priorityExists == null && bydateExists == null && fromdateExists == null) {
                throw new NoSuchElementException();
            }

            if (priorityExists != null && (bydateExists != null || fromdateExists != null)) {
                throw new IllegalValueException("Task or Event or Deadline");
            }

            if (bydateExists != null && fromdateExists != null) {
                throw new IllegalValueException("Task or Event or Deadline");
            }

            if (priorityExists != null && (fromdateExists != null || todateExists != null || bydateExists != null ||
                    starttimeExists != null || endtimeExists != null)) {
                throw new IllegalValueException("Task or Event or Deadline");
            }

            if (bydateExists != null && (fromdateExists != null || todateExists != null || starttimeExists != null
                    || priorityExists != null)) {
                throw new IllegalValueException("Task or Event or Deadline");
            }

            if (priorityExists != null && (fromdateExists != null || todateExists != null || starttimeExists != null ||
                    endtimeExists != null || bydateExists != null)) {
                throw new IllegalValueException("Task or Event or Deadline");
            }

            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    priorityExists,
                    starttimeExists,
                    fromdateExists,
                    endtimeExists,
                    todateExists,
                    bydateExists,
                    locationExists,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
