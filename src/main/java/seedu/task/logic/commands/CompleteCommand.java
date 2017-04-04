//@@author A0139161J
package seedu.task.logic.commands;

import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.task.model.task.UniqueTaskList.TaskNotFoundException;
public class CompleteCommand extends Command {
    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_SUCCESS = "Task completed: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task as completed, identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final int indexToComplete;

    public CompleteCommand(int targetIndex) {
        indexToComplete = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;

        UnmodifiableObservableList<ReadOnlyTask> list = model.getFilteredTaskList();

        if (list.size() < indexToComplete) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToComplete = list.get(indexToComplete - 1);
        Task task = new Task(taskToComplete);
        task.setParserInfo("complete");
        try {
            model.completeTask(task);
            return new CommandResult(String.format(MESSAGE_SUCCESS, task));
        } catch (DuplicateTaskException | TaskNotFoundException e) {
            throw new CommandException("Unable to complete specified task");
        }
    }
}
