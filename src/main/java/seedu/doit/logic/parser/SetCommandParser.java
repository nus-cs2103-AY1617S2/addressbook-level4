package seedu.doit.logic.parser;

import static seedu.doit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.doit.logic.commands.Command;
import seedu.doit.logic.commands.IncorrectCommand;
import seedu.doit.logic.commands.SetCommand;

//@@author A0138909R
public class SetCommandParser {
    private static final int MAX_STRING_WORDS = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * SetCommand and returns an SetCommand object for execution.
     */
    public Command parse(String args) {
        String[] split = args.trim().split("\\s+");

        if (split.length != MAX_STRING_WORDS) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        return new SetCommand(split[0].toLowerCase(), split[1].toLowerCase());
    }
}
