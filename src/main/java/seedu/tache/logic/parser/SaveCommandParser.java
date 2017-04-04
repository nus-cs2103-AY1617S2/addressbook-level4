//@@author A0139961U
package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_DIRECTORY;

import java.io.File;

import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.IncorrectCommand;
import seedu.tache.logic.commands.SaveCommand;

/**
 * Parses input arguments and creates a new SaveCommand object
 */
public class SaveCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveCommand
     * and returns an SaveCommand object for execution.
     */
    public Command parse(String args) {
        File directory = new File(args.trim());
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (directory.isDirectory()) {
            return new SaveCommand(args.trim());
        } else {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_DIRECTORY, SaveCommand.MESSAGE_USAGE));
        }
    }

}
