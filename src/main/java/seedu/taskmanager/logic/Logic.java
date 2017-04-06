package seedu.taskmanager.logic;

import javafx.collections.ObservableList;
import seedu.taskmanager.logic.commands.CommandResult;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.task.ReadOnlyTask;

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

    /** Creates initial UI view when Task Manager starts up
     * Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getInitialFilteredTaskList();
}
