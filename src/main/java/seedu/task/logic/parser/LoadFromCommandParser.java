package seedu.task.logic.parser;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.LoadFromCommand;

//@@author A0146789H

public class LoadFromCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     */
    public Command parse(String args) {
        return new LoadFromCommand();
    }
}
