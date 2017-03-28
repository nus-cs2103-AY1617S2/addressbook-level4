package seedu.todolist.logic.parser;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.todolist.commons.util.FileUtil;
import seedu.todolist.logic.commands.ChangeStoragePathCommand;
import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.IncorrectCommand;

//@@author A0139633B
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ChangeStoragePathCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
        String path = FileUtil.getPath(args.trim().toLowerCase());

        if (path.equals("")) { //need to think of more things to check for
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeStoragePathCommand.MESSAGE_USAGE));
        }

        return new ChangeStoragePathCommand(path);
    }
}
