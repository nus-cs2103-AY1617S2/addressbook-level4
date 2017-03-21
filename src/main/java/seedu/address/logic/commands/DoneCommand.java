package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Indicates an existing task is done in the task manager.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Indicates that the task identified is done"
            + "by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer) \n" + "Example: "
            + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Task Done: %1$s";

    private final int filteredTaskListIndex;

    /**
     * @param filteredTaskListIndex
     *            the index of the task in the filtered task list to edit
     */
    public DoneCommand(int filteredTaskListIndex) {
        assert filteredTaskListIndex > 0;
        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(
                    Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDone = lastShownList.get(filteredTaskListIndex);
        Task doneTask = createDoneTask(taskToDone);

        try {
            model.updateTask(filteredTaskListIndex, doneTask);
        } catch (UniqueTaskList.DuplicateTaskException dpe) {
            throw new CommandException(EditCommand.MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(
                String.format(MESSAGE_DONE_TASK_SUCCESS, doneTask));
    }

    /**
     * Creates and returns a {@code Task} that is done
     */
    private static Task createDoneTask(ReadOnlyTask taskToDone) {
        assert taskToDone != null;

        Name updatedName = taskToDone.getName();
        UniqueTagList updatedTags = taskToDone.getTags();
        boolean updatedDone = true;
        // TODO: Change Task constructor to TaskWithoutDeadline() or
        // TaskWithDeadline() based on task type
        return new Task(updatedName, updatedTags, updatedDone);
    }
}
