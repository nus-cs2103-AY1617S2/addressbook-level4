package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.commands.SaveCommand;
import seedu.taskboss.logic.commands.exceptions.CommandException;

//@@author A0138961W
/**
 * Parses input arguments and creates a new SaveCommand object
 */

public class SaveCommandParser {

    /**
     * Parses the given arguments in the context of the SaveCommand
     * and returns an SaveCommand object for execution.
     * @throws CommandException
     */
    public Command parse(String args) throws CommandException {

        if (args.trim().isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        if (checkInvalidSymbols(args)) {
            File file = new File(args.trim());

            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }

            return new SaveCommand(args.trim());
        } else {
            throw new CommandException(SaveCommand.MESSAGE_INVALID_FILEPATH);
        }


    }

    //@@author A0144904H
    /**
     * @param file path
     * @return true if non of the invalid symbols exists
     */
    private boolean checkInvalidSymbols(String args) {
        return !args.trim().contains(SaveCommand.SYMBOL_ASTERISK)
               && !args.trim().contains(SaveCommand.SYMBOL_BAR) && !args.trim().contains(SaveCommand.SYMBOL_LEFT)
               && !args.trim().contains(SaveCommand.SYMBOL_QUESTION) && !args.trim().contains(SaveCommand.SYMBOL_RIGHT);
    }

}
