package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPLETION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args, int idx) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_START, PREFIX_DEADLINE, PREFIX_PRIORITY,
                PREFIX_TAG, PREFIX_NOTES, PREFIX_COMPLETION);
        argsTokenizer.tokenize(args);

        String name = argsTokenizer.getPreamble().get();
        String start = argsTokenizer.getValue(PREFIX_START).orElse("-");
        String deadline = argsTokenizer.getValue(PREFIX_DEADLINE).orElse("-");
        int priority = Integer.parseInt(argsTokenizer.getValue(PREFIX_PRIORITY).orElse("0"));
        String notes = argsTokenizer.getValue(PREFIX_NOTES).orElse("-");
        String completion = argsTokenizer.getValue(PREFIX_COMPLETION).orElse("false");

        try {
            return new AddCommand(name, start, deadline, priority,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG)), notes, completion, idx);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
