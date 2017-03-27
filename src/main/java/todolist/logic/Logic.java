package todolist.logic;

import javafx.collections.ObservableList;
import todolist.logic.commands.CommandResult;
import todolist.logic.commands.exceptions.CommandException;
import todolist.model.task.ReadOnlyTask;

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
    // @@ A0143648Y
    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    ObservableList<ReadOnlyTask> getFilteredEventList();

    ObservableList<ReadOnlyTask> getFilteredFloatList();

}
