package seedu.todolist.logic.parser;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        try {
            AddCommand resultCommand = new AddCommand(args);
            return resultCommand;
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }
}
