package t09b1.today.logic.parser;

import t09b1.today.logic.commands.Command;
import t09b1.today.logic.commands.ExportCommand;

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
