package seedu.todolist.logic.parser;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.IncorrectCommand;
import seedu.todolist.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ListCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {
        String taskType = args.trim().toLowerCase();

        if (!validTaskType(taskType)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        return new ListCommand(taskType);
    }

    public boolean validTaskType(String taskType) {

        switch(taskType) {

        case "":
            return true;

        case "complete":
            return true;

        case "incomplete":
            return true;

        case "overdue":
            return true;

        default:
            return false;
        }
    }
}
