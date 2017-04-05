package seedu.taskmanager.logic.parser;

import static seedu.taskmanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.taskmanager.logic.commands.Command;
import seedu.taskmanager.logic.commands.IncorrectCommand;
import seedu.taskmanager.logic.commands.RecurringCommand;

//@@author A0141102H
/**
 * Parses input arguments and creates a new RecurringCommand object
 */
public class RecurringCommandParser {

    private int taskListIndex;
    private int numberOfRecurrence;
    private String typeOfRecurrence;

    public Command parse(String args) {
        assert args != null;

        if (args.trim().isEmpty()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurringCommand.MESSAGE_USAGE));
        }

        if (args.trim().matches(RecurringCommand.RECURRING_TASK_VALIDATION_REGEX)) {

            String[] splited = args.trim().split("\\s+");

            taskListIndex = Integer.parseInt(splited[0]);
            numberOfRecurrence = Integer.parseInt(splited[1]);
            typeOfRecurrence = splited[2];

        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecurringCommand.MESSAGE_USAGE));
        }

        return new RecurringCommand(taskListIndex, numberOfRecurrence, typeOfRecurrence);
    }
}
