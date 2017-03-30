package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_RECUR;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.onetwodo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;
import java.util.Set;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(
                PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_RECUR,
                PREFIX_PRIORITY, PREFIX_DESCRIPTION, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            String preamble = argsTokenizer.getPreamble().get();
            String startDate = argsTokenizer.getValue(PREFIX_START_DATE).orElse("");
            String endDate = argsTokenizer.getValue(PREFIX_END_DATE).orElse("");
            String recur = argsTokenizer.getValue(PREFIX_RECUR).orElse("");
            String priority = argsTokenizer.getValue(PREFIX_PRIORITY).orElse("");
            String description = argsTokenizer.getValue(PREFIX_DESCRIPTION).orElse("");
            Set<String> tags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));
            return new AddCommand(preamble, startDate, endDate, recur, priority, description, tags);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
}
