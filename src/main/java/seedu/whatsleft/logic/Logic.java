package seedu.whatsleft.logic;

import javafx.collections.ObservableList;
import seedu.whatsleft.logic.commands.CommandResult;
import seedu.whatsleft.logic.commands.exceptions.CommandException;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;

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

    /** Returns the filtered list of events */
    ObservableList<ReadOnlyEvent> getFilteredEventList();

    /** Returns the filtered list of floating tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

}
