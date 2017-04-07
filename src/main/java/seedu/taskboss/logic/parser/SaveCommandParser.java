package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.commands.SaveCommand;

//@@author A0138961W
/**
 * Parses input arguments and creates a new SaveCommand object
 */

public class SaveCommandParser {

    /**
     * Parses the given arguments in the context of the SaveCommand
     * and returns an SaveCommand object for execution.
     */
    public Command parse(String args) {

        if (args.trim().isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        File file = new File(args.trim());

        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }

        return new SaveCommand(args.trim());
    }

}
