package seedu.task.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
<<<<<<< HEAD:src/main/java/seedu/address/logic/Logic.java
import seedu.address.model.person.ReadOnlyTask;
=======
import seedu.task.model.task.ReadOnlyTask;
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/task/logic/Logic.java

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

<<<<<<< HEAD:src/main/java/seedu/address/logic/Logic.java
    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredPersonList();
=======
    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
>>>>>>> 02d6a24595d83597768726a029d5b6a7a4e01285:src/main/java/seedu/task/logic/Logic.java

}
