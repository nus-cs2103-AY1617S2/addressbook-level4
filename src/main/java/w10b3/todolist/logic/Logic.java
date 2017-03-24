package w10b3.todolist.logic;

import javafx.collections.ObservableList;
import w10b3.todolist.logic.commands.CommandResult;
import w10b3.todolist.logic.commands.exceptions.CommandException;
import w10b3.todolist.model.task.ReadOnlyTask;

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
