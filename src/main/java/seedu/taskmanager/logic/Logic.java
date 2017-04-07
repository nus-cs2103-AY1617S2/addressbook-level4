package seedu.taskmanager.logic;

import javafx.collections.ObservableList;
import seedu.taskmanager.logic.commands.CommandResult;
import seedu.taskmanager.logic.commands.exceptions.CommandException;
import seedu.taskmanager.model.Model;
import seedu.taskmanager.model.task.ReadOnlyTask;
import seedu.taskmanager.storage.Storage;

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

    // @@author A0131278H
    /** Returns the filtered list of incomplete tasks */
    ObservableList<ReadOnlyTask> getFilteredToDoTaskList();

    /** Returns the filtered list of completed tasks */
    ObservableList<ReadOnlyTask> getFilteredDoneTaskList();

    /** Sets the current tab selected */
    void setSelectedTab(String text);
    // @@author

    // @@author A0140032E
    void init(Model model, Storage storage);

    String getCommandText();
    // @@author

}
