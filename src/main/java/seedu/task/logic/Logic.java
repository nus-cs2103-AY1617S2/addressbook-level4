package seedu.task.logic;

import javafx.collections.ObservableList;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws IllegalValueException
     */
    CommandResult execute(String commandText) throws CommandException, IllegalValueException;

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    //@@author A0139161J
    /** Returns the filtered list of completed tasks */
    ObservableList<ReadOnlyTask> getCompletedTaskList();

    /** Returns the filtered list of overdue tasks */
    ObservableList<ReadOnlyTask> getOverdueList();
}
