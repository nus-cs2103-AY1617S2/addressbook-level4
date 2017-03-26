package seedu.doist.logic;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.doist.logic.commands.CommandResult;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.task.ReadOnlyTask;

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

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredPersonList();

    /**
     * This method is used for auto completion in UI component
     * It is defined in Logic component rather than in Model component to
     * avoid introducing the association from UI component to Model component
     * @return all the command words, including the default command words and aliases specified by the user
     */
    List<String> getAllCommandWords();
}
