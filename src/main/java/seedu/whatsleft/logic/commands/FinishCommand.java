package seedu.whatsleft.logic.commands;

import seedu.whatsleft.commons.core.EventsCenter;
import seedu.whatsleft.commons.core.Messages;
import seedu.whatsleft.commons.core.UnmodifiableObservableList;
import seedu.whatsleft.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.whatsleft.logic.commands.exceptions.CommandException;
import seedu.whatsleft.model.ModelManager;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.model.activity.UniqueTaskList.TaskNotFoundException;

//@@author A0121668A
/**
 * Mark a Task as complete
 */
public class FinishCommand extends Command {

    public static final String COMMAND_WORD = "finish";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": mark the task identified by index number as completed.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 5";

    public static final String MESSAGE_FINISH_TASK_SUCCESS = "Finished task: %1$s";

    private final int filteredActivityListIndex;

    public FinishCommand(int filteredActivityListIndex) {
        assert filteredActivityListIndex > 0;
        this.filteredActivityListIndex = filteredActivityListIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownTaskList = model.getFilteredTaskList();

        if (filteredActivityListIndex > lastShownTaskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        ReadOnlyTask taskToComplete = lastShownTaskList.get(filteredActivityListIndex - 1);
        try {
            ReadOnlyWhatsLeft currState = model.getWhatsLeft();
            ModelManager.setPreviousState(currState);
            model.markTaskAsComplete(taskToComplete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        if (model.getDisplayStatus() == "ALL") {
            EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(filteredActivityListIndex - 1));
        }
        model.storePreviousCommand("finish");
        return new CommandResult(String.format(MESSAGE_FINISH_TASK_SUCCESS, taskToComplete));
    }
}
