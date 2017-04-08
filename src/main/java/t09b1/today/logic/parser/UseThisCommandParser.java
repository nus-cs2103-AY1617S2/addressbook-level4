package t09b1.today.logic.parser;

import t09b1.today.logic.commands.Command;
import t09b1.today.logic.commands.UseThisCommand;

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
