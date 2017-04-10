package seedu.onetwodo.logic.parser;

import static seedu.onetwodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.onetwodo.logic.commands.Command;
import seedu.onetwodo.logic.commands.HelpCommand;
import seedu.onetwodo.logic.commands.IncorrectCommand;

//@@author A0141138N
/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class HelpCommandParser {

    public static final String DEFAULT = "";
    public static final String SHORT_USERGUIDE = "ug";
    public static final String LONG_USERGUIDE = "userguide";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * HelpCommand and returns an HelpCommand object for execution.
     */
    public Command parse(String args) {
        String argsTrimmed = args.trim();
        switch (argsTrimmed.toLowerCase()) {
        case DEFAULT:
            return new HelpCommand(DEFAULT);
        case SHORT_USERGUIDE:
            return new HelpCommand(SHORT_USERGUIDE);

        case LONG_USERGUIDE:
            return new HelpCommand(LONG_USERGUIDE);
        default:
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }
}
