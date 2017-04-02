//@@author A0139217E
package seedu.task.logic.commands;

import java.util.List;

import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;


/**
 * Marks an existing task in the task manager as complete.
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";
    public static final String COMMAND_WORD_FIRST_ALTERNATIVE = "finish";
    public static final String COMMAND_WORD_SECOND_ALTERNATIVE = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task as complete "
            + "by the index number used in the last task listing. "
            + "Existing priority level will be changed to completed"
            + "Parameters: [LIST_NAME] INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " floating 1";

    public static final String MESSAGE_MARK_COMPLETE_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final int filteredTaskListIndex;
    private final String targetList;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to mark complete
     */
    public CompleteCommand(String targetList, int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
        this.targetList = targetList;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = getTargetTaskList(targetList);

        validateTargetIndex(filteredTaskListIndex, lastShownList);

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = new Task(taskToEdit);
        editedTask.setAsCompleted();
        try {
            model.updateTask(targetList, filteredTaskListIndex, editedTask);
            highlight(editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        return new CommandResult(String.format(MESSAGE_MARK_COMPLETE_TASK_SUCCESS, editedTask));
    }
}
