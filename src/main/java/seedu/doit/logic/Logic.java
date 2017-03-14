package seedu.doit.logic;

import javafx.collections.ObservableList;
import seedu.doit.logic.commands.CommandResult;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.item.ReadOnlyEvent;
import seedu.doit.model.item.ReadOnlyFloatingTask;
import seedu.doit.model.item.ReadOnlyTask;
/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     */
    CommandResult execute(String commandText) throws CommandException;

    /**
     * Returns the filtered list of tasks
     */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
    ObservableList<ReadOnlyEvent> getFilteredEventList();
    ObservableList<ReadOnlyFloatingTask> getFilteredFloatingTaskList();

}
