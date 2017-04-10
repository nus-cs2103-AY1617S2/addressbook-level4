// @@author A0139399J
package seedu.doit.logic.parser;

import seedu.doit.logic.commands.Command;

public interface CommandParser {

    /**
     * Parses the given {@code String} of arguments
     * and returns a Command object for execution.
     */
    Command parse(String args);

}
