package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Selects a task identified using its last displayed index from the task manager.
 */
public class SelectCommand extends Command {

    public final int targetIndex;
    public final String targetList;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "The name of the list can also be specified.\n"
            + "Parameters: [LIST_NAME] INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " floating 1" + " or " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s %2$s";

    public SelectCommand(String targetList, int targetIndex) {
        this.targetIndex = targetIndex - 1;
        this.targetList = targetList;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = getTargetTaskList(targetList);

        validateTargetIndex(targetIndex, lastShownList);

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetList, targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetList, targetIndex + 1));

    }

}
