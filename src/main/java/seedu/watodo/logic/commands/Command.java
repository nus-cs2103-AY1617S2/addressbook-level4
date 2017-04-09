package seedu.watodo.logic.commands;

import seedu.watodo.commons.core.EventsCenter;
import seedu.watodo.commons.core.Messages;
import seedu.watodo.commons.core.UnmodifiableObservableList;
import seedu.watodo.commons.events.ui.JumpToListRequestEvent;
import seedu.watodo.logic.commands.exceptions.CommandException;
import seedu.watodo.model.Model;
import seedu.watodo.model.task.ReadOnlyTask;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

    //@@author A01398845R
    /**
     * Returns Command Name e.g. add
     */
    @Override
    public abstract String toString();

    public void unexecute() {

    }

    public void redo() {

    }

    //@@author A0139845R-reused
    /**
     * Selects the last task of the task list
     */
    protected void selectLastTask() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        int targetIndex = lastShownList.size();
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
    }

    /**
     * Selects the task at index (base 0)
     * @param taskIndex
     */
    protected void selectTaskAtIndex(int taskIndex) {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        assert lastShownList.size() > taskIndex;
        EventsCenter.getInstance().post(new JumpToListRequestEvent(taskIndex - 1));
    }
    //@@author
}
