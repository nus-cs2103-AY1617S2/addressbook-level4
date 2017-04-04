//@@author A0139961U
package seedu.tache.logic.parser;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_FILE;

import java.io.File;

import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.IncorrectCommand;
import seedu.tache.logic.commands.LoadCommand;

/**
 * Parses input arguments and creates a new LoadCommand object
 */
public class LoadCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the LoadCommand
     * and returns an LoadCommand object for execution.
     */
    public Command parse(String args) {
        File f = new File(args.trim());
        if (f.exists()) {
            return new LoadCommand(args.trim());
        } else {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_FILE, LoadCommand.MESSAGE_USAGE));
        }
    }

}
