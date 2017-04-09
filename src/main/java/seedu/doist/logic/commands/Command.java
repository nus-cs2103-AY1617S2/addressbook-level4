package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.doist.commons.core.Messages;
import seedu.doist.commons.core.UnmodifiableObservableList;
import seedu.doist.logic.commands.SortCommand.SortType;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.AliasListMapModel;
import seedu.doist.model.ConfigModel;
import seedu.doist.model.Model;
import seedu.doist.model.task.ReadOnlyTask;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected AliasListMapModel aliasModel;
    protected ConfigModel configModel;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    //@@author A0140887W
    /**
     * Constructs a feedback message to summarise an operation that sorted a listing of tasks.
     *
     * @param sortType used in the command
     * @return summary message for tasks sorted
     */
    public static String getMessageForTaskListSortedSummary(List<SortType> sortTypes) {
        return String.format(Messages.MESSAGE_TASKS_SORTED_OVERVIEW, sortTypes.toString());
    }

    //@@author
    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    //@@author A0140887W
    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, AliasListMapModel aliasModel, ConfigModel configModel) {
        this.model = model;
        this.aliasModel = aliasModel;
        this.configModel = configModel;
    }

    //@@author A0147980U
    public ArrayList<ReadOnlyTask> getMultipleTasksFromIndices(int[] targetIndices) throws CommandException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        ArrayList<ReadOnlyTask> relatedTasks = new ArrayList<ReadOnlyTask>();

        for (int targetIndex : targetIndices) {
            if (lastShownList.size() < targetIndex) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            ReadOnlyTask relatedTask = lastShownList.get(targetIndex - 1);
            relatedTasks.add(relatedTask);
        }
        return relatedTasks;
    }
}
