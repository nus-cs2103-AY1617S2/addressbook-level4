package seedu.jobs.logic.parser;

import static seedu.jobs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.jobs.logic.commands.Command;
import seedu.jobs.logic.commands.IncorrectCommand;
import seedu.jobs.logic.commands.PathCommand;

public class PathCommandParser extends Parser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */

    public Command parse(String args) {

        Optional<String> path =  Optional.of(args);
        if (!path.isPresent() || path.get().equals("")) {
            return new IncorrectCommand (
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PathCommand.MESSAGE_USAGE));
        }

        return new PathCommand(path.get());
    }

}
