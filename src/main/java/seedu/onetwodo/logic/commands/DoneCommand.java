package seedu.onetwodo.logic.commands;

import javafx.collections.transformation.FilteredList;
import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.TaskType;

//@@author A0135739W
/**
 * Marks the task identified by the index number as completed.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number as completed.\n"
            + "Parameters: PREFIX_INDEX (must be a prefix positive integer)\n"
            + "Example: " + COMMAND_WORD + " e1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Completed Task: %1$s";

    public final TaskType taskType;
    public final int targetIndex;

    public DoneCommand(char taskType, int targetIndex) {
        this.taskType = TaskType.getTaskTypeFromChar(taskType);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        FilteredList<ReadOnlyTask> lastShownList = model.getFilteredByDoneFindType(taskType);

        if (lastShownList.size() < targetIndex || taskType == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask taskToComplete = lastShownList.get(targetIndex - 1);

        try {
            if(!taskToComplete.hasRecur()) {
                model.doneTask(taskToComplete);
            } else {
                Task newTask = new Task(taskToComplete);
                newTask.forwardTaskRecurDate();
                model.doneTask(taskToComplete);
                model.addTask(newTask);
            }
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToComplete));
    }
}
