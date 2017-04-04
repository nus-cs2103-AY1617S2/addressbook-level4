package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.UseThisCommand;

/**
 * Parses input arguments and creates a new UseThisCommand object
 */
public class UseThisCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * UseThisCommand and returns an UseThisCommand object for execution.
     */
    public Command parse(String args) {
        String dirLocation = args.trim();
        return new UseThisCommand(dirLocation);
    }
}
