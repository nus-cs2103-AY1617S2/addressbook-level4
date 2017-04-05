//@@author A0163559U
package seedu.task.logic.parser;


import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.SaveCommand;

/**
 * Parses input arguments and creates a new SaveCommand object
 */
public class SaveCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveCommand
     * and returns a SaveCommand object for execution.
     */
    public Command parse(String args) {
        if (args == null || args.trim().equals("")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }
        try {
            System.out.println("@@@Parsing save command: " + args);
            return new SaveCommand(args);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
