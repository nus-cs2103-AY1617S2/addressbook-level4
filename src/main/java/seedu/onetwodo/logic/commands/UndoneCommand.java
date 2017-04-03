package seedu.onetwodo.logic.commands;

import javafx.collections.transformation.FilteredList;
import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.TaskType;
import seedu.onetwodo.model.task.UniqueTaskList.TaskNotFoundException;

public class UndoneCommand extends Command {

    public static final String COMMAND_WORD = "undone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number as uncompleted.\n"
            + "Parameters: PREFIX_INDEX (must be a prefix positive integer)\n"
            + "Example: " + COMMAND_WORD + " e1";

    public static final String MESSAGE_UNDONE_TASK_SUCCESS = "Uncomplete %1$s.";

    public final TaskType taskType;
    public final int targetIndex;

    public UndoneCommand(char taskType, int targetIndex) {
        this.taskType = TaskType.getTaskTypeFromChar(taskType);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        FilteredList<ReadOnlyTask> lastShownList = model.getFilteredByDoneFindType(taskType);

        if (lastShownList.size() < targetIndex || taskType == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask taskToUncomplete = lastShownList.get(targetIndex - 1);

        try {
            model.undoneTask(taskToUncomplete);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_UNDONE_TASK_SUCCESS, taskToUncomplete));
    }

}
