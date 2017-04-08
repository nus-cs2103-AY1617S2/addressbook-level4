package seedu.today.logic.parser;

import seedu.today.logic.commands.Command;
import seedu.today.logic.commands.SaveToCommand;

/**
 * Parses input arguments and creates a new SaveToCommand object
 */
public class SaveToCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * SaveToCommand and returns an SaveToCommand object for execution.
     */
    public Command parse(String args) {
        String dirLocation = args.trim();
        return new SaveToCommand(dirLocation);
    }
}
