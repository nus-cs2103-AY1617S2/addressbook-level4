package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_END;
import static seedu.task.logic.parser.CliSyntax.PREFIX_START;
import static seedu.task.logic.parser.CliSyntax.PREFIX_TAG;

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
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DUEDATE, PREFIX_START, PREFIX_END, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            String startDate = argsTokenizer.getValue(PREFIX_START).orElse("");
            String endDate = argsTokenizer.getValue(PREFIX_END).orElse("");
            if (startDate == "" && endDate != "" || startDate != "" && endDate == "") {
                throw new NoSuchElementException();
            }

            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_DUEDATE).orElse(""),
                    startDate,
                    endDate,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
