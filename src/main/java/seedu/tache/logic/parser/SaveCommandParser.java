//@@author A0139961U
package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_DIRECTORY;

import java.io.File;

import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.IncorrectCommand;
import seedu.tache.logic.commands.SaveCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class SaveCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
        File f = new File(args.trim());
        if (f.exists() && f.isDirectory()) {
            return new SaveCommand(args.trim());
        } else {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_DIRECTORY, SaveCommand.MESSAGE_USAGE));
        }
    }

}
