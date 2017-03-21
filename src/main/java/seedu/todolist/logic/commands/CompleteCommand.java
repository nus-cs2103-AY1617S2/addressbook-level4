package seedu.todolist.logic.commands;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Mark a Task as completed using its last displayed index from the ToDoList
 */
public class CompleteCommand extends Command {
    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Mark as completed the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed Task: %1$s";

    public static final String MESSAGE_INDEX_CONSTRAINTS = "Index number of the task must be at least 1.";

    public final int targetIndex;

    private String commandText;

    public CompleteCommand (int targetIndex) throws IllegalValueException {
        if (targetIndex < 1) {
            throw new IllegalValueException(MESSAGE_INDEX_CONSTRAINTS);
        }
        this.targetIndex = targetIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToComplete = lastShownList.get(targetIndex);

        try {
            model.completeTask(targetIndex, taskToComplete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        commandText = String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete);
        return new CommandResult(commandText);
    }

    @Override
    public boolean isMutating() {
        return true;
    }

    @Override
    public String getCommandText() {
        return commandText;
    }
}
