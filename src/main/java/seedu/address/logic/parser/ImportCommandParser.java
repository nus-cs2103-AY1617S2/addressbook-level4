package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ImportCommand;

/**
 * Parses input arguments and creates a new UseThisCommand object
 */
public class ImportCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ImportCommand and returns an ImportCommand object for execution.
     */
    public Command parse(String args) {
        String dirLocation = args.trim();
        return new ImportCommand(dirLocation);
    }
}
