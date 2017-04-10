package seedu.onetwodo.logic.commands;

import javafx.collections.transformation.FilteredList;
import seedu.onetwodo.commons.core.EventsCenter;
import seedu.onetwodo.commons.core.Messages;
import seedu.onetwodo.commons.events.ui.JumpToListRequestEvent;
import seedu.onetwodo.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.onetwodo.logic.commands.exceptions.CommandException;
import seedu.onetwodo.model.task.ReadOnlyTask;
import seedu.onetwodo.model.task.TaskType;

/**
 * Selects a task identified using it's last displayed index from the todo list.
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    private TaskType taskType;

    public static final String COMMAND_WORD = "select";
    public static final String SHORT_COMMAND_WORD = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(int targetIndex, char taskType) {
        this.targetIndex = targetIndex;
        this.taskType = TaskType.getTaskTypeFromChar(taskType);
    }

    @Override
    public CommandResult execute() throws CommandException {
        FilteredList<ReadOnlyTask> lastShownList = model.getFilteredByDoneFindType(taskType);

        if (lastShownList.size() < targetIndex || taskType == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToSelect = lastShownList.get(targetIndex - 1);

        // Scroll to and open the event
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1, taskType));
        EventsCenter.getInstance().post(new TaskPanelSelectionChangedEvent(taskToSelect));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, taskType.toString() + targetIndex));

    }

}
