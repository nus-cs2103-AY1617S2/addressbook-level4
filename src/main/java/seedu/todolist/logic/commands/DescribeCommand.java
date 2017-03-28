package seedu.todolist.logic.commands;

import java.util.List;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.parser.TaskParser;

//@@author A0141647E
/*
 * Add/modify the description of a task in the to-do list.
 * @@author A0141647E
 */
public class DescribeCommand extends Command {
    public static final String COMMAND_WORD = "describe";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add/Modify the description of an existing task "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) [DESCRIPTION]"
            + "Example: " + COMMAND_WORD + " 1 This is a very important task!";

    public static final String MESSAGE_DESCRIBE_TASK_SUCCESS = "Added description to task: %1$s";
    public static final String MESSAGE_NOT_DESCRIBED = "At least one field to edit must be provided.";

    private final int filteredTaskListIndex;
    private final String description;
    private String commandResultText;

    public DescribeCommand(int filteredTaskListIndex, String description) {
        assert filteredTaskListIndex > 0;
        assert description != null;

        this.filteredTaskListIndex = filteredTaskListIndex - 1;
        this.description = description;
    }

    public CommandResult execute() throws CommandException {
        List<Task> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToDescribe = lastShownList.get(filteredTaskListIndex);
        Task editedTask = TaskParser.parseTask(taskToDescribe);
        editedTask.setDescription(description);

        model.describeTask(filteredTaskListIndex, editedTask);
        model.updateFilteredListToShowAll();

        commandResultText = String.format(MESSAGE_DESCRIBE_TASK_SUCCESS, editedTask);
        return new CommandResult(commandResultText);
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public String getCommandText() {
        return commandResultText;
    }
}
