package seedu.tache.logic;

import javafx.collections.ObservableList;
import seedu.tache.logic.commands.CommandResult;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.task.ReadOnlyDetailedTask;
import seedu.tache.model.task.ReadOnlyTask;

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

    /** Returns the filtered list of detailed tasks */
    ObservableList<ReadOnlyDetailedTask> getFilteredDetailedTaskList();

}
