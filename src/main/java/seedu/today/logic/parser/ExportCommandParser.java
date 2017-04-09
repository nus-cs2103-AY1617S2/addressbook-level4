package seedu.today.logic.parser;

import seedu.today.logic.commands.Command;
import seedu.today.logic.commands.ExportCommand;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ExportCommand and returns an ExportCommand object for execution.
     */
    public Command parse(String args) {
        String dirLocation = args.trim();
        return new ExportCommand(dirLocation);
    }
}
