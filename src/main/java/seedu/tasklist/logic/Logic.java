package seedu.tasklist.logic;

import javafx.collections.ObservableList;
import seedu.tasklist.logic.commands.CommandResult;
import seedu.tasklist.logic.commands.exceptions.CommandException;
import seedu.tasklist.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     */
    CommandResult execute(String commandText) throws CommandException;

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    //@@author A0143355J
    /* Returns the today's filtered list of tasks */
    ObservableList<ReadOnlyTask> getTodayTaskList();

    //@@author A0143355J
    /* Returns tomorrow's filtered list of of tasks */
    ObservableList<ReadOnlyTask> getTomorrowTaskList();

}
