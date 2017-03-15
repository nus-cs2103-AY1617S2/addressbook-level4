package seedu.tasklist.logic.parser;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.tasklist.logic.commands.Command;
import seedu.tasklist.logic.commands.IncorrectCommand;
import seedu.tasklist.logic.commands.SaveCommand;


public class SaveCommandParser {

    public static final int CORRECT_LENGTH = 1;
    public static final int FILE_PATH = 0;


    /**
     * Parses the given {@code String} of arguments in the context of the SaveCommand
     * and returns an SaveCommand object for execution.
     */
    public Command parse(String args) {

        final String[] path = args.trim().split("\\s+");

        if (path.length > CORRECT_LENGTH || path[FILE_PATH].isEmpty()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }
        return new SaveCommand(path[FILE_PATH]);
    }
}
