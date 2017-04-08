package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.logic.commands.SortCommand;

//@@author A0163845X
public class SortCommandParser {
    public static final String byTime = "time";
    public static final String byName = "name";
    public static final String byCreationDate = "cd";
    public static final String byStatus = "status";

    public Command parse(String arguments) {
        arguments = arguments.toLowerCase();
        arguments = arguments.trim();
        switch (arguments) {
        case byTime:
            return new SortCommand(arguments);
        case byName:
            return new SortCommand(arguments);
        case byCreationDate:
            return new SortCommand(arguments);
        case byStatus:
            return new SortCommand(arguments);
        default:
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

}
