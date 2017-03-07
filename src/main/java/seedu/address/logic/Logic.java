package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
<<<<<<< HEAD
import seedu.address.model.task.ReadOnlyTask;
=======
import seedu.address.model.person.ReadOnlyTask;
>>>>>>> parent of 9b5fb6b... test

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

<<<<<<< HEAD
    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
=======
    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredPersonList();
>>>>>>> parent of 9b5fb6b... test

}
