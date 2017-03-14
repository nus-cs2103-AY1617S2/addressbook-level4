package seedu.todolist.logic.parser;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_END;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_START;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.logic.commands.AddCommand;
import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.IncorrectCommand;

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
                new ArgumentTokenizer(PREFIX_START, PREFIX_END, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    (argsTokenizer.getValue(PREFIX_START).isPresent() ?
                            argsTokenizer.getValue(PREFIX_START).get()
                                :null),
                    (argsTokenizer.getValue(PREFIX_END).isPresent() ?
                            argsTokenizer.getValue(PREFIX_END).get()
                            :null),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
