package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.logic.parser.CliSyntax.PREFIX_ANY_INFO;
import static seedu.task.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.task.logic.parser.CliSyntax.PREFIX_PRIORITY_LEVEL;
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
                new ArgumentTokenizer(PREFIX_DEADLINE, PREFIX_PRIORITY_LEVEL, PREFIX_ANY_INFO, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        //@@author A0139161J
        try {
            String deadline = new String("");
            String priority = new String("");
            String info = new String("");
            if (argsTokenizer.getValue(PREFIX_DEADLINE).isPresent()) {
                deadline = argsTokenizer.getValue(PREFIX_DEADLINE).get();
            }
            if (argsTokenizer.getValue(PREFIX_PRIORITY_LEVEL).isPresent()) {
                priority = argsTokenizer.getValue(PREFIX_PRIORITY_LEVEL).get();
            }
            if (argsTokenizer.getValue(PREFIX_ANY_INFO).isPresent()) {
                info = argsTokenizer.getValue(PREFIX_ANY_INFO).get();
            }
            //@@author
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    deadline,
                    priority,
                    info,
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
