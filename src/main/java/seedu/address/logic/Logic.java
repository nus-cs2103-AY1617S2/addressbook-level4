package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText
     *            The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException
     *             If an error occurs during command execution.
     */
    CommandResult execute(String commandText) throws CommandException;

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    /**
     * Divides task lists by categories into three separate ObservableList which
     * will be provided by UI
     */
    void prepareTaskList(ObservableList<ReadOnlyTask> taskListToday, ObservableList<ReadOnlyTask> taskListFuture,
            ObservableList<ReadOnlyTask> taskListCompleted);

    /*
     * translates task index on ui to internal integer index
     */
    int parseUIIndex(String uiIndex);

    /*
     * checks if a given ui index is present in model
     */
    boolean isValidUIIndex(String uiIndex);

    /*
     * Gets UI index by absolute index
     */
    public String getUIIndex(int index);
}
